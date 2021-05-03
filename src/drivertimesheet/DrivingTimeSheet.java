package drivertimesheet;

import entity.Route;

public class DrivingTimeSheet {
    private Route route;
    private int turn;

    public DrivingTimeSheet() {
    }

    public DrivingTimeSheet(Route route, int turn) {
        this.route = route;
        this.turn = turn;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "DrivingTimeSheet{" +
                "route=" + route +
                ", turn=" + turn +
                '}';
    }
}
