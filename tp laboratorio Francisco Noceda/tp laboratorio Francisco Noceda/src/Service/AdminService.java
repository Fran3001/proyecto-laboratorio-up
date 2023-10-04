package Service;

import Controlador.Administrador;
import Model.DAOAdmin;
import Model.DAOException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminService {
    private DAOAdmin daoAdmin;

    public AdminService() {
       daoAdmin = new DAOAdmin();
    }

    public void guardarAdmin(Administrador admin) throws ServiceException{
        try {
            daoAdmin.guardar(admin);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

   public Administrador buscar(int id)throws ServiceException{
        Administrador admin = null;
        try {
            admin = daoAdmin.buscar(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return admin;
    }

    public ArrayList buscarTodos() throws ServiceException{
        ArrayList<Administrador> datos = null;
        try {
            datos = daoAdmin.buscarTodos();
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return datos;
    }

    public void eliminar(int id)throws ServiceException{
        try{
            daoAdmin.eliminar(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }




}
