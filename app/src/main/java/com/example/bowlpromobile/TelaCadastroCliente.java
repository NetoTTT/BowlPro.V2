package com.example.bowlpromobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastroCliente extends AppCompatActivity {

    private EditText nome_cadastro,email_cadastro,senha_cadastro,idade_cadastro;
    private Button bl,buttonCadastrar;
    private View conteiner1_cadastro;

    private ImageView voltarforLogin;
    String msg[] = {"Preencha todos os campos!", "Cliente cadastrado com sucesso!"};
    String userID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_cliente);
        allCompCadastro();
        bl.setVisibility(View.INVISIBLE);
        conteiner1_cadastro.setVisibility(View.INVISIBLE);
        voltarforLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(),TelaLoginCliente.class);
                startActivity(e);
                finish();
            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome_c  = nome_cadastro.getText().toString();
                String email_c = email_cadastro.getText().toString();
                String senha_c = senha_cadastro.getText().toString();

                if( nome_c.isEmpty() || email_c.isEmpty() || senha_c.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, msg[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {
                    CadastrarUser(v);

                }
            }
        });

    }
    public void CadastrarUser(View v){

        String email = email_cadastro.getText().toString();
        String senha = senha_cadastro.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    SalvarDadosUser();

                    Snackbar snackbar = Snackbar.make(v, msg[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {

                    String erro;

                    try {

                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){

                        erro = "Digite uma senha com no mínimo 6 caracteres!";

                    }catch (FirebaseAuthInvalidCredentialsException e){

                        erro = "E-mail já existe!";

                    }catch (FirebaseAuthUserCollisionException e){

                        erro = "Essa conta já existe!";

                    } catch (Exception e){

                        erro = "Erro ao cadastrar usuário!";
                    }

                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }
            }
        });

    }

    private  void SalvarDadosUser(){

        String nome = nome_cadastro.getText().toString();
        String idade= idade_cadastro.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cliente = new HashMap<>();

        cliente.put("Nome", nome);
        cliente.put("Idade",idade);


        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Clientes").document(userID);

        documentReference.set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d("db", "Sucesso ao salvar do dados!");
                        Intent i = new Intent(getApplicationContext(),TelaLoginCliente.class);
                        startActivity(i);
                        finish();
                    }

                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("db_erro", "Erro ao salvar os dados!" + e.toString());

                    }
                });
    }




    private void allCompCadastro(){
        conteiner1_cadastro = findViewById(R.id.conteiner1);
        bl = findViewById(R.id.bl1);
        nome_cadastro = findViewById(R.id.nome_edit_cadastro);
        email_cadastro = findViewById(R.id.email_edit_cadastro);
        senha_cadastro = findViewById(R.id.senha_edit_cadastro);
        idade_cadastro = findViewById(R.id.idade_edit_cadastro);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        voltarforLogin = findViewById(R.id.iconVoltarforLogin);
    }


}
