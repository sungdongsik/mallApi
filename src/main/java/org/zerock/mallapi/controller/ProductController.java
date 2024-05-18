package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.service.ProductService;
import org.zerock.mallapi.util.CustomFileUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil customFileUtil;
    private final ProductService productService;


    /*@PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO){
        log.info(productDTO +" @@@@@@@@@@@@@@@@@@");

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = customFileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames + "=====uploadFileNames======>");

        return Map.of("RESULT", "SUCCESS");
    }*/


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName")String fileName){

        return customFileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){
        return productService.getList(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO){
        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = customFileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames + "============>");

        Long pno = productService.register(productDTO);

        return Map.of("result", pno);

    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno){
        return productService.get(pno);
    }


    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable Long pno, ProductDTO productDTO){

        productDTO.setPno(pno);

        ProductDTO oldProductDTO = productService.get(pno);

        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = customFileUtil.saveFiles(files);

        //업로드 저장
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDTO);

        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        if(oldFileNames != null && oldFileNames.size() > 0){
            List<String> removeFiles =
            oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            customFileUtil.deleteFiles(removeFiles);
        }

        //기존 저장되고 데이터베이스에 존재

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable Long pno){
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);

        customFileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }
}


