package Model;

import Controlador.*;
import Service.AlumnoService;
import Service.ClaseService;
import Service.ServiceException;

import java.sql.*;
import java.util.ArrayList;

public class DAOInscripciones implements DAO{
    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL = "jdbc:h2:C:\\Users\\franc\\OneDrive\\Escritorio\\lab\\Base\\test";
    private String DB_USER="sa";
    private String DB_PASSWORD="";

    ClaseService claseService = new ClaseService();
    AlumnoService alumnoService = new AlumnoService();


    public void guardarInscripcion(int legajoAlumno, int legajoCurso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into INSCRIPCIONES Values(?,?,?,?,?,?)");
            // script que elije el legajo para el nuevo admin
            String query = "SELECT MAX(ID_INSCRIPCION) FROM INSCRIPCIONES";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int maxLegajo = 0;
            if (resultSet.next()) {
                maxLegajo = resultSet.getInt(1);
            }
            Inscripcion inscripcion = new Inscripcion();
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
            //
            inscripcion.setLegajo(nuevoLegajo);
            inscripcion.setLegajoAlumno(legajoAlumno);
            inscripcion.setLegajoClase(legajoCurso);
            inscripcion.setNombreAlumno(alumnoService.buscar(legajoAlumno).getNombre());
            inscripcion.setNombreClase(claseService.buscarClase(legajoCurso).getNombre());
            //
            preparedStatement.setInt(1, inscripcion.getLegajo());
            preparedStatement.setInt(2, inscripcion.getLegajoAlumno());
            preparedStatement.setString(3, inscripcion.getNombreAlumno());
            preparedStatement.setInt(4, inscripcion.getLegajoClase());
            preparedStatement.setString(5, inscripcion.getNombreClase());
            preparedStatement.setString(6, null);

            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdInscripcion(int legajo, int legajoCurso) throws DAOException{
        int idInscripcion = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT ID_INSCRIPCION AS ID FROM INSCRIPCIONES WHERE LEGAJO_ALUMNO = (?) AND LEGAJO_CLASE = (?)");
            preparedStatement.setInt(1, legajo);
            preparedStatement.setInt(2, legajoCurso);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                idInscripcion = resultSet.getInt("ID");
            }

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }

