package org.handmk.websocketstudy.service;

import org.handmk.websocketstudy.data.dto.request.SellerDto;
import org.springframework.http.ResponseEntity;

public interface SellerService {
    ResponseEntity<?> createSeller(SellerDto sellerDto);
}
