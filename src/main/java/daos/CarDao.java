package daos;


import entities.Car;

import java.util.List;

public interface CarDao {
    void add(Car car);
    List<Car> getAll();
    Car getById(int id);
    void update(Car car);
    void delete(Car car);
}
