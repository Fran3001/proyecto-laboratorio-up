package Gui;

import Controlador.Inscripcion;
import Controlador.Profesor;
import Service.InscripcionesService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioVerInscripciones extends JPanel {

    PanelManager panel;
    InscripcionesService inscripcionesService;
    JPanel formularioVerInscripciones;

    JLabel jLabelInscripciones;

    JScrollPane JscrollpanePanel;
    JTextArea JtextArea;

    JButton jButtonVerInscripciones;
    JButton jButtonSalir;
    JPanel jPanelBotones;
    public FormularioVerInscripciones(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        inscripcionesService = new InscripcionesService();
        formularioVerInscripciones = new JPanel();
        formularioVerInscripciones.setLayout(new BorderLayout());

        jLabelInscripciones = new JLabel("Inscripciones:");
        JtextArea = new JTextArea();
        JscrollpanePanel = new JScrollPane(JtextArea);
        JscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioVerInscripciones.add(jLabelInscripciones, BorderLayout.NORTH);
        formularioVerInscripciones.add(JscrollpanePanel, BorderLayout.CENTER);

        jButtonVerInscripciones = new JButton("Ver inscripciones");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerInscripciones);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioVerInscripciones, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerInscripciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //
                    ArrayList<Inscripcion> inscripciones = inscripcionesService.buscarTodos();
                    String sb = "";
                    for (Inscripcion inscripcion : inscripciones) {
                        sb+= (inscripcion.toString()) + ("\n");
                    }
                    JtextArea.setText(sb.toString());
                    //

                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al buscar cursos", "Error", JOptionPane.ERROR_MESSAGE);
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
