package main;

import drivertimesheet.Driving;
import entity.BusLine;
import entity.Driver;
import repository.DriverDAO;
import repository.DrivingDAO;
import repository.RouteDAO;
import util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainRun {
    public static List<Driver> drivers = new ArrayList<>();
    public static List<BusLine> busLines = new ArrayList<>();
    public static List<Driving> drivings = new ArrayList<>();

    public static final DriverDAO driverDAO = new DriverDAO();
    public static final RouteDAO routeDAO = new RouteDAO();
    public static final DrivingDAO drivingDAO = new DrivingDAO();

    private static final DriverCreator driverCreator = new DriverCreator();
    private static final RouteCreator routeCreator = new RouteCreator();
    private static final DrivingTimeSheetCreator drivingTimeSheetCreator = new DrivingTimeSheetCreator();
    private static final DrivingTimeSheetSorterAndCalculator sortDrivingTable = new DrivingTimeSheetSorterAndCalculator();

    public static void main(String[] args) {
        System.out.println("Program is initializing ....");
        init();
        System.out.println("Program is ready!");
        menu();
    }

    public static void menu() {
        do {
            int functionChoice = functionChoice();
            switch (functionChoice) {
                case 1:
                    createNewDriver();
                    printDriver();
                    break;
                case 2:
                    createNewRoute();
                    printRoute();
                    break;
                case 3:
                    createNewDrivingTable();
                    printDriving();
                    break;
                case 4:
                    sortDrivingTable.sortDrivingTable();
                    break;
                case 5:
                    sortDrivingTable.distanceDriving();
                    break;
                case 6:
                    System.exit(0);
            }
        } while (true);
    }

    private static void init() {
        drivers = !CollectionUtil.isEmpty(driverDAO.getDrivers()) ? driverDAO.getDrivers() : new ArrayList<>();
        if (CollectionUtil.isEmpty(drivers)) {
            Driver.AUTO_ID = 10000;
        } else {
            drivers.sort(Comparator.comparing(Driver::getId));
            Driver.AUTO_ID = drivers.get(drivers.size() - 1).getId() + 1;
        }
        busLines = !CollectionUtil.isEmpty(routeDAO.getRoute()) ? routeDAO.getRoute() : new ArrayList<>();
        if (CollectionUtil.isEmpty(busLines)) {
            BusLine.AUTO_ID = 100;
        } else {
            busLines.sort(Comparator.comparing(BusLine::getId));
            BusLine.AUTO_ID = busLines.get(drivers.size() - 1).getId() + 1;
        }
        drivings = !CollectionUtil.isEmpty(drivingDAO.getDrivingTimeSheet()) ? drivingDAO.getDrivingTimeSheet() : new ArrayList<>();
    }

    public static int functionChoice() {
        System.out.println("-----QU???N L?? PH??N C??NG L??I XE BUS-------");
        System.out.println("1. Nh???p danh s??ch l??i xe");
        System.out.println("2. Nh???p danh s??ch tuy???n");
        System.out.println("3. Nh???p danh s??ch ph??n c??ng l??i xe");
        System.out.println("4. S???p x???p danh s??ch ph??n c??ng ");
        System.out.println("5. L???p b???ng th???ng k?? t???ng kho???ng c??ch");
        System.out.println("6. Tho??t");
        System.out.print("Nh???p s??? l???a ch???n c???a b???n: ");
        int functionChoice = 0;
        boolean checkFunction = true;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
                checkFunction = true;
            } catch (Exception e) {
                System.out.println("Kh??ng ???????c nh???p k?? t??? kh??c ngo??i s???! Nh???p l???i: ");
                continue;
            }
            if (functionChoice <= 0 || functionChoice > 6) {
                System.out.print("Nh???p s??? trong kho???ng t??? 1 ?????n 6! Nh???p l???i: ");
                checkFunction = false;
            } else break;
        } while (!checkFunction);
        return functionChoice;
    }

    public static void createNewDriver() {
        driverCreator.createNewDriver();
    }

    public static void printDriver() {
        drivers.forEach(System.out::println);
    }

    public static void createNewRoute() {
        routeCreator.createNewRoute();
    }

    public static void printRoute() {
        busLines.forEach(System.out::println);
    }

    public static void createNewDrivingTable() {
        drivingTimeSheetCreator.createDrivingTable();
    }

    public static void printDriving() {
        drivings.forEach(System.out::println);
    }
}
