package com.example.bowlpromobile;




import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLoginCliente extends AppCompatActivity {
    private EditText email_login,senha_login;
    private Button bl,buttonEntrar;
    private View conteiner_login;

    private ProgressBar progressBar;

    private ImageView voltarforFirst;
    private TextView cadastro;
    String msg = "Preencha todos os campos!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_cliente);
        allComp();
        bl.setVisibility(View.INVISIBLE);
        conteiner_login.setVisibility(View.INVISIBLE);

        voltarforFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(e);
                finish();
            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaCadastroCliente.class);
                startActivity(intent);
                finish();
            }
        });

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_login.getText().toString();
                String senha = senha_login.getText().toString();

                if( email.isEmpty() || senha.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {

                    AuthCliente(v);

                }

            }
        });

    }

    public void AuthCliente( View v){

        String email = email_login.getText().toString();
        String senha = senha_login.getText().toString();


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Menu_Cliente();


                        }
                    }, 1000);
                } else {
                    String erro;

                    try {
                        throw task.getException();

                    } catch (Exception e){

                        erro = "Erro ao logar usuário!";
                    }

                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }
            }
        });
    }

    private void Menu_Cliente(){

        Intent c = new Intent(getApplicationContext(), Menu_Cliente.class);
        startActivity(c);
        finish();
    }



    private void allComp(){
        conteiner_login = findViewById(R.id.conteiner_Login);
        bl = findViewById(R.id.bl);
        email_login = findViewById(R.id.email_edit_login);
        senha_login = findViewById(R.id.senha_edit_login);
        cadastro = findViewById(R.id.CadastrarCliente);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        voltarforFirst = findViewById(R.id.iconVoltarforFirst);
        progressBar = findViewById(R.id.pb);
    }
}
