package swing_logic;

import game_logic.Field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
/**
 * Created by Артём on 19.10.2014.
 */
public class SeaBattleSwing extends JFrame{
    private SwingField playerField;
    private SwingField computerField;

    public SeaBattleSwing() {
        setTitle("Морской бой");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(SwingField.WIDTH * 2 + 37, SwingField.HEIGHT + 68);

        playerField =  new SwingField(true, this);
        computerField = new SwingField(false, this);

        add(playerField);
        add(computerField);

        playerField.setLocation(10, 10);
        computerField.setLocation(SwingField.WIDTH + 20, 10);

        setLayout(null); //для того, чтобы установить расположение компонентов вручную
        setLocationRelativeTo(null); //устанавливает окно по центру экрана
        setVisible(true);
        setResizable(false); //нельзя изменять размер окна
    }

    public SwingField getPlayerField() {
        return playerField;
    }

    public SwingField getComputerField() {
        return computerField;
    }

    public void drawGameField(Field playerFieldMap, Field computerFieldMap) {
        playerField.setField(playerFieldMap);
        computerField.setField(computerFieldMap);

        playerField.printField();
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenu menuHelp = new JMenu("Справка");
        menuFile.setMnemonic(KeyEvent.VK_F); //вызов по Alt+F

        JMenuItem eMenuFileItemExit = new JMenuItem("Выход");
        eMenuFileItemExit.setMnemonic(KeyEvent.VK_E); //вызов по Alt+E
        eMenuFileItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        /*JMenuItem eMenuFileItemRestartGame = new JMenuItem("Новая игра");
        eMenuFileItemRestartGame.setMnemonic(KeyEvent.VK_N); //вызов по Alt+N
        eMenuFileItemRestartGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

            }

        });*/

        JMenuItem eMenuHelpItemAbout = new JMenuItem("О программе");
        eMenuHelpItemAbout.setMnemonic(KeyEvent.VK_A); //вызов по Alt+A
        eMenuHelpItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AboutProgramWindow.createAndShowGUI();
            }
        });

        //menuFile.add(eMenuFileItemRestartGame);
        menuFile.add(eMenuFileItemExit);
        menuHelp.add(eMenuHelpItemAbout);

        menuBar.add(menuFile);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
    }
}
