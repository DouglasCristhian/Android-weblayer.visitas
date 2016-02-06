package com.weblayer.visitas.SINC;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.weblayer.visitas.DAO.parametroDAO;
import com.weblayer.visitas.DAO.visitasDAO;
import com.weblayer.visitas.DTO.parametroDTO;
import com.weblayer.visitas.DTO.usuarioDTO;
import com.weblayer.visitas.DTO.visitasDTO;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class checkSINC {

    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/FischerBRTESTE/mobile/Service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

    public checkSINC() {}

    //"eron.eigat@fischerbrasil.com.br"

    public static String RetornaLista(Context context, String SOAP_ADDRESS,
                                      String operationname, Integer id_visita,
                                      String id_resultado,String ds_resultado,
                                      String ds_obsercacao) throws Exception {

        String OPERATION_NAME=operationname;

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        //Evitar o erro de acesso ao webservice.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ds_ip = com.weblayer.visitas.TOOLBOX.NetworkUtil.getIpAddressText(context);
        String ds_imei = com.weblayer.visitas.TOOLBOX.Common.getIMEI(context);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_visita");
        pi.setValue(id_visita);
        pi.setType(Integer.class);
        request.addProperty(pi);


        pi = new PropertyInfo();
        pi.setName("id_resultado");
        pi.setValue(id_resultado);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_resultado");
        pi.setValue(ds_resultado);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_observacao");
        pi.setValue(ds_obsercacao);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_ip");
        pi.setValue(ds_ip);
        pi.setType(String.class);
        request.addProperty(pi);


        pi = new PropertyInfo();
        pi.setName("ds_imei");
        pi.setValue(ds_imei);
        pi.setType(String.class);
        request.addProperty(pi);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,5000);

        Object response = null;

        try {

            if (!com.weblayer.visitas.TOOLBOX.Common.isNetWorkActive(context))
                throw new Exception("Rede não disponível");

            httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);

            response = envelope.getResponse();

        } catch (Exception e) {

            String errorMessage = (e.getMessage()==null)?"Rede não disponível":e.getMessage();
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);

        }

        if (response==null)
            throw new RuntimeException("Sem resposta do servidor");


        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String Objeto="";

        try
        {
            Objeto = response.toString();
        }
        catch(Exception e)
        {
            String errorMessage = (e.getMessage()==null)?"Rede não disponível":e.getMessage();

            Log.e("weblayer.log", errorMessage);

            throw new RuntimeException(errorMessage);
        }

        return Objeto;
    }

    public static void Sincronizar(Context context, String operationname, Integer id_visita,
                                   String id_resultado, String ds_resultado, String ds_observacao) throws Exception
    {

        String server = "";
        String usuario= "";
        String senha= "";
        String itens="";
        String ds_ip="";

        //buscar o server
        parametroDAO TabelaParametro =  new parametroDAO(context);

        TabelaParametro.open();

        parametroDTO param =TabelaParametro.Get("servidor");
        if (param!=null)
            server = param.getds_valor();

        server += "/mobile/Service.asmx";

        //buiscar o empresa
        param =TabelaParametro.Get("usuario");
        if (param!=null)
            usuario= param.getds_valor();

        //buscar a senha
        param =TabelaParametro.Get("senha");
        if (param!=null)
            senha= param.getds_valor();

        TabelaParametro.close();

        //Criticas
        if (server=="/mobile/Service.asmx")
            throw new Exception("ERRO:Servidor inválido. Verifique em configurações");

        if (usuario.length() == 0)
            throw new Exception("ERRO:Usuário inválido. Verifique em configurações");

        if (senha.length() == 0)
            throw new Exception("ERRO:Senha inválida. Verifique em configurações");

        try {

            //Buscar o IP

            ds_ip = com.weblayer.visitas.TOOLBOX.NetworkUtil.getIpAddressText(context);

            itens = RetornaLista(context, server, operationname, id_visita, id_resultado, ds_resultado, ds_observacao);

        }
        catch (Exception e) {

            throw e;

        }

        if (itens.length()==0)
            throw new Exception("ERRO:Hora da operação não retornada pelo server." );


        //gravar o horário na base...
        visitasDAO TabelaVisita = new visitasDAO(context);
        TabelaVisita.open();

        visitasDTO visita = TabelaVisita.Get(id_visita);
        if (visita==null) {
            TabelaVisita.close();
            throw new Exception("ERRO:Visita inválida.");
        }

        if (operationname.equals("FazerCheckin"))
        {
            visita.setds_checkin(itens);
        }

        if (operationname.equals("FazerCheckout")) {
            visita.setds_checkout(itens);
            visita.setid_resultado(id_resultado);
            visita.setds_resultado(ds_resultado);
            visita.setds_observacao(ds_observacao);
            visita.setds_ip(ds_ip);
        }

        TabelaVisita.insertorupdate(visita);

        TabelaVisita.close();

    }


}
