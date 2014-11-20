package com.testplus.dzikovski.ivan.testserver;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.testplus.dzikovski.ivan.test.model.Answer;
import com.testplus.dzikovski.ivan.test.model.Question;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Ivan on 11.11.2014.
 */
public class StartGroupAsyncTask extends AsyncTask {

    private Context context;
    private String pinNumber;
    ServerSocket serverSocket;

    @Override
    protected Object doInBackground(Object[] params) {
        try {

            serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            Log.d("SendMessageAsyncTask::doInBackground: Client Address: ", client.getInetAddress().toString());

            InputStream is=client.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br= new BufferedReader(isr);
            String enteredPinNumber=br.readLine();

            OutputStream os=client.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os);
            BufferedWriter bw=new BufferedWriter(osw);




            if (pinNumber.equals(enteredPinNumber)){
                Global.connectedDevices.clear();
                Global.connectedDevices.add(client.getInetAddress().toString());

                bw.write("Successfully connected!");
                bw.newLine();
                bw.flush();

                return client.getInetAddress().toString();
            }
            else{
                bw.write("Connection refused: Wrong PIN");
                bw.newLine();
                bw.flush();

                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        MainActivity mActivity=(MainActivity) context;
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1,Global.connectedDevices);
        mActivity.lvConnectedDevices.setAdapter(arrayAdapter);
        mActivity.startServer();
    }

    public StartGroupAsyncTask(Context context, String pinNumber) {
        super();
        this.context=context;
        this.pinNumber=pinNumber;
    }
}
