package ru.itmo.web.lesson4.model;

public enum UserColor {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private final String color;

    UserColor(String color) {
        this.color = color;
    }
}
