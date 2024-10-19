package com.onlineLearningPlatform.demo.File;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.io.File.separator;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private static  final Logger log= Logger.getLogger(String.valueOf(FileUploadService.class));
    @Value("${application.properties.document.upload-directory}")
    private String uploadDirectory;
    public String saveFile(@NonNull MultipartFile file,Integer courseId){
        final  String filesubfolder="course"+separator+courseId;
        return uploadFile(file,filesubfolder);
    }
    private String uploadFile(@NonNull MultipartFile file, String filesubfolder) {
        final String finalUploadPath=uploadDirectory+separator+filesubfolder;
        File targetfolder=new File(finalUploadPath);
        if(!targetfolder.exists()){
            boolean success=targetfolder.mkdirs();
            if(!success){
                log.warning("directory could not be created");
                return "";
            }
        }
        final String fileExtension=getfileExtension(file.getOriginalFilename());
        String targetFilepath=finalUploadPath+separator+System.currentTimeMillis()+"."+fileExtension;
        Path targetPath= Paths.get(targetFilepath);
        try{
            Files.write(targetPath,file.getBytes());
            log.info("File uploaded"+targetFilepath);
            return targetFilepath;
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String getfileExtension(String filename) {
        if(filename==null || filename.isEmpty()){
            return "";
        }
        int idx=filename.lastIndexOf(".");
        if(idx==-1){
            return "";
        }
        return filename.substring(idx+1).toLowerCase();


    }
}
