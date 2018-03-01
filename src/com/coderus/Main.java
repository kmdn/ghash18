package com.coderus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String filename = "small";
        FileOutputStream out = null;
        FileInputStream in = null;
        File file;

        try {
            file = new File(filename +".out");
            in = new FileInputStream(file);
            //TODO: READ THE F'ING FILE

            in.close();
            if (!file.exists()) {
                file.createNewFile();
            }

            //TODO: SOLVE PROBLEM





            //TODO: WRITE OUTPUT TO FILE
            out = new FileOutputStream(file);
            out.write(" ".getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
