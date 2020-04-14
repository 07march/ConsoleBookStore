package by.repository.filedb;

import java.util.List;

public interface DBPg {
    <T> void add(T t, Class<T> tClass);
    <T, TT> void delete(TT tt, Class<TT> ttClass, Class<T> tClass);
    <T, TT> void change(Object id, String field, TT tt, Class<TT> ttClass, Class<T> tClass);
    <T> List<T> findAll(Class<T> tClass);
    <T> T findBy(T t, Class<T> tClass);
}
