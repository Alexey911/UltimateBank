import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Department;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class DepartmentManagerTest extends ManagerTest<Department> {

    @Override
    protected IEntityManager<Department> getEntityManager() {
        return new EntityManager<>(Department.class);
    }

    @Override
    protected Map<String, Object> getUpdatedFieldValues() {
        return singletonMap("number", 5);
    }
}
