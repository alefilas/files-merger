package ru.alefilas;

import ru.alefilas.merger.FileMerger;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("There is no directory");
        } else {
            try {
                Path file = FileMerger.merge(Path.of(args[0]));
                System.out.println("All data merged in file: " + file);
            } catch (IOException e) {
                System.out.println("Something went wrong. Exception message: " + e);
            }
        }
    }
}
