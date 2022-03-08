package com.ozmaden.core;

import java.io.*;
import java.net.Socket;

/**
 * Класс для интеракций клиента и сервера.
 * @author <a href="mailto:dozmaden@edu.hse.ru">Ozmaden Deniz</a>
 */
public class NetworkService {
    /**
     * Общий сокет
     */
    private final Socket sock;

    /**
     * Конструктор
     * @param sock
     */
    public NetworkService(final Socket sock) {
        this.sock = sock;
    }

    /**
     * Получаем число
     * @return число
     * @throws IOException
     */
    public long readLong() throws IOException {
        return new DataInputStream(sock.getInputStream()).readLong();
    }

    /**
     * Отправляем число
     * @param n число
     * @throws IOException
     */
    public void sendLong(final long n) throws IOException {
        new DataOutputStream(sock.getOutputStream()).writeLong(n);
    }

    /**
     * Читаем имя файла
     * @return имя файла
     * @throws IOException
     */
    public String readString() throws IOException {
        try {
            final var bufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            return bufferedReader.readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();

            return null;
        }
    }

    /**
     * Отправляем имя файла
     * @param str имя файла
     * @throws IOException
     */
    public void sendString(final String str) throws IOException {
        try {
            final var buffWr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            buffWr.write(str);
            buffWr.write("\n");
            buffWr.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Читаем файл
     * @param inputFile файл, куда записывается
     * @param fileSize размер файла
     * @param loader грузчик
     * @throws IOException
     */
    public void readFile(final File inputFile, final long fileSize, final FileLoader loader) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(inputFile)) {
            final byte[] buffer = new byte[4096];
            int summaCnt = 0;
            int cnt;
            while (summaCnt < fileSize && (cnt = sock.getInputStream().read(buffer)) > 0) {
                summaCnt += cnt;
                fout.write(buffer, 0, cnt);
                loader.load(summaCnt, fileSize);
            }
        }
    }

    /**
     * Отправляем файл
     * @param outputFile файл
     * @throws IOException
     */
    public void sendFile(final File outputFile) throws IOException {
        try (FileInputStream fin = new FileInputStream(outputFile)) {
            final byte[] buffer = new byte[4096];
            int cnt;
            while ((cnt = fin.read(buffer)) > 0) {
                sock.getOutputStream().write(buffer, 0, cnt);
            }
        }
    }

}
