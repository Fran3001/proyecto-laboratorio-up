package Gui;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Profesor;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioAlumno extends JPanel{
    PanelManager panel;
    AlumnoService alumnoService;
    AdminService adminService;
    ClaseService claseService;
    JPanel formularioAlumno;

    JLabel jLabelNombre;
    JLabel jLabelApellido;
    JLabel jLabelLegajoAdmin;
    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JTextField jTextFieldLegajoAdmin;
    JButton jButtonGuardarAlumno;
    JButton jButtonSalir;
    JPanel jPanelBotones;


    public FormularioAlumno(PanelManager panel)
    {
        this.panel=panel;
        armarFormularioAlumno();
    }
    public void armarFormularioAlumno()
    {
        adminService = new AdminService();
        alumnoService = new AlumnoService();
        claseService = new ClaseService();

        formularioAlumno=new JPanel();
        formularioAlumno.setLayout(new GridLayout(3,3));

        jLabelNombre=new JLabel("Nombre:");
        jLabelApellido=new JLabel("Apellido:");
        jLabelLegajoAdmin = new JLabel("Legajo del admin a cargo");
        jTextFieldNombre= new JTextField(15);
        jTextFieldApellido= new JTextField(10);
        jTextFieldLegajoAdmin = new JTextField(10);
        jButtonGuardarAlumno=new JButton("Guardar alumno");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();

        formularioAlumno.add(jLabelNombre);
        formularioAlumno.add(jTextFieldNombre);

        formularioAlumno.add(jLabelApellido);
        formularioAlumno.add(jTextFieldApellido);

        formularioAlumno.add(jLabelLegajoAdmin);
        formularioAlumno.add(jTextFieldLegajoAdmin);

        setLayout(new BorderLayout());
        add(formularioAlumno, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardarAlumno);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones,BorderLayout.SOUTH);

        jButtonGuardarAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(jTextFieldNombre.getText().isEmpty() || jTextFieldApellido.getText().isEmpty() || jTextFieldLegajoAdmin.getText().isEmpty())){
                    try {
                        Alumno alumno = new Alumno();
                        alumno.setNombre(jTextFieldNombre.getText());
                        alumno.setApellido(jTextFieldApellido.getText());
                        int legajo = Integer.parseInt(jTextFieldLegajoAdmin.getText());
                        jTextFieldNombre.setText("");
                        jTextFieldApellido.setText("");
                        jTextFieldLegajoAdmin.setText("");
                        if(adminService.buscar(legajo) != null){
                            alumnoService.guardarAlumno(alumno);
                            JOptionPane.showMessageDialog(null, "Operacion realizada con exito", "Confirmacion", JOptionPane.PLAIN_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(null, "El legajo no corresponde a ningun admin", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch (ServiceException serExt){
                        JOptionPane.showMessageDialog(null, "error", "Error", JOptionPane.ERROR_MESSAGE);
                    }catch (NumberFormatException nex){
                        JOptionPane.showMessageDialog(null, "El tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "ningun campo puede quedar vacio", "Error", JOptionPane.ERROR_MESSAGE);
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
