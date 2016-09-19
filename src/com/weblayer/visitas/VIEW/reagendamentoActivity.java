package com.weblayer.visitas.VIEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.weblayer.visitas.DAO.resultadoDAO;
import com.weblayer.visitas.DAO.visitasDAO;
import com.weblayer.visitas.DTO.resultadoDTO;
import com.weblayer.visitas.DTO.retornoDTO;
import com.weblayer.visitas.DTO.visitasDTO;
import com.weblayer.visitas.R;

import java.util.Calendar;
import java.util.Date;


public class reagendamentoActivity extends Activity {

    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker txtdatepicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.reagendamento);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtdatepicker = (DatePicker) findViewById(R.id.datePickerReagendamento);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void Reagendar()
    {
        Intent i = getIntent();
        int id_visita = i.getIntExtra("id_visita", 0);

        try {

            //Busca a data do controle
            Date datareagendamento = getDateFromDatePicker(txtdatepicker);

            //Chama a rotina para alteracao
            retornoDTO retorno = com.weblayer.visitas.SINC.visitasSINC.ReagendarVisita(this, id_visita, DateFormat.format("yyyy/MM/dd", datareagendamento).toString());

            if (retorno.getfl_status().equals("ok")) {

                //remover a visita da base
                visitasDAO visitaDatabase = new visitasDAO(this);
                visitaDatabase.open();
                visitaDatabase.delete(id_visita);
                visitaDatabase.close();

                Toast toast = Toast.makeText(this, retorno.getds_comentario(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                setResult(RESULT_OK, null);
                finish();
            }
            else
            {
                Toast toast = Toast.makeText(this, retorno.getds_comentario(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

            }

        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            Log.e("weblayer.log", e.getMessage());
        }

    }

    public void onBtnReagenda_Enviar(View v) {

        //Exibir botão de confirmação da operação
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("ATENÇÃO");
        adb.setMessage("Confirma o reagendamento da visita? ");
        adb.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            Reagendar();

            }
        });
        adb.setNegativeButton("Não", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                //
            }

        });
        adb.show();

    }
}
