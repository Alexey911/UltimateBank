import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.backend.tool.RelationUtil;
import com.zhytnik.bank.domain.Client;
import com.zhytnik.bank.domain.Department;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws SQLException {
        Department department = new Department();
        new EntityManager<>(Department.class).save(department);


        IEntityManager<Client> manager = new EntityManager<>(Client.class);
//        manager.load(1);
//        manager.loadAll();

//        manager.load(1);

        Set<Client> client = new HashSet<>();

        Client l = new Client();
        l.setId(1);
        l.setPassword("p");
        l.setAddress("a");
        l.setSurname("s");
        l.setName("n");

        Department d = new Department();
        d.setAddress("a");
        d.setNumber(8);
        d.setId(1);

        l.setDepartment(d);

        manager.save(l);

        List<IEntity> entityList = RelationUtil.getChildRelationGraph(l);

        manager.save(l);

    }
}
