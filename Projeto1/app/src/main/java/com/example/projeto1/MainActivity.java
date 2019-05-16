package com.example.projeto1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ENABLE_BLUETOOTH = 11;
    private ListView devicesList;
    private Button scanningBtn;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devicesList = findViewById(R.id.devicesList);
        scanningBtn = findViewById(R.id.scanningBtn);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        devicesList.setAdapter(listAdapter);
        checkBluetoothState();
        scanningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter != null &&
                        bluetoothAdapter.isEnabled()) {
                    if (checkCoarseLocationPermission()) {
                        listAdapter.clear();
                        bluetoothAdapter.startDiscovery();
                    }
                } else {
                    checkBluetoothState();
                }
            }
        });
        checkCoarseLocationPermission();
    }
    Handler handler = new Handler();
    Runnable timedTask = new Runnable(){
        @Override
        public void run() {
            handler.postDelayed(timedTask, 1000);
        }};
    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(devicesFoundReceiver, new
                IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(devicesFoundReceiver, new
                IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        registerReceiver(devicesFoundReceiver, new
                IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }
    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(devicesFoundReceiver);
    }
    private boolean checkCoarseLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
            return false;
        }else{
            return true;
        }
    }
    private void checkBluetoothState(){
        if(bluetoothAdapter == null){
            Toast.makeText(this,"nao suportado",Toast.LENGTH_SHORT).show();
        }else{
            if (bluetoothAdapter.isEnabled()){
                if(bluetoothAdapter.isDiscovering()){
                    Toast.makeText(this,"carregando",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"desabilitado",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"precisa habilitar bluetooth",Toast.LENGTH_SHORT).show();
                        Intent enableIntent = new
                                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_ENABLE_BLUETOOTH){
            checkBluetoothState();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        ;
        switch(requestCode){
            case REQUEST_ACCESS_COARSE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permitido",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private final BroadcastReceiver devicesFoundReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if(BluetoothDevice.ACTION_FOUND.equals(action)){
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        //Informacoes
                        int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                        listAdapter.add("Nome:"+device.getName()+"\nEnd:"+
                                        device.getAddress()+"\nRSSI:"+
                                        rssi);
                        listAdapter.notifyDataSetChanged();
                    }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                        scanningBtn.setText("Scan");
                    }else if
                    (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                        scanningBtn.setText("Buscando");
                    }
                }
            };
}