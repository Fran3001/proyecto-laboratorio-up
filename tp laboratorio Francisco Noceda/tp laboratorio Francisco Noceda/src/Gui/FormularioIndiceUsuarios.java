package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndiceUsuarios extends JPanel {
    PanelManager panel;
    JPanel formularioIndiceUsuarios;

    JButton jButtonVerUsuarios;
    JButton jButtonAgregarUsuarios;
    JButton jButtonEliminarUsuarios;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioIndiceUsuarios(PanelManager panel){
        this.panel = panel;
        armarFormularioIndice();
    }

    public void armarFormularioIndice(){
        formularioIndiceUsuarios=new JPanel();
        formularioIndiceUsuarios.setLayout(new GridLayout(1,3));

        jButtonVerUsuarios=new JButton("Ver usuarios");
        jButtonAgregarUsuarios = new JButton("Crear Usuarios");
        jButtonEliminarUsuarios = new JButton("Eliminar Usuarios");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();

        jPanelBotones.add(jButtonVerUsuarios);
        jPanelBotones.add(jButtonAgregarUsuarios);
        jPanelBotones.add(jButtonEliminarUsuarios);
        jPanelBotones.add(jButtonSalir);
        formularioIndiceUsuarios.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioIndiceUsuarios, BorderLayout.CENTER);

        jButtonVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioVerUsuarios formularioVerUsuarios = new FormularioVerUsuarios(panel);
                panel.mostrarFormulario(formularioVerUsuarios);
            }
        });

        jButtonAgregarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioUsuarios formularioUsuarios = new FormularioUsuarios(panel);
                panel.mostrarFormulario(formularioUsuarios);
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndice formularioIndice = new FormularioIndice(panel);
                panel.mostrarFormulario(formularioIndice);
            }
        });

        jButtonEliminarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioEliminarUsuarios formularioEliminarUsuarios = new FormularioEliminarUsuarios(panel);
                panel.mostrarFormulario(formularioEliminarUsuarios);
            }
        });




    }
}
