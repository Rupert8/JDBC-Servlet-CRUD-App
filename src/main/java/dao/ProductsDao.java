package dao;

import entity.Customers;
import entity.Products;
import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsDao implements Dao<Integer, Products> {
    private static final ProductsDao INSTANCE = new ProductsDao();
    private static final String SQL_FIND_ALL_PRODUCTS = """
            SELECT p.* FROM products p
            """;


    public static ProductsDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Customers findById(Integer id) {
        return null;
    }

    @Override
    public Customers update(Integer id, Products products) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Customers save(Products products) {
        return null;
    }

    @Override
    public List<Products> findAll() {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Products> productsList = new ArrayList<>();

            while (resultSet.next()) {
                Products products = new Products();
                products.setId(resultSet.getInt("product_id"));
                products.setProductName(resultSet.getString("product_name"));
                products.setDescription(resultSet.getString("description"));
                products.setPrice(resultSet.getDouble("price"));
                productsList.add(products);
            }

            return productsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ProductsDao() {
    }
}
