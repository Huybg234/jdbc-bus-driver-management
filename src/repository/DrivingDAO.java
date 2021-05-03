package repository;

import constant.DatabaseConstant;
import drivertimesheet.Driving;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrivingDAO {
    private static final String DRIVING_TABLE_NAME = "driving_assignment";

    private static final String DRIVER_ID = "driver_id";
    private static final String ROUTE_ID = "route_id";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Driving> getDrivingTimeSheet(){
        List<Driving> drivings = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + DRIVING_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            drivings = new ArrayList<>();
            while (resultSet.next()){
                int driverID = resultSet.getInt(DRIVER_ID);
                int routeID = resultSet.getInt(ROUTE_ID);
                Driving driving = new Driving(driverID, routeID);
                if (ObjectUtil.isEmpty(driving)) {
                    continue;
                }
                drivings.add(driving);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return drivings;
    }

    public void insertNewDriving(Driving driving){
        if (ObjectUtil.isEmpty(driving)) {
            return;
        }
        int driverID = driving.getDriver().getId();
        driving.getDrivingTimeSheets().forEach(timesheet -> {
            PreparedStatement preparedStatement = null;
            try {
                String query = "INSERT INTO " + DRIVING_TABLE_NAME + " VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, driverID);
                preparedStatement.setInt(2, timesheet.getRoute().getId());
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
