package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndiceInscripciones extends JPanel{
    PanelManager panel;
    JPanel formularioIndiceInscripciones;
    JButton jButtonInscribirAlumno;
    JButton jButtonVerInscripciones;
    JButton jButtonEliminarAprobados;
    JButton jButtonAniadirNota;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioIndiceInscripciones(PanelManager panel){
        this.panel = panel;
        armarFormularioIndice();
    }
    public void armarFormularioIndice() {
        formularioIndiceInscripciones = new JPanel();
        formularioIndiceInscripciones.setLayout(new GridLayout(1, 2));

        jButtonVerInscripciones = new JButton("Ver inscripcion");
        jButtonInscribirAlumno = new JButton("Crear inscripcion");
        jButtonEliminarAprobados = new JButton("Eliminar inscripciones en cursos aprobados");
        jButtonAniadirNota = new JButton("Añadir nota final");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();

        jPanelBotones.add(jButtonVerInscripciones);
        jPanelBotones.add(jButtonInscribirAlumno);
        jPanelBotones.add(jButtonEliminarAprobados);
        jPanelBotones.add(jButtonAniadirNota);
        jPanelBotones.add(jButtonSalir);
        formularioIndiceInscripciones.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioIndiceInscripciones, BorderLayout.CENTER);

        jButtonInscribirAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioInscripcion formularioInscripcion = new FormularioInscripcion(panel);
                panel.mostrarFormulario(formularioInscripcion);
            }
        });

        jButtonVerInscripciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioVerInscripciones formularioVerInscripciones = new FormularioVerInscripciones(panel);
                panel.mostrarFormulario(formularioVerInscripciones);
            }
        });

        jButtonEliminarAprobados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioEliminarCursosAprobados formularioEliminarCursosAprobados = new FormularioEliminarCursosAprobados(panel);
                panel.mostrarFormulario(formularioEliminarCursosAprobados);
            }
        });

        jButtonAniadirNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioAniadirNota formularioAniadirNota = new FormularioAniadirNota(panel);
                panel.mostrarFormulario(formularioAniadirNota);
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
