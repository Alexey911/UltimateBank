import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Client;

public class ClientManagerTest extends ManagerTest<Client> {

    @Override
    protected IEntityManager<Client> getEntityManager() {
        return new EntityManager<>(Client.class);
    }

    @Override
    protected void updateEntity(Client c) {
        c.setPassword("pass");
    }
}
