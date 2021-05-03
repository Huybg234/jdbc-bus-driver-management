package repository;

import constant.DatabaseConstant;
import entity.Route;
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
    private static final String ROUTE_TABLE_NAME = "bus_line";

    private static final String ID = "id";
    private static final String RANGE = "range";
    private static final String STOP_NUMBER = "stopNumber";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Route> getRoute(){
        List<Route> routes = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + ROUTE_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            routes = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt(ID);
                float range = resultSet.getFloat(RANGE);
                int stopNumber = resultSet.getInt(STOP_NUMBER);
                Route route = new Route(id, range, stopNumber);
                if (ObjectUtil.isEmpty(route)) {
                    continue;
                }
                routes.add(route);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return routes;
    }

    public void insertNewRoute(Route route){
        if (ObjectUtil.isEmpty(route)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + ROUTE_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,route.getId());
            preparedStatement.setFloat(2,route.getRange());
            preparedStatement.setInt(3,route.getStopNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewRoute(List<Route> subjects) {
        if (!CollectionUtil.isEmpty(subjects)) {
            return;
        }
        subjects.forEach(this::insertNewRoute);
    }
}
