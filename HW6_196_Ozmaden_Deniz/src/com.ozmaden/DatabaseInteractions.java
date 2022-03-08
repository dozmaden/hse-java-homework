/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.ozmaden;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Основной класс для того, чтобы работать с базой данных.
 */
public class DatabaseInteractions {

    /**
     * SQL команда для создания базы данных.
     */
    private static final String createCommand = "CREATE TABLE PHONE_BOOK "
            + "(CONTACT_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
            + "CONTACT_SURNAME VARCHAR(64) NOT NULL, "
            + "CONTACT_NAME VARCHAR(64) NOT NULL,"
            + "CONTACT_PATRONYM VARCHAR(64),"
            + "CONTACT_MOBPHONE VARCHAR(64),"
            + "CONTACT_HOMEPHONE VARCHAR(64),"
            + "CONTACT_ADDRESS VARCHAR(64),"
            + "CONTACT_BIRTHDAY VARCHAR(64),"
            + "CONTACT_ABOUT VARCHAR(64))";

    /**
     * Строка для подключения к БД.
     */
    private String connectURL;

    /**
     * Метод сделан для тестов. Там я меняю это на БД в памяти.
     * @param connectURL ссылка на подключение
     */
    public void setConnectURL(String connectURL) {
        this.connectURL = connectURL;
    }

    /**
     * Подключение.
     */
    private Connection conn;

    /**
     * Команда.
     */
    private Statement stat;

    // подготовленные команды
    private final PreparedStatement psInsert;
    private final PreparedStatement psDelete;
    private final PreparedStatement psUpdateSurname;
    private final PreparedStatement psUpdateName;
    private final PreparedStatement psUpdatePatronym;
    private final PreparedStatement psUpdateMobphone;
    private final PreparedStatement psUpdateHomephone;
    private final PreparedStatement psUpdateAddress;
    private final PreparedStatement psUpdateBirthday;
    private final PreparedStatement psUpdateAbout;

    /**
     * Конструктор. Получает ссылку на подключение.
     * @param url ссылка на подключение
     * @throws SQLException может кинуть
     */
    public DatabaseInteractions(String url) throws SQLException {
        connectURL = url;
        conn = DriverManager.getConnection(connectURL);
        stat = conn.createStatement();

        if (!CheckTable(conn)) {
            stat.execute(createCommand);
        }

        psInsert = conn.prepareStatement("INSERT INTO PHONE_BOOK(CONTACT_SURNAME, CONTACT_NAME, " +
                "CONTACT_PATRONYM, CONTACT_MOBPHONE, CONTACT_HOMEPHONE, CONTACT_ADDRESS, " +
                "CONTACT_BIRTHDAY, CONTACT_ABOUT) values (?, ?, ?, ?, ?, ?, ?, ?)");

        psDelete = conn.prepareStatement("DELETE FROM PHONE_BOOK WHERE CONTACT_ID = (?)");

        psUpdateSurname = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_SURNAME = ? WHERE CONTACT_ID = ?");
        psUpdateName = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_NAME = ? WHERE CONTACT_ID = ?");
        psUpdatePatronym = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_PATRONYM = ? WHERE CONTACT_ID = ?");
        psUpdateMobphone = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_MOBPHONE = ? WHERE CONTACT_ID = ?");
        psUpdateHomephone = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_HOMEPHONE = ? WHERE CONTACT_ID = ?");
        psUpdateAddress = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_ADDRESS = ? WHERE CONTACT_ID = ?");
        psUpdateBirthday = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_BIRTHDAY = ? WHERE CONTACT_ID = ?");
        psUpdateAbout = conn.prepareStatement("UPDATE PHONE_BOOK SET CONTACT_ABOUT = ? WHERE CONTACT_ID = ?");
    }

