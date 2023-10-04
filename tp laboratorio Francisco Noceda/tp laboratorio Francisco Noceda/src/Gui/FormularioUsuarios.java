package Gui;

import Controlador.Administrador;
import Controlador.Profesor;
import Service.AdminService;
import Service.ProfesorService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioUsuarios extends JPanel{
    PanelManager panel;
    AdminService adminService;
    ProfesorService profesorService;
    JPanel formularioUsuario;

    JLabel jLabelNombre;
    JLabel jLabelApellido;

    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JButton jButtonGuardarAdmin;
    JButton jButtonGuardarProfe;
    JButton jButtonSalir;
    JPanel jPanelBotones;


    public FormularioUsuarios(PanelManager panel)
    {
        this.panel=panel;
        armarFormularioAdmin();
    }
    public  void armarFormularioAdmin()
    {
        adminService = new AdminService();
        profesorService = new ProfesorService();

        formularioUsuario=new JPanel();
        formularioUsuario.setLayout(new GridLayout(2,3));

        jLabelNombre=new JLabel("Nombre:");
        jLabelApellido=new JLabel("Apellido:");
        jTextFieldNombre= new JTextField(15);
        jTextFieldApellido= new JTextField(10);
        jButtonGuardarAdmin=new JButton("Guardar admin");
        jButtonGuardarProfe = new JButton("Guardar profe");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();

        formularioUsuario.add(jLabelNombre);
        formularioUsuario.add(jTextFieldNombre);

        formularioUsuario.add(jLabelApellido);
        formularioUsuario.add(jTextFieldApellido);

        setLayout(new BorderLayout());
        add(formularioUsuario, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardarAdmin);
        jPanelBotones.add(jButtonGuardarProfe);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones,BorderLayout.SOUTH);

        jButtonGuardarAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administrador admin = new Administrador();
                admin.setNombre(jTextFieldNombre.getText());
                admin.setApellido(jTextFieldApellido.getText());
                if(!jTextFieldApellido.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty()){
                    try{
                        adminService.guardarAdmin(admin);
                        jTextFieldNombre.setText("");
                        jTextFieldApellido.setText("");
                    }catch (ServiceException serEx){
                        JOptionPane.showMessageDialog(null, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se pueden dejar campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jButtonGuardarProfe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profesor profesor = new Profesor();
                profesor.setNombre(jTextFieldNombre.getText());
                profesor.setApellido(jTextFieldApellido.getText());
                if(!jTextFieldApellido.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty()){
                    try{
                        profesorService.guardarProfe(profesor);
                        jTextFieldNombre.setText("");
                        jTextFieldApellido.setText("");
                    }catch (ServiceException serEx){
                        JOptionPane.showMessageDialog(null, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se pueden dejar campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
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
