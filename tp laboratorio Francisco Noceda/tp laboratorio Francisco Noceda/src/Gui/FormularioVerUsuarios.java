package Gui;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Profesor;
import Service.AdminService;
import Service.ProfesorService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioVerUsuarios extends JPanel{
    PanelManager panel;
    AdminService adminService;
    ProfesorService profesorService;
    JPanel formularioVerUsuarios;

    JLabel jLabelUsuarios;

    JScrollPane JscrollpanePanel;
    JTextArea JtextArea;

    JButton jButtonVerAdmins;
    JButton jButtonVerProfes;
    JButton jButtonSalir;
    JPanel jPanelBotones;


    public FormularioVerUsuarios(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        adminService = new AdminService();
        profesorService = new ProfesorService();
        formularioVerUsuarios = new JPanel();
        formularioVerUsuarios.setLayout(new BorderLayout());

        jLabelUsuarios = new JLabel("");
        JtextArea = new JTextArea();
        JscrollpanePanel = new JScrollPane(JtextArea);
        JscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioVerUsuarios.add(jLabelUsuarios, BorderLayout.NORTH);
        formularioVerUsuarios.add(JscrollpanePanel, BorderLayout.CENTER);

        jButtonVerAdmins = new JButton("Ver admins");
        jButtonVerProfes = new JButton("Ver profes");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerAdmins);
        jPanelBotones.add(jButtonVerProfes);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioVerUsuarios, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerAdmins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Administrador> administradores = adminService.buscarTodos();
                    String sb = "";
                    jLabelUsuarios.setText("Admins:");
                    for (Administrador admin : administradores) {
                        sb+= (admin.toString()) + ("\n");
                    }
                    JtextArea.setText(sb.toString());
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al buscar administradores", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jButtonVerProfes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Profesor> profesores = profesorService.buscarTodos();
                    String sb = "";
                    jLabelUsuarios.setText("Profes:");
                    for (Profesor profe : profesores) {
                        sb+= (profe.toString()) + ("\n");
                    }
                    JtextArea.setText(sb.toString());
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al buscar administradores", "Error", JOptionPane.ERROR_MESSAGE);
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