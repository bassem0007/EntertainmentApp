package plural.capstone2.EntertainmentApp.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {

    boolean update(T updateObject);
    boolean delete(T deleteObject);
    T insert(T newObject);
    Optional<T> findById(int id);
    List<T> findAll();
    void resetDataStore();
    }
