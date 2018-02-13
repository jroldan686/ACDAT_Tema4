package jrl.acdat.acdat_tema4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnEjercicio1, btnEjercicio2, btnEjercicio3, btnEjercicio4, btnEjercicio5;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEjercicio1 = (Button)findViewById(R.id.btnEjercicio1);
        btnEjercicio1.setOnClickListener(this);

        btnEjercicio2 = (Button)findViewById(R.id.btnEjercicio2);
        btnEjercicio2.setOnClickListener(this);

        btnEjercicio3 = (Button)findViewById(R.id.btnEjercicio3);
        btnEjercicio3.setOnClickListener(this);

        btnEjercicio4 = (Button)findViewById(R.id.btnEjercicio4);
        btnEjercicio4.setOnClickListener(this);

        btnEjercicio5 = (Button)findViewById(R.id.btnEjercicio5);
        btnEjercicio5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnEjercicio1)
            i = new Intent(this, Ejercicio1Activity.class);
        if(v == btnEjercicio2)
            i = new Intent(this, Ejercicio2Activity.class);
        if(v == btnEjercicio3)
            i = new Intent(this, Ejercicio3Activity.class);
        if(v == btnEjercicio4)
            i = new Intent(this, Ejercicio4Activity.class);
        if(v == btnEjercicio5)
            i = new Intent(this, Ejercicio5Activity.class);
        startActivity(i);
    }
}
