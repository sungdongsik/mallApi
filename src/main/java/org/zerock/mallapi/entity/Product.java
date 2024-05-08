package org.zerock.mallapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "imageList")
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @ElementCollection //주인공이 되지 않는다?
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();


    public void changeName(String pname) {
        this.pname = pname;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void addImage(ProductImage image){
        image.setOrd(imageList.size());
        imageList.add(image);

    }

    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);
    }

    public void clearList(){
        this.imageList.clear();
    }
}
