package jrl.acdat.acdat_tema4;

public class Empresa {

    private String nombre;
    private String direccion;
    private String horario;
    private String telefono;
    private String web;

    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.nombre;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return this.horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefono() {
        return this.telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return this.web;
    }
    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "EMPRESA" + "\n" + this.nombre + "\n\n" +
                "DIRECCION" + "\n" + this.direccion + "\n\n" +
                "HORARIO" + "\n" + this.horario + "\n\n" +
                "TELEFONO" + "\n" + this.telefono + "\n\n" +
                "PAGINA WEB" + "\n" + this.web + "\n";
    }
}
