package com.weblayer.visitas.DAO;

import com.weblayer.visitas.DTO.visitasDTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public final class visitasDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String tableName="visitas";

    public visitasDAO(Context context)
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

    public void ClearTable()
    {

        try {

            // ClearTable
            String tableSql = "Delete from " + tableName + ";";
            db.execSQL(tableSql);

        }
        finally
        {
            //database.endTransaction();
        }

    }

    public void insertorupdate(visitasDTO objeto)
    {

        visitasDTO param = Get(objeto.getid());

        if (param==null)
            insert(objeto);
        else
            update(objeto);

    }

    public void insert(visitasDTO objeto) {
        try {

            //db.beginTransaction();

            int id = objeto.getid();
            String ds_data= objeto.getds_data();
            String ds_checkin= objeto.getds_checkin();
            String ds_checkout= objeto.getds_checkout();
            String ds_cliente= objeto.getds_cliente();
            String ds_objetivo= objeto.getds_objetivo();
            String ds_endereco= objeto.getds_endereco();
            String ds_contato= objeto.getds_contato();
            String id_resultado= objeto.getid_resultado();
            String ds_ip= objeto.getds_ip();
            String ds_resultado= objeto.getds_resultado();
            String ds_observacao= objeto.getds_observacao();

            String comandosql = "INSERT INTO  " + tableName + " "
                    + " (id, ds_data, ds_checkin, ds_checkout, ds_cliente, ds_objetivo, ds_endereco, ds_contato, id_resultado, ds_ip, ds_resultado, ds_observacao) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?,?,?);";

            db.execSQL(comandosql, new Object[] { id, ds_data, ds_checkin, ds_checkout, ds_cliente, ds_objetivo,ds_endereco, ds_contato, id_resultado, ds_ip, ds_resultado, ds_observacao});

            //db.setTransactionSuccessful();

        } catch (Exception e)
        {
            Log.e("weblayer.log.visitas", "insert", e); // log the error

        } finally {
            //db.endTransaction();
        }
    }


    public void update(visitasDTO objeto) {

        try {

            //db.beginTransaction();

            //TODO Fazer retornar inteiro

            int id = objeto.getid();

            String ds_data = objeto.getds_data();

            String ds_checkin = objeto.getds_checkin();
            String ds_checkout = objeto.getds_checkout();
            String ds_cliente = objeto.getds_cliente();
            String ds_objetivo = objeto.getds_objetivo();
            String ds_endereco = objeto.getds_endereco();
            String ds_contato = objeto.getds_contato();
            String id_resultado= objeto.getid_resultado();
            String ds_ip= objeto.getds_ip();
            String ds_resultado= objeto.getds_resultado();
            String ds_observacao= objeto.getds_observacao();

            String comandosql = "UPDATE  " + tableName + " SET " + " ds_data=?, "
                    + " ds_checkin=?, "
                    + " ds_checkout=?, "
                    + " ds_cliente=?, "
                    + " ds_objetivo=?, "
                    + " ds_endereco=?, "
                    + " ds_contato=?,"
                    + " id_resultado=?,"
                    + " ds_ip=?,"
                    + " ds_resultado=?,"
                    + " ds_observacao=?"
                    + "WHERE id=?";

            db.execSQL(comandosql, new Object[] { ds_data, ds_checkin, ds_checkout, ds_cliente, ds_objetivo, ds_endereco, ds_contato,id_resultado,ds_ip,ds_resultado, ds_observacao, id});

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.Visita", "update", e); // log the error
        } finally {
            //db.endTransaction();
        }

    }


    public void delete(visitasDTO objeto) {

        try {

            //db.beginTransaction();
            String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getid() + "'";
            db.execSQL(delete);
            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.visitas.Visita", "delete", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public void delete(int id) {

        try {

            //db.beginTransaction();
            String delete = "DELETE FROM  " + tableName + " WHERE id='" + id + "'";
            db.execSQL(delete);
            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.visitas.Visita", "delete", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }


    public visitasDTO Get(int id) {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id='" + id + "'", null);

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


    public  List<visitasDTO> fillAll() {
        Cursor cursor = null;
        try {

            List<visitasDTO> all = new ArrayList<visitasDTO>();

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " ", null);

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

    private visitasDTO cursorToObject(Cursor cursor) {

        visitasDTO objeto = new visitasDTO();

        objeto.setid(cursor.getInt(cursor.getColumnIndex("id")));
        objeto.setds_data(cursor.getString(cursor.getColumnIndex("ds_data")));
        objeto.setds_checkin(cursor.getString(cursor.getColumnIndex("ds_checkin")));
        objeto.setds_checkout(cursor.getString(cursor.getColumnIndex("ds_checkout")));
        objeto.setds_cliente(cursor.getString(cursor.getColumnIndex("ds_cliente")));
        objeto.setds_objetivo(cursor.getString(cursor.getColumnIndex("ds_objetivo")));
        objeto.setds_endereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
        objeto.setds_contato(cursor.getString(cursor.getColumnIndex("ds_contato")));
        objeto.setds_resultado(cursor.getString(cursor.getColumnIndex("ds_resultado")));
        objeto.setds_ip(cursor.getString(cursor.getColumnIndex("ds_ip")));
        objeto.setds_resultado(cursor.getString(cursor.getColumnIndex("ds_resultado")));
        objeto.setds_observacao(cursor.getString(cursor.getColumnIndex("ds_observacao")));

        return objeto;
    }

    public boolean CheckinAtivo(Integer id)
    {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id <>'" + id + "' and ds_checkin <>'' and ds_checkout=''", null);

            if (cursor.getCount() > 0) {
                return true;
            }

            return false;

        } catch (Exception e) {

            Log.e("weblayer.log.visitas.Visita", "CheckinAtivo", e);  // log the error

            return true;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


    }

}
