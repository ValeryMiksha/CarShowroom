package service;


import daos.CarDao;
import entities.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionHandler;

import java.util.List;

public class CarService implements CarDao {
    private SessionHandler sessionHandler;

    public CarService() {
        this.sessionHandler = new SessionHandler();
    }

    @Override
    public void add(Car car) {
        sessionHandler.openTransactionSession();

        Session session=sessionHandler.getCurrentSession();
        session.save(car);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public List<Car> getAll() {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM cars";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Car.class);
        List<Car>returnList=query.list();

        sessionHandler.closeTransactionSession();

        return returnList;
    }

    @Override
    public Car getById(int id) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT * FROM cars WHERE id=:id";
        Query query= session.createSQLQuery(sqlQuery).addEntity(Car.class);
        query.setParameter("id",id);

        Car returnCar=(Car) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnCar;
    }
    public Car getAlike(Car car)
    {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        String sqlQuery="SELECT FROM cars WHERE MODEL=:model AND VENDOR=:vendor";

        Query query= session.createSQLQuery(sqlQuery).addEntity(Car.class);
        query.setParameter("model",car.getModel());
        query.setParameter("vendor",car.getVendor());

        Car returnCar=(Car) query.uniqueResult();

        sessionHandler.closeTransactionSession();

        return returnCar;
    }

    @Override
    public void update(Car car) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.update(car);

        sessionHandler.closeTransactionSession();
    }

    @Override
    public void delete(Car car) {
        sessionHandler.openTransactionSession();

        Session session= sessionHandler.getCurrentSession();

        session.delete(car);

        sessionHandler.closeTransactionSession();
    }
}
