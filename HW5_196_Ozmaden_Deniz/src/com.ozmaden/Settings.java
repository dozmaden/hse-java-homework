package com.ozmaden;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Settings extends JFrame {
    private Database dbase;
    private DefaultTableModel tableModel;

    Settings(DefaultTableModel tableModel, Database dbase) {
        super("Настройки");
        this.tableModel = tableModel;
        this.dbase = dbase;

        Box allbox = new Box(BoxLayout.Y_AXIS);
        getContentPane().add(allbox);

        JButton exportBtn = new JButton("Экспорт");
        JButton importBtn = new JButton("Импорт");

        JPanel panel = new JPanel();
        panel.add(exportBtn);
        panel.add(importBtn);
        getContentPane().add(panel, BorderLayout.SOUTH);

        JPanel pan = new JPanel();

        JLabel comm = new JLabel("Необходимо выбрать локацию импорта/экспорта");
        TextArea pathto = new TextArea("");

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter extfilter = new FileNameExtensionFilter(".txt files",
                "txt");
        chooser.setFileFilter(extfilter);
        chooser.addActionListener(e -> {
            pathto.setText(chooser.getSelectedFile().getAbsolutePath());
        });

        pan.add(comm);
        pan.add(chooser);
        getContentPane().add(pan, BorderLayout.PAGE_START);

        JPanel centre = new JPanel();
        getContentPane().add(centre,BorderLayout.CENTER);

        centre.add(pathto);

        exportBtn.addActionListener(e -> {
            try {
                this.dbase.exportDatabase(pathto.getText());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        importBtn.addActionListener(e -> {
            var people = this.dbase.importDatabase(pathto.getText());
            for (var per:people){
                this.tableModel.addRow(new Person(per).toStringArr());
            }
        });

        setTitle("Импорт/Экспорт контактов");
        setSize(600, 550);
        setMinimumSize(new Dimension(400,450));
        setVisible(true);
    }
}
