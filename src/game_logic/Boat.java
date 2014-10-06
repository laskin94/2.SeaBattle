package game_logic;

/**
 * Created by Артём on 06.10.2014.
 * Однопалубный корабль - лодка (Boat)
 */
public class Boat extends Ship {
    Boat(int x, int y) {
        cells = new Cell[BOAT_SIZE];
        cells[0] = new Cell(x, y, this);
    }
}
