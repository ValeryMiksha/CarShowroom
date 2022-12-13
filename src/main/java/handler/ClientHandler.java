package handler;

import commands.Commands;
import connection.Connection;
import entities.*;
import org.json.JSONObject;
import util.MainService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ClientHandler {
    private final int REGISTRATION_COMMAND = 0;
    private final int CHECK_USER_COMMAND = 1;
    private final int SEND_USER_INFO=2;
    private final int CHANGE_USER_PASSWORD=3;
    private final int SEND_ALL_CARS=4;
    private final int SEND_CAR_COMMENTS=5;
    private final int SEND_USER_INFO_ANOTHER=6;
    private final int ADD_COMMENT_COMMAND=7;
    private final int ADD_ORDER_COMMAND=8;
    private final int SEND_USER_WISHLIST=9;
    private final int ADD_MESSAGE_TO_ADMIN_COMMAND=10;
    private final int ADD_CAR_TO_USER_WISHLIST=11;
    private final int SEND_ALL_USERS=12;
    private final int ADD_ONE_CAR_COMMAND=13;
    private final int UPDATE_CAR_COMMAND=14;
    private final int DELETE_CAR_COMMAND=15;
    private final int CHANGE_USER_STATUS=16;
    private final int SEND_STATISTICS=17;

    private final int SEND_MESSAGES=18;

    public void handleRequests() {
        new Thread(() ->
        {
            if (!Connection.waitForConnection()) {
                System.out.println("Connecting error!");
                return;
            } else {
                try {
                    Connection.handleSocket();
                    System.out.println("Client was connected!");
                    MainService service = new MainService();
                    while (true) {
                        String info = Connection.readObject();
                        if (info != null) {
                            JSONObject obj = new JSONObject(info);
                            Map<String, Object> parseMap = obj.toMap();
                            int command = (int) parseMap.get("command");
                            switch (command) {
                                case REGISTRATION_COMMAND -> {
                                    User userToAdd = makeUserFromJson(parseMap);
                                    service.add(userToAdd);
                                    break;
                                }
                                case CHECK_USER_COMMAND -> {
                                    String loginToCheck = parseMap.get("login").toString();
                                    String passwordToCheck = String.valueOf(Objects.hashCode(parseMap.get("password").toString()));
                                    boolean flag=false;
                                    for (User temp : service.getAllUsers()) {
                                        if (temp.getLogin().equals(loginToCheck))
                                            if (temp.getPassword().equals(passwordToCheck)) {
                                                User userToSend = makeUserInfo(loginToCheck, passwordToCheck,
                                                        "", temp.isAdmin(), temp.isLocked());
                                                Connection.writeObject(userToSend, Commands.LoginAndPasswordApproved);
                                                flag=true;
                                                break;
                                            }
                                    }
                                    if(!flag) {
                                        Connection.writeObject(new User(), Commands.NoUserInDatabase);
                                        break;
                                    }
                                }
                                case SEND_USER_INFO -> {
                                    String loginToFind=parseMap.get("login").toString();
                                    User temp=service.getUserByLogin(loginToFind);
                                    Connection.writeObject(temp,Commands.UserInfoSent);
                                    break;
                                }
                                case CHANGE_USER_PASSWORD -> {
                                    User temp=service.getUserByLogin(parseMap.get("login").toString());
                                    temp.setPassword(parseMap.get("password").toString());
                                    service.update(temp);
                                }
                                case SEND_ALL_CARS -> {
                                    List<Car> cars=service.getAllCars();
                                    Map<String,List<Car>>var=cars.stream().collect(Collectors.groupingBy(Car::getModel));
                                    Connection.writeObjectCar(var,Commands.AllCarsSent);
                                }
                                case SEND_CAR_COMMENTS -> {
                                    List<Comment>comments=service.getAllComments();
                                    List<User>users=new ArrayList<>();
                                    int carIdToLookFor=Integer.parseInt((parseMap.get("login").toString()));
                                    comments=comments.stream().filter(p->p.getCarId()==carIdToLookFor).collect(Collectors.toList());
                                    for(Comment temp:comments)
                                        users.add(service.getUserById(temp.getUserId()));
                                    Map<Integer,List<Comment>>var=comments.stream().collect(Collectors.groupingBy(Comment::getCarId));
                                    Connection.writeObjectComment(var,Commands.AllCarCommentsSent);
                                }
                                case SEND_USER_INFO_ANOTHER -> {
                                    int idToFind=Integer.parseInt(parseMap.get("login").toString());
                                    User temp=service.getUserById(idToFind);
                                    Connection.writeObject(temp,Commands.UserInfoSent);

                                }
                                case ADD_COMMENT_COMMAND -> {
                                    Comment newComment=new Comment();
                                    newComment.setUserId(service.getUserByLogin(parseMap.get("login").toString()).getId());
                                    newComment.setCarId(Integer.parseInt(parseMap.get("password").toString()));
                                    newComment.setContent(parseMap.get("email").toString());

                                    service.add(newComment);
                                }
                                case ADD_ORDER_COMMAND -> {
                                    Order newOrder=new Order();
                                    newOrder.setCarId(Integer.parseInt(parseMap.get("password").toString()));
                                    newOrder.setUserId(service.getUserByLogin(parseMap.get("login").toString()).getId());

                                    service.add(newOrder);

                                }
                                case SEND_USER_WISHLIST -> {
                                    String loginToFind=parseMap.get("login").toString();
                                    int userIdForWishlist=service.getUserByLogin(loginToFind).getId();

                                    List<Wishlist>wishlists=service.getAllWishlists().stream().filter(p->p.getUserId()==userIdForWishlist).toList();
                                    if(!wishlists.isEmpty()) {
                                        List<Car> cars = new ArrayList<>();
                                        for (Wishlist temp : wishlists) {
                                            int carId = temp.getCarId();
                                            Car ccar = service.getCarById(carId);
                                            cars.add(ccar);
                                        }

                                        Map<String, List<Car>> var = cars.stream().collect(Collectors.groupingBy(Car::getModel));
                                        Connection.writeObjectCar(var, Commands.UserWishlistSent);
                                    }
                                    else {
                                        Connection.writeObjectCar(new HashMap<>(), Commands.NoUserWishlist);
                                    }
                                }
                                case ADD_MESSAGE_TO_ADMIN_COMMAND -> {
                                    String userLogin=parseMap.get("login").toString();
                                    String content=parseMap.get("password").toString();

                                    MessageToAdmin newMessage=new MessageToAdmin();
                                    newMessage.setContent(content);
                                    newMessage.setUserLogin(userLogin);

                                    service.add(newMessage);
                                }
                                case ADD_CAR_TO_USER_WISHLIST -> {
                                    int carId=Integer.parseInt(parseMap.get("login").toString());
                                    int userId=service.getUserByLogin(parseMap.get("password").toString()).getId();

                                    Wishlist newWishlist=new Wishlist();
                                    newWishlist.setUserId(userId);
                                    newWishlist.setCarId(carId);

                                    service.add(newWishlist);
                                }
                                case SEND_ALL_USERS -> {
                                    List<User> users=service.getAllUsers().stream().filter(p-> !p.isAdmin()).toList();
                                    Map<String,List<User>>var=users.stream().collect(Collectors.groupingBy(User::getLogin));
                                    Connection.writeObjectUser(var,Commands.AllUsersSent);
                                }
                                case ADD_ONE_CAR_COMMAND -> {
                                    Car carToAdd=new Car();
                                    carToAdd.setPrice(Long.parseLong(parseMap.get("price").toString()));
                                    carToAdd.setModel(parseMap.get("model").toString());
                                    carToAdd.setTransmission(parseMap.get("transmission").toString());
                                    carToAdd.setYearOfIssue(parseMap.get("yearOfIssue").toString());
                                    carToAdd.setEngineVolume(parseMap.get("engineVolume").toString());
                                    carToAdd.setVendor(parseMap.get("vendor").toString());

                                    service.add(carToAdd);
                                }
                                case UPDATE_CAR_COMMAND -> {
                                    Car carToUpdate=service.getCarById(Integer.parseInt(parseMap.get("id").toString()));
                                    carToUpdate.setPrice(Long.parseLong(parseMap.get("price").toString()));
                                    carToUpdate.setModel(parseMap.get("model").toString());
                                    carToUpdate.setTransmission(parseMap.get("transmission").toString());
                                    carToUpdate.setYearOfIssue(parseMap.get("yearOfIssue").toString());
                                    carToUpdate.setEngineVolume(parseMap.get("engineVolume").toString());
                                    carToUpdate.setVendor(parseMap.get("vendor").toString());
                                    service.update(carToUpdate);
                                }
                                case DELETE_CAR_COMMAND -> {
                                    Car carToDelete=service.getCarById(Integer.parseInt(parseMap.get("id").toString()));
                                    carToDelete.setPrice(Long.parseLong(parseMap.get("price").toString()));
                                    carToDelete.setModel(parseMap.get("model").toString());
                                    carToDelete.setTransmission(parseMap.get("transmission").toString());
                                    carToDelete.setYearOfIssue(parseMap.get("yearOfIssue").toString());
                                    carToDelete.setEngineVolume(parseMap.get("engineVolume").toString());
                                    carToDelete.setVendor(parseMap.get("vendor").toString());
                                    service.delete(carToDelete);
                                }
                                case CHANGE_USER_STATUS -> {
                                    User userToChange=service.getUserByLogin(parseMap.get("login").toString());
                                    if(userToChange.isLocked())
                                        userToChange.setLocked(false);
                                    else userToChange.setLocked(true);

                                    service.update(userToChange);
                                }
                                case SEND_STATISTICS -> {
                                    List<Order>orders=service.getAllOrders();
                                    Map<String,String>orderMap=new HashMap<>();
                                    for(Order temp:orders)
                                    {
                                        User user=service.getUserById(temp.getUserId());
                                        Car car=service.getCarById(temp.getCarId());
                                        orderMap.put(user.getLogin(),car.getVendor()+"  "+car.getModel());
                                    }

                                    Connection.writeObjectOrder(orderMap,Commands.StatisticsSent);
                                }
                                case SEND_MESSAGES -> {
                                    List<MessageToAdmin>messages=service.getAllMessages();
                                    Map<String,List<MessageToAdmin>>var=messages.stream().collect(Collectors.groupingBy(MessageToAdmin::getUserLogin));
                                    Connection.writeObjectMessageToAdmin(var,Commands.AllMessagesSent);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private User makeUserFromJson(Map<String, Object> map) {
        User user = new User();
        user.setLogin(map.get("login").toString());
        user.setPassword(map.get("password").toString());
        user.setEmail(map.get("email").toString());
        user.setAdmin((boolean) map.get("isAdmin"));
        user.setLocked((boolean) map.get("isLocked"));

        return user;
    }

    private User makeUserInfo(String login, String password, String email, boolean isAdmin, boolean isLocked) {
        User temp = new User();
        temp.setLogin(login);
        temp.setPassword(password);
        temp.setEmail(email);
        temp.setAdmin(isAdmin);
        temp.setLocked(isLocked);

        return temp;
    }

}
