package br.com.projetospolo.projetospolo.infrastructure.service.crud;

public interface FindByIdService<ID_TYPE, DTO>{

    DTO findById(ID_TYPE id);

}
