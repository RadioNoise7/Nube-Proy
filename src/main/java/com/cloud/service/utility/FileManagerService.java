package com.cloud.service.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {

    public static final String VIDEOS_PATH = "storage/videos/";
    public static final String IMAGES_PATH = "storage/images/";
    public static final String SOUNDS_PATH = "storage/sounds/";

    @Async
    public String saveFile(MultipartFile file, Integer userId, String routeByFileType) throws IOException{
        String filename = System.currentTimeMillis() +"_" +StringUtils.cleanPath(file.getOriginalFilename());
        String fileUrl = routeByFileType +userId +"/" +filenameExtensionRemover(filename) +"/";
        Path uploadPath = Paths.get("./" +fileUrl);
        
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("No se pudo guardar el archivo");
        }

        return fileUrl +filename;
    }

    @Async
    public void deleteFile(String dir) throws IOException{
        Path fileToDeletePath = Paths.get(dir);
        Path fileParentFolder = fileToDeletePath.getParent();
        Files.delete(fileToDeletePath);
        Files.delete(fileParentFolder);
    }

    private String filenameExtensionRemover(String filename){
        int lastPeriodPos = filename.lastIndexOf('.');
        if (lastPeriodPos > 0) return filename.substring(0, lastPeriodPos);
        return filename;
    }
    
}
