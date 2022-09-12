package com.accenture.chickentest.domain.dto;

import com.accenture.chickentest.domain.dao.Parametro;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParametroDTO extends Parametro {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("clave")
    private String clave;

    @JsonProperty("valor")
    private Long valor;

    public ParametroDTO(Long id, String clave, Long valor) {


        this.id = id;
        this.clave = clave;
        this.valor = valor;
    }

    public ParametroDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
}
