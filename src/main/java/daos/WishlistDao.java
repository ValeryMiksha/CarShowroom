package daos;

import entities.Comment;
import entities.Wishlist;

import java.util.List;

public interface WishlistDao {
    void add(Wishlist wishlist);
    List<Wishlist> getAll();
    Wishlist getById(int id);
    void update(Wishlist wishlist);
    void delete(Wishlist wishlist);
}
