package Controlador;

import Service.ProfesorService;
import Service.ServiceException;

import java.util.List;

public class Profesor extends Usuario {

    public Profesor(String nombre, String apellido){
        super(nombre, apellido);
    }

    public Profesor(){
        super();
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + ", Apellido: " + getApellido() + ", Legajo: " + getLegajo();
    }

    public void agregarNotas(Alumno alumno, Curso curso, int notas){
        if(corroborarPertenencia(curso)){
            alumno.agregarNotas(curso, notas);
        }else{
            System.out.println("El profesor no puede modificar una clase que no es suya");
        }
    }

    public boolean corroborarPertenencia(Curso curso){ //devuelve verdadero si el profesor enseña en el curso
        return this == curso.getProfesor();
    }




}