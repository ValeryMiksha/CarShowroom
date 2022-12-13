package util;

import entities.*;
import service.*;

import java.util.List;

public class MainService {
    private CarService carService;
    private UserService userService;
    private CommentService commentService;
    private OrderService orderService;
    private WishlistService wishlistService;
    private MessageToAdminService messageToAdminService;


    public MainService() {
        carService=new CarService();
        userService=new UserService();
        commentService=new CommentService();
        orderService=new OrderService();
        wishlistService=new WishlistService();
        messageToAdminService=new MessageToAdminService();
    }

    public void add(User user)
    {
        userService.add(user);
    }
    public void add(Car car)
    {
        carService.add(car);
    }
    public void add(Comment comment)
    {
        commentService.add(comment);
    }
    public void add(Order order)
    {
        orderService.add(order);
    }
    public void add(Wishlist wishlist)
    {
        wishlistService.add(wishlist);
    }
    public void add(MessageToAdmin messageToAdmin){messageToAdminService.add(messageToAdmin);}

    public List<Car>getAllCars()
    {
        return carService.getAll();
    }
    public List<User>getAllUsers()
    {
        return userService.getAll();
    }
    public List<Comment>getAllComments()
    {
        return commentService.getAll();
    }
    public List<Order>getAllOrders(){return orderService.getAll();}
    public List<MessageToAdmin>getAllMessages(){return  messageToAdminService.getAll();}
    public List<Wishlist>getAllWishlists(){return wishlistService.getAll();}


    public User getUserById(int id)
    {
        return userService.getById(id);
    }
    public User getUserByLogin(String login){return userService.getByLogin(login);}
    public Car getCarById(int id)
    {
        return carService.getById(id);
    }
    public Car getAlike(Car car){return carService.getAlike(car);}


    public void update(Car car)
    {
        carService.update(car);
    }
    public void update(User user)
    {
        userService.update(user);
    }
    public void update(Comment comment)
    {
        commentService.update(comment);
    }


    public void delete(User user)
    {
        userService.delete(user);
    }
    public void delete(Car car)
    {
        carService.delete(car);
    }
    public void delete(Comment comment)
    {
        commentService.delete(comment);
    }
}
