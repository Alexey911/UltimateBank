import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class ManagerTest<T extends IEntity> {

    protected IEntityManager<T> manager;

    protected abstract IEntityManager<T> getEntityManager();

    @Before
    public void setUp() {
        manager = getEntityManager();
        manager.clear();
    }

    @After
    public void clear() {
        manager.clear();
    }

    @Test(timeout = 150L)
    public void shouldSave() {
        final T entity = instantiate();
        final int countBefore = manager.getCount();
        final int id = manager.save(entity);

        assertThat(manager.getCount()).isEqualTo(countBefore + 1);
        final T persistEntity = manager.load(id);
        assertThat(persistEntity).isEqualToComparingFieldByField(entity);
    }

    @Test(timeout = 150L)
    public void shouldLoad() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final T loadedEntity = manager.load(existEntity.getId());
        assertThat(loadedEntity).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 150L)
    public void shouldLoadAll() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final Set<T> entities = manager.loadAll();

        assertThat(entities).hasSize(1);
        assertThat(getOnlyElement(entities)).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 150L)
    public void shouldGetCount() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);
    }

    @Test(timeout = 150L)
    public void shouldClear() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);

        manager.clear();
        assertThat(manager.getCount()).isEqualTo(0);
    }

    @Test(timeout = 150L)
    public void shouldUpdate() {
        final T entity = instantiate();
        manager.save(entity);

        updateEntity(entity);

        manager.update(entity);

        final T loadedEntity = manager.load(entity.getId());
        assertThat(loadedEntity).isEqualToComparingFieldByField(entity);
    }

    protected abstract void updateEntity(T entity);

    protected T instantiate() {
        return EntityFiller.create(manager.getEntityClass());
    }
}
