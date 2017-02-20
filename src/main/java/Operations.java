import javax.persistence.EntityManager;
import java.util.Scanner;

/**
 * Created by User on 15.02.2017.
 */
public interface Operations {
    void addNewDish(Scanner scanner, EntityManager entityManager);
    void selectFromPriceToPrice(Scanner scanner,EntityManager entityManager);
    void selectOnlyWithSale(Scanner scanner, EntityManager entityManager);
    void specialOrder(Scanner scanner,Double maxWeight, EntityManager entityManager);
}
