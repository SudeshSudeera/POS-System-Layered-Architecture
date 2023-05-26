package com.devstack.pos.Dao.custom.impl;

import com.devstack.pos.Dao.CrudUtil;
import com.devstack.pos.Dao.custom.ProductDetailDao;
import com.devstack.pos.Dto.ProductDetailDto;
import com.devstack.pos.Dto.ProductDetailJoinDto;
import com.devstack.pos.entity.ProductDetail;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailDaoImpl implements ProductDetailDao {
    @Override
    public boolean save(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_details VALUES(?,?,?,?,?,?,?,?)",
                productDetail.getCode(),
                productDetail.getBarCode(),
                productDetail.getQtyOnHand(),
                productDetail.getSellingPrice(),
                productDetail.isDiscountAvailability(),
                productDetail.getShowPrice(),
                productDetail.getProductCode(),
                productDetail.getBuyingPrice()
                );
    }

    @Override
    public boolean update(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ProductDetail find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetail> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetail> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details WHERE product_code = ?",productCode);
        List<ProductDetail> list = new ArrayList<>();
        while (set.next()){
            list.add(new ProductDetail(
                    set.getString(1),set.getString(2),
                    set.getInt(3),set.getDouble(4),
                    set.getDouble(6),set.getDouble(8),
                    set.getInt(7),set.getBoolean(5)
            ));
        }
        return list;
    }

    @Override
    public ProductDetail findProductDetail(String code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details WHERE code = ?", code);
        if(set.next()){
            return new ProductDetail(
                    set.getString(1),set.getString(2),
                    set.getInt(3),set.getDouble(4),
                    set.getDouble(6),set.getDouble(8),
                    set.getInt(7),set.getBoolean(5)
            );
        }
        return null;
    }

    @Override
    public ProductDetailJoinDto findProductDetailJoinData(String code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details pd JOIN product p ON pd.code = ? AND pd.product_code=p.code", code);
        if(set.next()){
            return new ProductDetailJoinDto(
                    set.getInt(9),
                    set.getString(10),
                    new ProductDetailDto(set.getString(1),set.getString(2),
                            set.getInt(3),set.getDouble(4),
                            set.getDouble(6),set.getDouble(8),
                            set.getInt(7),set.getBoolean(5))
            );
        }
        return null;
    }

    @Override
    public boolean manageQty(String barcode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE product_details SET qty_on_hand(qty_on_hand-?) WHERE code=? ",qty, barcode);
    }
}
