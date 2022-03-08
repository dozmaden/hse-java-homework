/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.ozmaden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты на класс с взаимодействием.
 */
class DatabaseInteractionsTest {


    /**
     * Подключение к БД в памяти.
     */
    private static final String connectURL = "jdbc:derby:memory:myDB;create=true";

    /**
     * Взаимодействие с БД.
     */
    private DatabaseInteractions db;

    /**
     * Контакт-пример
     */
    private Contact con;

    @BeforeEach
    void setUp() throws SQLException {
        db = new DatabaseInteractions(connectURL);
        con = new Contact("Ozmaden", "Denis", "-", "+79851275530",
                "-", "Mitino", "06.04.2002", "author");
    }

    @org.junit.jupiter.api.Test
    void addContact() throws SQLException {
        db.addContact(con);
        // проверяем размер
        assertEquals(db.listContacts().size() , 1);
    }

    @org.junit.jupiter.api.Test
    void deleteContact() throws SQLException {
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.deleteContact(genid);
        // проверяем размер
        assertEquals(db.listContacts().size(), 0);
    }

    @org.junit.jupiter.api.Test
    void editSurname() throws SQLException {
        String orig = con.getSurname();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editSurname(genid, "Doe");
        String changed = db.listContacts().get(0).getSurname();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editName() throws SQLException {
        String orig = con.getName();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editName(genid, "John");
        String changed = db.listContacts().get(0).getName();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editPatronym() throws SQLException {
        String orig = con.getPatronym();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editPatronym(genid, "Johnson");
        String changed = db.listContacts().get(0).getPatronym();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editMobphone() throws SQLException {
        String orig = con.getMobilephone();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editMobphone(genid, "Johnson");
        String changed = db.listContacts().get(0).getMobilephone();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editHomephone() throws SQLException {
        String orig = con.getHomephone();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editHomephone(genid, "+905365040411");
        String changed = db.listContacts().get(0).getHomephone();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editAddress() throws SQLException {
        String orig = con.getAddress();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editAddress(genid, "Istanbul");
        String changed = db.listContacts().get(0).getAddress();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editBirthday() throws SQLException {
        String orig = con.getBirthday();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editBirthday(genid, "21.01.1998");
        String changed = db.listContacts().get(0).getBirthday();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void editAbout() throws SQLException {
        String orig = con.getAbout();
        db.addContact(con);
        int genid = db.listContacts().get(0).getId();
        db.editAbout(genid, "student");
        String changed = db.listContacts().get(0).getAbout();
        assertNotEquals(orig, changed);
    }

    @org.junit.jupiter.api.Test
    void getIDs() throws SQLException {
        db.addContact(con);
        db.addContact(new Contact("Ozmaden", "Can", "-", "???????",
                "-", "Aachen", "21.01.1998", "student"));

        ArrayList<Integer> genids = new ArrayList<>();
        genids.add(db.listContacts().get(0).getId());
        genids.add(db.listContacts().get(1).getId());

        assertEquals(genids, db.getIDs());
    }

    @org.junit.jupiter.api.Test
    void checkContact() throws SQLException {
        db.addContact(con);
        db.addContact(new Contact("Ozmaden", "Can", "-", "???????",
                "-", "Aachen", "21.01.1998", "student"));

        int congenid = db.listContacts().get(0).getId();
        assertTrue(db.checkContact(congenid));
    }

    @Test
    void exportDB() throws SQLException {
        db.addContact(con);
        db.addContact(new Contact("Ozmaden", "Can", "-", "???????",
                "-", "Aachen", "21.01.1998", "student"));
        db.addContact(new Contact("Doe", "John", "Johnson", "???????",
                "-", "NYC", "01.01.1980", "detective"));

        try {
            db.exportDB("output.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(new File("output.ser").exists(), String.valueOf(true));
    }

    @Test
    void importDB() throws SQLException {
        db.addContact(con);
        db.addContact(new Contact("Ozmaden", "Can", "-", "???????",
                "-", "Aachen", "21.01.1998", "student"));
        db.addContact(new Contact("Doe", "John", "Johnson", "???????",
                "-", "NYC", "01.01.1980", "detective"));

        try {
            db.exportDB("output.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.deleteContact(db.listContacts().get(0).getId());
        db.deleteContact(db.listContacts().get(1).getId());
        // удаляем из БД, теперь там 1 контакт

        db.addContact(new Contact("Trump", "Donald", "John", "???????",
                "-", "Florida", "14.06.1946", "outcast"));
        db.addContact(new Contact("Biden", "Joseph", "Robinette", "???????",
                "-", "Washington D.C.", "20.10.1942", "president"));
        // добавляем два других

        try {
            db.importDB("output.ser");
            // теперь читаем сохранённые. Там 2 уникальных, 1 повторённый.
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // всего было 3, импортнули 2 уникальных контакта, всего должно быть 5
        assertEquals(db.listContacts().size(), 5);
    }
}
