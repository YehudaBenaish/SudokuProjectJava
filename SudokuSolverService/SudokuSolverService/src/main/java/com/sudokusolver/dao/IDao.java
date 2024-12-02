package main.java.com.sudokusolver.dao;

import java.io.IOException;

public interface IDao<ID extends java.io.Serializable, T> {
    void delete(T entity);
    T find(ID id) throws IllegalArgumentException, IOException;
    boolean save(T entity) throws IllegalArgumentException;
}
