package org.handmk.websocketstudy.data.repository;

import org.handmk.websocketstudy.data.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findSellerByEmail(String email);
}
