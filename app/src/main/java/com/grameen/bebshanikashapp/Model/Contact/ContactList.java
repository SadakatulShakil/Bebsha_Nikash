package com.grameen.bebshanikashapp.Model.Contact;

import java.io.Serializable;

public class ContactList implements Serializable {
    private String name;
    private String number;

    public ContactList() {
    }

    public ContactList(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ContactList{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
