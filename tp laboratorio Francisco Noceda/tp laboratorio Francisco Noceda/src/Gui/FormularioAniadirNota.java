package Gui;

import Controlador.Curso;
import Controlador.Profesor;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioAniadirNota extends JPanel{
    PanelManager panel;
    JPanel formularioAniadirNota;
    InscripcionesService inscripcionesService;
    ClaseService claseService;
    JLabel jLabelIdAlumno;
    JLabel jLabelIdCurso;
    JLabel jLabelIdProfe;
    JLabel jLabelNota;


    JTextField jTextFieldIdAlumno;
    JTextField jTextFieldIdCurso;
    JTextField jTextFieldIdProfe;
    JTextField jTextFieldNota;

    JButton jButtonAniadirNota;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioAniadirNota(PanelManager panel)
    {
        this.panel = panel;armarFormularioIndice();
    }

    public void armarFormularioIndice() {
        inscripcionesService = new InscripcionesService();
        claseService = new ClaseService();
        formularioAniadirNota = new JPanel();
        formularioAniadirNota.setLayout(new GridLayout(5, 2));

        jLabelIdAlumno = new JLabel("Id del alumno");
        jLabelIdCurso = new JLabel("Id del curso");
        jLabelIdProfe = new JLabel("Id del profesor a cargo");
        jLabelNota = new JLabel("Nota");

        jTextFieldIdAlumno = new JTextField();
        jTextFieldIdCurso = new JTextField();
        jTextFieldIdProfe = new JTextField();
        jTextFieldNota = new JTextField();

        formularioAniadirNota.add(jLabelIdAlumno);
        formularioAniadirNota.add(jTextFieldIdAlumno);
        formularioAniadirNota.add(jLabelIdCurso);
        formularioAniadirNota.add(jTextFieldIdCurso);
        formularioAniadirNota.add(jLabelIdProfe);
        formularioAniadirNota.add(jTextFieldIdProfe);
        formularioAniadirNota.add(jLabelNota);
        formularioAniadirNota.add(jTextFieldNota);


        jButtonAniadirNota = new JButton("Añadir notas");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();

        jPanelBotones.add(jButtonAniadirNota);
        jPanelBotones.add(jButtonSalir);
        formularioAniadirNota.add(jPanelBotones);

        setLayout(new BorderLayout());
        add(formularioAniadirNota, BorderLayout.CENTER);

        jButtonAniadirNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int legajoAlumno = Integer.parseInt(jTextFieldIdAlumno.getText());
                    int legajoCurso = Integer.parseInt(jTextFieldIdCurso.getText());
                    int legajoProfe = Integer.parseInt(jTextFieldIdProfe.getText());
                    int nota = Integer.parseInt(jTextFieldNota.getText());
                    if(inscripcionesService.existeInscripcion(legajoAlumno, legajoCurso)){
                        Curso curso = claseService.buscarClase(legajoCurso);
                        if(curso.getProfesor().getLegajo() == legajoProfe){
                            //
                            int legajoInscripcion = inscripcionesService.getIdInscripcion(legajoAlumno, legajoCurso);
                            inscripcionesService.agregarNota(nota, legajoInscripcion);
                            JOptionPane.showMessageDialog(null, "Operacion realizada con exito", "Confirmacion", JOptionPane.PLAIN_MESSAGE);
                            //
                        }else{
                            JOptionPane.showMessageDialog(null, "El profesor no corresponde a ese curso", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El alumno no está registrado en el curso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ServiceException SerEx) {
                    JOptionPane.showMessageDialog(null, "error", "Error", JOptionPane.ERROR_MESSAGE);
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
