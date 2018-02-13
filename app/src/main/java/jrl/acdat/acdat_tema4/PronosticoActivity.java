package jrl.acdat.acdat_tema4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PronosticoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    int numdias;
    ArrayList<Dia> dias = new ArrayList<Dia>();
    ArrayAdapter<Dia> adapter;
    ListView ltvListaDias;
    TextView txvCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostico);

        txvCiudad = (TextView)findViewById(R.id.txvCiudad);

        Intent intent = getIntent();
        txvCiudad.setText(intent.getStringExtra("ciudad"));
        try {

            numdias = Integer.valueOf(intent.getStringExtra("numdias"));
            Dia dia = null;
            for(int i = 0; i < numdias; i++) {
                dia = new Dia();
                dia.setDt(intent.getStringExtra("dt_dia" + i));
                dia.setTemperaturaMinima(Double.valueOf(intent.getStringExtra("tempminima_dia" + i)));
                dia.setTemperaturaMaxima(Double.valueOf(intent.getStringExtra("tempmaxima_dia" + i)));
                dia.setPresion(Double.valueOf(intent.getStringExtra("presion_dia" + i)));
                dias.add(dia);
            }
        } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }

        ltvListaDias = (ListView)findViewById(R.id.ltvListaDias);
        ltvListaDias.setOnItemClickListener(this);

        mostrarLista();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void mostrarLista() {
        if (dias != null) {
            adapter = new ArrayAdapter<Dia>(this, android.R.layout.simple_list_item_1, dias);
            ltvListaDias.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }
}
