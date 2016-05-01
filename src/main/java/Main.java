import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.Department;

import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.getEntityManager;

public class Main {
    public static void main(String[] args) {

        final Currency entity = new Currency();
        entity.setId(2);
        getEntityManager(Currency.class).save(entity);
        Department d = getEntityManager(Department.class).load(1);
        System.out.println(d);
    }
}
