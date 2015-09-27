package ru.javawebinar.topjava;

public class LoggedUser {

    private static int id = 1;

    public static int id() {
        return id;
    }

    public static void setId(int value) {
        id = value;
    }
}
