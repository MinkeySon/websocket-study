package org.handmk.websocketstudy.controller;

import org.handmk.websocketstudy.data.dto.request.SellerDto;
import org.handmk.websocketstudy.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {
    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> createSeller(@RequestBody SellerDto sellerDto){
        return sellerService.createSeller(sellerDto);
    }
}
