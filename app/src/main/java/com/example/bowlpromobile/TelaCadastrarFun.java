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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastrarFun extends AppCompatActivity {

    private Button bl22,cadastrarFun;

    private ImageView voltarMenuADM1;

    private EditText eNome,eCPF,eCargo,eIdade,eID;
    String msg[] = {"Preencha todos os campos!", "Funcionário cadastrado com sucesso!"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_fun);
        AllCompCadFun();
        bl22.setVisibility(View.INVISIBLE);
        voltarMenuADM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TelaMenu_ADM.class);
                startActivity(i);
                finish();
            }
        });

        cadastrarFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome_f  = eNome.getText().toString();
                String cpf_f = eCPF.getText().toString();
                String cargo_f = eCargo.getText().toString();
                String idade_f = eIdade.getText().toString();
                String id_f = eIdade.getText().toString();

                if( nome_f.isEmpty() || cpf_f.isEmpty() || cargo_f.isEmpty() || idade_f.isEmpty() || id_f.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, msg[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {
                    CadastrarFun(v);

                }
            }
        });
    }

    private  void CadastrarFun(View v){

        String nome_f  = eNome.getText().toString();
        String cpf_f = eCPF.getText().toString();
        String cargo_f = eCargo.getText().toString();
        String idade_f = eIdade.getText().toString();
        String id_f = eID.getText().toString();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> fun = new HashMap<>();

        fun.put("Nome", nome_f);
        fun.put("CPF",cpf_f);
        fun.put("Cargo",cargo_f);
        fun.put("Idade",idade_f);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Funcionario").document(id_f);

        documentReference.set(fun).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d("db", "Sucesso ao salvar do dados!");

                        Snackbar snackbar = Snackbar.make(v, msg[1], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }

                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("db_erro", "Erro ao salvar os dados!" + e.toString());

                    }
                });
    }


    private void AllCompCadFun(){
        bl22 = findViewById(R.id.bl21);
        cadastrarFun = findViewById(R.id.buttonCadastrarFun1);
        voltarMenuADM1 = findViewById(R.id.iconVoltarforLogin12);
        eNome = findViewById(R.id.nome_edit_cadastroFun);
        eCargo = findViewById(R.id.cargo_edit_fun);
        eCPF = findViewById(R.id.cpf_edit_fun);
        eIdade = findViewById(R.id.idade_edit_fun);
        eID = findViewById(R.id.id_fun);
    }
}
