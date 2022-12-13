package service;

import daos.WishlistDao;
import entities.Comment;
import entities.Wishlist;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class WishlistService implements WishlistDao {
    private SessionHandler sessionHandler;

    public WishlistService() {
        sessionHandler=new SessionHandler();
    }

    @Override
    public void add(Wishlist wishlist) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(wishlist);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public List<Wishlist> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM wishlists";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Wishlist.class);
        List<Wishlist>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public Wishlist getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM wishlists WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Wishlist.class);
        query.setParameter("id",id);

        Wishlist returnWishlist=(Wishlist) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnWishlist;
    }

    @Override
    public void update(Wishlist wishlist) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(wishlist);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(Wishlist wishlist) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(wishlist);

        sessionHandler.closeTransactionSession();
    }
}
