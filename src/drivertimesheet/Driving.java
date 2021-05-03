package drivertimesheet;

import entity.Driver;

import java.util.ArrayList;
import java.util.List;

public class Driving {
    private Driver driver;
    private List<DrivingTimeSheet> drivingTimeSheets = new ArrayList<>();
    private int totalRouteNumber;
    private float totalRange;

    public Driving(Driving driving, List<DrivingTimeSheet> drivingTimeSheets) {
    }

    public Driving(Driver driver, List<DrivingTimeSheet> drivingTimeSheets) {
        this.driver = driver;
        this.drivingTimeSheets = drivingTimeSheets;
    }

    public float getTotalRange() {
        return totalRange;
    }

    public void setTotalRange(float totalRange) {
        this.totalRange = totalRange;
    }

    public int getTotalRouteNumber() {
        return totalRouteNumber;
    }

    public void setTotalRouteNumber(int totalRouteNumber) {
        this.totalRouteNumber = totalRouteNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<DrivingTimeSheet> getDrivingTimeSheets() {
        return drivingTimeSheets;
    }

    public void setDrivingTimeSheets(List<DrivingTimeSheet> drivingTimeSheets) {
        this.drivingTimeSheets = drivingTimeSheets;
    }

    @Override
    public String toString() {
        return "Driving{" +
                "driver=" + driver +
                ", drivingTimeSheets=" + drivingTimeSheets +
                '}';
    }
}
