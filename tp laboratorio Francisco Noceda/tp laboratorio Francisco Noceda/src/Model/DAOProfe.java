package Model;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Profesor;

import java.sql.*;
import java.util.ArrayList;

public class DAOProfe implements DAO<Profesor> {
    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL = "jdbc:h2:C:\\Users\\franc\\OneDrive\\Escritorio\\proyecto-laboratorio-up\\Base\\test";
    private String DB_USER="sa";
    private String DB_PASSWORD="";
    @Override
    public void guardar(Profesor elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into PROFESOR Values(?,?,?)");
            // script que elije el legajo para el nuevo admin
            String query = "SELECT MAX(LEGAJO) FROM PROFESOR";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int maxLegajo = 0;
            if(resultSet.next()){
                maxLegajo = resultSet.getInt(1);
            }

            // Verificar si hay legajos disponibles entre el 1 y el último en uso
            int nuevoLegajo = 0;
            if (maxLegajo > 1) {
                boolean legajoEncontrado = false;
                for (int i = 1; i <= maxLegajo; i++) {
                    if (!legajoEnUso(i, connection)) {
                        nuevoLegajo = i;
                        legajoEncontrado = true;
                        break;
                    }
                }
                // Si no se encontró un legajo disponible, asignar el siguiente al último en uso
                if (!legajoEncontrado) {
                    nuevoLegajo = maxLegajo + 1;
                }
            } else {
                nuevoLegajo = maxLegajo + 1;
            }
            elemento.setLegajo(nuevoLegajo);
            //
            preparedStatement.setInt(1, elemento.getLegajo());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setString(3, elemento.getApellido());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
    }


    public Profesor buscar(int id) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Profesor profesor=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM PROFESOR WHERE LEGAJO=?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();

            if (resultSet.next()) {
                profesor = new Profesor();
                profesor.setLegajo(resultSet.getInt("LEGAJO"));
                profesor.setNombre(resultSet.getString("NOMBRE"));
                profesor.setApellido(resultSet.getString("APELLIDO"));
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            throw  new DAOException(e.getMessage());
        }
        return profesor;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Profesor> datos = new ArrayList<>();
        Profesor profesor;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM PROFESOR");
            ResultSet resultSet =preparedStatement.executeQuery();

            while (resultSet.next()) {
                profesor = new Profesor();
                profesor.setNombre(resultSet.getString("NOMBRE"));
                profesor.setApellido(resultSet.getString("APELLIDO"));
                profesor.setLegajo(resultSet.getInt("LEGAJO"));
                datos.add(profesor);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM PROFESOR WHERE LEGAJO=?");
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("No se encontró ningún profesor con el legajo: " + id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());

        }
    }


    private boolean legajoEnUso(int legajo, Connection connection) throws DAOException {
        try{
            String query = "SELECT COUNT(*) FROM PROFESOR WHERE legajo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, legajo);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }
            return false;
        }catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }
}
