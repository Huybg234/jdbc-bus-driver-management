package entity;

import java.util.Scanner;

public class Route{
    private int id;
    private float range;
    private int stopNumber;

    private static int AUTO_ID = 100;

    public Route() {
    }

    public Route(int id, float range, int stopNumber) {
        this.id = id;
        this.range = range;
        this.stopNumber = stopNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public static int getAutoId() {
        return AUTO_ID;
    }

    public static void setAutoId(int autoId) {
        AUTO_ID = autoId;
    }

    public void inputInfo(){
        this.setId(Route.AUTO_ID);

        boolean check = true;
        System.out.println("Nhập khoảng cách: ");
        do {
            try {
                this.range = new Scanner(System.in).nextFloat();
                check = true;
            } catch (Exception e) {
                System.out.println("Không được có chữ! Nhập lại");
                check = false;
                continue;
            }
            if (this.range <=0 ){
                System.out.println("Khoảng cách không được nhỏ hơn hoặc bằng 0! Nhập lại: ");
                check = false;
            }
        }while (!check);
        System.out.println("Nhập số điểm dừng: ");
        do {
            try {
                this.stopNumber = new Scanner(System.in).nextInt();
                check = true;
            } catch (Exception e) {
                System.out.println("Không được có chữ! Nhập lại");
                check = false;
                continue;
            }
            if (this.stopNumber <=0 ){
                System.out.println("Số điểm dừng không được nhỏ hơn hoặc bằng 0! Nhập lại: ");
                check = false;
            }
        }while (!check);
        Route.AUTO_ID++;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", range=" + range +
                ", stopNumber=" + stopNumber +
                '}';
    }
}
