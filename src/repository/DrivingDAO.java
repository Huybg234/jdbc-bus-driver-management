package repository;

import constant.DatabaseConstant;
import drivertimesheet.Driving;
import drivertimesheet.DrivingTimeSheet;
import entity.Driver;
import entity.Route;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DrivingDAO {
    private static final String DRIVING_TABLE_NAME = "driving_assignment";

    private static final String DRIVER_ID = "driver_id";
    private static final String ROUTE_ID = "route_id";
    private static final String ROUND_TRIP_NUMBER = "round_trip_number";

    public static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Driving> getDrivingTimeSheet() {
        List<Driving> drivings = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * d.id driver_id, d.name, d.address, d.phone_number, d.driver_level, bl.id route_id, d.distance, d.bus_stop_number, da.round_trip_number " +
                    "from " + DRIVING_TABLE_NAME + " da join " + DriverDAO.DRIVER_TABLE_NAME + " d on da.driver_Id = d.id join " + RouteDAO.ROUTE_TABLE_NAME + " bl on da.bus_line_id = bl.id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            drivings = new ArrayList<>();
            while (resultSet.next()) {
                int driverID = resultSet.getInt(DRIVER_ID);
                String name = resultSet.getString(DriverDAO.NAME);
                String address = resultSet.getString(DriverDAO.ADDRESS);
                String phoneNumber = resultSet.getString(DriverDAO.PHONE_NUMBER);
                String level = resultSet.getString(DriverDAO.LEVEL);
                Driver driver = new Driver(driverID, name, address, phoneNumber, level);

                int routeID = resultSet.getInt(ROUTE_ID);
                float range = resultSet.getFloat(RouteDAO.RANGE);
                int stopNumber = resultSet.getInt(RouteDAO.STOP_NUMBER);
                Route route = new Route(routeID, range, stopNumber);

                int turn = resultSet.getInt(ROUND_TRIP_NUMBER);

                DrivingTimeSheet drivingTimeSheet = new DrivingTimeSheet(route, turn);

                Driving tempDriving = searchDriver(drivings, driverID);

                if (ObjectUtil.isEmpty(tempDriving)) {
                    Driving driving = new Driving(driver, Arrays.asList(drivingTimeSheet));
                    drivings.add(driving);
                } else {
                    int index = drivings.indexOf(tempDriving);
                    drivings.get(index).getDrivingTimeSheets().add(drivingTimeSheet);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return drivings;
    }

    private Driving searchDriver(List<Driving> drivings, int driverId) {

        List<Driving> collect = drivings.stream().filter(driving -> driving.getDriver().getId() == driverId).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(collect)) {
            collect.get(0);
        }
        return null;
    }

    public void insertNewDriving(Driving driving) {
        if (ObjectUtil.isEmpty(driving)) {
            return;
        }
        int driverID = driving.getDriver().getId();
        driving.getDrivingTimeSheets().forEach(timesheet -> {
            PreparedStatement preparedStatement = null;
            try {
                String query = "INSERT INTO " + DRIVING_TABLE_NAME + " VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, driverID);
                preparedStatement.setInt(2, timesheet.getRoute().getId());
                preparedStatement.setInt(3, timesheet.getTurn());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(null, preparedStatement, null);
            }
        });
    }

    public void insertNewDrivingTimeSheet(List<Driving> drivings) {
        if (!CollectionUtil.isEmpty(drivings)) {
            return;
        }
        drivings.forEach(this::insertNewDriving);
    }
}
