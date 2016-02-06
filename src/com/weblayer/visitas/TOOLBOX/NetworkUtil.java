package com.weblayer.visitas.TOOLBOX;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtil {

    private NetworkUtil() {
    }

    public static final String getIpAddressText(Context context) {

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String strIPAddess = ((ipAddress >> 0) & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "."
                + ((ipAddress >> 16) & 0xFF) + "." + ((ipAddress >> 24) & 0xFF);

        return strIPAddess;

    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // Log.e(Constants.LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static boolean connectionPresent(final ConnectivityManager cMgr) {

        if (cMgr != null) {

            NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
            if ((netInfo != null) && (netInfo.getState() != null)) {
                return netInfo.getState().equals(NetworkInfo.State.CONNECTED);
            } else {
                return false;
            }

        }
        return false;
    }

}
