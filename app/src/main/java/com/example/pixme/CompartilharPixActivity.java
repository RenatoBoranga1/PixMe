package com.example.pixme;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

//import com.google.zxing.WriterException;
//
//import androidmads.library.qrgenearator.QRGContents;
//import androidmads.library.qrgenearator.QRGEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public class CompartilharPixActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private WifiP2pDeviceList deviceList;
    private NfcAdapter nfcAdapter;
    private String chavePix = "sua_chave_pix_aqui"; // Substitua pela sua chave Pix real

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar_pix);

        Button btnWifi = findViewById(R.id.btn_compartilhar_wifi);
        Button btnNfc = findViewById(R.id.btn_compartilhar_nfc);
        Button btnBluetooth = findViewById(R.id.btn_compartilhar_bluetooth);
        Button btnQrCode = findViewById(R.id.btn_compartilhar_qrcode);
        Button btnVoltarMain = findViewById(R.id.btn_voltar_main);

        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC não suportado", Toast.LENGTH_SHORT).show();
        } else {
            // Linha comentada para evitar o erro de compilação
            // nfcAdapter.setNdefPushMessageCallback(this, this);
            Toast.makeText(this, "Funcionalidade NFC comentada", Toast.LENGTH_SHORT).show();
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        handler = new Handler();

        btnWifi.setOnClickListener(v -> descobrirDispositivos());

        btnNfc.setOnClickListener(v -> {
            if (nfcAdapter != null) {
                // Linha comentada para evitar o erro de compilação
                // Toast.makeText(CompartilharPixActivity.this, "Aproxime os dispositivos para compartilhar via NFC", Toast.LENGTH_SHORT).show();
                Toast.makeText(CompartilharPixActivity.this, "Funcionalidade NFC comentada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CompartilharPixActivity.this, "NFC não suportado", Toast.LENGTH_SHORT).show();
            }
        });

        btnBluetooth.setOnClickListener(v -> conectarBluetooth());

        btnQrCode.setOnClickListener(v -> {
            // Parte do QR Code comentada
            //gerarQRCode();
            Toast.makeText(this, "Funcionalidade de QR Code comentada", Toast.LENGTH_SHORT).show();
        });

        btnVoltarMain.setOnClickListener(v -> {
            Intent intent = new Intent(CompartilharPixActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void descobrirDispositivos() {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                wifiP2pManager.requestPeers(channel, peers -> {
                    deviceList = peers;
                    Toast.makeText(CompartilharPixActivity.this, "Dispositivos encontrados: " + deviceList.getDeviceList().size(), Toast.LENGTH_SHORT).show();
                    // Adicione aqui a lógica para exibir a lista de dispositivos e permitir a seleção
                    if(deviceList.getDeviceList().size() > 0){
                        conectar(deviceList.getDeviceList().iterator().next());
                    }
                });
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(CompartilharPixActivity.this, "Falha na descoberta de dispositivos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void conectar(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(CompartilharPixActivity.this, "Conectado ao dispositivo", Toast.LENGTH_SHORT).show();
                // Adicione aqui a lógica para transferir a chave Pix via Wi-Fi Direct
                transferirPixWifiDirect();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(CompartilharPixActivity.this, "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void transferirPixWifiDirect() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                Socket client = serverSocket.accept();
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(chavePix.getBytes());
                outputStream.close();
                client.close();
                serverSocket.close();
                runOnUiThread(() -> Toast.makeText(CompartilharPixActivity.this, "Chave Pix enviada via Wi-Fi Direct", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(CompartilharPixActivity.this, "Falha ao enviar chave Pix via Wi-Fi Direct", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        NdefRecord ndefRecord = NdefRecord.createTextRecord("en", chavePix);
        return new NdefMessage(ndefRecord);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        // Implemente a ação após a conclusão do envio NFC, se necessário
    }

    private void gerarQRCode() {
        /* Parte do QR Code comentada
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3 / 4;

        QRGEncoder qrgEncoder = new QRGEncoder(chavePix, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // Exiba o bitmap (QR Code) em um ImageView ou similar
            Toast.makeText(this, "QR Code gerado", Toast.LENGTH_SHORT).show();
        } catch (WriterException e) {
            Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void conectarBluetooth() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth não habilitado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adicione aqui a lógica para exibir a lista de dispositivos Bluetooth e permitir a seleção
        // Para simplificar, vamos simular a conexão com um dispositivo conhecido
        String deviceAddress = "ENDEREÇO_MAC_DO_DISPOSITIVO_BLUETOOTH"; // Substitua pelo endereço MAC real
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);

        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID padrão para SPP
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            outputStream.write(chavePix.getBytes());
            Toast.makeText(this, "Chave Pix enviada via Bluetooth", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Falha na conexão Bluetooth", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}