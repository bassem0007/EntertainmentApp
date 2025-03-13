package plural.capstone2.EntertainmentApp.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public interface BaseDAO<T> {

    boolean update(T updateObject);
    boolean delete(T deleteObject);
    T insert(T newObject);
    Optional<T> findById(int id);
    List<T> findAll();
    void resetDataStore();
    default List<T> findBy(Predicate<T> predicate) {
        return findAll().stream().filter(predicate).collect(toList());
    }
}
