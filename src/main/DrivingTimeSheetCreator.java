package main;

import drivertimesheet.Driving;
import drivertimesheet.DrivingTimeSheet;
import entity.BusLine;
import util.CollectionUtil;
import util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrivingTimeSheetCreator {
    private final List<Integer> checkID = new ArrayList<>();

    public boolean isValidSubjectAndTeacher() {
        return !CollectionUtil.isEmpty(MainRun.drivers) && !CollectionUtil.isEmpty(MainRun.busLines);
    }

    public void createDrivingTable() {
        if (!isValidSubjectAndTeacher()) {
            System.out.println("Bạn cần nhập lái xe và tuyến đường trước khi phân công");
            return;
        }
        boolean check = true;
        List<Driving> tempDrivings = new ArrayList<>();
        for (int i = 0; i < MainRun.drivers.size(); i++) {
            String driverName = MainRun.drivers.get(i).getName();
            System.out.println("------Phân công cho lái xe " + driverName + "---------");
            System.out.println("Nhập số tuyến đường mà lái xe " + driverName + " được phân công: ");
            int drivingRouteNumber = inputDrivingRouteNumber();
            if (drivingRouteNumber == 0){
                continue;
            }
            List<DrivingTimeSheet> drivingTimeSheets = new ArrayList<>();
            for (int j = 0; j < drivingRouteNumber; j++) {
                System.out.println("Nhập id tuyến đường thứ " + (j + 1) + " mà lái xe " + driverName + " được phân công: ");
                BusLine busLine = inputRouteId();
                System.out.println(busLine);
                System.out.println("Nhập số lượt lái xe " + driverName + " đi tuyến đường này: ");
                int drivingTurnNumber = inputTurnNumber(drivingTimeSheets);
                drivingTimeSheets.add(new DrivingTimeSheet(busLine, drivingTurnNumber));
            }
            Driving driving = new Driving(MainRun.drivers.get(i), drivingTimeSheets);
            tempDrivings.add(driving);
            driving.setTotalBusLineNumber(drivingRouteNumber);
            MainRun.drivings.add(driving);
        }
        MainRun.drivingDAO.insertNewDrivingTimeSheet(tempDrivings);
    }

    private int inputTurnNumber(List<DrivingTimeSheet> drivingTimeSheets) {
        int drivingTurnNumber = 0;
        boolean isValidTurnNumber = true;
        do {
            try {
                drivingTurnNumber = new Scanner(System.in).nextInt();
                isValidTurnNumber = true;
            } catch (Exception e) {
                System.out.println("Không được có ký tự khác ngoài số! Nhập lại: ");
                isValidTurnNumber = false;
                continue;
            }
            if (drivingTurnNumber <= 0) {
                System.out.print("Số lượt lái phải lớn hơn 0! Nhập lại: ");
                isValidTurnNumber = false;
                continue;
            }
            int currentTotalTurn = calculateTotalTurn(drivingTimeSheets);
            if (currentTotalTurn + drivingTurnNumber > 15) {
                System.out.println("Tổng lượt lái của lái xe đang là " + currentTotalTurn + ", lớn hơn 15! Nhập lại: ");
                isValidTurnNumber = false;
            }
        } while (!isValidTurnNumber);
        return drivingTurnNumber;
    }

    private int calculateTotalTurn(List<DrivingTimeSheet> drivingTimeSheets) {
        return CollectionUtil.isEmpty(drivingTimeSheets) ? 0 : drivingTimeSheets.stream().mapToInt(DrivingTimeSheet::getRoundTripNumber).sum();
    }

    private int inputDrivingRouteNumber() {
        int drivingRouteNumber = 0;
        boolean isValidRouteNumber = true;
        do {
            try {
                drivingRouteNumber = new Scanner(System.in).nextInt();
                isValidRouteNumber = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidRouteNumber = false;
                continue;
            }
            if (drivingRouteNumber < 0 || drivingRouteNumber > MainRun.busLines.size()) {
                System.out.print("Tuyến đường không được nhỏ hơn 0 và lớn hơn tổng số tuyến! Nhập lại: ");
                isValidRouteNumber = false;
            }
        } while (!isValidRouteNumber);
        return drivingRouteNumber;
    }

    private BusLine inputRouteId() {
        int routeId = 0;
        boolean isValidRouteId = true;
        do {
            try {
                routeId = new Scanner(System.in).nextInt();
                isValidRouteId = true;
            } catch (Exception e) {
                System.out.println("không được có ký tự khác ngoài số! Nhập lại: ");
                isValidRouteId = false;
                continue;
            }
            BusLine busLine = searchRouteId(routeId);
            if (ObjectUtil.isEmpty(busLine)) {
                System.out.print("Không có id tuyến đường vừa nhập! Nhập lại: ");
                isValidRouteId = false;
            }
            else return busLine;
            for (Integer integer : checkID) {
                if (integer == routeId) {
                    System.out.println("Tuyến đường đã tồn tại! Nhập lại: ");
                    isValidRouteId = false;
                    break;
                }
            }
            checkID.add(routeId);
        } while (!isValidRouteId);
        System.out.println("sai ở 2");
        return null;
    }

    public static BusLine searchRouteId(int id) {
        System.out.println(MainRun.busLines.size());
        for (int i = 0; i < MainRun.busLines.size(); i++) {
            if (MainRun.busLines.get(i).getId() == id) {
                return MainRun.busLines.get(i);
            }
        }
        return null;
    }
}
