package Model;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Inscripcion;
import Controlador.Profesor;

import java.util.ArrayList;

public interface DAO<T>{
    void guardar(T elemento) throws DAOException;

    ArrayList buscarTodos() throws DAOException;

}