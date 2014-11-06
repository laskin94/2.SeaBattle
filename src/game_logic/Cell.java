package game_logic;

/**
 * Created by Артём on 06.10.2014.
 * Ячейка игрового поля
 */
public class Cell {
    protected int x, y;
    private Ship ship;
    private boolean wasFired = false;
    private boolean AreaOfDeadShip = false;

    Cell(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void setWasFired() {
        this.wasFired = true;
    }

    public boolean isFired() {
        return wasFired;
    }

    public boolean isShip() {
        return ship != null;
    }

    public void setAreaOfDeadShip() { this.AreaOfDeadShip = true;}

    public boolean isAreaOfDeadShip() {
        return AreaOfDeadShip;
    }

}
