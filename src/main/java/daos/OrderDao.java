package daos;

import entities.Comment;
import entities.Order;

import java.util.List;

public interface OrderDao {
    void add(Order order);
    List<Order> getAll();
    Order getById(int id);
    void update(Order order);
    void delete(Order order);
}
