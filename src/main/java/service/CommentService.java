package service;

import daos.CommentDao;
import entities.Comment;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class CommentService implements CommentDao {
    private SessionHandler sessionHandler;

    public CommentService() {
        this.sessionHandler = new SessionHandler();
    }

    @Override
    public void add(Comment comment) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(comment);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public List<Comment> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM comments";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Comment.class);
        List<Comment>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public Comment getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM comments WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Comment.class);
        query.setParameter("id",id);

        Comment returnProject=(Comment) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnProject;
    }

    @Override
    public void update(Comment comment) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(comment);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(Comment comment) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(comment);

        sessionHandler.closeTransactionSession();
    }
}
