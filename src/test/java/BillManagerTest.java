import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Bill;

public class BillManagerTest extends ManagerTest<Bill> {

    @Override
    protected IEntityManager<Bill> getEntityManager() {
        return new EntityManager<>(Bill.class);
    }

    @Override
    protected void updateEntity(Bill b) {
        b.setIsActive(false);
    }
}
