import connection.Connection;
import entities.Car;
import entities.User;
import org.junit.jupiter.api.Test;
import util.MainService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionTest {

    @Test
    public void testSocketConnection()
    {
        boolean actual=Connection.waitForConnection();
        boolean expected=true;
        assertEquals(expected,actual);
    }

    @Test
    public void testSendingObjectsThroughSocket() throws IOException {
        Connection.waitForConnection();
        Connection.handleSocket();
        String actual=null;
        while(actual==null)
            actual=Connection.readObject();
        String expected="{\"password\":\"pass\",\"isLocked\":true,\"isAdmin\":false,\"login\":\"123\",\"email\":\"123\",\"command\":6}";
        assertEquals(expected,actual);
    }

    @Test
    public void testGettingCarFromDatabase()
    {
        MainService service=new MainService();
        Car actualCar=service.getCarById(2);
        Car expectedCar=new Car();
        expectedCar.setModel("A4");
        expectedCar.setTransmission("Mechanic");
        expectedCar.setVendor("Audi");
        expectedCar.setPrice(29476);
        expectedCar.setEngineVolume("6");
        expectedCar.setYearOfIssue("1998");

        assertEquals(expectedCar.getModel(),actualCar.getModel());
        assertEquals(expectedCar.getTransmission(),actualCar.getTransmission());
        assertEquals(expectedCar.getVendor(),actualCar.getVendor());
        assertEquals(expectedCar.getPrice(),actualCar.getPrice());
        assertEquals(expectedCar.getEngineVolume(),actualCar.getEngineVolume());
        assertEquals(expectedCar.getYearOfIssue(),actualCar.getYearOfIssue());
    }

    @Test
    public void isUserInDatabaseTest()
    {
        MainService service=new MainService();
        User user=new User();
        user.setAdmin(false);
        user.setPassword("sosi");
        user.setLogin("login");
        user.setEmail("mail");
        user.setLocked(false);

        List<User>list=service.getAllUsers();
        User actualUser=list.get(0);
        assertEquals(user.getLogin(),actualUser.getLogin());
        assertEquals(user.getPassword(),actualUser.getPassword());
        assertEquals(user.getEmail(),actualUser.getEmail());
        assertEquals(user.isAdmin(),actualUser.isAdmin());
        assertEquals(user.isLocked(),actualUser.isLocked());
    }

}
