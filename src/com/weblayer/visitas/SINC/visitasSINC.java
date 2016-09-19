package com.weblayer.visitas.SINC;
import java.lang.Integer;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.util.Log;
import android.widget.Toast;
import com.weblayer.visitas.DAO.parametroDAO;
import com.weblayer.visitas.DAO.visitasDAO;
import com.weblayer.visitas.DTO.parametroDTO;
import com.weblayer.visitas.DTO.retornoDTO;
import com.weblayer.visitas.DTO.visitasDTO;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class visitasSINC {

    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/FischerBRTESTE/mobile/Service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";
    public visitasSINC() {}

    public static ArrayList<retornoDTO> ReagendarVisitaCall(Context context, String SOAP_ADDRESS,
                                                                              Integer id_visita, String dt_visita) throws Exception {

        String OPERATION_NAME="ReagendarVisita";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_visita");
        pi.setValue(id_visita);
        pi.setType(PropertyInfo.INTEGER_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("dt_visita");
        pi.setValue(dt_visita);
        pi.setType(PropertyInfo.STRING_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes =true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,20000);
        httpTransport.debug = true;
        SoapPrimitive response = null;

        try {

            if (!com.weblayer.visitas.TOOLBOX.Common.isNetWorkActive(context))
                throw new RuntimeException("Rede não disponível");

            httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);

            response = (SoapPrimitive) envelope.getResponse();

        }
        catch (MalformedURLException e)
        {
            String errorMessage = "Servidor inválido";
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);
        }
        catch (Exception e) {

            String errorMessage = (e.getMessage()==null)?"Rede não disponível":e.getMessage();
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);
        }

        if (response==null)
            return null;

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        ArrayList<retornoDTO> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<retornoDTO>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    //Tentativa da correção do erro Host==null
    public static ArrayList<com.weblayer.visitas.DTO.visitasDTO> RetornaLista(Context context, String SOAP_ADDRESS,
                                                                              Integer id_empresa, Integer id_vendedor) throws Exception {

        String OPERATION_NAME="BuscarVisitas";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_empresa");
        pi.setValue(id_empresa);
        pi.setType(PropertyInfo.INTEGER_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("id_vendedor");
        pi.setValue(id_vendedor);
        pi.setType(PropertyInfo.INTEGER_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes =true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,20000);
        httpTransport.debug = true;
        SoapPrimitive response = null;

        try {

            if (!com.weblayer.visitas.TOOLBOX.Common.isNetWorkActive(context))
                throw new RuntimeException("Rede não disponível");

            httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);

            response = (SoapPrimitive) envelope.getResponse();

        }
        catch (MalformedURLException e)
        {
            String errorMessage = "Servidor inválido";
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);
        }
        catch (Exception e) {

            String errorMessage = (e.getMessage()==null)?"Rede não disponível":e.getMessage();
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);
        }

        if (response==null)
            return null;

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        ArrayList<visitasDTO> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<visitasDTO>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static retornoDTO ReagendarVisita(Context context, Integer id_visita, String dt_visita)throws Exception
    {

        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        //buscar o server
        parametroDAO paramDAO =  new parametroDAO(context);

        paramDAO.open();

        parametroDTO param =paramDAO.Get("servidor");
        if (param!=null)
            server = param.getds_valor();

        server += "/mobile/Service.asmx";

        //buiscar o empresa
        param =paramDAO.Get("idempresa");
        if (param!=null)
            idempresa= Integer.parseInt(param.getds_valor());

        //buscar a senha
        param = paramDAO.Get("idvendedor");
        if (param!=null)
            idvendedor= Integer.parseInt(param.getds_valor());

        paramDAO.close();

        ArrayList<retornoDTO> itens = ReagendarVisitaCall(context, server, id_visita, dt_visita);

        if (itens==null)
            return null;

        return itens.get(0);

    }

    public static void Sincronizar(Context context) throws Exception
    {

        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        //buscar o server
        parametroDAO paramDAO =  new parametroDAO(context);

        paramDAO.open();

        parametroDTO param =paramDAO.Get("servidor");
        if (param!=null)
            server = param.getds_valor();

        server += "/mobile/Service.asmx";

        //buiscar o empresa
        param =paramDAO.Get("idempresa");
        if (param!=null)
            idempresa= Integer.parseInt(param.getds_valor());

        //buscar a senha
        param = paramDAO.Get("idvendedor");
        if (param!=null)
            idvendedor= Integer.parseInt(param.getds_valor());

        paramDAO.close();

        ArrayList<visitasDTO> itens = RetornaLista(context, server, idempresa, idvendedor);

        if (itens==null)
            return;

        //Limpar as visitas do dia..
        visitasDAO TabelaVisita = new visitasDAO(context);
        TabelaVisita.open();

        TabelaVisita.ClearTable();

        //Incluir todas as visitas..
        for (visitasDTO item : itens) {
            TabelaVisita.insertorupdate(item);
        }
        TabelaVisita.close();


        //Servidor
        param = new parametroDTO();
        paramDAO =  new parametroDAO(context);
        paramDAO.open();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        param.setid_parametro("datasinc");
        param.setds_valor(date);
        paramDAO.insertorupdate(param);
        paramDAO.close();


    }

}



