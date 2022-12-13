package daos;

import entities.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> getAll();
    User getById(int id);
    User getByLogin(String login);
    void update(User user);
    void delete(User user);
}
