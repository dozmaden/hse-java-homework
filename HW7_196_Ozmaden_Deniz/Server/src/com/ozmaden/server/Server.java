package com.ozmaden.server;

import com.ozmaden.core.NetworkService;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:dozmaden@edu.hse.ru">Ozmaden Deniz</a>
 */
public class Server implements Runnable {

    /**
     * Общий сокет
     */
    private final Socket sock;

    /**
     * Директория
     */
    private final File dir;

    /**
     * Специальный класс для интеракций клиента с сервером.
     */
    private final NetworkService ns;

    /**
     * Конструктор
     * @param client сокет
     * @param dir директория
     */
    public Server(final Socket client, final File dir) {
        this.dir = dir;
        this.sock = client;
        ns = new NetworkService(sock);
    }

    /**
     * Основная логика сервера
     */
    @Override
    public void run() {
        System.out.println("Сервер отправляет клиенту содержание директории...");
        try {
            ns.sendString(getStrFiles(dir.listFiles()));

            while (sock.isConnected()) {
                try {
                    System.out.println("Сервер принимает от клиента имя файла для скачивания...");
                    String fileName = ns.readString();
                    System.out.println("Сервер принял имя файла: " + fileName);

                    File file = Paths.get(dir.getPath(), fileName).toFile();

                    System.out.println("Сервер уведомит клиента о размере файла");
                    ns.sendLong(file.length());

                    if (ns.readLong() == 0) {
                        System.out.printf("Сервер отправит %s клиенту! \n", fileName);
                        ns.sendFile(file);
                        System.out.printf("Сервер отправил файл %s клиенту! \n", fileName);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Клиент %s отключился! :(", sock.getInetAddress().toString());
    }

    /**
     * Получаем имя файлов
     * @param files файлы
     * @return имя файлов
     */
    public String getStrFiles(File[] files) {
        return Arrays.stream(files)
                .filter(File::isFile)
                .filter(x -> x.length() < (1L << 37))
                .map(File::getName)
                .collect(Collectors.joining("/"));
    }
}
