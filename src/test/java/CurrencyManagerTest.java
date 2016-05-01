import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Currency;

public class CurrencyManagerTest extends ManagerTest<Currency> {

    @Override
    protected IEntityManager<Currency> getEntityManager() {
        return new EntityManager<>(Currency.class);
    }

    @Override
    protected void updateEntity(Currency c) {
        c.setValue(10d);
    }
}
