package nl.novi.uploaddownload.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageService {

//    The next line is also possible instead of line 14 and 15
//    private static String storageLocation = "/Users/vanoo/IdeaProjects/upload-download/uploads"
    @Value("${my.upload_location}")
    private Path fileStoragePath;
    private final String fileStorageLocation;

    public FileStorageService(@Value("${my.upload_location}") String fileStorageLocation) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        this.fileStorageLocation = fileStorageLocation;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }

    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        return fileName;
    }

    public Resource downLoadFile(String fileName) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public List<String> downLoad() {
        // Directory path here
        var list = new ArrayList<String>();
        File folder = new File(fileStorageLocation);
        File[] listOfFiles = folder.listFiles();

        for(int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++){
            if(listOfFiles[i].isFile()){
                String name = listOfFiles[i].getName();
               list.add(name);
            }
        }
        return list;
    }
}
