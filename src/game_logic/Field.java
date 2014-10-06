package game_logic;

/**
 * Created by Артём on 06.10.2014.
 * Игровое поле игрока
 */
public class Field {
    public static final int FIELD_ROW_SIZE = 10;
    public static final int FIELD_COL_SIZE = 10;

    private Cell[][] field = new Cell[FIELD_ROW_SIZE][FIELD_COL_SIZE];
    public static final int BATTLE_SHIPS_COUNT = 1;
    public static final int CRUISERS_COUNT = 2;
    public static final int DESTROYERS_COUNT = 3;
    public static final int BOATS_COUNT = 4;
    public static final int ALL_SHIPS_CELLS_COUNT = BOATS_COUNT * Ship.BOAT_SIZE +
            DESTROYERS_COUNT * Ship.DESTROYER_SIZE +
            CRUISERS_COUNT * Ship.CRUISER_SIZE +
            BATTLE_SHIPS_COUNT * Ship.BATTLESHIP_SIZE;

    public Field() {
        /**Очищаем поле**/
        for (int i = 0; i < FIELD_COL_SIZE; i++) {
            for (int j = 0; j < FIELD_ROW_SIZE; j++) {
                Cell cell = new Cell(i, j, null);
                field[i][j] = cell;
            }
        }
    }

    public Cell[][] getFieldMap() {
        return field;
    }

    /**
     * Проверка пространства на наличие кораблей
     * x,y - координаты первой палубы
     * rotation - поворот корабля (располагает корабль по горизонтали или по вертикали)
     * shipSize - размер корабля (количество палуб)
     */
    private boolean shipInPlace(int x, int y, boolean rotation, int shipSize) {
        int cx, cy; // координаты обследуемого пространства
        int i, j;   // переменные размера обследуемого пространства

        // положения корабля всего 2, поэтому проверяем одно условие, по else присваиваются
        // другие значения
        if (rotation) {
            i = shipSize;
            j = 3;
        } else {
            i = 3;
            j = shipSize;
        }

        for (cx = -1; cx <= i; cx++)
            for (cy = -1; cy <= j; cy++) {
                if ((x + cx) >= 0 && (x + cx) <= 9 && (y + cy) >= 0 && (y + cy) <= 9) {
                    if (field[x + cx][y + cy].isShip()) {
                        return true;
                    }
                }
            }

        return false;
    }

    /**Добавление 1-палубного корабля**/
    public void addBoat(int x, int y) {
        Boat b = new Boat(x, y);
        field[x][y] = b.cells[0];
    }

    /**Добавление 2-палубного корабля**/
    public void addDestroyer(int x1, int y1, int x2, int y2) {
        Destroyer dr = new Destroyer(x1, y1, x2, y2);
        field[x1][y1] = dr.cells[0];
        field[x2][y2] = dr.cells[1];
    }

    /**Добавление 3-палубного корабля**/
    public void addCruiser(int x1, int y1, int x2, int y2, int x3, int y3) {
        Cruiser cr = new Cruiser(x1, y1, x2, y2, x3, y3);
        field[x1][y1] = cr.cells[0];
        field[x2][y2] = cr.cells[1];
        field[x3][y3] = cr.cells[2];
    }

    /**Добавление 4-палубного корабля**/
    public void addBattleship(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        Battleship bs = new Battleship(x1, y1, x2, y2, x3, y3, x4, y4);
        field[x1][y1] = bs.cells[0];
        field[x2][y2] = bs.cells[1];
        field[x3][y3] = bs.cells[2];
        field[x4][y4] = bs.cells[3];
    }
}
