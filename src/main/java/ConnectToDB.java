import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by User on 15.02.2017.
 */
public class ConnectToDB {
    public static EntityManager createDBconnection(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("restaurant");
        return entityManagerFactory.createEntityManager();
    }
}
