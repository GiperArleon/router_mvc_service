package com.router.pdf;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
public class PdfManager {
    public static String prepearePdf(String text){
        try {
            String fileName = getPath();
            createPdf(fileName, text);
            log.info("Report {} prepeared!", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Report doesn't prepeared! {}", e.getMessage());
            return "";
        }
    }

    private static String getPath() throws IOException {
        String baseDirectory = System.getProperty("user.dir");
        String pathName = baseDirectory
                .concat(File.separator)
                .concat("reports")
                .concat(File.separator);
        File file = new File(pathName);
        if(!file.exists()) {
            if(!file.mkdir()) {
                log.error(String.format("Can not create directory %s!", file.getAbsolutePath()));
                throw new IOException();
            }
        } else if(file.isFile()){
            log.error(String.format("File %s is not directory!", file.getAbsolutePath()));
            throw new IOException();
        }
        return pathName.concat(LocalDate.now().toString().concat("_report.pdf"));
    }

    private static void createPdf(String fileName, String text) {
        new PdfCreator().createPdf(fileName, text);
    }
}
