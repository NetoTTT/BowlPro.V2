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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TelaAgendarHorario extends AppCompatActivity {

    private Button bl3, bAgendar;
    private EditText nomeA, dataA, horaA, idA;
    private View conteiner3;
    private ImageView voltarMenuCliente;
    String msg[] = {"Preencha todos os campos!", "Horário agendado com sucesso!","Você Não pode mais agendar Horários!"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agendar_horario);
        allCompAgendarHorario();
        bl3.setVisibility(View.INVISIBLE);
        conteiner3.setVisibility(View.INVISIBLE);

        bAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome_a = nomeA.getText().toString();
                String data_a = dataA.getText().toString();
                String hora_a = horaA.getText().toString();
                if (nome_a.isEmpty() || data_a.isEmpty() || hora_a.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(v, msg[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {
                    AgendarHorario(v);

                }
            }
        });

        voltarMenuCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Menu_Cliente.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void AgendarHorario(View v) {
        String nome = nomeA.getText().toString();
        String data = dataA.getText().toString();
        String hora = horaA.getText().toString();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Clientes").document(userID);
        CollectionReference agendasRef = userRef.collection("Agendas");

        DocumentReference userRef1 = db.collection("ADM").document(userID);
        agendasRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshot = task.getResult();

                    int quantidadeDocumentos = documentSnapshot.size();
                    if (quantidadeDocumentos < 1) {
                        Map<String, Object> agenda = new HashMap<>();
                        int id = new Random().nextInt(100000);
                        String idRef = String.valueOf(id);
                        agenda.put("Id", id);
                        agenda.put("Nome", nome);
                        agenda.put("Data", data);
                        agenda.put("Hora", hora);

                        DocumentReference userRef1 = db.collection("Clientes").document(userID);
                        CollectionReference agendasRef1 = userRef1.collection("Agendas");
                        DocumentReference agendasDocRef = agendasRef1.document("1");
                        salvaAgendaADM();
                        agendasDocRef.set(agenda).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Snackbar snackbar = Snackbar.make(v, msg[1], Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.WHITE);
                                        snackbar.setTextColor(Color.BLACK);
                                        snackbar.show();
                                        nomeA.setText("");
                                        dataA.setText("");
                                        horaA.setText("");
                                        Log.d("db", "Sucesso ao Agendar o Horário!");
                                    }

                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Log.d("db_erro", "Erro ao Agendar o Horário!" + e.toString());

                                    }
                                });
                    }else{
                        Snackbar snackbar = Snackbar.make(v, msg[2], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }

                    Log.d("Firestore", "Quantidade de documentos: " + quantidadeDocumentos);
                } else {

                    Log.e("Firestore", "Erro ao obter documentos", task.getException());
                }
            }
        });


    }

    private void allCompAgendarHorario() {
        voltarMenuCliente = findViewById(R.id.iconVoltarforMenuCliente);
        bl3 = findViewById(R.id.bl3);
        conteiner3 = findViewById(R.id.conteiner3);
        nomeA = findViewById(R.id.nomeAgendar);
        dataA = findViewById(R.id.dataAgendar);
        horaA = findViewById(R.id.horaAgendar);
        bAgendar = findViewById(R.id.buttonAgendar);
    }

    private  void salvaAgendaADM(){
        String nome_a = nomeA.getText().toString();
        String data_a = dataA.getText().toString();
        String hora_a = horaA.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> agenda1 = new HashMap<>();
        agenda1.put("Nome", nome_a);
        agenda1.put("Data",data_a);
        agenda1.put("Hora",hora_a);

        String userID1 = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("ADM").document("Agendas");
        CollectionReference collectionReference = documentReference.collection(userID1);
        DocumentReference documentReference1 = collectionReference.document("Agenda");

        documentReference1.set(agenda1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d("db", "Sucesso ao salvar do dados!");
                    }

                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("db_erro", "Erro ao salvar os dados!" + e.toString());

                    }
                });
    }

}
