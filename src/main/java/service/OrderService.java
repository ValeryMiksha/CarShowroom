package service;

import daos.OrderDao;
import entities.Comment;
import entities.Order;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class OrderService implements OrderDao {
    private SessionHandler sessionHandler;

    public OrderService() {
        sessionHandler=new SessionHandler();
    }

    @Override
    public void add(Order order) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(order);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public List<Order> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM orders";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Order.class);
        List<Order>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public Order getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM orders WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Order.class);
        query.setParameter("id",id);

        Order returnOrder=(Order) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnOrder;
    }

    @Override
    public void update(Order order) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(order);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(Order order) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(order);

        sessionHandler.closeTransactionSession();

    }
}
