package ru.bulkashmak.jdbc;

public interface DAO<Entity, Key> {
    boolean create(Entity model);
    Entity read(Key key);
    boolean update(Entity model);
    boolean delete(Integer key);
}