package jrl.acdat.acdat_tema4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Ejercicio2Activity extends AppCompatActivity implements View.OnClickListener{

    private static final String ENLACEJSON = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static final String APIKEY = "&APPID=2e354e3034667a83b1ef5072328fb84b";
    private static final String CODPAIS = "es";
    private static final String PARAMETRODIAS = "&cnt=";
    private static final int NUMDIAS = 7;

    private static final String NOMBREFICHEROGSON = "pronostico.gson";
    private static final String NOMBREFICHEROXML = "pronostico.xml";

    EditText edtCiudad;
    Button btnPrevision;
    AsyncHttpClient cliente;
    ArrayList<Dia> dias = new ArrayList<Dia>();
    Intent intent;
    Memoria memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        edtCiudad = (EditText)findViewById(R.id.edtCiudad);
        btnPrevision = (Button)findViewById(R.id.btnPrevision);
        btnPrevision.setOnClickListener(this);

        cliente = new AsyncHttpClient();
        intent = new Intent(this, PronosticoActivity.class);
        memoria = new Memoria(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnPrevision) {
            String ciudad = edtCiudad.getText().toString().toLowerCase();
            if(ciudad.length() == 0) {
                Toast.makeText(Ejercicio2Activity.this, "Debe escribir el nombre de una ciudad española", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("ciudad", (ciudad.substring(0, 1).toUpperCase() + ciudad.substring(1)).toString());
                intent.putExtra("numdias", String.valueOf(NUMDIAS));
                String enlace = ENLACEJSON + ciudad + "," + CODPAIS + PARAMETRODIAS + NUMDIAS + APIKEY;
                descarga(enlace);
            }
        }
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        cliente.get(web, new JsonHttpResponseHandler() {

            private ProgressDialog progreso;

            @Override
            public void onStart() {
                super.onStart();
                progreso = new ProgressDialog(Ejercicio2Activity.this);
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progreso.dismiss();

                try {
                    dias = Analisis.obtenerPronostico(response);
                    if(Analisis.crearFicheroGSON(Ejercicio2Activity.this, dias, NOMBREFICHEROGSON)) {
                        Toast.makeText(Ejercicio2Activity.this, "El fichero " + NOMBREFICHEROGSON + " ha sido creado con exito en la memoria externa", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Ejercicio2Activity.this, "ERROR: No se ha podido crear el fichero " + NOMBREFICHEROGSON, Toast.LENGTH_SHORT).show();
                    }
                    for(int i = 0; i < dias.size(); i++) {
                        intent.putExtra("dt_dia" + i, dias.get(i).getDt().toString());
                        intent.putExtra("tempminima_dia" + i, String.valueOf(dias.get(i).getTemperaturaMinima()));
                        intent.putExtra("tempmaxima_dia" + i, String.valueOf(dias.get(i).getTemperaturaMaxima()));
                        intent.putExtra("presion_dia" + i, String.valueOf(dias.get(i).getPresion()));
                    }
                    startActivity(intent);

                } catch (JSONException e) {
                    Toast.makeText(Ejercicio2Activity.this, "ERROR: No se ha podido leer el fichero JSON", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(Ejercicio2Activity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                Toast.makeText(Ejercicio2Activity.this, "Error al descargar el fichero JSON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
