import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Deposit;

public class DepositManagerTest extends ManagerTest<Deposit> {

    @Override
    protected IEntityManager<Deposit> getEntityManager() {
        return new EntityManager<>(Deposit.class);
    }

    @Override
    protected void updateEntity(Deposit d) {
        d.setSum(135.1d);
    }
}
