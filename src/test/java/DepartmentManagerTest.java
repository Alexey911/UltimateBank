import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Department;

public class DepartmentManagerTest extends ManagerTest<Department> {

    @Override
    protected IEntityManager<Department> getEntityManager() {
        return new EntityManager<>(Department.class);
    }

    @Override
    protected void updateEntity(Department d) {
        d.setNumber(5);
    }
}
