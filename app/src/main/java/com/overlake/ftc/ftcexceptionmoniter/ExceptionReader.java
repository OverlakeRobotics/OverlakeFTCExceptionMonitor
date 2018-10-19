package com.overlake.ftc.ftcexceptionmoniter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExceptionReader
{
    private String path;

    public ExceptionReader(String path) {
        this.path = path;
    }

    public String read() {
        try
        {
            return readFile();
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("File does not exist at path " + path);
        }
    }

    private String readFile() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(path));
        StringBuilder builder = new StringBuilder();
        while(scanner.hasNextLine()) {
            builder.append(scanner.nextLine() + "\n");
        }
        return builder.toString();
    }

    public void ensureFileExists() {
        File file = new File(path);
        if (!file.exists()) {
            createFile(file);
        }
    }

    private void createFile(File file) {
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Failed to create file at path: " + path);
        }
    }
}
