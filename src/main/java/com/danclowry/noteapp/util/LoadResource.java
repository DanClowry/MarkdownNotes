package com.danclowry.noteapp.util;

import com.danclowry.noteapp.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class LoadResource {
    public static String LoadResource(String path) throws IOException, URISyntaxException {
        File file = new File(App.class.getResource(path).toURI());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.joining());
        }
    }
}
