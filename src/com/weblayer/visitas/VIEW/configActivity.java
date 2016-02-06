package com.weblayer.visitas.VIEW;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.weblayer.visitas.DAO.parametroDAO;
import com.weblayer.visitas.R;
import com.weblayer.visitas.DTO.parametroDTO;

public class configActivity extends Activity
{
    TextView txtservidor;
    TextView txtusuario;
    TextView txtsenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.config);

        android.app.ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        txtusuario = (TextView) findViewById(R.id.txtconfigUsuario);
        txtsenha = (TextView) findViewById(R.id.txtconfigSenha);

        txtservidor = (TextView) findViewById(R.id.txtconfigServidor);

        parametroDAO TabelaParametro = new parametroDAO(this);

        TabelaParametro.open();

        parametroDTO paramDTO = TabelaParametro.Get("servidor");
        if (paramDTO!=null)
            txtservidor.setText(paramDTO.getds_valor());

        paramDTO =TabelaParametro.Get("usuario");
        if (paramDTO!=null)
            txtusuario.setText(paramDTO.getds_valor());

        paramDTO =TabelaParametro.Get("senha");
        if (paramDTO!=null)
            txtsenha.setText(paramDTO.getds_valor());

        TabelaParametro.close();

        setTitle("Configuração");

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
    public void onBtnconfigVoltar(View v)
    {
        finish();
    }

    public void onBtnconfigSalvar(View v)
    {

        final ProgressDialog mydialog = ProgressDialog.show(this,"Salvando a Configuração...","Por favor, aguarde...",true);

        mydialog.setCancelable(true);

        parametroDAO TabelaParametro = new parametroDAO(this);

        TabelaParametro.open();
        //Servidor
        parametroDTO param = new parametroDTO();
        param.setid_parametro("servidor");
        param.setds_valor(txtservidor.getText().toString());
        TabelaParametro.insertorupdate(param);

        //Usuario
        param = new parametroDTO();
        param.setid_parametro("usuario");
        param.setds_valor(txtusuario.getText().toString());
        TabelaParametro.insertorupdate(param);

        //Senha
        param = new parametroDTO();
        param.setid_parametro("senha");
        param.setds_valor(txtsenha.getText().toString());
        TabelaParametro.insertorupdate(param);

        TabelaParametro.close();

        mydialog.dismiss();

        finish();


    }


}
