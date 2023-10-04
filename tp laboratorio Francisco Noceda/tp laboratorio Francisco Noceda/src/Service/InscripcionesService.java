package Service;

import Controlador.Curso;
import Controlador.Inscripcion;
import Model.DAOClase;
import Model.DAOException;
import Model.DAOInscripciones;

import java.util.ArrayList;

public class InscripcionesService {
    private DAOInscripciones daoInscripciones;
    public InscripcionesService(){
        daoInscripciones = new DAOInscripciones();
    }

    public void guardarInscripcion(int legajoAlumno, int legajoCurso) throws ServiceException{
        try{
            daoInscripciones.guardarInscripcion(legajoAlumno,legajoCurso);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public int getIdInscripcion(int legajo, int legajoCurso)throws ServiceException{
        try{
            return daoInscripciones.getIdInscripcion(legajo, legajoCurso);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }


    public void agregarNota(int nota, int legajoInscripcion) throws ServiceException{
        try{
            daoInscripciones.agregarNota(legajoInscripcion, nota);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean puedeInscribirse(int legajoAlumno, int legajoCurso) throws ServiceException{
        try{
            return daoInscripciones.puedeInscribirse(legajoAlumno, legajoCurso);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean existeInscripcion(int legajoAlumno, int legajoCurso) throws ServiceException{
        try{
            return daoInscripciones.existeInscripcion(legajoAlumno, legajoCurso);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public int getNota(int id) throws ServiceException{
        try{
            return daoInscripciones.getNota(id);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public int getCurso(int id) throws ServiceException{
        try{
            return daoInscripciones.getCurso(id);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }


    public ArrayList buscarTodos() throws ServiceException{
        ArrayList<Inscripcion> datos = null;
        try {
            datos = daoInscripciones.buscarTodos();
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }

    public ArrayList buscarTodosPara(int id) throws ServiceException{
        ArrayList<Inscripcion> datos = null;
        try {
            datos = daoInscripciones.buscarTodosPara(id);
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }

    public ArrayList buscarTodosIdPara(int id) throws ServiceException{
        ArrayList<String> datos = null;
        try {
            datos = daoInscripciones.buscarTodosIdPara(id);
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }

    public ArrayList buscarTodosParaCurso(int id) throws ServiceException{
        ArrayList<String> datos = null;
        try {
            datos = daoInscripciones.buscarTodosParaCurso(id);
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }

    public void eliminar(int id)throws ServiceException{
        try{
            daoInscripciones.eliminar(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }


}
