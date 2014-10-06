package game_logic;

import java.util.concurrent.TimeUnit;

/**
 * Created by Артём on 06.10.2014.
 */
public class SeaBattle {
    public static final int PAUSE_MILLISECONDS = 5;

    private User player1;
    private User player2;

    private Field player1FieldMap;
    private Field player2FieldMap;

    public static boolean userMakeShooting;
    public static boolean userKilledSomeone;

    public SeaBattle() {
        player1FieldMap = new Field();
        player2FieldMap = new Field();
    }

    public Field getPlayer1Field() {
        return this.player1FieldMap;
    }

    public Field getplayer2FieldMap() {
        return this.player2FieldMap;
    }

    public void play() {
        boolean player1Win = false;
        boolean player2Win = false;

        do {
            userMakeShooting = false;
            waitUserAttack();

            player1Win = checkWin(player2FieldMap.getFieldMap());
            if (player1Win) {
                continue;
            }

            player2Win = checkWin(player1FieldMap.getFieldMap());
            if (player2Win) {
                continue;
            }

            if (userKilledSomeone) {
                userKilledSomeone = false;
                continue;
            }

        } while(!player1Win && !player2Win);
    }

    public static void waitUserAttack() {
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(PAUSE_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!userMakeShooting);
    }

    public boolean checkWin(Cell[][] map) {
        int firedShips = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = map[i][j];
                if (cell.isShip() && cell.isFired()) {
                    firedShips++;
                    if (firedShips == Field.ALL_SHIPS_CELLS_COUNT) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
