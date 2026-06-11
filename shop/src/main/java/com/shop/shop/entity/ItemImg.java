package com.shop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Entity
@Table(name="item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity{

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String oriImgName;
    private String imgName;
    private String imgUrl;
    private String repimgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String ImgUrl) {

        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = ImgUrl;

    }

}
