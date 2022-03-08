package com.ozmaden;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

class PhoneBook extends JFrame
{
    public Database dbase = new Database();
    private DefaultTableModel model;
    private JTable table;

    public PhoneBook()
    {
        super("Phone Book");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {"Фамилия", "Имя", "Отчество", "Моб./Дом. телефон",
                "Адрес","День Рождения","Заметки"});

        dbase.importDatabase("export.txt");
        for (var i: dbase.getDbase()) {
            model.addRow(i.toStringArr());
        }

        table = new JTable(model);
        table.setShowGrid(true);

        JButton deletebtn = new JButton("Удалить");
        deletebtn.addActionListener(e -> {
            int n = table.getSelectedRow();
            if (n == -1){
                JOptionPane.showMessageDialog(new Frame(), "Не выбран контакт!");
            }
            dbase.delete(n);
            model.removeRow(n);
        });

        JButton editbtn = new JButton("Редактировать");
        editbtn.addActionListener(e -> {
            int n = table.getSelectedRow();
            if (n == -1){
                JOptionPane.showMessageDialog(new Frame(), "Не выбран контакт!");
            }
            Editing editing = new Editing(model, dbase, table);
        });

        JButton addbtn = new JButton("Добавить");
        addbtn.addActionListener(e -> {
            Adding adding = new Adding(model, dbase);
        });

        JPanel search = new JPanel();
        search.setLayout(new FlowLayout());
        JButton searchbtn = new JButton("Search");
        JTextField searchfield = new JTextField(20);
        searchbtn.addActionListener(e -> {
            SearchResults result = new SearchResults(model, dbase, table, searchfield.getText());
        });

        JPanel buttons = new JPanel();
        buttons.add(deletebtn);
        buttons.add(editbtn);
        buttons.add(addbtn);
        buttons.add(searchfield);
        buttons.add(searchbtn);
        getContentPane().add(buttons, "South");

        var deleteMenu = new JMenuItem("Удалить");
        deleteMenu.addActionListener(e -> {
            int idx = table.getSelectedRow();
            if (idx == -1){
                JOptionPane.showMessageDialog(new Frame(), "Не выбран контакт!");
            }
            dbase.delete(idx);
            model.removeRow(idx);
        });

        var addMenu = new JMenuItem("Добавить");
        addMenu.addActionListener(e -> {
            Adding adding = new Adding(model, dbase);
        });

        var editMenu = new JMenuItem("Редактировать");
        editMenu.addActionListener(e -> {
            int n = table.getSelectedRow();
            if (n == -1){
                JOptionPane.showMessageDialog(new Frame(), "Не выбран контакт!");
            }
            Editing editing = new Editing(model, dbase, table);
        });

        JButton aboutbtn = new JButton("Справка");
        aboutbtn.addActionListener(e -> {
            Info about = new Info();
        });

        JButton settingsBtn = new JButton("Настройки");
        settingsBtn.addActionListener(e -> {
            Settings set = new Settings(model, dbase);
        });

        var popups = new JPopupMenu("Файл");
        popups.add(deleteMenu);
        popups.add(editMenu);
        popups.add(addMenu);

        JPanel redact_top = new JPanel();
        redact_top.add(popups);
        redact_top.setComponentPopupMenu(popups);

        redact_top.add(settingsBtn);
        getContentPane().add(redact_top,"North");

        redact_top.add(aboutbtn);
        getContentPane().add(redact_top,"North");

        Box allbox = new Box(BoxLayout.Y_AXIS);
        allbox.add(new JScrollPane(table));
        getContentPane().add(allbox);

        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
       var pb =  new PhoneBook();
       pb.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    pb.dbase.exportDatabase("export.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
