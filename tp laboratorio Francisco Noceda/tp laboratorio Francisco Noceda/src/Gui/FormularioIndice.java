package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndice extends JPanel {
    PanelManager panel;
    JPanel formularioIndice;
    JButton jButtonUsuarios;
    JButton jButtonCursos;
    JButton jButtonAlumnos;
    JButton jButtonReportes;
    JButton jButtonInscripciones;

    public FormularioIndice(PanelManager panel) {
        this.panel = panel;
        armarFormularioIndice();
    }

    public void armarFormularioIndice() {
        formularioIndice = new JPanel();
        formularioIndice.setLayout(new GridLayout(2, 2));

        jButtonUsuarios = new JButton("Usuarios");
        jButtonCursos = new JButton("Cursos");
        jButtonAlumnos = new JButton("Alumnos");
        jButtonInscripciones = new JButton("Inscripciones");
        jButtonReportes = new JButton("Reportes");

        formularioIndice.add(jButtonUsuarios);
        formularioIndice.add(jButtonCursos);
        formularioIndice.add(jButtonAlumnos);
        formularioIndice.add(jButtonInscripciones);
        formularioIndice.add(jButtonReportes);

        setLayout(new BorderLayout());
        add(formularioIndice, BorderLayout.CENTER);

        jButtonUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndiceUsuarios formularioIndiceUsuarios = new FormularioIndiceUsuarios(panel);
                panel.mostrarFormulario(formularioIndiceUsuarios);
            }
        });

        jButtonCursos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndiceClases formularioIndiceClases = new FormularioIndiceClases(panel);
                panel.mostrarFormulario(formularioIndiceClases);
            }
        });

        jButtonAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndiceAlumnos formularioAlumno = new FormularioIndiceAlumnos(panel);
                panel.mostrarFormulario(formularioAlumno);
            }
        });

        jButtonReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndiceReportes formularioIndiceReportes = new FormularioIndiceReportes(panel);
                panel.mostrarFormulario(formularioIndiceReportes);
            }
        });

        jButtonInscripciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioIndiceInscripciones formularioIndiceInscripciones = new FormularioIndiceInscripciones(panel);
                panel.mostrarFormulario(formularioIndiceInscripciones);
            }
        });
    }
}

