package com.devstack.pos.Dao.custom.impl;

import com.devstack.pos.Dao.CrudUtil;
import com.devstack.pos.Dao.custom.ItemDetailDao;
import com.devstack.pos.entity.ItemDetail;

import java.sql.SQLException;
import java.util.List;

public class ItemDetailDaoImpl implements ItemDetailDao {
    @Override
    public boolean save(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_details_has_order_details VALUES (?,?,?,?,?)",itemDetail.getDetailCode(),itemDetail.getOrder(),itemDetail.getQty(),itemDetail.getDiscount(),itemDetail.getAmount());

    }

    @Override
    public boolean update(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetail find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ItemDetail> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
