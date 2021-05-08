package drivertimesheet;

import entity.BusLine;

public class DrivingTimeSheet {
    private BusLine busLine;
    private int roundTripNumber;

    public DrivingTimeSheet() {
    }

    public DrivingTimeSheet(BusLine busLine, int roundTripNumber) {
        this.busLine = busLine;
        this.roundTripNumber = roundTripNumber;
    }

    public BusLine getRoute() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }

    public int getRoundTripNumber() {
        return roundTripNumber;
    }

    public void setRoundTripNumber(int roundTripNumber) {
        this.roundTripNumber = roundTripNumber;
    }

    @Override
    public String toString() {
        return "DrivingTimeSheet{" +
                "busLine=" + busLine +
                ", roundTripNumber=" + roundTripNumber +
                '}';
    }
}
