package jrl.acdat.acdat_tema4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Ejercicio1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String ENLACEJSON = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String APIKEY = "&APPID=2e354e3034667a83b1ef5072328fb84b";
    private static final String CODPAIS = "es";

    ListView lsvListaCiudades;
    ArrayAdapter<String> adaptador;
    String[] ciudades;
    String nombreCiudad;
    AsyncHttpClient cliente;
    ArrayList<String> mediciones;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        lsvListaCiudades = (ListView)findViewById(R.id.lsvListaCiudades);
        lsvListaCiudades.setOnItemClickListener(this);

        ciudades = new String[]{"Asturias", "Barcelona", "Madrid", "Malaga", "Sevilla", "Valencia"};
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ciudades);
        lsvListaCiudades.setAdapter(adaptador);

        intent = new Intent(this, CiudadActivity.class);

        cliente = new AsyncHttpClient();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        nombreCiudad = ciudades[position].toString();
        String enlaceJSON = ENLACEJSON + nombreCiudad.toLowerCase() + "," + CODPAIS + APIKEY;
        descarga(enlaceJSON);
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        cliente.get(web, new JsonHttpResponseHandler() {

            private ProgressDialog progreso;

            @Override
            public void onStart() {
                super.onStart();
                progreso = new ProgressDialog(Ejercicio1Activity.this);
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progreso.dismiss();

                try {
                    mediciones = Analisis.obtenerClima(response);
                    intent.putExtra("ciudad", nombreCiudad);
                    intent.putExtra("temperatura", String.format("%.1f", (Double.parseDouble(mediciones.get(0)) + (-273.15))));
                    intent.putExtra("presion", mediciones.get(1));
                    intent.putExtra("humedad", mediciones.get(2));
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(Ejercicio1Activity.this, "ERROR: No se ha podido leer el fichero JSON", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(Ejercicio1Activity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                Toast.makeText(Ejercicio1Activity.this, "Error al descargar el fichero JSON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
