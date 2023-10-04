package Gui;

import Controlador.Alumno;
import Controlador.Curso;
import Service.AlumnoService;
import Service.ClaseService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioVerClases extends JPanel{
    PanelManager panel;
    ClaseService claseService;
    JPanel formularioVerClases;

    JLabel jLabelClases;

    JScrollPane JscrollpanePanel;
    JTextArea JtextArea;

    JButton jButtonVerClases;
    JButton jButtonSalir;
    JPanel jPanelBotones;
    public FormularioVerClases(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        claseService = new ClaseService();
        formularioVerClases = new JPanel();
        formularioVerClases.setLayout(new BorderLayout());

        jLabelClases = new JLabel("Clases:");
        JtextArea = new JTextArea();
        JscrollpanePanel = new JScrollPane(JtextArea);
        JscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioVerClases.add(jLabelClases, BorderLayout.NORTH);
        formularioVerClases.add(JscrollpanePanel, BorderLayout.CENTER);

        jButtonVerClases = new JButton("Ver clases");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerClases);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioVerClases, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Curso> cursos = claseService.buscarTodos();
                    String sb = "";
                    for (Curso curso : cursos) {
                        sb+= (curso.toStringMostrar()) + ("\n");
                    }
                    JtextArea.setText(sb.toString());
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
