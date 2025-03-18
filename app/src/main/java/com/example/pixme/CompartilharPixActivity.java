package com.example.pixme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CompartilharPixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar_pix);

        Button btnWifi = findViewById(R.id.btn_compartilhar_wifi);
        Button btnNfc = findViewById(R.id.btn_compartilhar_nfc);
        Button btnBluetooth = findViewById(R.id.btn_compartilhar_bluetooth);
        Button btnQrCode = findViewById(R.id.btn_compartilhar_qrcode);

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para compartilhar via Wi-Fi Direct
                Toast.makeText(CompartilharPixActivity.this, "Compartilhando via Wi-Fi Direct", Toast.LENGTH_SHORT).show();
            }
        });

        btnNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para compartilhar via NFC
                Toast.makeText(CompartilharPixActivity.this, "Compartilhando via NFC", Toast.LENGTH_SHORT).show();
            }
        });

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para compartilhar via Bluetooth
                Toast.makeText(CompartilharPixActivity.this, "Compartilhando via Bluetooth", Toast.LENGTH_SHORT).show();
            }
        });

        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para gerar e exibir QR Code
                Toast.makeText(CompartilharPixActivity.this, "Gerando QR Code", Toast.LENGTH_SHORT).show();
            }
        });
    }
}