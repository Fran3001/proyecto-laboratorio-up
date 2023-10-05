package Gui;

import Controlador.Curso;
import Service.AdminService;
import Service.ClaseService;
import Service.ProfesorService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioClase extends JPanel {
    PanelManager panel;
    ClaseService claseService;
    AdminService adminService;
    ProfesorService profeService;
    JPanel formularioClase;
    JLabel jLabelNombre;
    JLabel jLabelCupo;
    JLabel jLabelProfesorLegajo;
    JLabel jLabelNotaMin;
    JLabel jLabelPrecio;

    JTextField jTextFieldNombre;
    JTextField jTextFieldCupo;
    JTextField jTextFieldProfesorLegajo;
    JTextField jTextFieldNotaMin;
    JTextField jTextFieldPrecio;

    JButton jButtonGuardado;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioClase(PanelManager panel)
    {
        this.panel=panel;
        armarFormularioClase();
    }

    public void armarFormularioClase(){
        adminService = new AdminService();
        profeService = new ProfesorService();
        claseService = new ClaseService();

        formularioClase = new JPanel();
        formularioClase.setLayout(new GridLayout(6,6));

        jLabelNombre = new JLabel("Nombre del curso:");
        jLabelCupo = new JLabel("Cupo:");
        jLabelProfesorLegajo = new JLabel("Legajo del profesor:");
        jLabelNotaMin = new JLabel("Nota minima para aprobar:");
        jLabelPrecio = new JLabel("Precio:");

        jTextFieldNombre=new JTextField();
        jTextFieldCupo=new JTextField();
        jTextFieldProfesorLegajo = new JTextField();
        jTextFieldNotaMin = new JTextField();
        jTextFieldPrecio = new JTextField();

        jButtonGuardado = new JButton("Guardar curso");
        jButtonSalir = new JButton("Salir");

        formularioClase.add(jLabelNombre);
        formularioClase.add(jTextFieldNombre);
        formularioClase.add(jLabelCupo);
        formularioClase.add(jTextFieldCupo);
        formularioClase.add(jLabelProfesorLegajo);
        formularioClase.add(jTextFieldProfesorLegajo);
        formularioClase.add(jLabelNotaMin);
        formularioClase.add(jTextFieldNotaMin);
        formularioClase.add(jLabelPrecio);
        formularioClase.add(jTextFieldPrecio);

        setLayout(new BorderLayout());
        add(formularioClase, BorderLayout.CENTER);

        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonGuardado);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);


        jButtonGuardado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Curso curso = new Curso();
                if(!(jTextFieldNombre.getText().isEmpty() || jTextFieldCupo.getText().isEmpty() || jTextFieldPrecio.getText().isEmpty() || jTextFieldNotaMin.getText().isEmpty() || jTextFieldProfesorLegajo.getText().isEmpty())){
                    try{
                        if(profeService.buscar(Integer.parseInt(jTextFieldProfesorLegajo.getText())) != null){
                            if(Integer.parseInt(jTextFieldCupo.getText()) > -1) {
                                curso.setNombre(jTextFieldNombre.getText());
                                curso.setCupo(Integer.parseInt(jTextFieldCupo.getText()));
                                curso.setProfesor(profeService.buscar(Integer.parseInt(jTextFieldProfesorLegajo.getText()))); //busca el legajo en la base de datos para devolver el profesor
                                curso.setNotaParaMinimaAprobar(Integer.parseInt(jTextFieldNotaMin.getText()));
                                curso.setPrecio(Integer.parseInt(jTextFieldPrecio.getText()));
                                claseService.guardarClase(curso);
                                limpiarTodo();
                                JOptionPane.showMessageDialog(null, "Operacion realizada con exito", "Confirmacion", JOptionPane.PLAIN_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null, "el cupo debe ser igual o mayor a 0", "Error", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "ese legajo no corresponde a ningun profesor", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch (ServiceException serEx){
                        JOptionPane.showMessageDialog(null, "error", "Error", JOptionPane.ERROR_MESSAGE);
                    }catch (NumberFormatException nex){
                        JOptionPane.showMessageDialog(null, "Un tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "ningun campo de texto puede quedar vacio", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void limpiarTodo(){
        jTextFieldCupo.setText("");
        jTextFieldNombre.setText("");
        jTextFieldNotaMin.setText("");
        jTextFieldPrecio.setText("");
        jTextFieldProfesorLegajo.setText("");
    }
}
