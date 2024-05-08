package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.util.CustomFileUtil;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil customFileUtil;

    @PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO){
        log.info(productDTO +" @@@@@@@@@@@@@@@@@@");

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = customFileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames + "=====uploadFileNames======>");

        return Map.of("RESULT", "SUCCESS");
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName")String fileName){

        return customFileUtil.getFile(fileName);
    }
}


