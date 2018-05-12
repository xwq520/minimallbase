package com.minimall.boilerplate.business.dto.assembler;

import java.util.Optional;

public interface IDTOAssembler<DTO, ENTITY> {
    Optional<DTO> toDTO(ENTITY entity);
    Optional<ENTITY> toEntity(DTO dto);
}
