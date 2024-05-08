package org.zerock.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${org.zerock.upload.path}")
    private String upload;


    @PostConstruct
    public void init(){
        File tempFolder = new File(upload);

        //폴더가 존재해
        if(!tempFolder.exists()) tempFolder.mkdir();

        upload = tempFolder.getAbsolutePath();
        log.info(upload + "============================");
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{

        if(files == null || files.size() == 0){
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files){
            String saveName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path savePath = Paths.get(upload, saveName);

            try {
                Files.copy(file.getInputStream(), savePath); //원본 파일 업로드
                String contentType = file.getContentType(); //Mime type

                //이미지 파일이라면
                if(contentType != null || contentType.startsWith("image")){
                    Path thumbnailkPath = Paths.get(upload, "s_" + saveName);

                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailkPath.toFile());


                }


                uploadNames.add(saveName);
            }catch (IOException e){
                throw new RuntimeException(e);
            }

        }


        return uploadNames;
    }


    public ResponseEntity<Resource> getFile(String fileName){

        org.springframework.core.io.Resource resource = new FileSystemResource(upload + File.separator+fileName);

        if(!resource.isReadable()){
            resource = new FileSystemResource(upload + File.separator +"default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }



        return ResponseEntity.ok().headers(headers).body(resource);
    }


    public void deleteFiles(List<String> fileNames){
        if(fileNames == null || fileNames.size() == 0){
            return;
        }

        fileNames.forEach(fileName ->{
            //썸네일 삭제

            String thumbnameFileName = "s_" + fileName;

            Path thumbnailPath = Paths.get(upload, thumbnameFileName);
            Path filePath = Paths.get(upload, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            }catch (IOException e){
                throw  new RuntimeException(e);
            }

        });
    }
}
