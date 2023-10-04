package Service;

import Controlador.Administrador;
import Controlador.Profesor;
import Model.DAOException;
import Model.DAOProfe;

import java.util.ArrayList;

public class ProfesorService {
    private DAOProfe daoProfe;

    public ProfesorService(){
        daoProfe = new DAOProfe();
    }

    public void guardarProfe(Profesor profesor) throws ServiceException{
        try {
            daoProfe.guardar(profesor);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Profesor buscar(int id)throws ServiceException{
        Profesor profesor = null;
        try {
            profesor = daoProfe.buscar(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return profesor;
    }

    public ArrayList buscarTodos() throws ServiceException{
        ArrayList<Profesor> datos = null;
        try {
            datos = daoProfe.buscarTodos();
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }
    public void eliminar(int id)throws ServiceException{
        try{
            daoProfe.eliminar(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

}
