package org.handmk.websocketstudy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.request.SellerDto;
import org.handmk.websocketstudy.data.entity.Seller;
import org.handmk.websocketstudy.data.repository.SellerRepository;
import org.handmk.websocketstudy.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ResponseEntity<?> createSeller(SellerDto sellerDto) {
        Seller seller = Seller.builder()
                .email(sellerDto.getEmail())
                .profileUrl(sellerDto.getProfileUrl())
                .loginType(sellerDto.getLoginType())
                .workShopName(sellerDto.getWorkShopName())
                .name(sellerDto.getName())
                .workShopThumbnail(sellerDto.getWorkShopThumbnail())
                .password(sellerDto.getPassword())
                .address(sellerDto.getAddress())
                .useAble(sellerDto.isUseAble())
                .build();
        sellerRepository.save(seller);

        return ResponseEntity.ok(seller);
    }
}
