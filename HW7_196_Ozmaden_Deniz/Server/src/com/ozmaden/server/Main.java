package com.ozmaden.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author <a href="mailto:dozmaden@edu.hse.ru">Ozmaden Deniz</a>
 */
public class Main {

    /**
     * Для множества клиентов
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Запускаем сервер
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите порт");
        String port = sc.nextLine();
        while (!validatePort(port)) {
            System.out.println("Произошла ошибка! Введите порт ещё раз!");
            port = sc.nextLine();
        }

        System.out.println("Введите директорию");
        String dir = sc.nextLine();
        while (!validateDirectory(dir)) {
            System.out.println("Произошла ошибка! Введите директорию ещё раз!");
            dir = sc.nextLine();
        }

        while (true) {
            try {
                ServerSocket ss = new ServerSocket(Integer.parseInt(port));
                System.out.println("Сервер ждёт клиентов :( ");
                while (true) {
                    Socket clientSock = ss.accept();
                    String finalDir = dir;
                    executor.submit(() -> {
                        System.out.println("Успешное подключение к " + ss.getInetAddress().toString());
                        new Server(clientSock, Paths.get(finalDir).toFile()).run();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Проверяем порт
     * @param p порт
     * @return да или нет
     */
    private static boolean validatePort(final String p) {
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
    private static boolean validateDirectory(final String dir) {
        if (dir == null || dir.isBlank()) {
            return false;
        }

        File dirFile = Path.of(dir).toFile();
        if (!dirFile.isDirectory() || dirFile.listFiles() == null
                | Objects.requireNonNull(dirFile.listFiles()).length == 0) {
            return false;
        }

        return Arrays.stream(Objects.requireNonNull(dirFile.listFiles()))
                .noneMatch(x -> x.length() > (1L << 37));
    }
}
