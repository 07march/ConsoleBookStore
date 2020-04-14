package by.consoleapp.util;

import java.util.Scanner;

public class ConsoleReaderImpl implements Reader {
    private Scanner scanner;

    public ConsoleReaderImpl() {
        scanner = new Scanner(System.in);
    }

    public static Reader getInstance() {
        return new ConsoleReaderImpl();
    }

    public String read() {
        return scanner.next();
    }

    public int readNumber() {
        return scanner.nextInt();
    }
}
