package com.ozmaden;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

public class Database {

    private ArrayList<Person> dbase;

    public ArrayList<Person> getDbase() {
        return dbase;
    }

    public void setDbase(ArrayList<Person> dbase) {
        this.dbase = dbase;
    }

    public Database() {
        setDbase(new ArrayList<>());
    }

    public boolean add(Person person) {
        if (alreadyIn(getDbase(), person)) {
            return false;
        } else {
            getDbase().add(person);
            return true;
        }
    }

    public boolean alreadyIn(ArrayList<Person> people, Person person) {
        for (var p : people) {
            if (person.toString().equals(person.toString())) {
                return true;
            }
        }
        return false;
    }

    public void delete(int n) {
        try {
            getDbase().remove(n);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Person> filtering(String input) {
        ArrayList<Person> people = new ArrayList<>();
        var filter = input.split(" ");
        for (var per: getDbase()) {
            for (var c : filter) {
                if (per.surname.equals(filter) ||
                        per.name.equals(filter) ||
                        per.patronymic.equals(filter)) {
                    if (!alreadyIn(people, per)) {
                        people.add(per);
                    }
                }
            }
        }
        return people;
    }

    public void exportDatabase(String path) throws IOException {
        ArrayList<String> export = new ArrayList<>();
        for (var p : getDbase()) {
            export.add(p.toString());
        }

        FileWriter exporter = new FileWriter(path);
        for (String str : export) {
            exporter.write(str + ",");
        }
        exporter.close();
    }

    public ArrayList<String> importDatabase(String path) {
        ArrayList<String> imported = new ArrayList<>();
        try {
            BufferedReader rdr = new BufferedReader(new FileReader(path));
            String line = rdr.readLine();
            while (line != null) {
                imported.add(line);
                line = rdr.readLine();
            }
            rdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var l : imported) {
            Person p = new Person(l);
            add(p);
        }
        return imported;
    }
}
