package com.weblayer.visitas.TOOLBOX;

import java.sql.Date;
import java.text.SimpleDateFormat;



import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;

public class Common {


    //public static String getImei(Activity act) {
//asdfasdfasfd
     //return System.getString(act.getContentResolver(),System.ANDROID_ID);

    //}

//  private void EnviarNotificacao(String titulo, String mensagem, int icon)
//  {
//  	int MY_NOTIFICATION_ID=1;
//  	NotificationManager notificationManager;
//  	Notification myNotification;
//  	
//  	notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//  	
//  	myNotification = new Notification(icon, mensagem, System.currentTimeMillis());
//  	    	
//  	Context context = getApplicationContext();
//  	String notificationTitle = titulo;
//  	String notificationText = mensagem;
//  	
//  	
//  	Intent myIntent = new Intent(Intent.ACTION_VIEW,null);
//  	
//  	PendingIntent pendingIntent = PendingIntent.getActivity(Activity_Home.this,
//  			0, myIntent,
//  		       Intent.FLAG_ACTIVITY_NEW_TASK);
//  	
//  		   myNotification.defaults |= Notification.DEFAULT_LIGHTS;
//  		   myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//  		   myNotification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
//  		   
//  		   notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
//
//  		
//  }


    public static String getIMEI(Context context){

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }

    public static int getApplicationVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return 0;
    }

    public static String getApplicationVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return "";
    }
	
	public static boolean isNetWorkActive(Context ctx)
	{
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo current = cm.getActiveNetworkInfo();
		
		if (current==null)
			return false;
		else
			return true;
		
	}
	
	public static int GetIndexSpinner(Spinner spinner, Object myfind) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(myfind)) {
				index = i;
			} 
		}
		return index;
	}



	public static Date convertStringtoDate(String data) {

		if (data == null || data.equals(""))
			return null;

		Date date = null;
		try {
			SimpleDateFormat formato = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			date = new java.sql.Date(formato.parse(data).getTime());
		} catch (java.text.ParseException e) {
			date = null;
		}
		return date;
	}

	public static String convertDatetoString(java.util.Date datein) {

		if (datein == null || datein.equals(""))
			return "";

		String date = "";
		try {
			SimpleDateFormat formato = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			date = formato.format(datein);
		} catch (ParseException e) {
			return "";
		}
		return date;
	}

	public static void TestConnection(Context ctx) {
		try {

			ConnectivityManager cm = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			String Message;

			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.isConnected()) {
				Message = "Conectado a Internet 3G ";
			} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnected()) {
				Message = "Conectado a Internet WIFI ";
			} else {
				Message = "Sem acesso a internet ";
			}

			Toast.makeText(ctx, Message, Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public static int RetornaConnection(Context ctx) {

		int retorno = 0;

		try {
			ConnectivityManager cm = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.isConnected())
				retorno = 1;

			if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
				retorno = 2;

		} catch (Exception e) {
			retorno = 0;
		}

		return retorno;

	}
}
