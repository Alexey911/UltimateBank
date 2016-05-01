import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Found;

public class FoundManagerTest extends ManagerTest<Found> {

    @Override
    protected IEntityManager<Found> getEntityManager() {
        return new EntityManager<>(Found.class);
    }

    @Override
    protected void updateEntity(Found f) {
        f.setCode("New code");
    }
}
