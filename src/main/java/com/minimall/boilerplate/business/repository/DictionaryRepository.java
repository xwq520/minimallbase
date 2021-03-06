package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {
    Optional<Dictionary>findByCode(String code);
}
