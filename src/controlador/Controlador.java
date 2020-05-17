/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.Club;
import modelo.Futbolista;
import modelo.Futbolista_Club;
import vista.Vista;

/**
 *
 * @author Rafael
 */
public class Controlador implements ActionListener, MouseListener, ItemListener {
    
    
    /* instancia a nuestra interfaz de usuario*/
    Vista vista ;
    /** instancia a nuestros modelos */
    Futbolista modelo_Futbolista = new Futbolista();
    Club modelo_Club = new Club();
    Futbolista_Club Futbolista_Club =new Futbolista_Club();

   
     public enum AccionMVC
    {
     //futbolista
    crearFutbolista,
    modificarFutbolista,
    eliminarFutbolista,
    clubFutbolista,
    borrarFutbolista,
   
    
    //clubes
    crearClub,
    modificarClub,
    eliminarClub,
    futbolistaClub,
    borrarClub,
    
    //Relacion
    botonRelacion,
    borrarRelacion,
    eliminarRelacion
     }
     
     public Controlador( Vista vista )
    {
        this.vista = vista;
    }
    

    /** Inicia el skin y las diferentes variables que se utilizan */
    public void iniciar()
    {
        
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
            vista.jtextIdClub.setVisible(false);
            vista.jtextIdFutbolista.setVisible(false);
            vista.jtextIDFR.setVisible(false);
            vista.jtextIDCR.setVisible(false);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}
        //declaracion de botones
        this.vista.crearFutbolista.setActionCommand( "crearFutbolista" );
        this.vista.crearFutbolista.addActionListener(this);
        this.vista.modificarFutbolista.setActionCommand( "modificarFutbolista" );
        this.vista.modificarFutbolista.addActionListener(this);
        this.vista.crearClub.setActionCommand( "crearClub" );
        this.vista.crearClub.addActionListener(this);
         this.vista.modificarClub.setActionCommand( "modificarClub" );
        this.vista.modificarClub.addActionListener(this);
        this.vista.clubFutbolista.setActionCommand( "clubFutbolista" );
        this.vista.clubFutbolista.addActionListener(this);
        this.vista.futbolistaClub.setActionCommand( "futbolistaClub" );
        this.vista.futbolistaClub.addActionListener(this);
        this.vista.eliminarFutbolista.setActionCommand( "eliminarFutbolista" );
        this.vista.eliminarFutbolista.addActionListener(this);
        this.vista.eliminarClub.setActionCommand( "eliminarClub" );
        this.vista.eliminarClub.addActionListener(this);
        this.vista.botonRelacion.setActionCommand( "botonRelacion" );
        this.vista.botonRelacion.addActionListener(this);
        this.vista.borrarFutbolista.setActionCommand( "borrarFutbolista" );
        this.vista.borrarFutbolista.addActionListener(this);
        this.vista.borrarClub.setActionCommand( "borrarClub" );
        this.vista.borrarClub.addActionListener(this);
        this.vista.borrarRelacion.setActionCommand( "borrarRelacion" );
        this.vista.borrarRelacion.addActionListener(this);
        this.vista.eliminarRelacion.setActionCommand( "eliminarRelacion" );
        this.vista.eliminarRelacion.addActionListener(this);
         //declaracion de tablas
        
