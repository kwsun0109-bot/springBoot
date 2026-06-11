package com.shop.shop.dto;

import com.shop.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repimgYn;
    // 💡 책에서 엔티티를 DTO로 쉽게 복사하기 위해 ModelMapper를 자주 사용합니다.
    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * ItemImg 엔티티 객체를 파라미터로 받아서
     * DTO 객체로 변환하여 반환해주는 static 메소드입니다.
     */
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}
