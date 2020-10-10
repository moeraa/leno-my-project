package com.moer.day.other;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilewriterTest {
    public static void main(String[] args) {
        String failPath = "D://filewriter.txt";
        File file = new File(failPath);
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(String.valueOf(true));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
