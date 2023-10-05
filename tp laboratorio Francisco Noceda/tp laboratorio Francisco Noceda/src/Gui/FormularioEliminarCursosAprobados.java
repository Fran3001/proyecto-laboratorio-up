package Gui;

import Controlador.Curso;
import Controlador.Inscripcion;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioEliminarCursosAprobados extends JPanel {
    PanelManager panel;
    InscripcionesService inscripcionesService;
    AdminService adminService;
    AlumnoService alumnoService;
    ClaseService claseService;
    JPanel formularioEliminarCursosAprobados;

    JLabel jLabelIdAlumno;
    JTextField jTextFieIdAlumno;
    JLabel jLabelIdAdmin;
    JTextField jTextFieIdAdmin;
    JButton jButtonEliminarCursosAprobados;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioEliminarCursosAprobados(PanelManager panel)
    {
        this.panel=panel;
        armarFormularioEliminar();
    }


    public void armarFormularioEliminar(){
        adminService = new AdminService();
        alumnoService = new AlumnoService();
        inscripcionesService = new InscripcionesService();
        claseService = new ClaseService();

        formularioEliminarCursosAprobados=new JPanel();
        formularioEliminarCursosAprobados.setLayout(new GridLayout(3,2));

        jLabelIdAlumno=new JLabel("Id alumno:");
        jTextFieIdAlumno= new JTextField(15);
        jLabelIdAdmin=new JLabel("Id admin:");
        jTextFieIdAdmin= new JTextField(15);
        jButtonEliminarCursosAprobados = new JButton("Eliminar cursos aprobados");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();


        formularioEliminarCursosAprobados.add(jLabelIdAlumno);
        formularioEliminarCursosAprobados.add(jTextFieIdAlumno);
        formularioEliminarCursosAprobados.add(jLabelIdAdmin);
        formularioEliminarCursosAprobados.add(jTextFieIdAdmin);


        setLayout(new BorderLayout());
        add(formularioEliminarCursosAprobados, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminarCursosAprobados);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones,BorderLayout.SOUTH);

        jButtonEliminarCursosAprobados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int legAlumno = Integer.parseInt(jTextFieIdAlumno.getText());
                int legAdmin = Integer.parseInt(jTextFieIdAdmin.getText());
                try{
                    if(adminService.buscar(legAdmin) != null){
                        if(alumnoService.buscar(legAlumno) != null){
                            if(inscripcionesService.buscarTodosPara(legAlumno) != null){
                                //
                                    ArrayList<String> listaIdInscripcionesSystem = inscripcionesService.buscarTodosIdPara(legAlumno);
                                    for (String inscripcion : listaIdInscripcionesSystem){
                                        int idIns = Integer.parseInt(inscripcion);
                                        Curso curso = claseService.buscarClase(inscripcionesService.getCurso(idIns));
                                        int nota = inscripcionesService.getNota(idIns);
                                        if(nota >= curso.getNotaParaMinimaAprobar()){
                                            inscripcionesService.eliminar(idIns);
                                            JOptionPane.showMessageDialog(null, "Operacion realizada con exito", "Confirmacion", JOptionPane.PLAIN_MESSAGE);
                                        }
                                    }
                                //
                            }else{
                                JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun alumno", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun alumno", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun admin", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (ServiceException serEx){
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la nota", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndice formularioIndice = new FormularioIndice(panel);
                panel.mostrarFormulario(formularioIndice);
            }
        });
    }
}
