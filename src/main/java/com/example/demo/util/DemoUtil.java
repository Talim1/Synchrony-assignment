package com.example.demo.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;

public class DemoUtil {

    private static Logger logger = LoggerFactory.getLogger(DemoUtil.class);


//    public static File convertToFile(MultipartFile multipartFile) {
//
//        File tempFile = null;
//        try {
//            tempFile = new File("src/main/resources/targetFile.tmp");
//            multipartFile.transferTo(tempFile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return tempFile;
//    }

    public static File convertToFile(MultipartFile file) throws IOException {
        var initialStream = file.getInputStream();
        var buffer = new byte[1000];
        var count = 0;
        var targetFile = new File(FilenameUtils.getName(file.getOriginalFilename()));
        boolean targetFileCreated = targetFile.createNewFile();
        logger.info("New targetFileCreated: {}", targetFileCreated);
        var baseDirectory = new File(System.getProperty("user.dir"));

        boolean fileStatus = FileUtils.directoryContains(baseDirectory, targetFile);
        if (!fileStatus) {
            throw new IOException("File is not in correct");
        }
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            while ((count = initialStream.read(buffer)) > 0) {
                outStream.write(buffer);
            }
            return targetFile;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return null;
    }

    public static void viewInBrowser(String url) {

        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();

        try{

            desktop.browse(new URI("https://www.google.com/"));

        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }
}
