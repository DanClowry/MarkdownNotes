package com.danclowry.noteapp.util;

import com.danclowry.noteapp.App;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URISyntaxException;
import java.util.StringJoiner;

public class LoadResource {
    public static String LoadResource(String path) throws IOException, URISyntaxException {
        File file = new File(App.class.getResource(path).toURI());
        try (LineNumberReader lr = new LineNumberReader(new FileReader(file))) {
            StringJoiner sr = new StringJoiner(System.getProperty("line.separator"));
            String line;
            while ((line = lr.readLine()) != null) {
                sr.add(line);
            }
            return sr.toString();
        }
    }
}
