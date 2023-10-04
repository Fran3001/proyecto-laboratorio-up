package Controlador;

import Service.AdminService;
import Service.AlumnoService;
import Service.ClaseService;
import Service.ServiceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.time.LocalTime;


public class Administrador extends Usuario{

    public Administrador(String nombre, String apellido){
        super(nombre, apellido);
    }

    public Administrador() {
        super();
    }

    public Curso crearCurso(Curso curso){
        return curso;
    }
    public Alumno crearAlumno(Alumno alumno) {
        return alumno;
    }

    public void agregarAlumnoACurso(Alumno alumno, Curso curso, boolean becado){
        boolean descuento = corroborarDescuento();
        if(puedeAnotarse(curso, alumno)){
            alumno.agregarCurso(curso, becado, descuento);
        }else if(alumno.getCursoYnotas().size() > 3){
            System.out.println("no se pueden agregar mas cursos a este alumno");
        }else {
            System.out.println("el curso no tiene mas cupos");
        }
    }

    public boolean puedeAnotarse(Curso curso, Alumno alumno){
        return cantInscripciones(alumno)<3 && curso.getCupo()>0;
    }

    public boolean corroborarDescuento(){ //devuelve si se asigna un descuento o no (si se inscribe antes de las 12, se le asigna)
        LocalTime horaActual = LocalTime.now();
        LocalTime limite = LocalTime.of(12, 0);
        return horaActual.isBefore(limite);
    }

    public int cantInscripciones(Alumno alumno){ //devuelve la cantidad de cursos a la que esta asignado el alumno
        return alumno.getCursoYnotas().size();
    }

    public String toString(){
        return "Nombre: " + this.getNombre() + " Apellido: " + this.getApellido() + " Legajo: " + this.getLegajo();
    }


}