    /**
     * Метод для проверки существует таблица или нет.
     * @param conTst подключение
     * @return да или нет
     * @throws SQLException может кинуть
     */
    public static boolean CheckTable(Connection conTst) throws SQLException {
        try {
            Statement s = conTst.createStatement();
            s.execute("update PHONE_BOOK set CONTACT_SURNAME = 'OZMADEN', CONTACT_NAME = 'DENIZ'," +
                    "CONTACT_PATRONYM = '-',  CONTACT_MOBPHONE = '+79851275530', CONTACT_HOMEPHONE = '-'," +
                    "CONTACT_ADDRESS = 'Mitino', CONTACT_BIRTHDAY = '06.04.2002', CONTACT_ABOUT = '-' where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05"))
            {
                return false;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("Incorrect table definition. Drop table and rerun this program");
                throw sqle;
            } else {
                System.out.println("Unhandled SQLException");
                throw sqle;
            }
        }
        return true;
    }

    /**
     * Метод для получение контактов из БД.
     * @return ArrayList контактов
     * @throws SQLException может кинуть
     */
    public ArrayList<Contact> listContacts() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        // получаем контакты
        ResultSet pbquery = stat.executeQuery("select CONTACT_ID, CONTACT_SURNAME, CONTACT_NAME, CONTACT_PATRONYM,"
                + "CONTACT_MOBPHONE, CONTACT_HOMEPHONE, CONTACT_ADDRESS, CONTACT_BIRTHDAY, CONTACT_ABOUT" +
                " from PHONE_BOOK");

        while (pbquery.next()) {
            contacts.add(new Contact(pbquery.getInt(1), pbquery.getString(2),
                    pbquery.getString(3), pbquery.getString(4),
                    pbquery.getString(5), pbquery.getString(6),
                    pbquery.getString(7), pbquery.getString(8),
                    pbquery.getString(9)));
        }
        return contacts;
    }

    /**
     * Метод добавления контактов.
     * @param con контакт
     * @throws SQLException может кинуть
     */
    public void addContact(Contact con) throws SQLException {
        psInsert.setString(1, con.getSurname());
        psInsert.setString(2, con.getName());
        psInsert.setString(3, con.getPatronym());
        psInsert.setString(4, con.getMobilephone());
        psInsert.setString(5, con.getHomephone());
        psInsert.setString(6, con.getAddress());
        psInsert.setString(7, con.getBirthday());
        psInsert.setString(8, con.getAbout());
        psInsert.executeUpdate();
    }

    /**
     * Метод удаления контакта.
     * @param id уникальный id контакта
     * @throws SQLException может кинуть
     */
    public void deleteContact(int id) throws SQLException {
        psDelete.setInt(1, id);
        psDelete.executeUpdate();
    }

    /**
     * Метод изменения фамилии контакта.
     * @param id уникальный идентификатор контакта
     * @param surname фамилия
     * @throws SQLException может кинуть
     */
    public void editSurname(int id, String surname) throws SQLException {
        psUpdateSurname.setString(1, surname);
        psUpdateSurname.setInt(2, id);
        psUpdateSurname.executeUpdate();
    }

    /**
     * Метод изменения имени контакта.
     * @param id уникальный идентификатор контакта
     * @param name фамилия
     * @throws SQLException может кинуть
     */
    public void editName(int id, String name) throws SQLException {
        psUpdateName.setString(1, name);
        psUpdateName.setInt(2, id);
        psUpdateName.executeUpdate();
    }

    /**
     * Метод изменения отчества контакта.
     * @param id уникальный идентификатор
     * @param patronym отчества
     * @throws SQLException может кинуть
     */
    public void editPatronym(int id, String patronym) throws SQLException {
        psUpdatePatronym.setString(1, patronym);
        psUpdatePatronym.setInt(2, id);
        psUpdatePatronym.executeUpdate();
    }

    /**
     * Метод изменения моб. телефона контакта.
     * @param id уникальный идентификатор
     * @param mobphone отчества
     * @throws SQLException может кинуть
     */
    public void editMobphone(int id, String mobphone) throws SQLException {
        psUpdateMobphone.setString(1, mobphone);
        psUpdateMobphone.setInt(2, id);
        psUpdateMobphone.executeUpdate();
    }

    /**
     * Метод изменения изменения дом. телефона контакта.
     * @param id уникальный идентификатор
     * @param homephone домашний телефон
     * @throws SQLException может кинуть
     */
    public void editHomephone(int id, String homephone) throws SQLException {
        psUpdateHomephone.setString(1, homephone);
        psUpdateHomephone.setInt(2, id);
        psUpdateHomephone.executeUpdate();
    }

    /**
     * Метод изменения изменения адреса контакта.
     * @param id уникальный идентификатор
     * @param address домашний телефон
     * @throws SQLException может кинуть
     */
    public void editAddress(int id, String address) throws SQLException {
        psUpdateAddress.setString(1, address);
        psUpdateAddress.setInt(2, id);
        psUpdateAddress.executeUpdate();
    }

    /**
     * Метод изменения изменения даты рождения контакта.
     * @param id уникальный идентификатор
     * @param birthday дата рождения
     * @throws SQLException может кинуть
     */
    public void editBirthday(int id, String birthday) throws SQLException {
        psUpdateBirthday.setString(1, birthday);
        psUpdateBirthday.setInt(2, id);
        psUpdateBirthday.executeUpdate();
    }

    /**
     * Метод изменения изменения заметок о контакте.
     * @param id уникальный идентификатор
     * @param about заметки
     * @throws SQLException может кинуть
     */
    public void editAbout(int id, String about) throws SQLException {
        psUpdateAbout.setString(1, about);
        psUpdateAbout.setInt(2, id);
        psUpdateAbout.executeUpdate();
    }

    /**
     * Метод для получения идентификаторов всех контактов в БД.
     * @return ArrayList идентификаторов
     * @throws SQLException может кинуть
     */
    public ArrayList<Integer> getIDs() throws SQLException {
        ResultSet pbquery = stat.executeQuery("select CONTACT_ID from PHONE_BOOK");
        ArrayList<Integer> ids = new ArrayList<>();
        while (pbquery.next()) {
            ids.add(pbquery.getInt(1));
        }
        return ids;
    }

    /**
     * Метод для обновления контактов в БД после импорта файла.
     * @param contacts ArrayList контактов
     * @throws SQLException может кинуть
     */
    public void updateContacts(ArrayList<Contact> contacts) throws SQLException {
        ArrayList<Integer> ids = getIDs();
        for (Contact con : contacts){
            if (!ids.contains(con.getId())){
                addContact(con);
            }
        }
    }

    /**
     * Метод для проверки, есть ли контакт уже в БД (при импорте из файла)
     * @param id уникальный идентификатор
     * @return да/нет
     * @throws SQLException может кинуть
     */
    public boolean checkContact(int id) throws SQLException {
        return getIDs().contains(id);
    }

    /**
     * Метод для экспорта контактов из БД в файл.
     * @param path путь к файлу
     * @throws IOException может кинуть при записи
     * @throws SQLException может кинуть при запросе
     */
    public void exportDB(String path) throws IOException, SQLException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        ArrayList<Contact> contacts = listContacts();
        out.writeObject(contacts);
        out.close();
    }

    /**
     * Метод для импорта контактов из файла в БД.
     * @param path путь к фалу
     * @throws IOException может кинуть при импорте
     * @throws SQLException может кинуть при запросе
     * @throws ClassNotFoundException может кинуть если нет класса
     */
    public void importDB(String path) throws IOException, SQLException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        ArrayList<Contact> imported = (ArrayList<Contact>) in.readObject();
        in.close();
        updateContacts(imported);
    }

    /**
     * Выход из БД.
     * @throws SQLException может кинуть
     */
    public void exitDB() throws SQLException {
        psInsert.close();
        psDelete.close();
        psUpdateSurname.close();
        psUpdateName.close();
        psUpdatePatronym.close();
        psUpdateMobphone.close();
        psUpdateHomephone.close();
        psUpdateAddress.close();
        psUpdateBirthday.close();
        psUpdateAbout.close();
        stat.close();
        conn.close();
        DriverManager.getConnection("jdbc:derby:;shutdown=true");
    }
}
