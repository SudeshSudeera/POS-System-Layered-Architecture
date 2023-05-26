package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.Dao.DaoFactory;
import com.devstack.pos.Dao.custom.ProductDao;
import com.devstack.pos.Dto.ProductDto;
import com.devstack.pos.bo.custom.ProductBo;
import com.devstack.pos.entity.Product;
import com.devstack.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = (ProductDao) DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.save(
                new Product(dto.getCode(),dto.getDescription())
        );
    }

    @Override
    public boolean updateProduct(ProductDto dto) {
        return false;
    }

    @Override
    public boolean deleteProduct(int code) {
        return false;
    }

    @Override
    public ProductDto findProduct(int code) {
        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product p : productDao.findAll()) {
                dtos.add(new ProductDto(p.getCode(),p.getDescription())); // Data drop wait
        }
        return dtos;
    }

    @Override
    public int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductId();
    }
}
