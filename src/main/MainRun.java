package main;

import drivertimesheet.Driving;
import entity.Driver;
import entity.Route;
import repository.DriverDAO;
import repository.DrivingDAO;
import repository.RouteDAO;
import util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainRun {
    public static List<Driver> drivers = new ArrayList<>();
    public static List<Route> routes = new ArrayList<>();
    public static List<Driving> drivings = new ArrayList<>();

    public static final DriverDAO driverDAO =new DriverDAO();
    public static final RouteDAO routeDAO = new RouteDAO();
    public static final DrivingDAO drivingDAO = new DrivingDAO();

    private static final DriverCreator driverCreator =new DriverCreator();
    private static final RouteCreator routeCreator = new RouteCreator();
    private static final DrivingTimeSheetCreator drivingTimeSheetCreator = new DrivingTimeSheetCreator();
    private static final DrivingTimeSheetSorterAndCalculator sortDrivingTable = new DrivingTimeSheetSorterAndCalculator();

    public static void main(String[] args) {
        System.out.println("Program is initializing ....");
//        init();
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
        routes = !CollectionUtil.isEmpty(routeDAO.getRoute()) ? routeDAO.getRoute() : new ArrayList<>();
        drivings = !CollectionUtil.isEmpty(drivingDAO.getDrivingTimeSheet()) ? drivingDAO.getDrivingTimeSheet() : new ArrayList<>();
    }

    public static int functionChoice() {
        System.out.println("-----QUẢN LÝ PHÂN CÔNG LÁI XE BUS-------");
        System.out.println("1. Nhập danh sách lái xe");
        System.out.println("2. Nhập danh sách tuyến");
        System.out.println("3. Nhập danh sách phân công lái xe");
        System.out.println("4. Sắp xếp danh sách phân công ");
        System.out.println("5. Lập bảng thống kê tổng khoảng cách");
        System.out.println("6. Thoát");
        System.out.print("Nhập sự lựa chọn của bạn: ");
        int functionChoice = 0;
        boolean checkFunction = true;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
                checkFunction = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                continue;
            }
            if (functionChoice <= 0 || functionChoice > 6) {
                System.out.print("Nhập số trong khoảng từ 1 đến 6! Nhập lại: ");
                checkFunction = false;
            } else break;
        } while (!checkFunction);
        return functionChoice;
    }

    public static void createNewDriver(){
        driverCreator.createNewDriver();
    }
    public static void printDriver(){
        drivers.forEach(System.out::println);
    }

    public static void createNewRoute(){
        routeCreator.createNewRoute();
    }
    public static void printRoute(){
        routes.forEach(System.out::println);
    }

    public static void createNewDrivingTable(){
        drivingTimeSheetCreator.createDrivingTable();
    }
    public static void printDriving(){
        drivings.forEach(System.out::println);
    }
}
