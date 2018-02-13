package jrl.acdat.acdat_tema4;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ejercicio3Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String ENLACEJSON = "http://apilayer.net/api/live?access_key=";
    private static final String APIKEY = "7f3ebd36e434b6aec16e3a8f4770862b";

    EditText euros, dolares;
    RadioButton eurDolar, dolarEur;
    Button convertir;
    Conversion conversor;
    AsyncHttpClient cliente;
    boolean esEuro = false;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        euros = (EditText)findViewById(R.id.edtEuros);
        dolares = (EditText)findViewById(R.id.edtDolares);
        eurDolar = (RadioButton)findViewById(R.id.rdoEurosADolares);
        eurDolar.setChecked(true);
        dolarEur = (RadioButton)findViewById(R.id.rdoDolaresAEuros);
        convertir = (Button)findViewById(R.id.btnConvertir);
        convertir.setOnClickListener(this);

        cliente = new AsyncHttpClient();
    }

    @Override
    public void onClick(View v)
    {
        if (v == convertir)
        {
            if (eurDolar.isChecked())
            {
                if(euros.getText().length() == 0) {
                    Toast.makeText(Ejercicio3Activity.this, "Debe escribir un valor en euros", Toast.LENGTH_SHORT).show();
                } else {
                    esEuro = true;
                    descarga(ENLACEJSON + APIKEY);
                }
            }
            if (dolarEur.isChecked())
            {
                if(dolares.getText().length() == 0) {
                    Toast.makeText(Ejercicio3Activity.this, "Debe escribir un valor en dolares", Toast.LENGTH_SHORT).show();
                } else {
                    esEuro = false;
                    descarga(ENLACEJSON + APIKEY);
                }
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
                progreso = new ProgressDialog(Ejercicio3Activity.this);
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progreso.dismiss();

                try {
                    double cambio = Analisis.obtenerCambio(response);
                    conversor = new Conversion(cambio);
                    String resultado;
                    if(esEuro) {
                        resultado = conversor.convertirADolares(euros.getText().toString());
                        dolares.setText(resultado);
                    } else {
                        resultado = conversor.convertirAEuros(dolares.getText().toString());
                        euros.setText(resultado);
                    }
                } catch (JSONException e) {
                    Toast.makeText(Ejercicio3Activity.this, "ERROR: No se ha podido leer el fichero JSON", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(Ejercicio3Activity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                Toast.makeText(Ejercicio3Activity.this, "Error al descargar el fichero JSON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
