package com.example.pixme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCadastrarPix = findViewById(R.id.btn_cadastrar_pix);
        btnCadastrarPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroPixActivity.class);
                startActivity(intent);
            }
        });

        Button btnCompartilharPix = findViewById(R.id.btn_compartilhar_pix);
        btnCompartilharPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompartilharPixActivity.class);
                startActivity(intent);
            }
        });
    }
}