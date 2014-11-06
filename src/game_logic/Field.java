package game_logic;

import java.util.Random;

/**
 * Created by Артём on 06.10.2014.
 * Игровое поле игрока
 */
public class Field {
    public static final int FIELD_ROW_SIZE = 10;
    public static final int FIELD_COL_SIZE = 10;

    private Cell[][] field = new Cell[FIELD_ROW_SIZE][FIELD_COL_SIZE];
    public static final int BATTLESHIPS_COUNT = 1;
    public static final int CRUISERS_COUNT = 2;
    public static final int DESTROYERS_COUNT = 3;
    public static final int BOATS_COUNT = 4;
    public static final int ALL_SHIPS_CELLS_COUNT = BOATS_COUNT * Ship.BOAT_SIZE +
            DESTROYERS_COUNT * Ship.DESTROYER_SIZE +
            CRUISERS_COUNT * Ship.CRUISER_SIZE +
            BATTLESHIPS_COUNT * Ship.BATTLESHIP_SIZE;

    public Field() {
        regenerateForGame();
    }

    private void regenerateForGame() {

        // заполним поле пустыми ячейками
        for (int i = 0; i < FIELD_COL_SIZE; i++) {
            for (int j = 0; j < FIELD_ROW_SIZE; j++) {
                Cell cell = new Cell(i, j, null);
                field[i][j] = cell;
            }
        }

        addBattleships();
        addCruisers();
        addDestroyers();
        addBoats();
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

    /**Генерирование координат кораблей*/
    public static int getRandomCoordinate() {
        Random random = new Random();
        return random.nextInt(10);
    }

    /**Генерирование поворота кораблей*/
    private static boolean rotationShipGenerate() {
        return new Random().nextBoolean();
    }

    /**
     * Расчет координат следующей палубы
     * x1,y1 - координаты текущей палубы
     * rotation - поворот корабля (располагается корабль по горизонтали или по вериткали)
     */
    public int calculateXCoordinate(int x1, boolean rotation) {
        int x2;

        if (!rotation) {
            x2 = x1;
        } else {
            x2 = x1 + 1;
        }

        if ((x2 < 0) || (x2 > 9)) {
            return 10;
        } else {
            return x2;
        }
    }

    public int calculateYCoordinate(int y1, boolean rotation) {
        int y2;

        if (!rotation) {
            y2 = y1 + 1;
        } else {
            y2 = y1;
        }

        if ((y2 < 0) || (y2 > 9)) {

            return 10;
        } else {
            return y2;
        }
    }

    /**Добавление однопалубных кораблей*/
    public void addBoats() {
        int iteration = 0;
        while (iteration < BOATS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            if (!shipInPlace(x, y, false, Ship.BOAT_SIZE)) {
                addBoat(x, y);
                iteration++;
            }
        }
    }

    /**Добавление одного 1-палубного корабля*/
    public void addBoat(int x, int y) {
        Boat b = new Boat(x, y);
        field[x][y] = b.cells[0];
    }

    /**
     * Добавление двухпалубных кораблей
     */
    public void addDestroyers() {
        int iteration = 0;
        while (iteration < DESTROYERS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            if ((x1 != 10) && (y1 != 10))
                if (!shipInPlace(x, y, rotationShipGenerate(), Ship.DESTROYER_SIZE)) {
                    addDestroyer(x, y, x1, y1);
                    iteration++;
                }
        }
    }

    /**Добавление одного 2-палубного корабля*/
    public void addDestroyer(int x1, int y1, int x2, int y2) {
        Destroyer dr = new Destroyer(x1, y1, x2, y2);
        field[x1][y1] = dr.cells[0];
        field[x2][y2] = dr.cells[1];
    }

    /**
     * Добавление трехпалубного корабля кораблей
     */
    public void addCruisers() {
        int iteration = 0;
        while (iteration < CRUISERS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            int x2 = calculateXCoordinate(x1, rotation);
            int y2 = calculateYCoordinate(y1, rotation);
            if ((x1 != 10) && (y1 != 10) & (x2 != 10) && (y2 != 10))
                if (!shipInPlace(x, y, rotation, Ship.CRUISER_SIZE)) {
                    addCruiser(x, y, x1, y1, x2, y2);
                    iteration++;
                }
        }
    }

    /**Добавление 3-палубного корабля*/
    public void addCruiser(int x1, int y1, int x2, int y2, int x3, int y3) {
        Cruiser cr = new Cruiser(x1, y1, x2, y2, x3, y3);
        field[x1][y1] = cr.cells[0];
        field[x2][y2] = cr.cells[1];
        field[x3][y3] = cr.cells[2];
    }

    /**Добавление четырехпалубного корабля*/
    public void addBattleships() {
        int iteration = 0;
        while (iteration < BATTLESHIPS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            int x2 = calculateXCoordinate(x1, rotation);
            int y2 = calculateYCoordinate(y1, rotation);
            int x3 = calculateXCoordinate(x2, rotation);
            int y3 = calculateYCoordinate(y2, rotation);
            if ((x1 != 10) && (y1 != 10) && (x2 != 10) && (y2 != 10) && (x3 != 10) && (y3 != 10))
                if (!shipInPlace(x, y, rotation, Ship.BATTLESHIP_SIZE)) {
                    addBattleship(x, y, x1, y1, x2, y2, x3, y3);
                    iteration++;
                }
        }
    }

    /**Добавление одного 4-палубного корабля*/
    public void addBattleship(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        Battleship bs = new Battleship(x1, y1, x2, y2, x3, y3, x4, y4);
        field[x1][y1] = bs.cells[0];
        field[x2][y2] = bs.cells[1];
        field[x3][y3] = bs.cells[2];
        field[x4][y4] = bs.cells[3];
    }
}
