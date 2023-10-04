package Gui;

import Controlador.Inscripcion;
import Service.ClaseService;
import Service.InscripcionesService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioReportesCursos extends JPanel{
    PanelManager panel;
    ClaseService claseService;
    JPanel formularioReportesCursos;
    InscripcionesService inscripcionesService;

    JLabel jLabelIdCurso;
    JTextField jTextFieldId;
    JScrollPane jscrollpanePanel;
    JTextArea jtextArea;
    JButton jButtonVerCurso;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioReportesCursos(PanelManager panel){
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario() {
        claseService = new ClaseService();
        inscripcionesService = new InscripcionesService();

        formularioReportesCursos = new JPanel();
        formularioReportesCursos.setLayout(new BorderLayout());

        jLabelIdCurso = new JLabel("Id del curso:");
        jTextFieldId = new JTextField();
        jtextArea = new JTextArea();
        jscrollpanePanel = new JScrollPane(jtextArea);
        jscrollpanePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jscrollpanePanel.setPreferredSize(new Dimension(400, 100));  // establece las dimensiones de el formulario

        formularioReportesCursos.add(jLabelIdCurso, BorderLayout.NORTH);
        formularioReportesCursos.add(jTextFieldId, BorderLayout.CENTER);
        formularioReportesCursos.add(jscrollpanePanel, BorderLayout.SOUTH);

        jButtonVerCurso = new JButton("Ver reporte");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonVerCurso);
        jPanelBotones.add(jButtonSalir);

        setLayout(new BorderLayout());
        add(formularioReportesCursos, BorderLayout.CENTER);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonVerCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(jTextFieldId.getText());
                    if(claseService.buscarClase(id) != null){
                        ArrayList<Inscripcion> listaInscripciones = inscripcionesService.buscarTodosParaCurso(id);
                        int notaAprobar = claseService.buscarClase(id).getNotaParaMinimaAprobar();
                        StringBuilder sb = new StringBuilder();
                        sb.append("La nota necesaria para aprobar es " + notaAprobar + "\n");
                        sb.append("Alumnos:" + ("\n"));
                        for (Inscripcion st : listaInscripciones){
                            sb.append(st.getNombreAlumno() + (": "));
                            if(notaAprobar <= st.getNota()){
                                sb.append("Aprobado (nota: " + st.getNota() + ")\n");
                            }else{
                                sb.append("Desaprobado (nota: " + st.getNota() + ")\n");
                            }
                        }
                        jtextArea.setText(sb.toString());
                    }else{
                        JOptionPane.showMessageDialog(null, "El id no pertenece a ninguna clase", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al hacer el reporte", "Error", JOptionPane.ERROR_MESSAGE);
                }catch (NumberFormatException nex){
                    JOptionPane.showMessageDialog(null, "El tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
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
