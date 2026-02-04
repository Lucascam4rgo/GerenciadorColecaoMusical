package model.dao;

import java.util.List;

public interface MusicDAO<T> {
    void save(T musicThing);
    void update(T musicThing);
    void removeByID(Integer id);
    T searchByID(Integer id);
    List<T> listAll();
}
