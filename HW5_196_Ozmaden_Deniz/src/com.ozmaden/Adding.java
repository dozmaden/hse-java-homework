package com.ozmaden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.MessageFormat;

public class Adding extends JFrame {
    private static DefaultTableModel tableModel;
    private Database dbase;

    public Adding(DefaultTableModel tableModel, Database dbase){
        super("Добавление контакта");
        Adding.tableModel = tableModel;
        this.dbase = dbase;

        createMenu(getContentPane(), this.dbase);
        setTitle("Добавление контакта");
        setSize(400, 800);
        setMinimumSize(new Dimension(300,500));
        setVisible(true);
    }


    public static void createMenu(Container container, Database dbase)
    {
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton addBtn;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy   = 0;

        JLabel entername = new JLabel("Имя:");
        constraints.gridx = 0;
        container.add(entername, constraints);

        JTextField name = new JTextField("");
        constraints.gridx = 1;
        container.add(name, constraints);


        JLabel surnameL = new JLabel("Фамилия:");

        constraints.gridx = 0;
        constraints.gridy = 1 ;
        container.add(surnameL, constraints);

        JTextField entersurname = new JTextField("");
        constraints.gridx = 1;
        constraints.gridy = 1 ;
        container.add(entersurname, constraints);

        JLabel entergran = new JLabel("Отчество");
        constraints.gridx = 0;
        constraints.gridy = 2;
        container.add(entergran, constraints);

        JTextField patronym = new JTextField("");
        constraints.gridx = 1;
        constraints.gridy = 2;
        container.add(patronym, constraints);

        JLabel enterphone = new JLabel("Телефон");
        constraints.gridx = 0;
        constraints.gridy = 3;
        container.add(enterphone, constraints);

        JTextField phone = new JTextField("");
        constraints.gridx = 1;
        constraints.gridy = 3;
        container.add(phone, constraints);

        JLabel enteraddress = new JLabel("Адрес");
        constraints.gridx = 0;
        constraints.gridy = 4;
        container.add(enteraddress, constraints);

        JTextField address = new JTextField("");
        constraints.gridx = 1;
        constraints.gridy = 4;
        container.add(address, constraints);

        JLabel birthday = new JLabel("Дата Рождения");
        constraints.gridx = 0;
        constraints.gridy = 5;
        container.add(birthday, constraints);

        JTextField enterbirthday = new JTextField("");
        constraints.gridx = 1;
        constraints.gridy = 5;
        container.add(enterbirthday, constraints);

        JTextField comment = new JTextField("Заметки");
        constraints.ipady     = 20;
        constraints.gridwidth = 2;
        constraints.weightx   = 0.0;
        constraints.gridx     = 0;
        constraints.gridy     = 6;
        container.add(comment, constraints);

        addBtn = new JButton("Добавить контакт");
        constraints.weighty   = 1.0;
        constraints.ipady     = 20;
        constraints.anchor    = GridBagConstraints.PAGE_END;
        constraints.insets    = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 2;
        constraints.gridx     = 1;
        constraints.gridy     = 7;
        container.add(addBtn, constraints);


        addBtn.addActionListener(e -> {
            if (name.getText().isEmpty() || name.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ИМЯ!");

            }
            else if (entersurname.getText().isEmpty() || entersurname.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ФАМИЛИЮ!");

            }
            else if (phone.getText().isEmpty() || phone.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ТЕЛЕФОН!");

            }
            Person p = new Person(
                    MessageFormat.format("{0},{1},{2},{3},{4},{5},{6}", getFields(name),
                            getFields(entersurname), getFields(patronym), getFields(phone),
                            getFields(address), getFields(enterbirthday), getFields(comment)));
            if (dbase.add(p)) {
                tableModel.addRow(p.toStringArr());
            }
        });
    }

    public static String getFields(JTextField textField){
        if (textField.getText().isEmpty() || textField.getText().isBlank()){
            return " ";
        }
        else{
            return textField.getText();
        }
    }
}
