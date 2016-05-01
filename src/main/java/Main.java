import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.domain.Banker;
import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.Department;
import com.zhytnik.bank.domain.Found;

import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.getEntityManager;

public class Main {
    public static void main(String[] args) {
/*        IEntityManager<Currency> m = getEntityManager(Currency.class);
        Currency c = new Currency();
        c.setName("BLR");
        c.setValue(10_000.45d);

        m.clear();*/

/*        final IEntityManager<Banker> manager = getEntityManager(Banker.class);
        Banker d = manager.load(1);
        d.setPrivilege(5);
        manager.update(d);
        System.out.println(d);
        */

        Currency c = new Currency();
        c.setName("My Currency");
        c.setValue(123.986);

        Found f = new Found();
        f.setCode("My Found");
        f.setBalance(19_123.5);
        f.setCurrency(c);

        getEntityManager(Found.class).save(f);

//        m.remove();
    }
}
