package com.ozmaden;

import java.util.TimerTask;

/*
Класс тележки. Наследует класс TimerTask, чтобы каждые 2 секунды докладывать свою локацию.
 */
public class Cart extends TimerTask {

    /**
     * Конструктор тележки
     * @param x икс
     * @param y игрик
     */
    public Cart(double x, double y) {
        setX(x);
        setY(y);
    }

    /*
    Координаты локации
     */
    private static double x;
    private static double y;

    /**
     * Геттер для икс
     * @return икс
     */
    public static double getX() {
        return x;
    }

    /**
     * Сеттер для икс
     * @param x икс
     */
    public static void setX(double x) {
        Cart.x = x;
    }

    /**
     * Геттер для игрик
     * @return игрик
     */
    public static double getY() {
        return y;
    }

    /**
     * Сеттер для игрик
     * @param y игрик
     */
    public static void setY(double y) {
        Cart.y = y;
    }

    /**
     Каждые две секунды будет запускаться локацию координат
     */
    public void run() {
        System.out.printf("%nCurrent Cart's coordinates are: %s %n", getLocation());
    }

    /**
     * Координаты. В отдельном методе, чтобы также отдельно вызвать метод в мейне под конец.
     * @return строку с координатами
     */
    public synchronized String getLocation() {
        return String.format("%nx = %.2f  y: = %.2f %n %n", getX(), getY());
    }
}
