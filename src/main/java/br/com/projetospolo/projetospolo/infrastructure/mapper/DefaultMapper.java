package br.com.projetospolo.projetospolo.infrastructure.mapper;

public interface DefaultMapper <FORM, DTO, DOMAIN> {

    DOMAIN domainFromForm(FORM form);
    DTO dtoFromDomain(DOMAIN domain);

    DOMAIN transferInformation(DOMAIN source, DOMAIN destiny);

}
