package com.example.bowlpromobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.checkerframework.checker.units.qual.A;

public class BuscarFun extends AppCompatActivity {

    private Button bBuscarFun;

    private TextView tNome,tCPF,tIdade,tCargo;

    private ImageView voltarMenuADM222;

    private EditText tId;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_fun);
        AllCompBuscarFun();

        voltarMenuADM222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TelaMenu_ADM.class);
                startActivity(i);
                finish();
            }
        });
        bBuscarFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buscar = tId.getText().toString();

                DocumentReference d = db.collection("Funcionario").document(buscar);
                d.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value != null){
                            tNome.setText(value.getString("Nome"));
                            tCPF.setText(value.getString("CPF"));
                            tIdade.setText(value.getString("Idade"));
                            tCargo.setText((value.getString("Cargo")));
                        }
                    }
                });
            }
        });

    }

    private void AllCompBuscarFun(){
        bBuscarFun = findViewById(R.id.buttonBuscarFun3);
        tNome = findViewById(R.id.Caixa_text_nome_perfil2);
        tCPF = findViewById(R.id.caixa_text_email_perfil22);
        tIdade = findViewById(R.id.caixa_text_old_perfil2);
        tCargo = findViewById(R.id.caixa_text_email_perfil2);
        tId = findViewById(R.id.id_fun2);
        voltarMenuADM222 = findViewById(R.id.iconVoltarforMenuCliente333);
    }
}
