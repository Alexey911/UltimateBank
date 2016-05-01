import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Banker;

public class BankerManagerTest extends ManagerTest<Banker> {

    @Override
    protected IEntityManager<Banker> getEntityManager() {
        return new EntityManager<>(Banker.class);
    }

    @Override
    protected void updateEntity(Banker b) {
        b.setPrivilege(10);
    }
}
