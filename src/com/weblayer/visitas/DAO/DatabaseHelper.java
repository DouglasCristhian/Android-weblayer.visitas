package com.weblayer.visitas.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weblayer_visitas";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {

        String DATABASE_CREATE;

        //Tabela parametro
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS parametro ("
                + "id_parametro VARCHAR(20) PRIMARY KEY, "
                + "ds_valor VARCHAR(80)"
                + ");";
        database.execSQL(DATABASE_CREATE);

        //Tabela visitas
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS visitas ("
                + "id INTEGER PRIMARY KEY, "
                + "ds_data VARCHAR(10), "
                + "ds_checkin VARCHAR(10), "
                + "ds_checkout VARCHAR(10), "
                + "ds_cliente VARCHAR(100), "
                + "ds_objetivo VARCHAR(100), "
                + "ds_endereco VARCHAR(100), "
                + "ds_contato VARCHAR(100), "
                + "id_resultado VARCHAR(2),"
                + "ds_ip VARCHAR(30),"
                + "ds_resultado VARCHAR(100),"
                + "ds_observacao VARCHAR(1000)"
                + ");";

        database.execSQL(DATABASE_CREATE);

    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){

        onCreate(database);

        //Upgrade versão 1 para 2
        if (oldVersion < 2) {
            //Mais campos na tabela de visitas.
            //database.execSQL("ALTER TABLE visitas ADD id_resultado VARCHAR(2);");
            //database.execSQL("ALTER TABLE visitas ADD ds_ip VARCHAR(30);");
            //database.execSQL("ALTER TABLE visitas ADD ds_resultado VARCHAR(100);");
        }

        //Upgrade versão 2 para 3
        if (oldVersion < 3) {
            //database.execSQL("ALTER TABLE visitas ADD ds_observacao VARCHAR(100);");
        }



    }
}
