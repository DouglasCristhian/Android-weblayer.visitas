package com.weblayer.visitas.DTO;

public final class parametroDTO
{

    private String id_parametro;
    private String ds_valor;

    public parametroDTO()
    {
    }

    //ds_parametro
    public void setid_parametro(String ds_parametro) {
        this.id_parametro = ds_parametro;
    }

    public String getid_parametro() {
        return id_parametro;
    }

    //ds_valor
    public void setds_valor(String ds_valor) {
        this.ds_valor = ds_valor;
    }

    public String getds_valor() {
        return ds_valor;
    }


}