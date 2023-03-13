package ru.practicum.yandex.order;

public class OrderGenerator {
    public static Order getWithGrey() {
        return new Order("Максим", "Суховерхов", "Маршала Прошлякова,13", new String[]{"GREY"});
    }

    public static Order getWithBlack() {
        return new Order("Максим", "Суховерхов", "Маршала Прошлякова,13", new String[]{"BLACK"});
    }

    public static Order getWithoutTwoColours() {
        return new Order("Максим", "Суховерхов", "Маршала Прошлякова,13", null);
    }

    public static Order getWithTwoColours() {
        return new Order("Максим", "Суховерхов", "Маршала Прошлякова,13", new String[]{"GREY", "BLACK"});
    }
}