package Model;
import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Curso;
import Controlador.Profesor;
import Service.ServiceException;

import java.sql.*;
import java.util.ArrayList;
public class DAOClase implements DAO<Curso>{

    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL = "jdbc:h2:C:\\Users\\franc\\OneDrive\\Escritorio\\proyecto-laboratorio-up\\Base\\test";
    private String DB_USER="sa";
    private String DB_PASSWORD="";

    @Override
    public void guardar(Curso elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //
            String query = "SELECT COUNT(LEGAJO) FROM CURSO";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int maxLegajo = 0;
            if(resultSet.next()){
                maxLegajo=resultSet.getInt(1);
            }
            int nuevoLegajo = maxLegajo+1;
            elemento.setLegajo(nuevoLegajo);
            //
            preparedStatement = connection.prepareStatement("INSERT into CURSO Values(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, elemento.getLegajo());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setInt(3, elemento.getCupo());
            preparedStatement.setInt(4, elemento.getProfesor().getLegajo());
            preparedStatement.setString(5, elemento.getProfesor().getNombre());
            preparedStatement.setInt(6, elemento.getNotaParaMinimaAprobar());
            preparedStatement.setInt(7, elemento.getPrecio());
            preparedStatement.setInt(8, elemento.getRecaudacionTotal());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
    }

    public void sumarRecaudacion(int recaudacion, int legajo) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("UPDATE CURSO SET RECAUDACIONTOTAL = (?)  WHERE LEGAJO = (?)");
            preparedStatement.setInt(1, recaudacion);
            preparedStatement.setInt(2, legajo);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
    }

    public void restarCupo(int legajo) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE CURSO SET CUPO = CUPO - 1 WHERE LEGAJO = (?)");
            preparedStatement.setInt(1, legajo);
            int res = preparedStatement.executeUpdate();
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
    }


    public Curso buscarClase(int id) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Curso curso = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM CURSO WHERE LEGAJO=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                curso = new Curso();
                curso.setLegajo(resultSet.getInt("LEGAJO"));
                curso.setNombre(resultSet.getString("NOMBRE"));
                curso.setCupo(resultSet.getInt("CUPO"));
                //
                Profesor profe = new Profesor();
                profe.setLegajo(resultSet.getInt("LEGAJO_PROFESOR"));
                profe.setNombre(resultSet.getString("NOMBRE_PROFESOR"));
                //
                curso.setProfesor(profe);
                curso.setNotaParaMinimaAprobar(resultSet.getInt("NOTAPARAAPROBAR"));
                curso.setPrecio(resultSet.getInt("PRECIO"));
                curso.setRecaudacionTotal(resultSet.getInt("RECAUDACIONTOTAL"));
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            throw  new DAOException(e.getMessage());
        }
        return curso;
    }


    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Curso> datos = new ArrayList<>();
        Profesor profe;
        Curso curso;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM CURSO");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                curso = new Curso();
                profe = new Profesor();
                curso.setLegajo(resultSet.getInt("LEGAJO"));
                curso.setNombre(resultSet.getString("NOMBRE"));
                curso.setCupo(resultSet.getInt("CUPO"));
                //
                profe.setNombre(resultSet.getString("NOMBRE_PROFESOR"));
                profe.setLegajo(resultSet.getInt("LEGAJO_PROFESOR"));
                //
                curso.setProfesor(profe);
                curso.setNotaParaMinimaAprobar(resultSet.getInt("NOTAPARAAPROBAR"));
                curso.setPrecio(resultSet.getInt("PRECIO"));
                curso.setRecaudacionTotal(resultSet.getInt("RECAUDACIONTOTAL"));
                datos.add(curso);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }
}
