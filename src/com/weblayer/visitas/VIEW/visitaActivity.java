package com.weblayer.visitas.VIEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.weblayer.visitas.DAO.resultadoDAO;
import com.weblayer.visitas.DAO.visitasDAO;
import com.weblayer.visitas.DTO.resultadoDTO;
import com.weblayer.visitas.DTO.visitasDTO;
import com.weblayer.visitas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class visitaActivity extends Activity
{

    private Button btnCheckin;
    private Button btnCheckout;
    private Button btnReagendar;
    private EditText txtobsercao;
    private String id_operacao;
    private Integer id_visita;
    private Spinner SpinnerResultado;

    private resultadoSpinAdapter AdapterResultado;
    private ColorDrawable buttonColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visita);

        android.app.ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        btnCheckin = (Button)findViewById(R.id.BtnCheckIn);
        btnCheckout = (Button)findViewById(R.id.BtnCheckOut);
        btnReagendar = (Button)findViewById(R.id.BtnReagendar);

        btnCheckout.setBackgroundColor(Color.DKGRAY);
        btnCheckin.setBackgroundColor(Color.DKGRAY);
        SpinnerResultado = (Spinner) findViewById(R.id.spinresultado);

        btnCheckin.setBackgroundColor(Color.parseColor("#ff34b5e5"));
        //btnReagendar.setBackgroundColor(Color.parseColor("#ff34b5e5"));

        Load();

        txtobsercao = (EditText)findViewById(R.id.txtobservacao);

        txtobsercao.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                resultadoDTO resultado = AdapterResultado.getItem(SpinnerResultado.getSelectedItemPosition());
                btnCheckout.setBackgroundColor(Color.DKGRAY);

                if(s.length()>0)
                {
                    if (!resultado.getid().equals("0")) {
                        btnCheckout.setBackgroundColor(Color.parseColor("#ff34b5e5"));
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        SpinnerResultado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                resultadoDTO resultado = AdapterResultado.getItem(SpinnerResultado.getSelectedItemPosition());
                btnCheckout.setBackgroundColor(Color.DKGRAY);

                String s = txtobsercao.getText().toString();

                if(s.length()>0)
                {
                    if (!resultado.getid().equals("0")) {
                        btnCheckout.setBackgroundColor(Color.parseColor("#ff34b5e5"));

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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

    private void Load()
    {

        Intent i = getIntent();
        int id_visita = i.getIntExtra("id_visita", 0);

        visitasDAO TabelaVisita = new visitasDAO(this);
        TabelaVisita.open();
        visitasDTO visita = TabelaVisita.Get(id_visita);
        TabelaVisita.close();

        if (visita==null)
            return;

        TextView txtData = (TextView)findViewById(R.id.txtData);
        txtData.setText(visita.getds_data());
        txtData.setFocusable(false);

        TextView txtChekin = (TextView)findViewById(R.id.txtChekin);
        txtChekin.setText(visita.getds_checkin());
        txtChekin.setFocusable(false);

        TextView txtChekout = (TextView)findViewById(R.id.txtChekout);
        txtChekout.setText(visita.getds_checkout());
        txtChekout.setFocusable(false);

        TextView txtObjetivo = (TextView)findViewById(R.id.txtObjetivo);
        txtObjetivo.setText(visita.getds_objetivo());
        txtObjetivo.setFocusable(false);

        TextView txtEndereco = (TextView)findViewById(R.id.txtEndereco);
        txtEndereco.setText(visita.getds_endereco());
        txtEndereco.setFocusable(false);

        TextView txtContato = (TextView)findViewById(R.id.txtContato);
        txtContato.setText(visita.getds_contato());
        txtContato.setFocusable(false);

        TextView lblresultado = (TextView)findViewById(R.id.lblresultado);
        TextView lblobservacao= (TextView)findViewById(R.id.lblobservacao);
        Spinner cmbresultado = (Spinner)findViewById(R.id.spinresultado);
        EditText txtobsercao = (EditText)findViewById(R.id.txtobservacao);


        TableRow lblresultadox = (TableRow)findViewById(R.id.lblresultadox);
        TableRow lblobservacaox= (TableRow)findViewById(R.id.lblobservacaox);
        TableRow txtobservacaox = (TableRow)findViewById(R.id.txtobservacaox);
        TableRow txtresultadoox = (TableRow)findViewById(R.id.txtresultadox);

        EditText txtobservacaoz = (EditText)findViewById(R.id.txtobservacaoz);
        EditText txtresultadooz = (EditText)findViewById(R.id.txtresultadoz);


        btnCheckout.setVisibility(View.GONE);
        btnCheckin.setVisibility(View.GONE);
        btnReagendar.setVisibility(View.GONE);

        lblresultado.setVisibility(View.GONE);
        lblobservacao.setVisibility(View.GONE);
        cmbresultado.setVisibility(View.GONE);
        txtobsercao.setVisibility(View.GONE);

        lblresultadox.setVisibility(View.GONE);
        lblobservacaox.setVisibility(View.GONE);
        txtobservacaox.setVisibility(View.GONE);
        txtresultadoox.setVisibility(View.GONE);

        if (visita.getds_checkin().equals("")) {

            TabelaVisita.open();
            boolean CheckinAtivo = TabelaVisita.CheckinAtivo(visita.getid());
            TabelaVisita.close();

            if (CheckinAtivo) {
                btnCheckin.setVisibility(View.GONE);
                btnReagendar.setVisibility(View.GONE);
            }
            else {
                btnCheckin.setVisibility(View.VISIBLE);
                btnReagendar.setVisibility(View.VISIBLE);
            }
        }
        else
        {

            if (visita.getds_checkout().equals("")) {

                FillResultado();
                btnCheckin.setVisibility(View.GONE);
                btnReagendar.setVisibility(View.GONE);
                btnCheckout.setVisibility(View.VISIBLE);
                lblresultado.setVisibility(View.VISIBLE);
                lblobservacao.setVisibility(View.VISIBLE);
                cmbresultado.setVisibility(View.VISIBLE);
                txtobsercao.setVisibility(View.VISIBLE);

            }
            else
            {

                lblresultadox.setVisibility(View.VISIBLE);
                lblobservacaox.setVisibility(View.VISIBLE);
                txtobservacaox.setVisibility(View.VISIBLE);
                txtresultadoox.setVisibility(View.VISIBLE);

                txtobservacaoz.setText(visita.getds_observacao());
                txtobservacaoz.setFocusable(false);
                txtresultadooz.setText(visita.getds_resultado());
                txtresultadooz.setFocusable(false);

            }

        }

        setTitle(visita.getds_cliente());

    }

    public void onBtnChekin(View v) {

        btnCheckin.setEnabled(false);
        btnCheckin.setText(R.string.Aguarde);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(c.getTime());

        Intent i = getIntent();
        id_visita = i.getIntExtra("id_visita", 0);

        visitasDAO TabelaVisita = new visitasDAO(this);
        TabelaVisita.open();
        visitasDTO visita = TabelaVisita.Get(id_visita);
        TabelaVisita.close();
        if (visita==null)
            return;

        id_operacao="FazerCheckin";

        try
        {

            com.weblayer.visitas.SINC.checkSINC.Sincronizar(this,id_operacao, id_visita, "0", "", "");

            Toast toast = Toast.makeText(this, R.string.ChekInOk, Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            setResult(RESULT_OK, null);
            finish();

        } catch (Exception e) {

            btnCheckin.setText(R.string.ChekIn);
            btnCheckin.setEnabled(true);

            Log.e("weblayer.log", e.getMessage());

            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

        }

    }

    public void onBtnChekout(View v) {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(c.getTime());

        Intent i = getIntent();
        id_visita = i.getIntExtra("id_visita", 0);

        visitasDAO TabelaVisita = new visitasDAO(this);

        TabelaVisita.open();
        visitasDTO visita = TabelaVisita.Get(id_visita);
        TabelaVisita.close();
        if (visita==null)
            return;


        SpinnerResultado = (Spinner) findViewById(R.id.spinresultado);
        resultadoDTO resultado = AdapterResultado.getItem(SpinnerResultado.getSelectedItemPosition());

        EditText txtobsercao = (EditText)findViewById(R.id.txtobservacao);

        //Críticas
        if (resultado.getid()=="0")
        {
            //Resultado inválido...
            Toast toast = Toast.makeText(this, "Resultado inválido.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;

        }
        if (txtobsercao.length()==0)
        {
            //Observação inválida...
            Toast toast = Toast.makeText(this, "Observação inválida.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        id_operacao="FazerCheckout";

        btnCheckout.setEnabled(false);
        btnCheckout.setText(R.string.Aguarde);

        try
        {

            com.weblayer.visitas.SINC.checkSINC.Sincronizar(this,id_operacao, id_visita, resultado.getid(),
                    resultado.getds_descricao(), txtobsercao.getText().toString() );

            Toast toast = Toast.makeText(this, R.string.ChekOutOk, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            setResult(RESULT_OK, null);
            finish();

        } catch (Exception e) {

            btnCheckout.setText(R.string.ChekOut);
            btnCheckout.setEnabled(true);

            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            Log.e("weblayer.log", e.getMessage());

        }



    }

    public void onBtnReagendar(View v) {


        Intent i = getIntent();
        id_visita = i.getIntExtra("id_visita", 0);

        Intent intent = new Intent(this,reagendamentoActivity.class);
        intent.putExtra("id_visita", id_visita);
        startActivityForResult(intent, 1);

    }

    private void FillResultado() {

        resultadoDAO tabresultado =  new resultadoDAO();

        List<resultadoDTO> resultado = tabresultado.fillCombo();

        resultadoDTO[] resutladoArray = new resultadoDTO[resultado.size()];

        resultado.toArray(resutladoArray);

        AdapterResultado = new resultadoSpinAdapter(this, android.R.layout.simple_spinner_item, resutladoArray);

        SpinnerResultado.setAdapter(AdapterResultado);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, null);
            finish();
        }

    }


}
