package daos;

import entities.Comment;
import entities.User;

import java.util.List;

public interface CommentDao {
    void add(Comment comment);
    List<Comment> getAll();
    Comment getById(int id);
    void update(Comment comment);
    void delete(Comment comment);
}
