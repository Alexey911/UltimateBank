import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.card.BillCard;

public class BillCardManagerTest extends ManagerTest<BillCard> {

    @Override
    protected IEntityManager<BillCard> getEntityManager() {
        return new EntityManager<>(BillCard.class);
    }

    @Override
    protected void updateEntity(BillCard c) {
        c.setCode("new code");
    }
}
