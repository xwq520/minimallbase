package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommodityRepository extends JpaRepository<Commodity, Long>, JpaSpecificationExecutor<Commodity> {
    Optional<Commodity> findByIdAndUserUserId(Long id,String userId);
    Optional<Commodity> findByIdAndComNo(Long id,String comNo);
}
