package br.com.projetospolo.projetospolo.infrastructure.service.crud;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CRUDService<FORM, DTO, ID_TYPE, FILTER> extends CreateService<FORM, DTO>{

    DTO create(FORM form);

    DTO update(ID_TYPE id, FORM form);

    Page<DTO> read(FILTER filter, Pageable pageable);

    void delete(ID_TYPE id);
}
