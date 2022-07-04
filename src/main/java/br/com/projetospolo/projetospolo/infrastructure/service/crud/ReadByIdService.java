package br.com.projetospolo.projetospolo.infrastructure.service.crud;

public interface ReadByIdService<ID_TYPE, DTO>{

    DTO readById(ID_TYPE id);

}
