package Gui;

import Controlador.Alumno;
import Controlador.Inscripcion;
import Service.AlumnoService;
import Service.ClaseService;
import Service.InscripcionesService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioVerNotas extends JPanel{
    PanelManager panel;
    InscripcionesService inscripcionesService;

    JPanel formularioVerNotas;

    JLabel jLabelId;
    JTextField jTextFieldId;
    JScrollPane JscrollpanePanel;
    JTextArea JtextArea;

    JButton jButtonVerNotas;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioVerNotas(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }
    public void armarFormulario() {
        inscripcionesService = new InscripcionesService();
        formularioVerNotas = new JPanel();
        formularioVerNotas.setLayout(new BorderLayout());

        jLabelId = new JLabel("Id alulmno:");
        jTextFieldId = new JTextField();
        JtextArea = new JTextArea();
        JscrollpanePanel = new JScrollPane(JtextArea);
        JscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioVerNotas.add(jLabelId, BorderLayout.NORTH);
        formularioVerNotas.add(jTextFieldId, BorderLayout.CENTER);
        formularioVerNotas.add(JscrollpanePanel, BorderLayout.SOUTH);

        jButtonVerNotas = new JButton("Ver notas");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerNotas);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioVerNotas, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jTextFieldId.getText().isEmpty()){
                    try {
                        String sb = "";
                        int id = Integer.parseInt(jTextFieldId.getText());
                        ArrayList<Inscripcion> inscripciones = inscripcionesService.buscarTodosPara(id);
                        sb = "Notas: " + ("\n");
                        for (Inscripcion inscripcion : inscripciones) {
                            sb+= (inscripcion.toStringNotas()) + ("\n");
                        }
                        JtextArea.setText(sb.toString());
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "Error al buscar notas", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException nex){
                        JOptionPane.showMessageDialog(null, "El tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "El campo de texto no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
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
