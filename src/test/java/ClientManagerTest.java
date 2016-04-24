import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.ClientManager;
import com.zhytnik.bank.domain.Client;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class ClientManagerTest extends ManagerTest<Client> {

    @Override
    protected IEntityManager<Client> getEntityManager() {
        return new ClientManager();
    }

    @Override
    protected Map<String, Object> getUpdatedFieldValues() {
        return singletonMap("password", "pass");
    }
}
