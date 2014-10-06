package game_logic;

/**
 * Created by Артём on 06.10.2014.
 * Трехпалубный корабль - крейсер (Cruiser)
 */
public class Cruiser extends Ship{
    Cruiser(int x1, int y1, int x2, int y2, int x3, int y3) {
        cells = new Cell[CRUISER_SIZE];
        cells[0] = new Cell(x1, y1, this);
        cells[1] = new Cell(x2, y2, this);
        cells[2] = new Cell(x3, y3, this);
    }
}
