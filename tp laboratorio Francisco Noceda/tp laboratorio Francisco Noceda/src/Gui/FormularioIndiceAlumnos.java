package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndiceAlumnos extends JPanel{
    PanelManager panel;
    JPanel formularioIndiceAsuarios;

    JButton jButtonVerAlumnos;
    JButton jButtonCrearAlumnos;
    JButton jButtonVerNota;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioIndiceAlumnos(PanelManager panel){
        this.panel = panel;
        armarFormularioIndice();
    }

    public void armarFormularioIndice(){
        formularioIndiceAsuarios=new JPanel();
        formularioIndiceAsuarios.setLayout(new GridLayout(1,2));

        jButtonVerAlumnos=new JButton("Ver alumnos");
        jButtonCrearAlumnos = new JButton("Crear alumnos");
        jButtonVerNota = new JButton("Ver notas");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();

        jPanelBotones.add(jButtonVerAlumnos);
        jPanelBotones.add(jButtonCrearAlumnos);
        jPanelBotones.add(jButtonVerNota);
        jPanelBotones.add(jButtonSalir);
        formularioIndiceAsuarios.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioIndiceAsuarios, BorderLayout.CENTER);

        jButtonVerAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioVerAlumnos formularioVerAlumnos = new FormularioVerAlumnos(panel);
                panel.mostrarFormulario(formularioVerAlumnos);
            }
        });

        jButtonCrearAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioAlumno formularioAlumno = new FormularioAlumno(panel);
                panel.mostrarFormulario(formularioAlumno);
            }
        });
        jButtonVerNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioVerNotas formularioVerNotas = new FormularioVerNotas(panel);
                panel.mostrarFormulario(formularioVerNotas);
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
