package com.ozmaden.client;

import com.ozmaden.core.NetworkService;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * Реализация сервера, работающего с несколькими клиентами.
 * @author <a href="mailto:dozmaden@edu.hse.ru">Ozmaden Deniz</a>
 */
public class Client implements Runnable {

    private final Socket sock;

    /**
    Директория со скачанными файлами
     */
    private final File fileDir;

    /**
    Специальный класс для интеракций между клиентом и сервером
     */
    private final NetworkService ns;

    /**
    Список скачанных файлов
     */
    private final ArrayList<String> downloadedFiles;

    /**
     *
     * @param sock
     * @param fileDir
     */
    public Client(final Socket sock, final File fileDir) {
        this.sock = sock;
        this.fileDir = fileDir;
        ns = new NetworkService(sock);
        downloadedFiles = new ArrayList<>();
    }

    /**
     * Основная логика работы сервера
     */
    @Override
    public void run() {
        try {
            List<String> allFiles = Arrays.asList(ns.readString().split("/"));
            // все файлы из директории сервера
            while (sock.isConnected()) {
                checkDownloads();
                // сначала пишем уже скачанный файлы
                if (checkFileExistance(allFiles)) {
                    break;
                }
                listFiles(allFiles);
                // перечисляем директорию

                String fileName = getFilename(allFiles);
                ns.sendString(fileName);
                // выбираем нужный файл

                long fileSize = ns.readLong();
                // читаем размер файла
                if (chooseToDownload(fileSize)) {
                    ns.sendLong(0);
                    File input = Paths.get(fileDir.getPath(), fileName).toFile();
                    ns.readFile(input, fileSize, (summa, maxSize)
                            -> System.out.printf("Downloaded %.3f percent \n", summa * 1.0 / maxSize * 100));
                    successfulDownload(fileName);
                } else {
                    ns.sendLong(1);
                    cancelDownload(fileName);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println("Успешно отключились от сервера!");
    }


    /**
     * Скачанные файлы в консоль
     */
    public void checkDownloads() {
        if (downloadedFiles.size() > 0) {
            System.out.println("______________________________");
            System.out.println("Скачанные пользователем файлы:");
            for (String file : downloadedFiles) {
                System.out.println(file + "\n");
            }
        }
    }


    /**
     * Для проверки существования файлов в директории
     * @param allFiles
     * @return
     */
    public boolean checkFileExistance(final List<String> allFiles) {
        return allFiles.size() == 0 || allFiles.get(0).isBlank();
    }


    /**
     * Перечислить все файлы
     * @param files файлы
     */
    public void listFiles(final List<String> files) {
        System.out.println("______________________________");
        System.out.println("Список файлов на сервере: ");
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("%d: %s \n", i + 1, files.get(i));
        }
        System.out.println("______________________________");
    }

    /**
     * получить имя файла
     * @param allFiles все файлы
     * @return имя файла
     */
    public String getFilename(final List<String> allFiles) {
        String fileName;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Корректно введите название файла, который вам нужен!");
            fileName = sc.nextLine();
        } while (!allFiles.contains(fileName));
        return fileName;
    }

    /**
     * Решение скачивания файла
     * @param fileSize размер файла
     * @return да или нет
     */
    public boolean chooseToDownload(final long fileSize) {
        System.out.println("______________________________");
        System.out.printf("Размер нужного файла: %d байт \n", fileSize);
        System.out.println("Напишите Y для подтверждения или любой другой символ для сброса загрузки.");

        Scanner sc = new Scanner(System.in);
        String cmd = sc.nextLine();
        return cmd.equals("Y") || cmd.equals("yes") || cmd.equals("0");
    }

    /**
     * Уведомеление про успешное скачивание
     * @param fileName название файла
     */
    public void successfulDownload(final String fileName) {
        System.out.println("______________________________ \n");
        System.out.printf("Файл %s успешно скачан!", fileName);
        downloadedFiles.add(fileName);
    }

    /**
     * Отмена скачивания файла
     * @param fileName название файла
     */
    public void cancelDownload(final String fileName) {
        System.out.println("______________________________ \n");
        System.out.printf("Отменена загрузка файла %s!", fileName);
    }

}
