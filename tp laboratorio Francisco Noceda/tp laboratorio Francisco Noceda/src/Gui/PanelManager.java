package Gui;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private FormularioUsuarios formularioAdmin;
    private FormularioClase formularioClase;
    private FormularioAlumno formularioAlumno;
    private FormularioInscripcion formularioInscripcion;
    private FormularioIndice formularioIndice;

    JFrame ventana;

    public PanelManager() {
        ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        formularioIndice = new FormularioIndice(this);
        formularioAdmin = new FormularioUsuarios(this);
        formularioClase = new FormularioClase(this);
        formularioAlumno = new FormularioAlumno(this);
        formularioInscripcion = new FormularioInscripcion(this);

        // Agrega más formularios según sea necesario

        mostrarFormulario(formularioIndice);
    }

    public void mostrarFormulario(JPanel panel) {
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(panel, BorderLayout.WEST);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.setVisible(true);
        ventana.pack();
    }

}
