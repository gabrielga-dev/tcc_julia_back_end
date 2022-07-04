package br.com.projetospolo.projetospolo.infrastructure.service.crud;

public interface CreateService<FORM, DTO>{

    DTO create(FORM form);
}
