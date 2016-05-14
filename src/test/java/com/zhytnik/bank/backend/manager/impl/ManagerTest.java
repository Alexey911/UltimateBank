package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.types.IEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public abstract class ManagerTest<T extends IEntity> {

    @Autowired
    private ManagerFactory managerFactory;

    protected IEntityManager<T> manager;

    @Before
    public void setUp() {
        manager = (IEntityManager<T>) managerFactory.getEntityManager(getEntityClass());
        manager.clear();
    }

    protected abstract Class<T> getEntityClass();

    @After
    public void clear() {
        for (Class<? extends IEntity> clazz : managerFactory.getSupportedClasses()) {
            managerFactory.getEntityManager(clazz).clear();
        }
    }

    @Test(timeout = 2000L)
    public void shouldSave() {
        final T entity = instantiate();
        final int countBefore = manager.getCount();
        final int id = manager.save(entity);

        assertThat(manager.getCount()).isEqualTo(countBefore + 1);
        final T persistEntity = manager.load(id);
        assertThat(persistEntity).isEqualToComparingFieldByField(entity);
    }

    @Test(timeout = 2000L)
    public void shouldLoad() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final T loadedEntity = manager.load(existEntity.getId());
        assertThat(loadedEntity).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 2000L)
    public void shouldLoadAll() {
        final T existEntity = instantiate();
        manager.save(existEntity);

        final Set<T> entities = manager.loadAll();

        assertThat(entities).hasSize(1);
        assertThat(getOnlyElement(entities)).isEqualToComparingFieldByField(existEntity);
    }

    @Test(timeout = 2000L)
    public void shouldGetCount() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);
    }

    @Test(timeout = 2000L)
    public void shouldClear() {
        manager.save(instantiate());
        assertThat(manager.getCount()).isEqualTo(1);

        manager.clear();
        assertThat(manager.getCount()).isEqualTo(0);
    }

    @Test(timeout = 2000L)
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
