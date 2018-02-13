package jrl.acdat.acdat_tema4;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Analisis {

    public static ArrayList<Empresa> obtenerEmpresasRentACar(JSONObject object) throws Exception {
        JSONObject objetoDirectorios = object.getJSONObject("directorios");
        JSONArray directorios = objetoDirectorios.getJSONArray("directorio");
        JSONObject empresaJSON, nombre, telefono;
        JSONArray direccion;
        ArrayList<Empresa> empresas = new ArrayList<Empresa>();
        Empresa empresa = null;
        for(int i = 0; i < directorios.length(); i++) {
            empresa = new Empresa();
            empresaJSON = directorios.getJSONObject(i);
            direccion = empresaJSON.getJSONArray("direccion");
            empresa.setDireccion(direccion.getString(0));
            empresa.setHorario(empresaJSON.getString("horario"));
            nombre = empresaJSON.getJSONObject("nombre");
            empresa.setNombre(nombre.getString("content"));
            telefono = empresaJSON.getJSONObject("telefono");
            empresa.setTelefono(telefono.getString("content"));
            empresa.setWeb(empresaJSON.getString("web"));
            empresas.add(empresa);
        }
        return empresas;
    }

    public static double obtenerCambio(JSONObject object) throws JSONException {
        JSONObject ratios = (JSONObject)object.get("quotes");
        double cambio = ratios.getDouble("USDEUR");
        return cambio;
    }

    public static boolean crearFicheroGSON(Context contexto, ArrayList<Dia> dias, String nombreFichero) throws Exception {
        String texto;
        Memoria memoria = new Memoria(contexto);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        texto = gson.toJson(dias);
        if(memoria.disponibleEscritura()) {
            if(!memoria.escribirExterna(nombreFichero, texto, false, "UTF-8")) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Dia> obtenerPronostico(JSONObject object) throws Exception {
        JSONArray pronostico = object.getJSONArray("list");
        JSONObject diaJSON, temperaturas;
        ArrayList<Dia> dias = new ArrayList<Dia>();
        Dia dia = null;
        for(int i = 0; i < pronostico.length(); i++) {
            dia = new Dia();
            diaJSON = pronostico.getJSONObject(i);
            dia.setDt(String.valueOf(diaJSON.getLong("dt") * 1000));
            temperaturas = (JSONObject)diaJSON.get("temp");
            dia.setTemperaturaMinima(Double.valueOf(temperaturas.get("min").toString()));
            dia.setTemperaturaMaxima(Double.valueOf(temperaturas.get("max").toString()));
            dia.setPresion(Double.valueOf(diaJSON.get("pressure").toString()));
            dias.add(dia);
        }
        return dias;
    }

    public static ArrayList<String> obtenerClima(JSONObject object) throws JSONException {
        JSONObject clima = object.getJSONObject("main");
        ArrayList<String> mediciones = new ArrayList<String>();
        mediciones.add(String.valueOf(clima.get("temp")));
        mediciones.add(String.valueOf(clima.get("pressure")));
        mediciones.add(String.valueOf(clima.get("humidity")));
        return mediciones;
    }
}
