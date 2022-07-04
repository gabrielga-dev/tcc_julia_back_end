package br.com.projetospolo.projetospolo.infrastructure.service.crud;

public interface UpdateService<FORM, ID_TYPE, DTO>{

    DTO update(ID_TYPE id, FORM form);

}
