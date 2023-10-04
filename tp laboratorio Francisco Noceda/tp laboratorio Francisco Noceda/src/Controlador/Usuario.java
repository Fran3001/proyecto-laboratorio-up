package Controlador;

public abstract class Usuario {
    private String nombre;
    private String Apellido;
    private int legajo;
    static int sumador;
    public Usuario(String nombre, String apellido) {
        sumador+=1;
        this.nombre = nombre;
        Apellido = apellido;
        this.legajo = sumador;
    }

    public Usuario() {
        sumador+=1;
        this.legajo = sumador;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getApellido() {
        return Apellido;
    }

    public int getLegajo() {
        return legajo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", legajo=" + legajo +
                '}';
    }
}
