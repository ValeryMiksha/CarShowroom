package service;

import daos.UserDao;
import entities.Car;
import entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class UserService implements UserDao {
    private SessionHandler sessionHandler;

    public UserService() {
        this.sessionHandler = new SessionHandler();
    }
    @Override
    public void add(User user) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(user);

        sessionHandler.closeTransactionSession();
    }


    @Override
    public List<User> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM users";
        Query query= session.createSQLQuery(sqlQuery).addEntity(User.class);
        List<User>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public User getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM users WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(User.class);
        query.setParameter("id",id);

        User returnUser=(User) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnUser;
    }

    @Override
    public User getByLogin(String login)
    {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM users WHERE LOGIN=:login";
        Query query= session.createSQLQuery(sqlQuery).addEntity(User.class);
        query.setParameter("login",login);

        User returnUser=(User) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnUser;
    }

    @Override
    public void update(User user) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(user);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(User user) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(user);

        sessionHandler.closeTransactionSession();

    }
}
