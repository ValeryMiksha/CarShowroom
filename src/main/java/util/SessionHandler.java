package util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionHandler {
    private Session currentSession;
    private Transaction currentTransaction;

    public Session getCurrentSession() {
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public Session openSession()
    {
        return Util.getSessionFactory().openSession();
    }
    public Session openTransactionSession()
    {
        currentSession=openSession();
        currentTransaction= currentSession.beginTransaction();
        return currentSession;
    }
    public void closeSession()
    {
        currentSession.close();
    }
    public void closeTransactionSession()
    {
        currentTransaction.commit();
        closeSession();
    }
}
