package com.ozmaden;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
Класс животного. По задаче это будет лебедь, рак и щука.
 */
public final class Animal extends Thread {

    /*
    Имя животного
     */
    private final String name;

    /*
    Коэффициент, генерирующийся от 0 до 10
     */
    private final double s_coef;

    /*
    Угол, под которым животное толкает тележку (в радианах)
     */
    private final double rad;

    /*
    Для рандомной генерации чисел
     */
    private final Random rnd = new Random();

    /**
     * Конструктор животного
     * @param name Имя
     * @param angle Угол
     */
    public Animal(String name, int angle) {
        this.name = name;

        // переводим углы в радианы
        rad = Math.toRadians(angle);

        // генерируем уникальный рандомный коэффициент
        s_coef = 10 * rnd.nextDouble();

        System.out.printf("%s has coefficient of %.2f and angle of %d %n", name, s_coef, angle);
    }


    /*
    Синхронный метод run. Каждое животное тут толкает тележку
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            // двигаем тележку
            MoveCart();

            // спим на рандомное время
            var sleeptime = 1000 + rnd.nextInt(4000);
            System.out.println(name + " will now sleep for " + sleeptime + " milliseconds");

            try {
                // спим
                TimeUnit.MILLISECONDS.sleep(sleeptime);

                // будимся и повторяем толкание
                System.out.println(name + " slept for " + sleeptime + "!");
            } catch (InterruptedException ex) {
                // 25 секунд прошло и животное устало
                System.out.println(name + " is tired and leaves the cart alone!");
                break;
            }
        }
    }

    /*
    Метод, для толкание тележки (доступ к статическим координатам)
     */
    public synchronized void MoveCart() {
        double x_shift = s_coef * Math.cos(rad);
        double y_shift = s_coef * Math.sin(rad);

        Cart.setX(Cart.getX() + x_shift);
        Cart.setY(Cart.getY() + y_shift);

        System.out.printf("%s moved x by %.2f and y by %.2f %n", name, x_shift, y_shift);
    }
}
