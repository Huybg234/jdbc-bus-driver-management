package main;

import entity.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverCreator {
    public void createNewDriver(){
        System.out.println("Nhập số lượng lái xe muốn thêm mới: ");
        int driverQuantity = 0;
        boolean isValidDriverQuantity = true;
        do {
            try {
                driverQuantity = new Scanner(System.in).nextInt();
                isValidDriverQuantity = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidDriverQuantity = false;
                continue;
            }
            if (driverQuantity <= 0) {
                System.out.print("Số lượng lái xe không được nhỏ hơn 0! Nhập lại: ");
                isValidDriverQuantity = false;
            }
        } while (!isValidDriverQuantity);

        List<Driver> tempDriver = new ArrayList<>();
        for (int i = 0; i < driverQuantity; i++) {
            Driver driver = new Driver();
            driver.inputInfo();
            tempDriver.add(driver);
        }
        MainRun.drivers.addAll(tempDriver);
        MainRun.driverDAO.insertNewDriver(tempDriver);
    }
}
