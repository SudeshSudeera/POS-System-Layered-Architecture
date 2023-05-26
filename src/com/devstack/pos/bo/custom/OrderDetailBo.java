package com.devstack.pos.bo.custom;

import com.devstack.pos.Dto.OrderDetailDto;

import java.sql.SQLException;

public interface OrderDetailBo {
    public boolean makeOrder(OrderDetailDto d) throws SQLException;
}
