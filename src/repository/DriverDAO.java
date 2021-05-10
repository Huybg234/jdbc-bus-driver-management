package repository;

import constant.DatabaseConstant;
import entity.Driver;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    public static final String DRIVER_TABLE_NAME = "driver";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String LEVEL = "driver_level";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Driver> getDrivers() {
        List<Driver> drivers = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + DRIVER_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            drivers = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                String address = resultSet.getString(ADDRESS);
                String phoneNumber = resultSet.getString(PHONE_NUMBER);
                String level = resultSet.getString(LEVEL);
                Driver driver = new Driver(id, name, address, phoneNumber, level);
                if (ObjectUtil.isEmpty(driver)) {
                    continue;
                }
                drivers.add(driver);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return drivers;
    }

    public void insertNewDriver(Driver driver) {
        if (ObjectUtil.isEmpty(driver)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + DRIVER_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, driver.getId());
            preparedStatement.setString(2, driver.getName());
            preparedStatement.setString(3, driver.getAddress());
            preparedStatement.setString(4, driver.getPhoneNumber());
            preparedStatement.setString(5, driver.getLevel());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewDriver(List<Driver> drivers) {
        if (CollectionUtil.isEmpty(drivers)) {
            return;
        }
        drivers.forEach(this::insertNewDriver);
    }
}
