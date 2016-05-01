import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Credit;

public class CreditManagerTest extends ManagerTest<Credit> {

    @Override
    protected IEntityManager<Credit> getEntityManager() {
        return new EntityManager<>(Credit.class);
    }

    @Override
    protected void updateEntity(Credit c) {
        c.setAmount(78d);
    }
}
