package com.blogapp.serviceImpl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //Get file name
        String name= file.getOriginalFilename();
        String randomID= UUID.randomUUID().toString();
        //random file name
        String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
        //full path
        String filePath=path+File.separator+fileName1;
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) {
        String fullPath=path+File.separator+fileName;
        try{
         InputStream is=new FileInputStream(fullPath);
            return is;
        }catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }
}
