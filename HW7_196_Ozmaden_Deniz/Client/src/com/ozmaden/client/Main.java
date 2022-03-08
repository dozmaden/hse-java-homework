package com.ozmaden.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * @author <a href="mailto:dozmaden@edu.hse.ru">Ozmaden Deniz</a>
 */
public class Main {
    /**
     * Запускаем клиента
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Введите порт!");
            String port = sc.nextLine();
            while (!validatePort(port)) {
                System.out.println("Произошла ошибка! Введите порт ещё раз!");
                port = sc.nextLine();
            }

            System.out.println("Введите хост!");
            String host = sc.nextLine();

            System.out.println("Введите директорию для сохранения файлов!");
            String dir = sc.nextLine();
            while (!validateDirectory(dir)) {
                System.out.println("Произошла ошибка! Введите директорию ещё раз!");
                dir = sc.nextLine();
            }

            try {
                Socket sock = new Socket(host, Integer.parseInt(port));
                Client cl = new Client(sock, Paths.get(dir).toFile());
                cl.run();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Произошла ошибка!");
            }
        }
    }

    /**
     * Проверяем существование порта
     * @param p порт
     * @return да или нет
     */
    private static boolean validatePort(String p) {
        if (p.isBlank()) {
            return false;
        }
        return p.chars().allMatch(Character::isDigit);
    }

    /**
     * Проверяем существование директории
     * @param dir директория
     * @return да или нет
     */
    private static boolean validateDirectory(String dir) {
        if (dir == null || dir.isBlank()) {
            return false;
        }
        return Path.of(dir)
                .toFile()
                .isDirectory();
    }
}
