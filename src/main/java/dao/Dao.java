package dao;

import entity.Customers;

import java.util.List;

public interface Dao<I, E>{
    Customers findById(I id);
    Customers update(I id, E e);
    boolean delete(I id);
    Customers save(E e);
    List<E> findAll();

}
