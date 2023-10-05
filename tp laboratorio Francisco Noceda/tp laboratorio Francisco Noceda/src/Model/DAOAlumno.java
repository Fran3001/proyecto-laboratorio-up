package Model;

import Controlador.Administrador;
import Controlador.Alumno;

import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.util.ArrayList;

public class DAOAlumno implements DAO<Alumno> {
    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL = "jdbc:h2:C:\\Users\\franc\\OneDrive\\Escritorio\\proyecto-laboratorio-up\\Base\\test";
    private String DB_USER="sa";
    private String DB_PASSWORD="";


    @Override
    public void guardar(Alumno elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into ALUMNOS Values(?,?,?)");
            // script que elije el legajo para el nuevo admin
            String query = "SELECT MAX(LEGAJO) FROM ALUMNOS";
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

    public Alumno buscar(int legajo) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Alumno alumno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM ALUMNOS WHERE LEGAJO=?");
            preparedStatement.setInt(1,legajo);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                alumno = new Alumno();
                alumno.setLegajo(resultSet.getInt("LEGAJO"));
                alumno.setNombre(resultSet.getString("NOMBRE"));
                alumno.setApellido(resultSet.getString("APELLIDO"));
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            throw  new DAOException(e.getMessage());
        }
        return alumno;
    }


    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Alumno> datos = new ArrayList<>();
        Alumno alumno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM ALUMNOS");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                alumno = new Alumno();
                alumno.setNombre(resultSet.getString("NOMBRE"));
                alumno.setApellido(resultSet.getString("APELLIDO"));
                alumno.setLegajo(resultSet.getInt("LEGAJO"));
                datos.add(alumno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    private boolean legajoEnUso(int legajo, Connection connection) throws DAOException {
        try {
            String query = "SELECT COUNT(*) FROM ALUMNOS WHERE legajo = ?";
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



}
