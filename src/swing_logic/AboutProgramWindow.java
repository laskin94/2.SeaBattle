package swing_logic;

import javax.swing.*;
import java.awt.*;
/**
 * Created by Артём on 19.10.2014.
 */
public class AboutProgramWindow {

    public Component createComponents() {
        JLabel label = new JLabel("Автор: Ласкин А.Г., ФКН 3 курс 2 группа");
        JPanel pane = new JPanel();

        pane.setPreferredSize(new Dimension(300, 100));
        pane.add(label);
        pane.setBorder(BorderFactory.createEmptyBorder(
                30, //сверху
                30, //слева
                10, //снизу
                30) //справа
        );

        return pane;
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("О программе");

        AboutProgramWindow swingExample = new AboutProgramWindow();
        Component contents = swingExample.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        frame.pack(); //устанавливает размер окна, необходимый для отображения всех компонентов
        frame.setLocationRelativeTo(null); //устанавливает окно по центру
        frame.setVisible(true);
    }
}
