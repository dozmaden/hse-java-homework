/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.ozmaden;

/**
 * Класс контакта в телефонной книге
 */
public class Contact implements java.io.Serializable {

    private int id;
    private final String name;
    private final String surname;
    private final String patronym;
    private final String mobilephone;
    private final String homephone;
    private final String address;
    private final String birthday;
    private final String about;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronym() {
        return patronym;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getHomephone() {
        return homephone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAbout() {
        return about;
    }

    public int getId() {
        return id;
    }

    /**
     * Конструктор контакта, когда мы добавляем его в БД.
     * Тут нет ID, так как он ещё не сгенерировался.
     * @param surname Фамилия.
     * @param name Имя.
     * @param patronym Отчество.
     * @param mobphone Мобильный телефон.
     * @param homephone Домашний телефон.
     * @param address Адрес.
     * @param birthday День рождения.
     * @param about Заметки.
     */
    public Contact(String surname, String name, String patronym, String mobphone,
                   String homephone, String address, String birthday, String about) {
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.mobilephone = mobphone;
        this.homephone = homephone;
        this.address = address;
        this.birthday = birthday;
        this.about = about;
    }

    /**
     * Конструктор контакта, когда мы получаем его из базы данных.
     * @param id тут есть ID, потому что он уже автоматически сгенерировался в БД.
     * @param surname Фамилия.
     * @param name Имя.
     * @param patronym Отчество.
     * @param mobphone Мобильный телефон.
     * @param homephone Домашний телефон.
     * @param address Адрес.
     * @param birthday День рождения.
     * @param about Заметки.
     */
    public Contact(int id, String surname, String name, String patronym, String mobphone,
                   String homephone, String address, String birthday, String about) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.mobilephone = mobphone;
        this.homephone = homephone;
        this.address = address;
        this.birthday = birthday;
        this.about = about;
    }


    /**
     * Метод для того, чтобы контакт показывался в моей таблице.
     * @return Строчное представление контакта.
     */
    @Override
    public String toString() {
        return String.format("| %d| %s | %s | %s | %s | %s | %s | %s | %s|", id,
                cutIfTooBig(surname), cutIfTooBig(name), cutIfTooBig(patronym), cutIfTooBig(mobilephone),
                cutIfTooBig(homephone), cutIfTooBig(address), cutIfTooBig(birthday), cutIfTooBig(about));
    }


    /**
     * Метод для сокращения строкового представления поля контакта, если оно слишком большое для таблицы
     * @param str поле
     * @return сокращённое поле
     */
    public String cutIfTooBig (String str){
        if (str.length() > 18){
            // беру только 18 символов. Всего в ячейке = 20.
            return str.substring(0, 18);
        } else {
            // для того, чтобы не было дырок в таблице, заполняю оставшееся пробелами
            while(str.length() < 18){
                str += " ";
            }
            return str;
        }
    }
}
