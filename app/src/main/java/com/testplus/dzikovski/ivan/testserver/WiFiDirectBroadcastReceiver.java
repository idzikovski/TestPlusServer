package com.testplus.dzikovski.ivan.testserver;

import android.content.BroadcastReceiver;
import 	android.content.Context;
import java.util.Collection;
import 	android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.util.Log;
import java.util.Iterator;
import android.widget.Toast;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private MainActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
                                       MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Toast.makeText(context,"Wifi Direct ON",Toast.LENGTH_LONG).show();
                mActivity.setWifiDirectEnabled(true);
            } else {
                // Wi-Fi P2P is not enabled
                Toast.makeText(context,"Wifi Direct OFF",Toast.LENGTH_LONG).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                @Override
                public void onPeersAvailable(WifiP2pDeviceList peers) {

                    Log.d("WIFI_P2P_PEERS_CHANGED_ACTION",String.format("%d peers available",peers.getDeviceList().size()));
                    mActivity.peers.clear();
                    mActivity.peers.addAll(peers.getDeviceList());
                    /*Collection<WifiP2pDevice> deviceList= peers.getDeviceList();
                    Iterator iterator=deviceList.iterator();
                    if (deviceList!=null) {
                        WifiP2pDevice device=(WifiP2pDevice) iterator.next();
                        Log.d("WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION",device.deviceName);

                    }*/
                }
            });

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }

}