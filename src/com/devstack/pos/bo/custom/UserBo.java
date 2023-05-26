package com.devstack.pos.bo.custom;

import com.devstack.pos.Dto.CustomerDto;
import com.devstack.pos.Dto.UserDto;
import com.devstack.pos.bo.SuperBo;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    public boolean updateUser(UserDto dto);
    public boolean deleteUser(String email);
    public UserDto findUser(String email) throws SQLException, ClassNotFoundException;
    public List<UserDto> findAllUsers();
}
