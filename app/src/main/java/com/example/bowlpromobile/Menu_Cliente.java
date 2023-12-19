package com.example.bowlpromobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_Cliente extends AppCompatActivity {

    private Button bl2, bAgendaHoracio,bExibirHorario,bPerfil;
    private ImageView voltarLogin2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_cliente);
        allCompMenuCliente();
        bl2.setVisibility(View.INVISIBLE);
        voltarLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(),TelaLoginCliente.class);
                startActivity(e);
                finish();
            }
        });
        bAgendaHoracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TelaAgendarHorario.class);
                startActivity(i);
                finish();
            }
        });

        bExibirHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TelaExibirHorario.class);
                startActivity(i);
                finish();
            }
        });

        bPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TelaPerfil.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void allCompMenuCliente(){
        bAgendaHoracio = findViewById(R.id.buttonAgendar);
        bl2 = findViewById(R.id.bl22);
        voltarLogin2 = findViewById(R.id.iconVoltarforLogin2);
        bExibirHorario = findViewById(R.id.buttonExibirHorario);
        bPerfil = findViewById(R.id.buttonPerfil);

    }
}
