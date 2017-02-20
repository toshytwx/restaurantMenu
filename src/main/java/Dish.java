import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by User on 15.02.2017.
 */
@Entity
@Table(name = "dish")
public class Dish implements Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id")
    private long id;

    @Column(name="dish_name")
    private String name;

    @Column(name = "dish_wight")
    private Double weight;

    @Column(name = "dish_price")
    private Double price;

    @Column(name = "dish_sale")
    private Integer sale;


    public Dish() {
    }

    public Dish(String name, Double weight, Double price, Integer sale) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.sale = sale;
    }

    public void addNewDish(Scanner scanner, EntityManager entityManager){
        System.out.println("Type dish name: ");
        String userDishName = scanner.nextLine();
        System.out.println("Type dish weight: ");
        Double userDishWeight = scanner.nextDouble();
        System.out.println("Type dish price: ");
        Double userDishPrice = scanner.nextDouble();
        System.out.println("Type dish sale: ");
        Integer userDishSale = scanner.nextInt();
        entityManager.getTransaction().begin();
        try{
            Dish userDish = new Dish(userDishName, userDishWeight, userDishPrice, userDishSale);
            entityManager.persist(userDish);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            entityManager.getTransaction().rollback();
        }
    }

    public void selectFromPriceToPrice(Scanner scanner, EntityManager entityManager){
        System.out.println("Please input parameter from:");
        Double userFromParameter = scanner.nextDouble();
        System.out.println("Please input parameter to:");
        Double userToParameter = scanner.nextDouble();
        Dish d = null;
        Query query = entityManager.createQuery("SELECT d from Dish d where d.price> :userFromParameter AND d.price< :userToParameter", Dish.class);
        query.setParameter("userFromParameter", userFromParameter);
        query.setParameter("userToParameter", userToParameter);
        List<Dish> menuList = (List<Dish>) query.getResultList();
        for (Dish iterator:menuList) {
            System.out.println(iterator.toString());
        }
    }

    public void selectOnlyWithSale(Scanner scanner, EntityManager entityManager){
        Query query = entityManager.createQuery("SELECT d from Dish d where d.sale> 0", Dish.class);
        List<Dish> menuList = (List<Dish>) query.getResultList();
        for (Dish iterator:menuList) {
            System.out.println(iterator.toString());
        }
    }

    public void specialOrder(Scanner scanner,Double maxWeight, EntityManager entityManager) {
        Double interactiveWeight = 0.0;
        boolean flag = true;
        List<Dish> wishList = new ArrayList<Dish>();
        do {
            System.out.println("Please choose some food: ");
            printAllDishes(entityManager);
            String userDishName = scanner.nextLine();
            Dish userChoiceDish = addDishToWishList(userDishName, entityManager);
            wishList.add(userChoiceDish);
            interactiveWeight +=userChoiceDish.weight;
            if(interactiveWeight <0){
                flag = false;
            }
        }while(interactiveWeight>=maxWeight && flag);
    }
    private List<Dish> getDishesList(EntityManager entityManager){
        Query query = entityManager.createQuery("select d from Dish d", Dish.class);
        return (List<Dish>) query.getResultList();
    }
    private void printAllDishes(EntityManager entityManager){
        for (Dish iterator: getDishesList(entityManager)){
            System.out.println(iterator.toString());
        }
    }
    private Dish addDishToWishList(String userDishName, EntityManager entityManager){
        Query query = entityManager.createQuery("SELECT d from Dish d where d.name= :userDishName");
        query.setParameter("userDishName", userDishName);
        return (Dish) query.getSingleResult();
    }

    @Override
    public String toString() {
        return "\n"+"Dish" +"\n"+ "name= " + name + "\n"+ "weight= " + weight +"\n"+ "price= " + price +"\n"+ "sale= " + sale +"\n";
    }
}
