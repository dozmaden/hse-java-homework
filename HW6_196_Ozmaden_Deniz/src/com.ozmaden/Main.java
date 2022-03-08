/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.ozmaden;

/**
 * Метод main. Используется только для запуска класса консольного приложения.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleInterface phonebookInterface = new ConsoleInterface();
        phonebookInterface.EnterInterface();
        System.exit(0);
    }
}
