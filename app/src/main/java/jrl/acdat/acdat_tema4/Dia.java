package jrl.acdat.acdat_tema4;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Dia {

    @SerializedName("dt")
    private String dt;          // datatime
    @SerializedName("min")
    private double temperaturaMinima;
    @SerializedName("max")
    private double temperaturaMaxima;
    @SerializedName("pressure")
    private double presion;

    public String getDt() {
        return this.dt;
    }
    public void setDt(String dt) {
        this.dt = dt;
    }

    public double getTemperaturaMinima() {
        return this.temperaturaMinima;
    }
    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return this.temperaturaMaxima;
    }
    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getPresion() {
        return this.presion;
    }
    public void setPresion(double presion) {
        this.presion = presion;
    }

    @Override
    public String toString() {
        //SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
        //String fecha = formateador.format(new Date(Long.valueOf(dt)));
        String fecha = new Date(Long.valueOf(dt)).toString();
        fecha = fecha.substring(0, fecha.length() - 18) + fecha.substring(fecha.length() - 4, fecha.length());
        return "Fecha: " + fecha + "\n" +
               "Temperatura: " + getTemperaturaMinima() + " / " + getTemperaturaMaxima() + "\n" +
               "Presion: " + getPresion();
    }
}
