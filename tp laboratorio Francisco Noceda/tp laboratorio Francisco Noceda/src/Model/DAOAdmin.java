package Model;

import Controlador.Administrador;
import Controlador.Alumno;
import Service.ServiceException;

import java.sql.*;
import java.util.ArrayList;

public class DAOAdmin implements DAO<Administrador> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:C:\\Users\\franc\\OneDrive\\Escritorio\\lab\\Base\\test";

    private String DB_USER = "sa";
    private String DB_PASSWORD = "";


    @Override
    public void guardar(Administrador elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into ADMIN Values(?,?,?)");
            // script que elije el legajo para el nuevo admin
            String query = "SELECT MAX(LEGAJO) FROM ADMIN";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int maxLegajo = 0;
            if (resultSet.next()) {
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


    public Administrador buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Administrador administrador = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM ADMIN WHERE LEGAJO=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                administrador = new Administrador();
                administrador.setNombre(resultSet.getString("NOMBRE"));
                administrador.setApellido(resultSet.getString("APELLIDO"));
                administrador.setLegajo(resultSet.getInt("LEGAJO"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
        return administrador;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Administrador> datos = new ArrayList<>();
        Administrador administrador;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM ADMIN");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                administrador = new Administrador();
                administrador.setNombre(resultSet.getString("NOMBRE"));
                administrador.setApellido(resultSet.getString("APELLIDO"));
                administrador.setLegajo(resultSet.getInt("LEGAJO"));
                datos.add(administrador);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    private boolean legajoEnUso(int legajo, Connection connection) throws DAOException {
        try {
            String query = "SELECT COUNT(*) FROM ADMIN WHERE legajo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, legajo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM ADMIN WHERE LEGAJO=?");
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("No se encontró ningún administrador con el legajo: " + id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());

        }
    }
}
