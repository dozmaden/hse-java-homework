package com.ozmaden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchResults extends JFrame {
    private Database dbase;
    private JTable table;
    private static DefaultTableModel tableModel;


    public SearchResults(DefaultTableModel model, Database dbase, JTable table, String filter){
        super("Search");
        this.dbase = dbase;
        tableModel = model;

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"Фамилия", "Имя", "Отчество", "Моб./Дом. телефон",
                "Адрес","День Рождения","Заметки"});

        var filtered = this.dbase.filtering(filter);
        for (var p:filtered) {
            tableModel.addRow(p.toStringArr());
        }

        this.table = new JTable(tableModel);
        this.table.setShowGrid(true);

        Box allbox = new Box(BoxLayout.Y_AXIS);
        allbox.add(new JScrollPane(this.table));
        getContentPane().add(allbox);

        setTitle("Search results");
        setSize(600, 300);
        setMinimumSize(new Dimension(300,200));
        setVisible(true);
    }
}
