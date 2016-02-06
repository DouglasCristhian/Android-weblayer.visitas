package com.weblayer.visitas.DAO;

import com.weblayer.visitas.DTO.parametroDTO;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public final class  parametroDAO {

        private DatabaseHelper dbHelper;
        private SQLiteDatabase db;

        //private static SQLiteDatabase db;
        //private static String tableName="parametro";

        public parametroDAO(Context context)
        {
                dbHelper = new DatabaseHelper(context);
        }

        public void open() throws SQLException {

                db = dbHelper.getWritableDatabase();
        }

        public void close() {
                dbHelper.close();
        }

        private boolean isTableEmpty(String table) {

                Cursor cursor = null;
                try {

                        cursor = db.rawQuery("SELECT count(*) FROM " + table, null);
                        int countIndex = cursor.getColumnIndex("count(*)");
                        cursor.moveToFirst();
                        int rowCount = cursor.getInt(countIndex);
                        if (rowCount > 0) {
                                return false;
                        }

                        return true;

                } finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                }
        }

        public void insertorupdate(parametroDTO objeto)
        {

                parametroDTO param = Get(objeto.getid_parametro());

                if (param==null)
                    insert(objeto);
                else
                    update(objeto);

        }

        public void insert(parametroDTO objeto) {
                try {

                        //db.beginTransaction();

                        String id_parametro= objeto.getid_parametro();
                        String ds_valor= objeto.getds_valor();

                        String comandosql = "INSERT INTO  parametro "
                                + " (id_parametro, ds_valor) VALUES "
                                + " (?,?);";

                        db.execSQL(comandosql, new Object[] { id_parametro, ds_valor });

                        //db.setTransactionSuccessful();

                } catch (Exception e) {
                        Log.e("weblayer.log.visitas.Visita", "insert", e); // log the error
                } finally {
                        //db.endTransaction();
                }
        }


        public void update(parametroDTO objeto) {
                try {

                        //db.beginTransaction();

                        //TODO Fazer retornar inteiro

                        String id_parametro = objeto.getid_parametro();
                        String ds_valor = objeto.getds_valor();

                        String comandosql = "UPDATE  parametro SET " + " ds_valor=? WHERE id_parametro=?";

                        db.execSQL(comandosql, new Object[] { ds_valor, id_parametro });

                        //db.setTransactionSuccessful();

                } catch (Exception e) {
                        Log.e("weblayer.log.visitas.Visita", "update", e); // log the error
                } finally {
                        //db.endTransaction();
                }

        }


        public void delete(parametroDTO objeto) {
                try {

                        //db.beginTransaction();
                        String delete = "DELETE FROM parametro WHERE id_parametro='" + objeto.getid_parametro() + "'";
                        db.execSQL(delete);
                        //db.setTransactionSuccessful();

                } catch (Exception e) {
                        Log.e("weblayer.log.visitas", "delete", e); // log the error
                } finally {
                        //db.endTransaction();
                }
        }


        public parametroDTO Get(String id) {

                Cursor cursor = null;

                try {

                        cursor = db.rawQuery("SELECT * FROM  parametro Where id_parametro='" + id + "'", null);

                        if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                                return (cursorToObject(cursor));
                        }

                        return null;
                } catch (Exception e) {
                        Log.e("weblayer.log.visitas.Visita", "Get", e); // log the error
                        return null;
                } finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                }
        }


        public List<parametroDTO> fillAll() {
                Cursor cursor = null;
                try {
                        List<parametroDTO> all = new ArrayList<parametroDTO>();

                        cursor = db.rawQuery("SELECT * FROM  parametro ", null);

                        if (cursor.getCount() > 0) {

                                cursor.moveToFirst();
                                do {
                                        all.add(cursorToObject(cursor));
                                        cursor.moveToNext();

                                } while (!cursor.isAfterLast());
                        }

                        return all;
                }
                catch (Exception e)
                {
                        Log.e("weblayer.log.visitas.Visita", "fill", e);  // log the error
                        return null;
                }
                finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                }
        }


        public List<parametroDTO> fillCombo(int id_categ3) {
                Cursor cursor = null;
                try {
                        List<parametroDTO> all = new ArrayList<parametroDTO>();

                        //cursor = db.rawQuery("select 0 id,0 id_empresa,'SELECIONE..' ds_nome union select a.id, a.id_empresa, a.ds_nome from categ1 a inner Join Produto b on a.id = b.id_categ1 where b.id_categ3=" + id_categ3 + " order by a.id", null);

                        if (cursor.getCount() > 0) {

                                cursor.moveToFirst();
                                do {
                                        all.add(cursorToObject(cursor));
                                        cursor.moveToNext();

                                } while (!cursor.isAfterLast());
                        }

                        return all;
                }
                catch (Exception e)
                {
                        Log.e("weblayer.log.visitas.Visita", "fill", e);  // log the error
                        return null;
                }
                finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                }
        }


        private parametroDTO cursorToObject(Cursor cursor) {

                parametroDTO objeto = new parametroDTO();

                objeto.setid_parametro(cursor.getString(cursor.getColumnIndex("id_parametro")));
                objeto.setds_valor(cursor.getString(cursor.getColumnIndex("ds_valor")));

                return objeto;
        }

}
