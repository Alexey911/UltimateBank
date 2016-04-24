import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Client;
import org.junit.Test;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class ClientManagerTest extends ManagerTest<Client> {

    @Override
    protected IEntityManager<Client> getEntityManager() {
        return new EntityManager<>(Client.class);
    }

    @Override
    protected Map<String, Object> getUpdatedFieldValues() {
        return singletonMap("password", "pass");
    }

    @Test
    public void test(){

    }
}
