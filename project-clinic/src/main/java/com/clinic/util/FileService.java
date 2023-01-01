package com.clinic.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class FileService {

    private FileService() {}

    public static File copyPhotoToProjectDirectory(File photoFile) {
        try {
            File destFile = new File("src/main/resources/photos/" + photoFile.getName());
            Files.copy(photoFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
