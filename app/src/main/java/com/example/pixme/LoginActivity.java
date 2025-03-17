package com.example.pixme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                realizarLogin(email, senha);
            }
        });
        //Para salvar as credenciais iniciais descomente a linha abaixo e mude os dados.
        //salvarCredenciais("teste@teste.com", "123456");
    }

    private void realizarLogin(String email, String senha) {
        SharedPreferences preferences = getSharedPreferences("Credenciais", MODE_PRIVATE);
        String emailSalvo = preferences.getString("email", "");
        String senhaSalva = preferences.getString("senha", "");

        if (email.equals(emailSalvo) && senha.equals(senhaSalva)) {
            // Login bem-sucedido
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Login falhou
            Toast.makeText(LoginActivity.this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarCredenciais(String email, String senha) {
        SharedPreferences preferences = getSharedPreferences("Credenciais", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("senha", senha);
        editor.apply();
    }
}