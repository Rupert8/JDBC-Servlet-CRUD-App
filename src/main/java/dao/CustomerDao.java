package dao;

import entity.Customers;
import lombok.Getter;
import start.Dao;
import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements Dao<Integer,Customers> {
    @Getter
    private static final CustomerDao instance = new CustomerDao();

    private static final String SQLSAVE = """
            insert into customers (first_name, last_name, email) 
            values (?,?,?)
            """;
    private static final String SQLDELETE = """
            DELETE FROM customers
            Where customer_id = ?
            """;
    private static final String SQLUPDATE = """
            UPDATE customers
            SET first_name = ?, last_name = ?, email = ?
            Where customer_id = ?
            """;

    private static final String SQLSELECTALL = """
            Select * from customers
            """;
    private static final String SQL_FIND_BY_ID = """
            SELECT * from customers
            WHERE customer_id = ?
            """;

    public Customers save(Customers customers) {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQLSAVE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customers.getFirstName());
            statement.setString(2, customers.getLastName());
            statement.setString(3, customers.getEmail());

            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                customers.setId(resultSet.getInt(1));
            }

            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQLDELETE)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customers update(Integer id, Customers customers) {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQLUPDATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customers.getFirstName());
            statement.setString(2, customers.getLastName());
            statement.setString(3, customers.getEmail());
            statement.setInt(4, id);

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                customers.setId(keys.getInt(1));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customers> findAll() {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQLSELECTALL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Customers> customers = new ArrayList<>();

            while (resultSet.next()) {
                Customers customer = new Customers();
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(4));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customers findById(Integer id) {
        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.prepareStatement(SQL_FIND_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Customers customer = new Customers();

            if (resultSet.next()) {
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(4));
            }

            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerDao() {

    }

}
