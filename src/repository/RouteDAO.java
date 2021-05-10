package repository;

import constant.DatabaseConstant;
import entity.BusLine;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    public static final String ROUTE_TABLE_NAME = "bus_line";

    public static final String ID = "id";
    public static final String RANGE = "distance";
    public static final String STOP_NUMBER = "bus_stop_number";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<BusLine> getRoute() {
        List<BusLine> busLines = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + ROUTE_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            busLines = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                float range = resultSet.getFloat(RANGE);
                int stopNumber = resultSet.getInt(STOP_NUMBER);
                BusLine busLine = new BusLine(id, range, stopNumber);
                if (ObjectUtil.isEmpty(busLine)) {
                    continue;
                }
                busLines.add(busLine);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return busLines;
    }

    public void insertNewRoute(BusLine busLine) {
        if (ObjectUtil.isEmpty(busLine)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + ROUTE_TABLE_NAME + " VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, busLine.getId());
            preparedStatement.setFloat(2, busLine.getDistance());
            preparedStatement.setInt(3, busLine.getStopNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewRoute(List<BusLine> subjects) {
        if (CollectionUtil.isEmpty(subjects)) {
            return;
        }
        subjects.forEach(this::insertNewRoute);
    }
}
