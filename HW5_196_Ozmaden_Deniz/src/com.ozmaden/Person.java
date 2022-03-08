package com.ozmaden;

import java.text.MessageFormat;

public class Person {

    public String surname;
    public String name;
    public String patronymic;
    public String phone;
    public String address;
    public String birthday;
    public String about;

    public Person(String name, String surname, String patronymic, String phone, String adress,
                  String dateOfBirth, String about)
    {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
        this.address = adress;
        this.birthday = dateOfBirth;
        this.about = about;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        return this.toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1},{2},{3},{4},{5},{6}",
                surname, name, patronymic, phone , address, birthday, about);
    }

    public String[] toStringArr(){
        return new String[]{name, surname, patronymic, phone, address, birthday, about};
    }


    public Person(String input){
        var params = input.split(",");
        this.surname = params[0];
        this.name = params[1];
        this.patronymic = params[2];
        this.phone = params[3];
        this.address = params[4];
        this.birthday = params[5];
        this.about = params[6];
    }
}
