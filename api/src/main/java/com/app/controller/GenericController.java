package com.app.controller;

import java.util.List;

public interface GenericController<T> {
    T show(int id);
    List<T> index();
    void create(T entity);
    void update(int id,T entity);
    void delete(int id);
}
