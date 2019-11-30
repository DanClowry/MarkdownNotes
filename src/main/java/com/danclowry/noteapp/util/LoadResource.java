package com.danclowry.noteapp.util;

import com.danclowry.noteapp.App;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadResource {
    public static String LoadResource(String path) throws IOException {
        InputStream file = App.class.getResourceAsStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while ((length = file.read(buffer)) != -1) {
            bos.write(buffer, 0, length);
        }

        return bos.toString();
    }
}
