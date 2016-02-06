package com.weblayer.visitas.DTO;


public class usuarioDTO {

    private Integer id_empresa;
    private Integer id_usuario;
    private Integer id_vendedor;
    private String ds_empresa;
    private String ds_perfil;

    public usuarioDTO()
    {
    }

    //id_empresa
    public void setid_empresa (Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public Integer getid_empresa() {return id_empresa;   }

    //id_usuario
    public void setid_usuario (Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    public Integer getid_usuario() {
        return id_usuario;
    }

    //id_vendedor
    public void setid_vendedor(Integer id_vendedor) {
        this.id_vendedor= id_vendedor;
    }
    public Integer getid_vendedor() {
        return id_vendedor;
    }

    //ds_empresa
    public void setds_empresa(String ds_empresa) {
        this.ds_empresa = ds_empresa;
    }

    public String getds_empresa() {
        return ds_empresa;
    }

    //ds_perfil
    public void setds_perfil(String ds_perfil) {
        this.ds_perfil = ds_perfil;
    }

    public String getds_perfil() {
        return ds_perfil;
    }


}
