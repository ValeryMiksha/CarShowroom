package connection;

import com.mysql.cj.xdevapi.JsonParser;
import commands.Commands;
import entities.*;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Connection {
    private static Socket socket;

    private static ServerSocket serverSocket;

    private static DataInputStream dis;
    private static DataOutputStream dos;

    private static BufferedReader reader;
    private static PrintWriter writer;

    private final static int PORT = 1024;

    public static boolean waitForConnection() {
        try {
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void handleSocket() throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        reader = new BufferedReader(new InputStreamReader(dis));
        dos = new DataOutputStream(socket.getOutputStream());
        writer = new PrintWriter(dos);
    }

    public static String readObject() {
        String str = null;
        try {
            while (reader.ready()) {
                str = reader.readLine();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
    public static void writeObject(User user, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", user.getLogin());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("isAdmin", user.isAdmin());
        jsonObject.put("isLocked", user.isLocked());
        jsonObject.put("command", commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeObjectCar(Map<String, List<Car>> map, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,List<Car>> temp: map.entrySet())
            jsonObject.put(temp.getKey(),temp.getValue());
        jsonObject.put("command",commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeObjectUser(Map<String, List<User>> map, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,List<User>> temp: map.entrySet())
            jsonObject.put(temp.getKey(),temp.getValue());
        jsonObject.put("command",commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeObjectComment(Map<Integer, List<Comment>> map, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<Integer,List<Comment>> temp: map.entrySet())
            jsonObject.put(String.valueOf(temp.getKey()),temp.getValue());
        jsonObject.put("command",commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeObjectOrder(Map<String,String>map,Commands command)
    {
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,String> temp: map.entrySet())
            jsonObject.put(temp.getKey(),temp.getValue());
        jsonObject.put("command",command.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeObjectMessageToAdmin(Map<String, List<MessageToAdmin>>map, Commands command)
    {
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,List<MessageToAdmin>> temp: map.entrySet())
            jsonObject.put(temp.getKey(),temp.getValue());
        jsonObject.put("command",command.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }

}
