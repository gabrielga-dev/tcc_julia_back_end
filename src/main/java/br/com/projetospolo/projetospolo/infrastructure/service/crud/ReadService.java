package br.com.projetospolo.projetospolo.infrastructure.service.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadService<FILTER, DTO>{

    Page<DTO> read(FILTER filter, Pageable pageable);

}
