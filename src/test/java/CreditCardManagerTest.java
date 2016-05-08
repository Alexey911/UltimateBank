import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.card.CreditCard;

public class CreditCardManagerTest extends ManagerTest<CreditCard> {

    @Override
    protected IEntityManager<CreditCard> getEntityManager() {
        return new EntityManager<>(CreditCard.class);
    }

    @Override
    protected void updateEntity(CreditCard c) {
        c.setValidationCode(7778);
    }
}
