package jrl.acdat.acdat_tema4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

public class Ejercicio5Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String ENLACEJSON = "http://datos.gijon.es/doc/transporte/alquiler-coches.json";

    ListView lsvListaEmpresas;
    ArrayAdapter<Empresa> adaptador;
    AsyncHttpClient cliente;
    ArrayList<Empresa> empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio5);

        cliente = new AsyncHttpClient();

        lsvListaEmpresas = (ListView)findViewById(R.id.lsvListaEmpresas);
        lsvListaEmpresas.setOnItemClickListener(this);

        descarga(ENLACEJSON);
    }

    public void mostrarLista() {
        if(empresas != null) {
            adaptador = new ArrayAdapter<Empresa>(Ejercicio5Activity.this, android.R.layout.simple_list_item_1, empresas);
            lsvListaEmpresas.setAdapter(adaptador);
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Uri uri = Uri.parse(empresas.get(position).getWeb());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(Ejercicio5Activity.this, "Lo sentimos, sin datos de la pagina web", Toast.LENGTH_SHORT).show();
        }
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        cliente.get(web, new JsonHttpResponseHandler() {

            private ProgressDialog progreso;

            @Override
            public void onStart() {
                super.onStart();
                progreso = new ProgressDialog(Ejercicio5Activity.this);
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progreso.dismiss();

                try {
                    empresas = Analisis.obtenerEmpresasRentACar(response);
                    mostrarLista();
                } catch (JSONException e) {
                    Toast.makeText(Ejercicio5Activity.this, "ERROR: No se ha podido leer el fichero JSON", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(Ejercicio5Activity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                Toast.makeText(Ejercicio5Activity.this, "Error al descargar el fichero JSON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}