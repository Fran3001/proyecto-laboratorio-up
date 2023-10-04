package Controlador;


import Service.ClaseService;
import Service.ServiceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Curso {
    private String nombre;
    private int cupo;
    private int precio;
    private int notaParaMinimaAprobar;
    private static int sumador = 1;
    private int legajo = sumador;
    private static LinkedHashMap reporteTotal = new LinkedHashMap<>();
    private List<Alumno> listaInscriptos = new ArrayList<Alumno>();
    private int recaudacionTotal;
    private Profesor profesor;

    public Curso(String nombre, int cupo, Profesor profesor, int precio, int notaParaMinimaAprobar) {
        this.nombre = nombre;
        this.cupo = cupo;
        this.profesor = profesor;
        this.precio = precio;
        this.notaParaMinimaAprobar = notaParaMinimaAprobar;
        reporteTotal.put(this.getNombre(), recaudacionTotal);
        sumador += 1;
    }

    public Curso(){
        sumador += 1;
    }

    public LinkedHashMap anotadoVSaprobado(){
        LinkedHashMap<String, String> anotadoVSaprobado = new LinkedHashMap();
        for(int i = 0; i<this.listaInscriptos.size();i++){
            if(listaInscriptos.get(i).estaAprobado(this)){
                anotadoVSaprobado.put(listaInscriptos.get(i).getLegajo() + " " + listaInscriptos.get(i).getNombre() + " " + listaInscriptos.get(i).getApellido(), "Aprobado");
            }else{
                anotadoVSaprobado.put(listaInscriptos.get(i).getLegajo() + " " + listaInscriptos.get(i).getNombre() + " " + listaInscriptos.get(i).getApellido(), "Desaprobado");
            }
        }
        return anotadoVSaprobado;
    }

    public void actualizarCupo(){
        this.cupo -= 1;
    }

    public void sumarRecaudacion(boolean descuento){ //si descuento es true, divide el precio de la inscripcion
        if(descuento){
            recaudacionTotal+=precioConDescuento(this.precio);
        }else{
            recaudacionTotal+=precio;
        }
        reporteTotal.put(this.getNombre(), recaudacionTotal);
    }

    public int precioConDescuento(int precio){
        return precio/2;
    }

    public List generarReporte(){
        List reporte = new ArrayList();
        reporte.add("La recaudacion del curso de "+ nombre + " fue de $" + recaudacionTotal);
        for(int i=0; i<listaInscriptos.size();i++){
            reporte.add(listaInscriptos.get(i));
        }
        return reporte;
    }

    public void aniadirAlumno(Alumno alumno) {
        actualizarCupo();
        this.listaInscriptos.add(alumno);
    }
    public int getCupo() {
        return cupo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public int getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public List<Alumno> getListaInscriptos() {
        return listaInscriptos;
    }

    public int getPrecio() {
        return precio;
    }
    public static LinkedHashMap getReporteTotal() {
        return reporteTotal;
    }


    public int getNotaParaMinimaAprobar() {
        return notaParaMinimaAprobar;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public void setRecaudacionTotal(int recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setNotaParaMinimaAprobar(int notaParaMinimaAprobar) {
        this.notaParaMinimaAprobar = notaParaMinimaAprobar;
    }

    public String toStringMostrar(){
        return "Legajo del curso: " + getLegajo() + ", Nombre: " + getNombre() + ", Cupo restante: " + getCupo() + ", Legajo del profesor: " + getProfesor().getLegajo() + ", Nombre del profesor: " + getProfesor().getNombre() + ", Nota para aporbar: " + getNotaParaMinimaAprobar() + ", Precio: " + getPrecio();
    }

    public String toStringReporteTotal(){
        return "Legajo del curso: " + getLegajo() + ", Nombre: " + getNombre() + ", Recaudacion: " + getRecaudacionTotal();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
