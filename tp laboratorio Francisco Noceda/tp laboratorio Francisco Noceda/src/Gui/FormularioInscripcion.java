package Gui;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Curso;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioInscripcion extends JPanel {

    PanelManager panel;
    ClaseService claseService;
    AdminService adminService;
    AlumnoService alumnoService;
    InscripcionesService inscripcionesService;

    JPanel formularioInscripcion;

    JLabel jLabelIdAlumno;
    JLabel jLabelIdAdmin;
    JLabel jLabelIdClase;

    JTextField jTextFieldIdAlumno;
    JTextField jTextFieldIdAdmin;
    JTextField jTextFieldIdClase;

    JButton jButtonGuardar;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    JRadioButton jRadioButtonBecado;
    JRadioButton jRadioButtonNoBecado;
    ButtonGroup buttonGroupBecado;

    public FormularioInscripcion(PanelManager panel){
        this.panel = panel;
        armarFormularioInscripcion();
    }

    public void armarFormularioInscripcion(){
        claseService = new ClaseService();
        adminService = new AdminService();
        alumnoService = new AlumnoService();
        inscripcionesService = new InscripcionesService();


        formularioInscripcion = new JPanel();
        formularioInscripcion.setLayout(new GridLayout(4,2));

        jLabelIdAlumno = new JLabel("Id del alumno");
        jLabelIdAdmin = new JLabel("Id del admin");
        jLabelIdClase = new JLabel("Id de la clase");

        jTextFieldIdAlumno = new JTextField();
        jTextFieldIdAdmin = new JTextField();
        jTextFieldIdClase = new JTextField();

        jButtonGuardar = new JButton("Guardar inscripcion");
        jButtonSalir = new JButton("Salir");

        jRadioButtonBecado = new JRadioButton("Becado");
        jRadioButtonNoBecado = new JRadioButton("No becado");
        buttonGroupBecado = new ButtonGroup();
        buttonGroupBecado.add(jRadioButtonBecado);
        buttonGroupBecado.add(jRadioButtonNoBecado);

        formularioInscripcion.add(jLabelIdAlumno);
        formularioInscripcion.add(jTextFieldIdAlumno);
        formularioInscripcion.add(jLabelIdAdmin);
        formularioInscripcion.add(jTextFieldIdAdmin);
        formularioInscripcion.add(jLabelIdClase);
        formularioInscripcion.add(jTextFieldIdClase);


        setLayout(new BorderLayout());
        add(formularioInscripcion, BorderLayout.CENTER);

        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        formularioInscripcion.add(jRadioButtonBecado);
        formularioInscripcion.add(jRadioButtonNoBecado);
        add(jPanelBotones, BorderLayout.SOUTH);


        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndice formularioIndice = new FormularioIndice(panel);
                panel.mostrarFormulario(formularioIndice);
            }
        });

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    if((!jRadioButtonBecado.isSelected() && !jRadioButtonNoBecado.isSelected()) || jTextFieldIdAdmin.getText().isEmpty() || jTextFieldIdAlumno.getText().isEmpty() || jTextFieldIdClase.getText().isEmpty()){  //verifica que al menos una opcion esté marcada
                        JOptionPane.showMessageDialog(null, "Ningun campo puede quedar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        if(alumnoService.buscar(Integer.parseInt(jTextFieldIdAlumno.getText())) != null){        //se verifica que el id alumno exista
                            if(claseService.buscarClase(Integer.parseInt(jTextFieldIdClase.getText())) != null){ //se verifica que el id clase exista
                                if(adminService.buscar(Integer.parseInt(jTextFieldIdAdmin.getText())) != null){  //se verifica que el id admin exista
                                    boolean esBecado = jRadioButtonBecado.isSelected();
                                    Administrador admin = adminService.buscar(Integer.parseInt(jTextFieldIdAdmin.getText()));
                                    Alumno alumno = alumnoService.buscar(Integer.parseInt(jTextFieldIdAlumno.getText()));
                                    Curso curso = claseService.buscarClase(Integer.parseInt(jTextFieldIdClase.getText()));
                                    if(inscripcionesService.puedeInscribirse(alumno.getLegajo(), curso.getLegajo())){
                                        admin.agregarAlumnoACurso(alumno, curso, esBecado);
                                        inscripcionesService.guardarInscripcion(alumno.getLegajo(), curso.getLegajo());
                                        admin.agregarAlumnoACurso(alumno, curso, esBecado);
                                        claseService.sumarRecaudacion(curso.getRecaudacionTotal(), curso.getLegajo());
                                        claseService.actualizarCupo(curso.getLegajo());
                                        limpiarCampos();
                                        JOptionPane.showMessageDialog(null, "Operacion realizada con exito", "Confirmacion", JOptionPane.PLAIN_MESSAGE);
                                    }else{
                                        JOptionPane.showMessageDialog(null, "El alumno no puede inscribirse al curso", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun admin", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun curso", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                             JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun alumno", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }catch(ServiceException serEx){
                    JOptionPane.showMessageDialog(null, "ERROR", "Error", JOptionPane.ERROR_MESSAGE);
                }catch (NumberFormatException nex){
                    JOptionPane.showMessageDialog(null, "El tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

        private void limpiarCampos(){
            jTextFieldIdAdmin.setText("");
            jTextFieldIdClase.setText("");
            jTextFieldIdAlumno.setText("");

        }
}

