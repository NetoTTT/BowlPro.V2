package com.example.bowlpromobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class TelaPerfil extends AppCompatActivity {

    private ImageView voltaMenuCliente3;
    private TextView nome_P_Cliente,email_P_CLiente,old_P_Cliente;

    private String IDUser;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        allCompTelaPerfil();

        voltaMenuCliente3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Menu_Cliente.class);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        IDUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Clientes").document(IDUser);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if(documentSnapshot != null){
                    nome_P_Cliente.setText(documentSnapshot.getString("Nome"));
                    email_P_CLiente.setText(email);
                    old_P_Cliente.setText(documentSnapshot.getString("Idade"));
                }
            }
        });
    }



    private void allCompTelaPerfil(){
        voltaMenuCliente3 = findViewById(R.id.iconVoltarforMenuCliente3);
        nome_P_Cliente = findViewById(R.id.Caixa_text_nome_perfil);
        email_P_CLiente = findViewById(R.id.caixa_text_email_perfil);
        old_P_Cliente = findViewById(R.id.caixa_text_old_perfil);
    }
}
