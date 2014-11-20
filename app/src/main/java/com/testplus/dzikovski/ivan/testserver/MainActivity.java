package com.testplus.dzikovski.ivan.testserver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {


    private TextView tvConnectedDevices,tvStartTest;
    private View viewStartSeparator,viewSeparator1,viewSeparator2,viewConnectedDevices;
    private Button btnChooseQuestion;
    public ListView lvConnectedDevices;
    private EditText etPinNumber;

    String pinNumber;

    public List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();


    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    private boolean wifiDirectEnabled=false;


    public boolean isWifiDirectEnabled() {
        return wifiDirectEnabled;
    }

    public void setWifiDirectEnabled(boolean wifiDirectEnabled) {
        this.wifiDirectEnabled = wifiDirectEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve UI
        tvConnectedDevices=(TextView) findViewById(R.id.tvConnectedDevices);
        tvStartTest=(TextView) findViewById(R.id.tvStartTest);
        viewStartSeparator=(View) findViewById(R.id.viewStartSeparator);
        viewSeparator1=(View) findViewById(R.id.viewSeparator1);
        viewSeparator2=(View) findViewById(R.id.viewSeparator2);
        viewConnectedDevices=(View) findViewById(R.id.viewConnectedDevices);
        btnChooseQuestion=(Button) findViewById(R.id.btnChooseQuestion);
        lvConnectedDevices=(ListView) findViewById(R.id.lvConnectedDevices);
        etPinNumber=(EditText) findViewById(R.id.etPinNumber);

        //Initializing WiFi Direct manager

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        /*//Creating dummy items for list

        ArrayList<String> itemList=new ArrayList<String>();
        for (int i=0; i<=2; i++){
            itemList.add(i,"Item "+Integer.toString(i));
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemList);
        lvConnectedDevices.setAdapter(arrayAdapter);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void btnStartGroupClicked(View view){




        //Get entered pin number
        if (etPinNumber!=null) pinNumber=etPinNumber.getText().toString();

        //Create WiFi Direct group
        mManager.createGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

                startServer();

                //Updating UI

                if (tvConnectedDevices!=null) tvConnectedDevices.setVisibility(View.VISIBLE);
                if (tvStartTest!=null) tvStartTest.setVisibility(View.VISIBLE);
                if (viewStartSeparator!=null) viewStartSeparator.setVisibility(View.VISIBLE);
                if (viewSeparator1!=null) viewSeparator1.setVisibility(View.VISIBLE);
                if (viewSeparator2!=null) viewSeparator2.setVisibility(View.VISIBLE);
                if (viewConnectedDevices!=null) viewConnectedDevices.setVisibility(View.VISIBLE);
                if (btnChooseQuestion!=null) btnChooseQuestion.setVisibility(View.VISIBLE);
                if (lvConnectedDevices!=null) lvConnectedDevices.setVisibility(View.VISIBLE);

                pinNumber=etPinNumber.getText().toString();

                Toast.makeText(MainActivity.this, "Group created! Waiting for peers..", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Group not created!" + Integer.toString(reason), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btnChooseQuestionClicked(View view){
        Intent intent=new Intent(this,ChoseQuestionActivity.class);
        startActivity(intent);
    }

    public void startServer(){
        StartGroupAsyncTask asyncTask=new StartGroupAsyncTask(MainActivity.this,pinNumber);
        asyncTask.execute();
        Log.d("MainActivity::startServer","Server started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Group removed!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Group not removed!" + Integer.toString(reason), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
