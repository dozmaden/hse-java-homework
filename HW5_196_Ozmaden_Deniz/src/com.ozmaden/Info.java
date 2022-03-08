package com.ozmaden;

import javax.swing.*;
import java.awt.*;

public class Info extends JFrame {

    public Info(){
        super("Справка");

        var container = getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy   = 0  ;

        JLabel info1 = new JLabel("<html>Выполнил: Дениз Озмаден, студент БПИ196 </html>");
        constraints.gridy = 1;
        container.add(info1,constraints);

        JLabel feelsbadman = new JLabel("<html> Не бейте плиз!!! Я не смог сделать JDatePicker ((( </html>");
        constraints.gridy = 2;
        container.add(feelsbadman,constraints);

        setTitle("Справка");
        setSize(400, 300);
        setMinimumSize(new Dimension(200,150));
        setVisible(true);
    }
}
