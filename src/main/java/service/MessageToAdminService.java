package service;

import daos.MessageToAdminDao;
import entities.MessageToAdmin;
import entities.Order;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class MessageToAdminService implements MessageToAdminDao {
    private SessionHandler sessionHandler;

    public MessageToAdminService() {
        sessionHandler=new SessionHandler();
    }

    @Override
    public void add(MessageToAdmin message) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(message);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public List<MessageToAdmin> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM messages";
        Query query= session.createSQLQuery(sqlQuery).addEntity(MessageToAdmin.class);
        List<MessageToAdmin>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public MessageToAdmin getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM messages WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(MessageToAdmin.class);
        query.setParameter("id",id);

        MessageToAdmin returnMessage=(MessageToAdmin) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnMessage;
    }

    @Override
    public void update(MessageToAdmin message) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(message);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(MessageToAdmin message) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(message);

        sessionHandler.closeTransactionSession();
    }
}
