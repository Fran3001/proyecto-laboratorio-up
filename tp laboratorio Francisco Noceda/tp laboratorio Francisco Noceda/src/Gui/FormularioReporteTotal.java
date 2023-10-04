package Gui;

import Controlador.Curso;
import Service.ClaseService;
import Service.InscripcionesService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioReporteTotal extends JPanel{
    PanelManager panel;
    ClaseService claseService;
    JPanel formularioReporteTotal;

    JLabel jLabelCursos;
    JScrollPane jscrollpanePanel;
    JTextArea jtextArea;
    JButton jButtonVerReporte;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioReporteTotal(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        claseService = new ClaseService();
        formularioReporteTotal = new JPanel();
        formularioReporteTotal.setLayout(new BorderLayout());

        jLabelCursos = new JLabel("Cursos:");
        jtextArea = new JTextArea();
        jscrollpanePanel = new JScrollPane(jtextArea);
        jscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioReporteTotal.add(jLabelCursos, BorderLayout.NORTH);
        formularioReporteTotal.add(jscrollpanePanel, BorderLayout.SOUTH);

        jButtonVerReporte = new JButton("Ver reporte");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerReporte);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioReporteTotal, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Curso> cursos  = claseService.buscarTodos();
                    String sb = "Cursos:" + ("\n");
                    for (Curso curso : cursos){
                        sb += curso.toStringReporteTotal() + ("\n");
                    }
                    jtextArea.setText(sb);

                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al hacer el reporte", "Error", JOptionPane.ERROR_MESSAGE);
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
