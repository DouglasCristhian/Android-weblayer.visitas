package com.weblayer.visitas.VIEW;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.view.*;
import android.widget.*;
import com.weblayer.visitas.DAO.parametroDAO;
import com.weblayer.visitas.DAO.visitasDAO;
import com.weblayer.visitas.DTO.parametroDTO;
import com.weblayer.visitas.R;
import com.weblayer.visitas.DTO.visitasDTO;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;


public class pesquisaActivity extends ListActivity
{

    private Boolean fl_sincronizado;
    private ProgressDialog progress;
    private Boolean fl_sincronizando;

    private ArrayList<visitasDTO> m_orders = null;
    private OrderAdapter m_adapter;
    private ListView lista;
    private String response;
    private String strultimasinc="";
    private String dbultimasinc="";
    private String versionName;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);

        android.app.ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        versionName = com.weblayer.visitas.TOOLBOX.Common.getApplicationVersionName(this);

        RefreshLabelSinc();

        // Evento click da lista
        lista = getListView();
        lista.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                ItemSelecionado(arg2, arg3);

            }

        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FillList("");
    }

    private void RefreshLabelSinc()
    {


        parametroDAO paramDAO =  new parametroDAO(this);
        String strultimasinc="";
        paramDAO.open();
        parametroDTO param =paramDAO.Get("datasinc");
        if (param!=null) {
            dbultimasinc = param.getds_valor();
            setTitle(getString(R.string.app_name)+ " " + dbultimasinc.substring(0,8));
        }
        else {
            dbultimasinc="";
            setTitle(getString(R.string.app_name)+ " (Não sincronizado..)");
        }



        paramDAO.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            //Home
            case android.R.id.home:
                //Intent intent = new Intent(this, Activity_main.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                break;

            //Refresh
            case R.id.action_refresh:
                mLockScreenRotation();
                LongOperation processo = new LongOperation(this);
                //Destravar a orientação...
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                processo.execute("");
                break;

            // Config
            case R.id.action_configure:
                intent = new Intent(this, configActivity.class);
                startActivity(intent);
                break;

            //Sair
            case R.id.action_exit:
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void FillList(String Filtro) {

        try {

            //Buscar a ultima atualização
            TextView ultimasinc = (TextView) findViewById(R.id.txtultimasinc);
            if (dbultimasinc.length()==0)
                ultimasinc.setText("Não sincronizado" + " - Ver " + versionName );
            else
                ultimasinc.setText(getString(R.string.Datasinc)+ " " + dbultimasinc + " - Ver " + versionName);

            visitasDAO TabelaVisita = new visitasDAO(this);

            m_orders = new ArrayList<visitasDTO>();

            TabelaVisita.open();
            m_orders = (ArrayList<visitasDTO>) TabelaVisita.fillAll();
            TabelaVisita.close();

            this.m_adapter = new OrderAdapter(this,R.layout.pesquisa_linha, m_orders);

            setListAdapter(this.m_adapter);

            Log.i("ARRAY", "" + m_orders.size());

        } catch (Exception e) {

            Log.e("weblayer.log", e.getMessage());
        }

    }

    private void ItemSelecionado(int position, long id)
    {

        visitasDTO o = m_orders.get(position);

        Intent intent = new Intent(this,visitaActivity.class);
        intent.putExtra("id_visita", o.getid() );
        startActivityForResult(intent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            FillList("");
        }

    }

    public void onBtnpesquisavoltar(View v)
    {
        finish();
    }

    private class OrderAdapter extends ArrayAdapter<visitasDTO> {

        //private int[] colors = new int[] { 0xFFFFFFFF, 0xFFF5F5F5 };

        private ArrayList<visitasDTO> items;

        public OrderAdapter(Context context, int textViewResourceId,
                            ArrayList<visitasDTO> items) {
            super(context, textViewResourceId, items);
            this.items = items;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = vi.inflate(R.layout.pesquisa_linha, null);
            }


            visitasDTO o = items.get(position);
            if (o != null) {

                TextView tt = (TextView) v.findViewById(R.id.txtcliente);
                TextView bt = (TextView) v.findViewById(R.id.txtcontato);
                //TextView ft = (TextView) v.findViewById(R.id.footertext);

                ImageView im = (ImageView) v.findViewById(R.id.imageView);


                if (tt != null) {
                    tt.setText(o.getds_cliente());
                }

                if (bt != null) {
                    bt.setText(o.getds_contato());
                }

                im.setVisibility(View.INVISIBLE);

                if (o.getds_checkin().equals("")) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.ic_openx);
                }
                else
                {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.ic_chekinx);
                }

                if (!o.getds_checkout().equals("")) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.ic_checkoutx);
                }

            }



            return v;
        }
    }

    private void Sincronizar(Context context) throws Exception
    {

        try
        {

            com.weblayer.visitas.SINC.usuarioSINC.Sincronizar(context);
            com.weblayer.visitas.SINC.visitasSINC.Sincronizar(context);


        } catch (Exception e) {

            throw new Exception(e);

        }

    }

    public void onDestroy() {
        super.onDestroy();
        if (progress != null && progress.isShowing()) {
            progress.cancel();
        }

    }

    private void mLockScreenRotation() {
        // Stop the screen orientation changing during an event
        switch (this.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        private Context context;


        public LongOperation(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {


            progress = new ProgressDialog(context);
            progress.setTitle(R.string.Sincronizacao);
            progress.setMessage(getResources().getString(R.string.Aguarde));
            progress.show();

            fl_sincronizando=true;

        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                Sincronizar(context);

                return "";

            } catch (Exception e) {

                String message = e.getMessage();

                if (message.indexOf("Exception:")>0)
                    message = message.substring(message.indexOf("Exception:") + 10, message.length());

                return message;

            }

        }

        @Override
        protected void onPostExecute(String result) {

            try
            {
                RefreshLabelSinc();
                FillList("");
                progress.dismiss();

            } catch (Exception e) {

                Log.e("weblayer.log", e.getMessage());

                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (result.length()>0) {

                Toast toast = Toast.makeText(context, result, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                //new AlertDialog.Builder(context)
                //        .setMessage(result)
                //        .setIcon(android.R.drawable.ic_dialog_alert)
                //        .setPositiveButton("OK", null)
                //        .show();
            }

            fl_sincronizando=false;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}


