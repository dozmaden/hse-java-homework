/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.ozmaden;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Класс консоли для взаимодействия с пользователем. Н
 * а это невозможно сделать тесты (так как почти постоянно требует пользовательского ввода)
 */
public class ConsoleInterface {
    /**
     * Таблица, которая будет показывать в консоле
     */
    private ArrayList<String> lines;

    /**
     * Класс взаимодействия с базой данных.
     */
    private DatabaseInteractions db;

    /**
     * Конструктор. Создаёт таблицу.
     */
    public ConsoleInterface() {
        ResetLines();
    }

    /**
     * Метод возвразения массива таблицы в начальную позицию
     */
    public void ResetLines(){
        lines = new ArrayList<>();
        lines.add("| ID |       Фамилия      |        Имя         |       Отчество     |    Моб. телефон    |" +
                "    Дом. телефон    |        Адрес       |      Дата рожд.    |       Заметки     |");
        lines.add("| -- | ------------------ | ------------------ | ------------------ | ------------------ |" +
                " ------------------ | ------------------ | ------------------ | ----------------- |");
    }

    /**
     * Главное меню взаимодействия с пользователем. Тут вводятся команды.
     */
    public void EnterInterface()  {
        System.out.println("Добро пожаловать в консольное приложение телефонной книги!");

        do {
            System.out.println("Пожалуйста введите '$ ./contacts' чтобы начать работу с базой данных!");
        } while (!getCommand(true).equals("$ ./contacts"));

        try {
            db = new DatabaseInteractions("jdbc:derby:Phone_Book;create=true");
            System.out.println("Программа успешно подключились к базе данных телефонной книги!");

            System.out.println("Введите 'list', чтобы увидеть все контакты. " +
                    "Введите 'about' для дополнительной информации.");

            String ans;
            do{
                ans = getCommand(true);

                // использую trim на всякий случай, чтобы команда с пробелом например прошла
                switch (ans.trim()){
                    case "list":
                        listContacts();
                        break;
                    case "add":
                        addContact();
                        break;
                    case "delete":
                        deleteContact();
                        break;
                    case "edit":
                        editContact();
                        break;
                    case "import":
                        importPB();
                        break;
                    case "export":
                        exportPB();
                        break;
                    case "about":
                        about();
                        break;
                    case "exit":
                        try {
                            db.exitDB();
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Программа прекращает работу!");
                        break;
                    default:
                        System.out.println("Некорректный ввод! Пожалуйста попробуйте снова");
                        break;
                }
            } while (!ans.equals("exit"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка в работе с базой данных!");
        }
    }


    /**
     * Метод для получения команды/строки
     * @param necessary обязательно ли что-то должно быть
     * @return строку из ввода пользователя
     */
    public String getCommand(boolean necessary) {
        String ans = "";
        boolean ex = true;
        do{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                // требовалось в описании задания
                System.out.print(">");

                ans = br.readLine();
                if ((ans.trim().isEmpty() || ans.equals("-") ) && necessary){
                    System.out.println("Некорректый ввод!");
                } else if (ans.length() >= 50){
                    // думаю вводы больше, чем в 50 в телефонной книжке не нужны
                    System.out.println("Слишком длинный ввод! Сократите введённые данные");
                } else {
                    ex = false;
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (ex);
        return ans;
    }

    /**
     * Метод для получения значения ID контакта
     * @return ID контакта
     */
    public int getInt(){
        int id = -1;
        boolean ex = true;
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print(">");
                id = Integer.parseInt(br.readLine());
                ex = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Пожалуйста попробуйте снова!");
            }
        } while (ex);
        return id;
    }

    /**
     * Метод, который высвечивает на таблице все вещи из БД.
     * Реализует команду list.
     */
    public void listContacts()  {
        try {
            ArrayList<Contact> contacts = db.listContacts();
            for (Contact con : contacts){
                lines.add(con.toString());
            }
            for (String line : lines) {
                System.out.println(line);
            }
            ResetLines();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            System.out.println("Произошла ошибка при получении контактов!");
        }
    }

    /**
     * Метод для добавления контакта в БД.
     * Реализует команду add.
     */
    public void addContact(){
        // сделал ввод как из примера описания задания
        System.out.println("Введите фамилию:");
        String surname = getCommand(true);

        System.out.println("Введите имя:");
        String name = getCommand(true);

        System.out.println("Введите отчество (не обязательно):");
        String patronym = getCommand(false);
        System.out.println("Введите мобильный телефон:");
        String mobphone = getCommand(false);

        String homephone;
        if(mobphone.isEmpty() || mobphone.equals("-")){
            // если моб. телефон не ввёлся, то на этот раз дом. телефон обязательно
            // так требуется в описани задания
            System.out.println("Введите домашний телефон (обязательно!):");
            homephone = getCommand(true);
        } else{
            System.out.println("Введите домашний телефон:");
            homephone = getCommand(false);
        }

        System.out.println("Введите адрес:");
        String address = getCommand(false);

        System.out.println("Введите дату рождения:");
        String birthday = getCommand(false);

        System.out.println("Введите заметки о контакте:");
        String about = getCommand(false);

        // создаём контакт из введённых данных
        Contact person = new Contact(surname, name, patronym, mobphone, homephone, address, birthday, about);

        try {
            // идём в базу данных, добавляем
            db.addContact(person);
            System.out.println("Контакт добавлен!");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка при добавлении контакта!");
        }
    }

    /**
     * Метод для удаления контакта из БД.
     * Реализует команду delete.
     */
    public void deleteContact() {
        System.out.println("Напишите ID контакта, которого нужно удалить:");
        int id = getInt();

        try {
            if (!db.checkContact(id)){
                System.out.println("Такого контакта не существует!");
                return;
            }
            db.deleteContact(id);
            System.out.println("Контакт успешно удалён!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка при удалении контакта!");
        }
    }

    /**
     * Метод для редактирования контакта из БД.
     * Реализует команду edit.
     */
    public void editContact(){
        System.out.println("Напишите ID контакта, который нужно отредактировать:");
        int id = getInt();

        try {
            if (!db.checkContact(id)){
                System.out.println("Такого контакта не существует!");
                return;
            }

            System.out.println("Что именно вы хотите редактировать? Введите название колонки: ");
            String ans = getCommand(true);

            // делаю это ради того, чтобы команда сработала даже если есть большие буквы
            switch (ans.toLowerCase(Locale.ROOT)) {
                case "фамилия":
                    System.out.println("Введите фамилию:");
                    String surname = getCommand(true);
                    db.editSurname(id, surname);
                    System.out.println("Фамилия изменена!");
                    break;
                case "имя":
                    System.out.println("Введите имя:");
                    String name = getCommand(true);
                    db.editName(id, name);
                    System.out.println("Имя изменено!");
                    break;
                case "отчество":
                    System.out.println("Введите отчество");
                    String patronym = getCommand(false);
                    db.editPatronym(id, patronym);
                    System.out.println("Отчество изменено!");
                    break;
                case "моб. телефон":
                case "мобильный телефон":
                    System.out.println("Введите мобильный телефон:");
                    String mobphone = getCommand(true);
                    db.editMobphone(id, mobphone);
                    System.out.println("Мобильный телефон изменён!");
                    break;
                case "дом. телефон":
                case "домашний телефон":
                    System.out.println("Введите домашний телефон:");
                    String homephone = getCommand(true);
                    db.editHomephone(id, homephone);
                    System.out.println("Домашний телефон изменён!");
                    break;
                case "адрес":
                    System.out.println("Введите адрес:");
                    String address = getCommand(false);
                    db.editAddress(id, address);
                    System.out.println("Адрес изменён!");
                    break;
                case "дата рожд.":
                case "дата рождения":
                    System.out.println("Введите дату рождения:");
                    String birthday = getCommand(false);
                    db.editBirthday(id, birthday);
                    System.out.println("Дата рождения изменена!");
                    break;
                case "заметки":
                    System.out.println("Введите заметки:");
                    String about = getCommand(false);
                    db.editAbout(id, about);
                    System.out.println("Заметки изменены!");
                    break;
                default:
                    System.out.println("Некорректный ввод! Пожалуйста попробуйте снова");
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка при изменении контакта!");
        }
    }

    /**
     * Метод для импорта контактов в файл.
     * Реализует команду import.
     */
    public void importPB() {
        try {
            System.out.println("Укажите путь к файлу с его расширением (или просто файл): ");
            String path = getFilePath();
            System.out.println("Импорт начался!");
            db.importDB(path);
            System.out.println("Импорт закончился!");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.out.println("Произошла ошибка при обновлении базы данных!");
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Произошла IO ошибка!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для экспорта контактов в файл.
     * Реализует команду export.
     */
    public void exportPB(){
        try {
            System.out.println("Укажите путь к файлу с его расширением (или просто файл): ");
            String path = getFilePath();
            System.out.println("Экспорт начался!");
            db.exportDB(path);
            System.out.println("Экспорт закончился!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Произошла ошибка при получении контактов с базы данных!");
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Произошла IO ошибка!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для получения пути к файлу (импорт/экспорт)
     */
    public String getFilePath(){
        String ans = "";
        boolean ex = true;
        do{
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (ans.isEmpty()) {
                    System.out.print(">");
                    ans = br.readLine();
                    if (ans.isEmpty()){
                        System.out.println("Некорректный ввод!");
                    }else {
                        ex = false;
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (ex);

        return ans;
    }


    /**
     * Метод для описания сделанной работы
     */
    public void about(){
        System.out.println("Работу выполнил: Озмаден Дениз, студент группы БПИ196");
        System.out.println("Список комманд для консооли: list, add, delete, edit, import, export, exit");
        System.out.println("Я сделал так, что если поле > 20 в длину, то в консоле изображается только часть." +
                "В настоящем БД же остаётся так, как вводилось");
        System.out.println("Не обращай внимания, я тут просто молюсь на десятку ༼ つ ◕_◕ ༽つ");
    }
}
