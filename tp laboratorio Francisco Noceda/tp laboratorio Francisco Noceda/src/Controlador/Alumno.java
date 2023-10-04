package Controlador;


import Service.AlumnoService;
import Service.InscripcionesService;
import Service.ServiceException;

import java.time.LocalTime;
import java.util.*;

public class Alumno {
    private String nombre;
    private String apellido;
    private static int sumador = 1;
    private int legajo = sumador;
    private Map<Curso, Integer> cursoYnotas = new LinkedHashMap();


    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        sumador += 1;
    }

    public Alumno() {
        sumador += 1;
    }

    public void agregarCurso(Curso curso, boolean becado, boolean descuento){
        if (puedeAnotarse(curso)) {
            this.cursoYnotas.put(curso, null);  // agrega a la lista el nombre del curso como key y las notas en null ya que todavia no tiene
            curso.aniadirAlumno(this); // añade al alumno en la lista que tiene el curso con sus inscriptos
            if (!becado) { //si el alumno está becado, el curso no suma a la recaudacion
                curso.sumarRecaudacion(descuento);
            }
        } else {
            if (this.cursoYnotas.size() == 3) {
                System.out.println("El usuario ya està ingresado a 3 materias");
            }
        }
    }


    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getLegajo() {
        return legajo;
    }

    public void agregarNotas(Curso curso, int nota){
        if(estaEnElCurso(curso)){ //verifica que estè en el curso
            this.cursoYnotas.put(curso, nota);
        }else{
            System.out.println("no està en el curso");
        }
    }
    public boolean estaAprobado(Curso curso){
        if(!estaEnElCurso(curso)){
            return false;
        }
        return cursoYnotas.get(curso) > curso.getNotaParaMinimaAprobar();
    }

    public boolean estaEnElCurso(Curso curso){
        return this.cursoYnotas.containsKey(curso);
    }
    public boolean puedeAnotarse(Curso curso){
        return !this.cursoYnotas.containsKey(curso) && this.cursoYnotas.size()<3;
    }

    public Map getCursoYnotas() {
        return cursoYnotas;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", legajo='" + legajo + '\'';
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
}
