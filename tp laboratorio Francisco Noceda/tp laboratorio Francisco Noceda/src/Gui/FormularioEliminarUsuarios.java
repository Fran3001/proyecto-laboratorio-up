package Gui;

import Service.AdminService;
import Service.ProfesorService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioEliminarUsuarios extends JPanel{
    PanelManager panel;
    AdminService adminService;
    ProfesorService profesorService;
    JPanel formularioEliminarUsuarios;
    JLabel jLabelId;
    JTextField jTextField;
    JButton jButtonEliminarAdmin;
    JButton jButtonEliminarProfe;
    JButton jButtonSalir;
    JPanel jPanelBotones;

    public FormularioEliminarUsuarios(PanelManager panel)
    {
        this.panel=panel;
        armarFormularioEliminar();
    }

   public void armarFormularioEliminar(){
        adminService = new AdminService();
        profesorService = new ProfesorService();

        formularioEliminarUsuarios=new JPanel();
        formularioEliminarUsuarios.setLayout(new GridLayout(2,3));

        jLabelId=new JLabel("Id:");
        jTextField= new JTextField(15);
        jButtonEliminarAdmin=new JButton("Eliminar admin");
        jButtonEliminarProfe = new JButton("Eliminar profe");
        jButtonSalir = new JButton("Salir");
        jPanelBotones=new JPanel();

       formularioEliminarUsuarios.add(jLabelId);
       formularioEliminarUsuarios.add(jTextField);

       setLayout(new BorderLayout());
       add(formularioEliminarUsuarios, BorderLayout.CENTER);

       jPanelBotones.add(jButtonEliminarAdmin);
       jPanelBotones.add(jButtonEliminarProfe);
       jPanelBotones.add(jButtonSalir);
       add(jPanelBotones,BorderLayout.SOUTH);

       jButtonEliminarAdmin.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try{
                   if(!jTextField.getText().isEmpty()){
                       adminService.eliminar(Integer.parseInt(jTextField.getText()));
                       jTextField.setText("");
                   }else{
                       JOptionPane.showMessageDialog(null, "El campo de texto no puede quedar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                   }
               }catch (ServiceException serEx){
                   JOptionPane.showMessageDialog(null, "No se pudo eliminar", "Error", JOptionPane.ERROR_MESSAGE);
               }catch (NumberFormatException nex){
                   JOptionPane.showMessageDialog(null, "El tipo de dato ingresado no concuerda", "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
       });

       jButtonEliminarProfe.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try{
                   if(!jTextField.getText().isEmpty()){
                       profesorService.eliminar(Integer.parseInt(jTextField.getText()));
                       jTextField.setText("");
                   }else{
                       JOptionPane.showMessageDialog(null, "El campo de texto no puede quedar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                   }
               }catch (ServiceException serEx){
                   JOptionPane.showMessageDialog(null, "No se pudo eliminar", "Error", JOptionPane.ERROR_MESSAGE);
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
