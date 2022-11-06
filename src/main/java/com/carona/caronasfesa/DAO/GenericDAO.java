package com.carona.caronasfesa.DAO;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<E> extends Serializable {

    void insert(E e);

    void update(E e);

    void remove(E e);

    E readById(E e);

    List<E> readAll();
}