        return idInscripcion;
    }

    public boolean puedeInscribirse(int legajo, int legajoCurso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean puede = false;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Verificar si el alumno tiene menos de 3 inscripciones
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM INSCRIPCIONES WHERE LEGAJO_ALUMNO = ?");
            preparedStatement.setInt(1, legajo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int cant = resultSet.getInt("CANTIDAD");
                if (cant < 3) {
                    // Verificar si el alumno ya está inscrito en el curso específico
                    boolean puede2 = !existeInscripcion(legajo, legajoCurso);
                        if (puede2) {
                            // Verificar si el cupo del curso es mayor a 0
                            preparedStatement = connection.prepareStatement("SELECT CUPO FROM CURSO WHERE LEGAJO = ?");
                            preparedStatement.setInt(1, legajoCurso);
                            resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                int cupo = resultSet.getInt("CUPO");
                                puede = cupo > 0;
                            }
                        }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
        return puede;
    }



    public boolean existeInscripcion(int legajoAlumno, int legajoCurso) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean existe = false;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM INSCRIPCIONES WHERE LEGAJO_ALUMNO = ? AND LEGAJO_CLASE = ?");
            preparedStatement.setInt(1, legajoAlumno);
            preparedStatement.setInt(2, legajoCurso);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int cant = resultSet.getInt("CANTIDAD");
                if(cant==1){
                    existe=true;
                }
            }
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }

        return existe;
    }

    public void agregarNota(int legajoInscripcion,int nota) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE INSCRIPCIONES SET NOTA = ? WHERE ID_INSCRIPCION = ?");
            preparedStatement.setInt(1, nota);
            preparedStatement.setInt(2, legajoInscripcion);
            int resultSet = preparedStatement.executeUpdate();
            System.out.println("se agrego una nota");

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
    }

    public int getNota(int id) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int nota = 0;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT NOTA FROM INSCRIPCIONES WHERE ID_INSCRIPCION = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                nota = resultSet.getInt("NOTA");
            }

        }catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            throw  new DAOException(e.getMessage());
        }
        return nota;
    }

    public int getCurso(int id) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int legCurso = 0;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT LEGAJO_CLASE FROM INSCRIPCIONES WHERE ID_INSCRIPCION = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                legCurso = resultSet.getInt("LEGAJO_CLASE");
            }
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            throw new DAOException(e.getMessage());
        }
        return legCurso;
    }

    @Override
    public void guardar(Object elemento) throws DAOException {

    }

    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Inscripcion> datos = new ArrayList<>();
        Inscripcion inscripcion;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM INSCRIPCIONES");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inscripcion = new Inscripcion();

                inscripcion.setLegajo(resultSet.getInt("ID_INSCRIPCION"));
                inscripcion.setLegajoAlumno(resultSet.getInt("LEGAJO_ALUMNO"));
                inscripcion.setNombreAlumno(resultSet.getString("NOMBRE_ALUMNO"));
                inscripcion.setLegajoClase(resultSet.getInt("LEGAJO_CLASE"));
                inscripcion.setNombreClase(resultSet.getString("NOMBRE_CLASE"));
                inscripcion.setNota(resultSet.getInt("NOTA"));
                datos.add(inscripcion);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<Inscripcion> buscarTodosPara(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Inscripcion> datos = new ArrayList<>();
        Inscripcion inscripcion;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM INSCRIPCIONES WHERE LEGAJO_ALUMNO = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inscripcion = new Inscripcion();
                inscripcion.setLegajo(resultSet.getInt("ID_INSCRIPCION"));
                inscripcion.setLegajoAlumno(resultSet.getInt("LEGAJO_ALUMNO"));
                inscripcion.setNombreAlumno(resultSet.getString("NOMBRE_ALUMNO"));
                inscripcion.setLegajoClase(resultSet.getInt("LEGAJO_CLASE"));
                inscripcion.setNombreClase(resultSet.getString("NOMBRE_CLASE"));
                inscripcion.setNota(resultSet.getInt("NOTA"));
                datos.add(inscripcion);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList buscarTodosIdPara(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<String> datos = new ArrayList<>();
        Inscripcion inscripcion;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM INSCRIPCIONES WHERE LEGAJO_ALUMNO = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inscripcion = new Inscripcion();
                inscripcion.setLegajo(resultSet.getInt("ID_INSCRIPCION"));
                datos.add(inscripcion.toStringId());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }
//

    public ArrayList buscarTodosParaCurso(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Inscripcion> datos = new ArrayList<>();
        Inscripcion inscripcion;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM INSCRIPCIONES WHERE LEGAJO_CLASE = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inscripcion = new Inscripcion();
                inscripcion.setLegajo(resultSet.getInt("ID_INSCRIPCION"));
                inscripcion.setLegajoAlumno(resultSet.getInt("LEGAJO_ALUMNO"));
                inscripcion.setNombreAlumno(resultSet.getString("NOMBRE_ALUMNO"));
                inscripcion.setLegajoClase(resultSet.getInt("LEGAJO_CLASE"));
                inscripcion.setNombreClase(resultSet.getString("NOMBRE_CLASE"));
                inscripcion.setNota(resultSet.getInt("NOTA"));
                datos.add(inscripcion);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }


    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM INSCRIPCIONES WHERE ID_INSCRIPCION=?");
            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage());

        }
    }

    private boolean legajoEnUso(int legajo, Connection connection) throws DAOException {
        try {
            String query = "SELECT COUNT(*) FROM INSCRIPCIONES WHERE ID_INSCRIPCION = ?";
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
