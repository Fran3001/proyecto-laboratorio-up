package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioIndiceReportes extends JPanel{
    PanelManager panel;
    JPanel formularioIndiceReportes;

    JButton jButtonVerReporteAnotados;
    JButton jButtonVerReporteCursos;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioIndiceReportes(PanelManager panel){
        this.panel = panel;
        armarFormularioIndice();
    }

    public void armarFormularioIndice() {
        formularioIndiceReportes = new JPanel();
        formularioIndiceReportes.setLayout(new GridLayout(1, 3));

        jButtonVerReporteAnotados = new JButton("Ver reporte anotados");
        jButtonVerReporteCursos = new JButton("Ver reporte recaudacion");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();

        jPanelBotones.add(jButtonVerReporteAnotados);
        jPanelBotones.add(jButtonVerReporteCursos);
        jPanelBotones.add(jButtonSalir);
        formularioIndiceReportes.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioIndiceReportes, BorderLayout.CENTER);

        jButtonVerReporteAnotados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioReportesCursos formularioReportesCursos = new FormularioReportesCursos(panel);
                panel.mostrarFormulario(formularioReportesCursos);
            }
        });

        jButtonVerReporteCursos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioReporteTotal formularioReporteTotal = new FormularioReporteTotal(panel);
                panel.mostrarFormulario(formularioReporteTotal);
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

