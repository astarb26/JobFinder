package com.hit.dao;

import java.io.IOException;
import java.util.List;

public interface IDao<T> {
    boolean delete(T entity);
    List<T> getAllJobs() throws IllegalArgumentException, IOException;
    boolean save(T entity) throws IllegalArgumentException;
}
