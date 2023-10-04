package Service;

import Controlador.Administrador;
import Controlador.Curso;
import Model.DAOClase;
import Model.DAOException;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;

public class ClaseService {
    private DAOClase daoClase;

    public ClaseService(){
        daoClase = new DAOClase();
    }

    public void guardarClase(Curso curso) throws ServiceException{
        try{
            daoClase.guardar(curso);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException((e.getMessage()));
        }
    }

    public void sumarRecaudacion(int recaudacion, int legajo) throws ServiceException{
        try{
            daoClase.sumarRecaudacion(recaudacion, legajo);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public void actualizarCupo(int legajo) throws ServiceException{
        try{
            daoClase.restarCupo(legajo);
        }catch (DAOException e){
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public Curso buscarClase(int id) throws ServiceException{
        try{
            return daoClase.buscarClase(id);
        } catch (DAOException e) {
            System.out.println(e);
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList buscarTodos() throws ServiceException{
        ArrayList<Curso> datos = null;
        try {
            datos = daoClase.buscarTodos();
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }




}
