package com.ozmaden;

import java.util.Timer;

public class Main {

    /**
     * Вход в программу
     * @param args консольные аргументы
     * @throws InterruptedException если поток прекращается
     */
    public static void main(String[] args) throws InterruptedException {

        // Дефолтные значения координат тележки
        double x = 0, y = 0;
        try {
            // если только икс, то только икс
            if (args != null && args.length == 1) {
                x = Double.parseDouble(args[0]);
            } else if (args != null && args.length == 2) {
                // или читаем два аргумента
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            // если выходит ошибка, продолжаем с дефолтными данными
            System.out.println("Error while parsing arguments! Will use default ones");
        }

        // Создаём массив потоков - животных
        Thread[] animals = new Thread[] {
                new Animal("Swan", 60), // Лебедь
                new Animal("Crayfish", 180), // Рак
                new Animal("Pike", 300), // Щука
        };

        // Создаём экземпляр тележки. Заполняем координаты.
        Cart crt = new Cart(x, y);

        // Животные начинают толкать тележку.
        AnimalsMoveCart(animals, crt);

        // Тут ждём одну пол секунду, потому что без этого сообщения животных,
        // что они "устают" часто появляется после финальных координат.
        // А так - финальная локация всегда появляется после этих сообщений
        Thread.sleep(500);
        System.out.println("Final coordinates are: " + crt.getLocation());

        // Заканчиваем исполнение программы.
        System.exit(0);
    }


    /**
     * @param animals Потоки животных
     * @param crt Тележка
     * @throws InterruptedException если потоки прерываются
     */
    public static void AnimalsMoveCart(Thread[] animals, Cart crt) throws InterruptedException {
        // Используем класс таймер и его метод schedule,
        // чтобы каждые 2 секунды печатать локацию тележки
        Timer timer = new Timer();
        timer.schedule(crt, 0, 2000);

        // Ждём одну миллисекунду, чтобы начальная позиция вышла до действий животных
        Thread.sleep(1);

        for (Thread t : animals) {
            t.start();
        }

        // Ждём 25 секунды
        Thread.sleep(25000);

        // Останавливаем все потоки животных (они устали)
        System.out.printf("%n25 SECONDS HAVE PASSED. %n");
        for (Thread t : animals) {
            t.interrupt();
        }

        // Отменяем постоянное сообщение в консоль (которые каждое 2 секунды)
        timer.cancel();
    }
}
