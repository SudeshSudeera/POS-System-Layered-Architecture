package com.devstack.pos.Dao;

import com.devstack.pos.entity.Product;
import com.devstack.pos.entity.SuperEntity;
import javafx.scene.control.Tab;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T extends SuperEntity, ID> extends SuperDao{
    public boolean save(T t) throws SQLException, ClassNotFoundException;
    public boolean update(T t) throws SQLException, ClassNotFoundException;
    public boolean delete(ID id) throws SQLException, ClassNotFoundException;
    public T find(ID id) throws SQLException, ClassNotFoundException;
    public List<T> findAll() throws SQLException, ClassNotFoundException;

}
