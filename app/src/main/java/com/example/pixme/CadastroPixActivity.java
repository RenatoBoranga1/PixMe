package com.example.pixme;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroPixActivity extends AppCompatActivity {

    private Spinner spinnerTipoChave;
    private EditText editChavePix;
    private Button btnSalvarChave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pix);

        spinnerTipoChave = findViewById(R.id.spinner_tipo_chave);
        editChavePix = findViewById(R.id.edit_chave_pix);
        btnSalvarChave = findViewById(R.id.btn_salvar_chave);

        // Configurar o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_chave_pix, // Crie um array de strings no arquivo strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoChave.setAdapter(adapter);

        btnSalvarChave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipoChave = spinnerTipoChave.getSelectedItem().toString();
                String chavePix = editChavePix.getText().toString();

                // Aqui você pode adicionar a lógica para salvar a chave Pix
                Toast.makeText(CadastroPixActivity.this, "Chave Pix salva: " + tipoChave + " - " + chavePix, Toast.LENGTH_SHORT).show();
            }
        });
    }
}