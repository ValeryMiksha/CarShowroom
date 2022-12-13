package daos;

import entities.MessageToAdmin;

import java.util.List;

public interface MessageToAdminDao {
    void add(MessageToAdmin message);
    List<MessageToAdmin> getAll();
    MessageToAdmin getById(int id);
    void update(MessageToAdmin message);
    void delete(MessageToAdmin message);
}
