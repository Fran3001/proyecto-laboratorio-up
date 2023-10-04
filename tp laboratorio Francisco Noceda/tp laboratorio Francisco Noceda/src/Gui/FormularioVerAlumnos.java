package Gui;

import Controlador.Administrador;
import Controlador.Alumno;
import Controlador.Profesor;
import Service.AdminService;
import Service.AlumnoService;
import Service.ProfesorService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioVerAlumnos extends JPanel {
    PanelManager panel;
    AlumnoService alumnoService;
    JPanel formularioVerAlumnos;

    JLabel jLabelAlumnos;

    JScrollPane JscrollpanePanel;
    JTextArea JtextArea;

    JButton jButtonVerAlumnos;
    JButton jButtonSalir;
    JPanel jPanelBotones;


    public FormularioVerAlumnos(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        alumnoService = new AlumnoService();
        formularioVerAlumnos = new JPanel();
        formularioVerAlumnos.setLayout(new BorderLayout());

        jLabelAlumnos = new JLabel("Alumnos:");
        JtextArea = new JTextArea();
        JscrollpanePanel = new JScrollPane(JtextArea);
        JscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioVerAlumnos.add(jLabelAlumnos, BorderLayout.NORTH);
        formularioVerAlumnos.add(JscrollpanePanel, BorderLayout.CENTER);

        jButtonVerAlumnos = new JButton("Ver alumnos");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerAlumnos);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioVerAlumnos, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Alumno> alumnos = alumnoService.buscarTodos();
                    String sb = "";
                    for (Alumno alumno : alumnos) {
                        sb+= (alumno.toString()) + ("\n");
                    }
                    JtextArea.setText(sb.toString());
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al buscar alumnos", "Error", JOptionPane.ERROR_MESSAGE);
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
