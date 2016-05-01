import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.ManagerContainer;
import com.zhytnik.bank.backend.tool.EntityRelationUtil;
import com.zhytnik.bank.backend.types.IEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.drop;
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
        drop();
    }

    @Test(timeout = 1000L)
    public void shouldSave() {
        final T entity = instantiate();
        final int countBefore = manager.getCount();
        final int id = manager.save(entity);

        assertThat(manager.getCount()).isEqualTo(countBefore + 1);
        final T persistEntity = manager.load(id);
        assertThat(persistEntity).isEqualToComparingFieldByField(entity);
    }

    @Test(timeout = 1000L)
    public void shouldLoad() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final T loadedEntity = manager.load(existEntity.getId());
        assertThat(loadedEntity).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 1000L)
    public void shouldLoadAll() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final Set<T> entities = manager.loadAll();

        assertThat(entities).hasSize(1);
        assertThat(getOnlyElement(entities)).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 1000L)
    public void shouldGetCount() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);
    }

    @Test(timeout = 1000L)
    public void shouldClear() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);

        manager.clear();
        assertThat(manager.getCount()).isEqualTo(0);
    }

    @Test(timeout = 1000L)
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
        final T entity = EntityFiller.create(manager.getEntityClass());
        initial(entity);
        return entity;
    }

    protected void initial(T entity) {
    }

    protected static <T extends IEntity> T mock(Class<T> clazz) {
        final T entity = EntityFiller.create(clazz);
        ManagerContainer.getEntityManager(clazz).save(entity);
        return entity;
    }
}
