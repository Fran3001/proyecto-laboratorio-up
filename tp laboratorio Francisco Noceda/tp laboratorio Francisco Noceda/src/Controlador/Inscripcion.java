package Controlador;

public class Inscripcion {
    private int legajo;
    private int legajoAlumno;
    private String nombreAlumno;
    private int legajoClase;
    private String nombreClase;
    private int nota;

    public Inscripcion(){
    }

    public int getLegajo() {
        return legajo;
    }

    public int getLegajoAlumno() {
        return legajoAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public int getLegajoClase() {
        return legajoClase;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public void setLegajoAlumno(int legajoAlumno) {
        this.legajoAlumno = legajoAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public void setLegajoClase(int legajoClase) {
        this.legajoClase = legajoClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String toStringId() {
        return Integer.toString(legajo);
    }


    public String toStringNotas() {
        String st = ", No tiene nota asgnada";
        if(nota!=0){
            st = ", Nota: " + nota;
        }
        return ", Nombre alumno:" + nombreAlumno + ", Nombre clase: " + nombreClase + st;
    }

    @Override
    public String toString() {
        return "Legajo inscripcion: " + legajo + ", Legajo alumno:" + legajoAlumno +
                ", Nombre alumno:" + nombreAlumno + ", Legajo clase:" + legajoClase +
                ", Nombre clase:" + nombreClase + ", Nota: " + nota;
    }
}
