package entity;

import java.util.Scanner;

public class Driver {
    private static int AUTO_ID = 10000;
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String level;

    private static final String LEVEL_A = "Loại A";
    private static final String LEVEL_B = "Loại B";
    private static final String LEVEL_C = "Loại C";
    private static final String LEVEL_D = "Loại D";
    private static final String LEVEL_E = "Loại E";
    private static final String LEVEL_F = "Loại F";

    public Driver() {
    }

    public Driver(int id, String name, String address, String phoneNumber, String level) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.level = level;
    }

    public static int getAutoId() {
        return AUTO_ID;
    }

    public static void setAutoId(int autoId) {
        AUTO_ID = autoId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static String getLevelA() {
        return LEVEL_A;
    }

    public static String getLevelB() {
        return LEVEL_B;
    }

    public static String getLevelC() {
        return LEVEL_C;
    }

    public static String getLevelD() {
        return LEVEL_D;
    }

    public static String getLevelE() {
        return LEVEL_E;
    }

    public static String getLevelF() {
        return LEVEL_F;
    }

    public void inputInfo() {
        this.setId(Driver.AUTO_ID);

        System.out.println("Nhập tên lái xe: ");
        this.name = new Scanner(System.in).nextLine();
        System.out.println("Nhập địa chỉ lái xe: ");
        this.address = new Scanner(System.in).nextLine();
        System.out.println("Nhập số điện thoại lái xe: ");
        this.phoneNumber = new Scanner(System.in).nextLine();
        System.out.println("Nhập trình độ lái xe: ");
        System.out.println("1.Loại A");
        System.out.println("2.Loại B");
        System.out.println("3.Loại C");
        System.out.println("4.Loại D");
        System.out.println("5.Loại E");
        System.out.println("6.Loại F");
        System.out.println("Nhập sự lựa chọn của bạn: ");
        boolean check = true;
        do {
            int choice;
            try {
                choice = new Scanner(System.in).nextInt();
                check = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Vui lòng thử lại: ");
                check = false;
                continue;
            }
            if (choice <= 0 || choice > 4) {
                System.out.print("Nhập số trong khoảng từ 1 đến 4! Vui lòng thử lại: ");
                check = false;
            }
            switch (choice) {
                case 1:
                    this.setLevel(Driver.LEVEL_A);
                    check = true;
                    break;
                case 2:
                    this.setLevel(Driver.LEVEL_B);
                    check = true;
                    break;
                case 3:
                    this.setLevel(Driver.LEVEL_C);
                    check = true;
                    break;
                case 4:
                    this.setLevel(Driver.LEVEL_D);
                    check = true;
                    break;
                case 5:
                    this.setLevel(Driver.LEVEL_E);
                    check = true;
                    break;
                case 6:
                    this.setLevel(Driver.LEVEL_F);
                    check = true;
                    break;
                default:
                    System.out.print("Trình độ vừa chọn không hợp lệ, vui lòng chọn lại: ");
                    check = false;
                    break;
            }
        } while (!check);
        Driver.AUTO_ID++;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
