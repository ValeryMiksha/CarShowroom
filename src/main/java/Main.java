import entities.Car;
import entities.Comment;
import entities.User;
import handler.ClientHandler;
import service.CarService;
import service.CommentService;
import service.UserService;
import util.Util;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ClientHandler handler=new ClientHandler();
        handler.handleRequests();
    }
}