        this.vista.tablaFutbolistas.addMouseListener(this);
        this.vista.tablaFutbolistas.setModel(modelo_Futbolista.getTablaFutbolistas());
        this.vista.tablaClubes.addMouseListener(this);
        this.vista.tablaClubes.setModel(modelo_Club.getTablaClub());
        this.vista.tablaFutbolistaClub.addMouseListener(this);
        this.vista.tablaFutbolistaClub.setModel(Futbolista_Club.getTablaFutbolistaClub(this.vista.jtextNIF.getText()));
        this.vista.tablaClubFutbolista.addMouseListener(this);
        this.vista.tablaClubFutbolista.setModel(Futbolista_Club.getTablaClubFutbolista(this.vista.jtextClub.getText()));
        this.vista.tablaClubR.addMouseListener(this);
        this.vista.tablaClubR.setModel(modelo_Club.getTablaClubPartic());
        this.vista.tablaFutbolistaR.addMouseListener(this);
        this.vista.tablaFutbolistaR.setModel(modelo_Futbolista.getTablaFutbolistaPartic());
        this.vista.tablaRelacion.addMouseListener(this);
        this.vista.tablaRelacion.setModel(Futbolista_Club.getTablaRelacion(this.vista.jtextIDFR.getText()));
        
       
       
    
    }
    public void actionPerformed(ActionEvent e) {
        switch ( AccionMVC.valueOf( e.getActionCommand() ) )
        {
       case crearFutbolista:
           
                //añade un nuevo Futbolista
                if ( this.modelo_Futbolista.RegistrarFutbolista(
                        this.vista.jtextIdFutbolista.getText(),
                        this.vista.jtextNIF.getText() ,
                        this.vista.jtextNombre.getText(),
                        this.vista.jtextApellido.getText(),
                        this.vista.jtextNacimiento.getText(),
                        this.vista.jtextNacionalidad.getText()))
                       
                        {
                    //ocurrio un error
                    this.vista.tablaFutbolistas.setModel( this.modelo_Futbolista.getTablaFutbolistaPartic());
                    this.vista.tablaFutbolistas.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    this.vista.tablaFutbolistaR.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    JOptionPane.showMessageDialog(vista,"Exito: Nuevo Futbolista agregado.");
                    
                    this.vista.jtextIdFutbolista.setText("");
                    this.vista.jtextNIF.setText("");
                    this.vista.jtextNombre.setText("");
                    this.vista.jtextApellido.setText("");
                    this.vista.jtextNacimiento.setText("") ;
                    this.vista.jtextNacionalidad.setText("");
                  
        }else JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                break;
    
        //modificar Futbolista
        
            case modificarFutbolista:
        
         
        {
            try {
                if ( this.modelo_Futbolista.ModificarFutbolista(
                        this.vista.jtextIdFutbolista.getText(),
                        this.vista.jtextNIF.getText() ,
                        this.vista.jtextNombre.getText() ,
                        this.vista.jtextApellido.getText(),
                        this.vista.jtextNacimiento.getText() ,
                        this.vista.jtextNacionalidad.getText()))
                {
                    //ocurrio un error
                    
                    this.vista.tablaFutbolistas.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    this.vista.tablaFutbolistaR.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    JOptionPane.showMessageDialog(vista,"Exito: Se ha modificado el Futbolista con exito.");
                    this.vista.jtextIdFutbolista.setText("");
                    this.vista.jtextNIF.setText("");
                    this.vista.jtextNombre.setText("");
                    this.vista.jtextApellido.setText("");
                    this.vista.jtextNacimiento.setText("") ;
                    this.vista.jtextNacionalidad.setText("");
                  
                }else JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
         break;
         
         
 

        //controlador crear Club
          
       case crearClub:
                //Añade una nueva Club
                if ( this.modelo_Club.RegistrarClub(
                        
                        this.vista.jtextIdClub.getText(),
                        this.vista.jtextClub.getText(),
                        this.vista.jtextEstadio.getText(),
                        this.vista.jtextCreacion.getText()
                        ) ) 
               
                        {
                    //ocurrio un error
                    this.vista.tablaClubes.setModel( this.modelo_Club.getTablaClubPartic());
                    this.vista.tablaClubes.setModel( this.modelo_Club.getTablaClub() );
                    this.vista.tablaClubR.setModel( this.modelo_Club.getTablaClub() );
                    JOptionPane.showMessageDialog(vista,"Exito: Nueva Club creado.");   
                  
                    this.vista.jtextIdClub.setText("");
                    this.vista.jtextClub.setText("");
                    this.vista.jtextEstadio.setText("") ;
                    this.vista.jtextCreacion.setText("");
                    
        }else JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                break;
                
                //modificar Club
            case modificarClub:
        
        {
            try {
                if ( this.modelo_Club.ModificarClub(
                        this.vista.jtextIdClub.getText(),
                        this.vista.jtextClub.getText() ,
                        this.vista.jtextEstadio.getText() ,
                        this.vista.jtextCreacion.getText() 
                        ))
                {
                    //ocurrio un error
                    
                    this.vista.tablaClubes.setModel( this.modelo_Club.getTablaClub() );
                    this.vista.tablaClubR.setModel( this.modelo_Club.getTablaClub() );
                    JOptionPane.showMessageDialog(vista,"Exito: Se ha modificado el Club con exito.");
                   
                    this.vista.jtextIdClub.setText("");
                    this.vista.jtextClub.setText("");
                    this.vista.jtextEstadio.setText("");
                    this.vista.jtextCreacion.setText("") ;
                    
                  
                }else JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
         break;
         
         
          
        
        case eliminarFutbolista:
            //Metodo para eliminar un futbolista
                if ( this.modelo_Futbolista.EliminarFutbolista(
                        this.vista.jtextIdFutbolista.getText()
                       ) )
                {
                    this.vista.tablaFutbolistas.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    this.vista.tablaFutbolistaR.setModel( this.modelo_Futbolista.getTablaFutbolistas() );
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    
                    this.vista.jtextIdFutbolista.setText("");
                    this.vista.jtextNIF.setText("");  
                    this.vista.jtextNombre.setText("");
                    this.vista.jtextApellido.setText("");
                    this.vista.jtextNacimiento.setText("") ;
                    this.vista.jtextNacionalidad.setText("");


         }
        
                
                case eliminarClub:
            //Metodo para eliminar un Club
                if ( this.modelo_Club.EliminarClub(
                        this.vista.jtextIdClub.getText() ) )
                {
                    this.vista.tablaClubes.setModel( this.modelo_Club.getTablaClub() );
                    this.vista.tablaClubR.setModel( this.modelo_Club.getTablaClub() );
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    this.vista.jtextIdClub.setText("");
                    this.vista.jtextClub.setText("");  
                    this.vista.jtextEstadio.setText("");
                    this.vista.jtextCreacion.setText("") ;


         }
                
              
                
                case botonRelacion:
                //Añade una nueva Relacion
                if ( this.Futbolista_Club.AsociarFutbolistaClub(
                        
                        this.vista.jtextIDFR.getText(),
                        this.vista.jtextIDCR.getText(),
                        this.vista.jtextTemporada.getText()
                        ) ) 
               
                        {
                    //ocurrio un error
                     this.vista.tablaRelacion.setModel( this.Futbolista_Club.getTablaRelacion(this.vista.jtextIDFR.getText()) );
                    JOptionPane.showMessageDialog(vista,"Exito: Nueva Asociacion creada.");   
                  
                    this.vista.jtextIDFR.setText("");
                    this.vista.jtextIDCR.setText("");
                    this.vista.jtextFutbolistaR.setText("");
                    this.vista.jtextClubR.setText("");
                    this.vista.jtextTemporada.setText("") ;
                    
                    
        }else JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                break;
                
        case borrarFutbolista:
            //Metodo para borrar datos jtextfield
                
                    
                    this.vista.jtextIdFutbolista.setText("");
                    this.vista.jtextNIF.setText("");  
                    this.vista.jtextNombre.setText("");
                    this.vista.jtextApellido.setText("");
                    this.vista.jtextNacimiento.setText("") ;
                    this.vista.jtextNacionalidad.setText("");
            
            
        case borrarClub:
            //Metodo para borrar datos jtextfield de un Club
                
                    
                    this.vista.jtextIdClub.setText("");
                    this.vista.jtextClub.setText("");  
                    this.vista.jtextEstadio.setText("");
                    this.vista.jtextCreacion.setText("") ;


         case borrarRelacion:
            //Metodo para borrar datos jtextfield de una Relacion
                
                    
                    this.vista.jtextIDFR.setText("");
                    this.vista.jtextIDCR.setText(""); 
                    this.vista.jtextFutbolistaR.setText("");
                    this.vista.jtextClubR.setText("");
                    this.vista.jtextTemporada.setText("");
             
             
        
         case clubFutbolista:
            //Metodo para eliminar un futbolista
                //metodo para listar los actores de una pelicula
                if (this.vista.jtextIdFutbolista.getText().isEmpty()&& this.vista.jtextIdClub.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay datos para listar, seleccione un registro de la tabla.");
                    } else {

                        this.Futbolista_Club.getTablaFutbolistaClub(
                          
                        this.vista.jtextIdFutbolista.getText());
                        this.vista.tablaFutbolistaClub.setModel(this.Futbolista_Club.getTablaFutbolistaClub(this.vista.jtextIdFutbolista.getText()));
                        JOptionPane.showMessageDialog(vista, "Exito: Lista de Clubes en los que ha jugado.");

                    }
            break;
             
        case futbolistaClub:
            //Metodo para eliminar un futbolista
                //metodo para listar los actores de una pelicula
                if (this.vista.jtextIdClub.getText().isEmpty()&& this.vista.jtextIdFutbolista.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay datos para listar, seleccione un registro de la tabla.");
                    } else {

                        this.Futbolista_Club.getTablaClubFutbolista(
                          
                        this.vista.jtextIdClub.getText());
                        this.vista.tablaClubFutbolista.setModel(this.Futbolista_Club.getTablaClubFutbolista(this.vista.jtextIdClub.getText()));
                        JOptionPane.showMessageDialog(vista, "Exito: Lista de los jugadores que han jugado en el club.");

                    }
            break;
            
          case eliminarRelacion:
            //Metodo para eliminar una asociacion
                if ( this.Futbolista_Club.EliminarRelacion(
                        this.vista.jtextIDFR.getText(),
                        this.vista.jtextIDCR.getText(),
                        this.vista.jtextTemporada.getText()) )
                {
                    this.vista.tablaRelacion.setModel( this.Futbolista_Club.getTablaRelacion( this.vista.jtextIDFR.getText()) );
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    this.vista.jtextIDFR.setText("");
                    this.vista.jtextIDCR.setText("") ;
                    this.vista.jtextTemporada.setText("") ;
                   
                    
                }
                break;    
                    


         
        
        


             
                
                
                
                
    }
    }
       //insertar datos en los campos de escritura
    public void TablaFutbolistas (MouseEvent e) {
         int fila = this.vista.tablaFutbolistas.rowAtPoint(e.getPoint());
        DefaultTableModel escogerFut = modelo_Futbolista.getTablaFutbolistas();

        if (fila > -1) {
            
            this.vista.jtextIdFutbolista.setText(String.valueOf(escogerFut.getValueAt(fila, 0)));
            this.vista.jtextNIF.setText(String.valueOf(escogerFut.getValueAt(fila, 1)));
            this.vista.jtextNombre.setText(String.valueOf(escogerFut.getValueAt(fila, 2)));
            this.vista.jtextApellido.setText(String.valueOf(escogerFut.getValueAt(fila, 3)));
            this.vista.jtextNacimiento.setText(String.valueOf(escogerFut.getValueAt(fila, 4)));
            this.vista.jtextNacionalidad.setText(String.valueOf(escogerFut.getValueAt(fila, 5)));
            
        }
    }
        
        
        public void TablaClubes (MouseEvent e) {
        int fila= this.vista.tablaClubes.rowAtPoint(e.getPoint());
        DefaultTableModel escogerClub = modelo_Club.getTablaClub();

        if (fila> -1) {
            this.vista.jtextIdClub.setText(String.valueOf(escogerClub.getValueAt(fila, 0)));
            this.vista.jtextClub.setText(String.valueOf(escogerClub.getValueAt(fila, 1)));
            this.vista.jtextEstadio.setText(String.valueOf(escogerClub.getValueAt(fila, 2)));
            this.vista.jtextCreacion.setText(String.valueOf(escogerClub.getValueAt(fila, 3)));
        }
        
        
        
    }
        
        public void TablaFutbolistasR (MouseEvent e) {
         int fila = this.vista.tablaFutbolistaR.rowAtPoint(e.getPoint());
        DefaultTableModel escogerFut = modelo_Futbolista.getTablaFutbolistaPartic();

        if (fila > -1) {
            
            
             this.vista.jtextIDFR.setText(String.valueOf(escogerFut.getValueAt(fila, 0)));
             this.vista.jtextFutbolistaR.setText(String.valueOf(escogerFut.getValueAt(fila, 1)));
           
            
            
        }
    }
        
        public void TablaClubesR (MouseEvent e) {
        int fila= this.vista.tablaClubR.rowAtPoint(e.getPoint());
        DefaultTableModel escogerClub = modelo_Club.getTablaClub();

        if (fila> -1) {
            this.vista.jtextIDCR.setText(String.valueOf(escogerClub.getValueAt(fila, 0)));
            this.vista.jtextClubR.setText(String.valueOf(escogerClub.getValueAt(fila, 1)));
            
        }
        
        
        
    }
        
         public void TablaRelacion (MouseEvent e) {
        int fila= this.vista.tablaRelacion.rowAtPoint(e.getPoint());
        DefaultTableModel escogerClub = Futbolista_Club.getTablaRelacion(this.vista.jtextIDFR.getText());

        if (fila> -1) {
            this.vista.jtextIDFR.setText(String.valueOf(escogerClub.getValueAt(fila, 0)));
            this.vista.jtextFutbolistaR.setText(String.valueOf(escogerClub.getValueAt(fila, 1)));
            this.vista.jtextIDCR.setText(String.valueOf(escogerClub.getValueAt(fila, 2)));
            this.vista.jtextClubR.setText(String.valueOf(escogerClub.getValueAt(fila, 3)));
            this.vista.jtextTemporada.setText(String.valueOf(escogerClub.getValueAt(fila, 4)));
            
        }
        
        
        
    }
         
        public void getTablaFutbolistaClub (MouseEvent e) {
        int fila= this.vista.tablaFutbolistaClub.rowAtPoint(e.getPoint());
        DefaultTableModel escogerAct1 = Futbolista_Club.getTablaFutbolistaClub(this.vista.jtextIdFutbolista.getText());

        if (fila> -1) {
            this.vista.jtextIdFutbolista.setText(String.valueOf(escogerAct1.getValueAt(fila, 0)));
         
        }
        
        
        
    }
        
        public void getTablaClubFutbolista (MouseEvent e) {
        int fila= this.vista.tablaClubFutbolista.rowAtPoint(e.getPoint());
        DefaultTableModel escogerAct1 = Futbolista_Club.getTablaClubFutbolista(this.vista.jtextIdClub.getText());

        if (fila> -1) {
            this.vista.jtextIdClub.setText(String.valueOf(escogerAct1.getValueAt(fila, 0)));
         
        }
        
        
        
    }
        
       

         
    public void mouseClicked(MouseEvent e) {
        
    }

    
    public void mousePressed(MouseEvent e) {
        
    }

    
    public void mouseReleased(MouseEvent e) {
       
    }

    
    public void mouseEntered(MouseEvent e) {
       
    }

    
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        
    }
          
     
        
        
        

    
   


