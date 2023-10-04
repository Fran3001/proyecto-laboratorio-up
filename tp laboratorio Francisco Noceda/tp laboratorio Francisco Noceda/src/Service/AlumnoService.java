package Service;

import Controlador.Administrador;
import Controlador.Alumno;
import Model.DAOAlumno;
import Model.DAOException;

import java.util.ArrayList;

public class AlumnoService {
    private DAOAlumno daoAlumno;

    public AlumnoService(){
        daoAlumno = new DAOAlumno();
    }


    public void guardarAlumno(Alumno alumno) throws ServiceException {
        try{
            daoAlumno.guardar(alumno);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Alumno buscar(int legajo)throws ServiceException{
        Alumno alumno = null;
        try {
            alumno = daoAlumno.buscar(legajo);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return alumno;
    }

    public ArrayList buscarTodos() throws ServiceException{
        ArrayList<Alumno> datos = null;
        try {
            datos = daoAlumno.buscarTodos();
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }





}
