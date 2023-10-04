package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndiceClases extends JPanel{
    PanelManager panel;
    JPanel formularioIndiceClases;

    JButton jButtonVerClases;
    JButton jButtonCrearClases;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioIndiceClases(PanelManager panel){
        this.panel = panel;
        armarFormularioIndice();
    }

    public void armarFormularioIndice() {
        formularioIndiceClases = new JPanel();
        formularioIndiceClases.setLayout(new GridLayout(1, 2));

        jButtonVerClases = new JButton("Ver clases");
        jButtonCrearClases = new JButton("Crear clases");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();

        jPanelBotones.add(jButtonVerClases);
        jPanelBotones.add(jButtonCrearClases);
        jPanelBotones.add(jButtonSalir);
        formularioIndiceClases.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioIndiceClases, BorderLayout.CENTER);

        jButtonVerClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioVerClases formularioVerClases = new FormularioVerClases(panel);
                panel.mostrarFormulario(formularioVerClases);
            }
        });

        jButtonCrearClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioClase formularioClase = new FormularioClase(panel);
                panel.mostrarFormulario(formularioClase);
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
