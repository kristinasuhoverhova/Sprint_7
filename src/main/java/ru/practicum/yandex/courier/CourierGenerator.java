package ru.practicum.yandex.courier;

import java.util.Random;

public class CourierGenerator {
    private static final Random rnd = new Random();
    public static final Courier defaultCourier = new Courier("name" + getRnd(), "1111" + getRnd(), "Максим" + getRnd());

    private static int getRnd() {
        return rnd.nextInt(1000);
    }

    public static Courier getWithPasswordOnly() {
        return new Courier(null, "1111" + getRnd(), "Максим" + getRnd());
    }

    public static Courier getWithLoginOnly() {
        return new Courier("name" + getRnd(), null, "Максим" + getRnd());
    }

    public static Courier getWithIncorrectValue() {
        return new Courier("name", "11", "Максим");
    }
}
