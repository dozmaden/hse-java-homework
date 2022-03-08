package com.ozmaden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.MessageFormat;

public class Editing extends JDialog {

    private Database dbase;

    public Editing(DefaultTableModel tablemodel, Database dbase, JTable table){
        setTitle("Добавление контакта");
        this.dbase = dbase;
        createMenu(getContentPane(), this.dbase, table);
        setSize(400, 750);
        setMinimumSize(new Dimension(350,600));
        setVisible(true);
    }

    public static void createMenu(Container container, Database db, JTable table)
    {
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        int n = table.getSelectedRow();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy   = 0;

        JLabel entername = new JLabel("Имя:");
        constraints.gridx = 0;
        container.add(entername, constraints);

        JTextField name = new JTextField(table.getValueAt(n,0).toString());
        constraints.gridx = 1;
        container.add(name, constraints);

        JLabel entersurname = new JLabel("Фамилия:");
        constraints.gridx = 0;
        constraints.gridy = 1 ;
        container.add(entersurname, constraints);

        JTextField surname = new JTextField(table.getValueAt(n,1).toString());
        constraints.gridx = 1;
        constraints.gridy = 1 ;
        container.add(surname, constraints);

        JLabel entergrand = new JLabel("Отчество");
        constraints.gridx = 0;
        constraints.gridy = 2;
        container.add(entergrand, constraints);

        JTextField patronym = new JTextField(table.getValueAt(n,2).toString());
        constraints.gridx = 1;
        constraints.gridy = 2;
        container.add(patronym, constraints);

        JLabel enterphone = new JLabel("Телефон:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        container.add(enterphone, constraints);

        JTextField phone = new JTextField(table.getValueAt(n,3).toString());
        constraints.gridx = 1;
        constraints.gridy = 3;
        container.add(phone, constraints);

        JLabel enteraddress = new JLabel("Адрес: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        container.add(enteraddress, constraints);

        JTextField address = new JTextField(table.getValueAt(n,4).toString());
        constraints.gridx = 1;
        constraints.gridy = 4;
        container.add(address, constraints);

        JLabel enterbirthday = new JLabel("Дата рождения: ");
        constraints.gridx = 0;
        constraints.gridy = 5;
        container.add(enterbirthday, constraints);

        JTextField birthday = new JTextField(table.getValueAt(n,5).toString());
        constraints.gridx = 1;
        constraints.gridy = 5;
        container.add(birthday, constraints);

        JTextField about = new JTextField(table.getValueAt(n,6).toString());
        constraints.gridwidth = 2;
        constraints.gridx     = 0;
        constraints.gridy     = 6;
        constraints.ipady     = 20;
        constraints.weightx   = 0.0;
        container.add(about, constraints);

        JButton editbutton = new JButton("Редактировать контакт");
        constraints.gridwidth = 2;
        constraints.gridx     = 1;
        constraints.gridy     = 7;
        constraints.ipady     = 20;
        constraints.weighty   = 1.0;
        constraints.anchor    = GridBagConstraints.PAGE_END;
        constraints.insets    = new Insets(0, 0, 0, 0);
        container.add(editbutton, constraints);


        editbutton.addActionListener(e -> {
            if (name.getText().isEmpty() || name.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ИМЯ!");
            }
            else if (surname.getText().isEmpty() || surname.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ФАМИЛИЮ!");

            }
            else if (phone.getText().isEmpty() || phone.getText().isBlank()) {
                JOptionPane.showMessageDialog(new Frame(), "ЗАПОЛНИТЕ ТЕЛЕФОН!");

            }
            Person p = new Person(MessageFormat.format("{0},{1},{2},{3},{4},{5},{6}",
                    getFields(name), getFields(surname), getFields(patronym), getFields(phone),
                    getFields(address), getFields(birthday), getFields(about)));

            int row = table.getSelectedRow();
            if (!db.alreadyIn(db.getDbase(), p)) {
                db.getDbase().set(row,p);
                table.setValueAt(name.getText(),row,0);
                table.setValueAt(surname.getText(),row,1);
                table.setValueAt(patronym.getText(),row,2);
                table.setValueAt(phone.getText(),row,3);
                table.setValueAt(address.getText(),row,4);
                table.setValueAt(birthday.getText(),row,5);
                table.setValueAt(about.getText(),row,6);
            }
        });
    }

    public static String getFields(JTextField textField){
        if (textField.getText().isBlank()|| textField.getText().isEmpty()){
            return " ";
        }
        else{
            return textField.getText();
        }
    }
}
