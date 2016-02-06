package com.weblayer.visitas.SINC;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.util.Log;
import com.weblayer.visitas.DAO.parametroDAO;
import com.weblayer.visitas.DTO.parametroDTO;
import com.weblayer.visitas.DTO.usuarioDTO;
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

public class usuarioSINC {

    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/FischerBRTESTE/mobile/Service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

    public usuarioSINC() {}

    //"eron.eigat@fischerbrasil.com.br"

    public static ArrayList<com.weblayer.visitas.DTO.usuarioDTO> RetornaLista(Context context, String SOAP_ADDRESS,
                                                               String usuario, String senha) throws Exception {

        String OPERATION_NAME="Login";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("usuario");
        pi.setValue(usuario);
        pi.setType(PropertyInfo.STRING_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("senha");
        pi.setValue(senha);
        pi.setType(PropertyInfo.STRING_CLASS);
        pi.setNamespace(WSDL_TARGET_NAMESPACE);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,10000);
        //httpTransport.debug = true;
        SoapPrimitive response = null;

        try {

            if (!com.weblayer.visitas.TOOLBOX.Common.isNetWorkActive(context))
                throw new RuntimeException("Rede não disponível");

            httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);

            response = (SoapPrimitive) envelope.getResponse();

        } catch (Exception e) {

            String errorMessage = (e.getMessage()==null)?"Rede não disponível":e.getMessage();
            Log.e("weblayer.log", errorMessage);
            throw new RuntimeException(errorMessage);

        }

        if (response==null)
            throw new RuntimeException("Sem resposta do servidor");

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        ArrayList<usuarioDTO> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<usuarioDTO>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            Log.e("weblayer.log", e.getMessage());
            throw e;
        }

        return Objetos;
    }

    public static void Sincronizar(Context context) throws Exception
    {

        String server = "";
        String usuario= "";
        String senha= "";


        //buscar o server
        parametroDAO TabelaParametro =new parametroDAO(context);

        TabelaParametro.open();

        parametroDTO param = TabelaParametro.Get("servidor");
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
            throw new RuntimeException("ERRO:Servidor inválido. Verifique em configurações");

        if (usuario.length() == 0)
            throw new RuntimeException("ERRO:Usuário inválido. Verifique em configurações");

        if (senha.length() == 0)
            throw new RuntimeException("ERRO:Senha inválida. Verifique em configurações");

        ArrayList<usuarioDTO> itens= RetornaLista(context, server, usuario, senha);
        if (itens==null)
            throw new RuntimeException("ERRO:Servidor não retornou informações");

        if (itens.get(0).getid_usuario()==0)
            throw new RuntimeException("ERRO:" + itens.get(0).getds_perfil());


        //Atualização de parametros
        TabelaParametro.open();

        param= new parametroDTO();
        param.setid_parametro("nomeempresa");
        param.setds_valor(itens.get(0).getds_empresa());
        TabelaParametro.insertorupdate(param);

        param= new parametroDTO();
        param.setid_parametro("idempresa");
        param.setds_valor(itens.get(0).getid_empresa().toString());
        TabelaParametro.insertorupdate(param);

        param= new parametroDTO();
        param.setid_parametro("idvendedor");
        param.setds_valor(itens.get(0).getid_vendedor().toString());
        TabelaParametro.insertorupdate(param);

        TabelaParametro.close();

    }


}
