package com.weblayer.visitas.DTO;

/**
 * Created by dcris on 19/09/2016.
 */
public class retornoDTO {

    private String fl_status;
    private String ds_comentario;

    public retornoDTO()
    {
    }

    public void setfl_status(String ds_objeto) {
        this.fl_status = ds_objeto;
    }

    public String getfl_status() {
        return fl_status;
    }

    public void setds_comentario(String ds_objeto) {
        this.ds_comentario = ds_objeto;
    }

    public String getds_comentario() {
        return ds_comentario;
    }

}
