package operation;

import java.util.List;

public interface BaseCRUDMapper<T> {

    T getClassz();

    void insert(T t);

    void delete(T t);

    void update(T t);

    List<T> select(T t);

}
