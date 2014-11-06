package game_logic;
import swing_logic.SeaBattleSwing;
import swing_logic.Sector;
import swing_logic.SwingField;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Артём on 06.10.2014.
 */
public class SeaBattle {
    public static final int PAUSE_MILLISECONDS = 1;

    private Field playerFieldMap;
    private Field computerFieldMap;

    private SeaBattleSwing seaBattleSwing;

    public static boolean userMakeShooting;
    public static boolean userKilledSomeone;

    public SeaBattle() {
        playerFieldMap = new Field();
        computerFieldMap = new Field();
        userMakeShooting = false;
        userKilledSomeone = false;
    }

    public void restart() {
        playerFieldMap = new Field();
        computerFieldMap = new Field();
        userMakeShooting = false;
        userKilledSomeone = false;
        seaBattleSwing.setVisible(false);
        SeaBattleSwing seaBattle = new SeaBattleSwing();
        seaBattle.addMenuBar();
        seaBattle.drawGameField(this.getPlayerField(), this.getComputerField());
        this.setSeaBattleSwing(seaBattle);
        play();
    }

    public void setSeaBattleSwing(SeaBattleSwing seaBattleSwing) {
        this.seaBattleSwing = seaBattleSwing;
    }

    public SeaBattleSwing getSeaBattleSwing() {
        return seaBattleSwing;
    }

    public Field getPlayerField() {
        return this.playerFieldMap;
    }

    public Field getComputerField() {
        return this.computerFieldMap;
    }

    public void play() {
        boolean userWin = false;
        boolean computerWin = false;

        do {
            userMakeShooting = false;
            waitUserAttack();

            userWin = checkWin(computerFieldMap.getFieldMap());
            if (userWin) {
                continue;
            }

            if (userKilledSomeone) {
                userKilledSomeone = false;
                continue;
            }

            while (computerAttack()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(PAUSE_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            computerWin = checkWin(playerFieldMap.getFieldMap());

        } while(!userWin && !computerWin);

        if (computerWin) {
            showWinMessageBox("Вы проиграли", false);
        } else {
            showWinMessageBox("Вы выиграли", true);
        }

    }

    private boolean computerAttack() {
        int x = Field.getRandomCoordinate();
        int y = Field.getRandomCoordinate();

        SwingField playerSwingField = seaBattleSwing.getPlayerField();
        Sector sector = playerSwingField.getSectors()[x][y];

        Cell[][] cells = playerFieldMap.getFieldMap();
        Cell cell = cells[x][y];

        while (cell.isFired()) {
            x = Field.getRandomCoordinate();
            y = Field.getRandomCoordinate();
            cell = cells[x][y];
        }

        cell.setWasFired();

        sector.setAttacked();
        if (cell.isShip()) {
            sector.setShip();
        }

        sector.repaint();

        return cell.isShip();
    }

    private void showWinMessageBox(String message, boolean userWin) {
        if (userWin) {
            seaBattleSwing.getComputerField().setGameEnd();
            seaBattleSwing.getComputerField().repaint();
        } else {
            seaBattleSwing.getPlayerField().setGameEnd();
            seaBattleSwing.getPlayerField().repaint();
        }

        Object[] options = {"Да", "Нет"};
        int result = JOptionPane.showOptionDialog(seaBattleSwing.getComputerField().getParent(), "Хотите сыграть еще раз ?", message,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (result == 0) {
            restart();
        }

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
