package jrl.acdat.acdat_tema4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CiudadActivity extends AppCompatActivity {

    TextView txvCiudad, txvValorTemperatura, txvValorPresion, txvValorHumedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);

        txvCiudad = (TextView)findViewById(R.id.txvCiudad);
        txvValorTemperatura = (TextView)findViewById(R.id.txvValorTemperatura);
        txvValorPresion = (TextView)findViewById(R.id.txvValorPresion);
        txvValorHumedad = (TextView)findViewById(R.id.txvValorHumedad);

        Intent intent = getIntent();
        txvCiudad.setText(intent.getStringExtra("ciudad"));
        txvValorTemperatura.setText(intent.getStringExtra("temperatura"));
        txvValorPresion.setText(intent.getStringExtra("presion"));
        txvValorHumedad.setText(intent.getStringExtra("humedad"));
    }
}
