package io.anastasiou.util;

import java.util.List;

public abstract class DataAccessObject<T extends DataTransferObject> {
    public abstract T getById(int id);
    public abstract List<T> getAll();
    public abstract T update(T dto);
    public abstract void create(T dto);
    public abstract void delete(int id);
}
