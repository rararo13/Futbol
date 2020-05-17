/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class Futbolista extends Conexion {
    
    String id;
    String NIF;
    String Nombre;
    String Apellido;
    String añoNacimiento;
    String Nacionalidad;

     public Futbolista(){
        NIF="";
        Nombre="";
        Apellido="";
        añoNacimiento = "";
        Nacionalidad = "";
        
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
   
    
   

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public void setAñoNacimiento(String añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public void setNacionalidad(String Nacionalidad) {
        this.Nacionalidad = Nacionalidad;
    }

    

    public String getNIF() {
        return NIF;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getAñoNacimiento() {
        return añoNacimiento;
    }

   
    
    //Metodo para crear un Futbolista
     public boolean RegistrarFutbolista(String id,String NIF,String Nombre, String Apellido, String añoNacimiento, String Nacionalidad)
    {
        if( valida_datos(NIF, Nombre,  Apellido,
             añoNacimiento,  Nacionalidad ) )
        {
           
          //Se arma la consulta
           String q=" INSERT INTO Futbolistas (id,NIF, Nombre, Apellido , Nacimiento , Nacionalidad )"
                    + "VALUES (default,'"+NIF+ "','" + Nombre + "','" + Apellido + "','" + añoNacimiento + "','" + Nacionalidad + "') ";
            //se ejecuta la consulta
            try {
               try (PreparedStatement pstm = this.getConexion().prepareStatement(q)) {
                   pstm.execute();
               }
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
       
    }
        return false;
    }
 
     //Metodo para modificar un futbolista
       public boolean ModificarFutbolista(String id,String NIF,String Nombre, String Apellido, String añoNacimiento, String Nacionalidad) throws SQLException{
            
           if( valida_datos(NIF, Nombre,  Apellido,
             añoNacimiento,  Nacionalidad ) )
        {
        //se arma la consulta
             String q=" UPDATE Futbolistas SET NIF='" + NIF + "',Nombre='" + Nombre + "', Apellido='"
                        + Apellido + "', Nacimiento='" +añoNacimiento + "', Nacionalidad='" + Nacionalidad +
                    "'WHERE id='"+id+"'";
             //se ejecuta
        try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
        }
        return false;
       }
       
        public boolean EliminarFutbolista(String id)
    {
         boolean res=false;
        //se arma la consulta
        String q = " DELETE FROM Futbolistas WHERE id='"+id+"'";
        //se ejecuta la consulta
         try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return res;
    }
       
        
        public DefaultTableModel getTablaFutbolistas()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"id","NIF","Nombre","Apellido","Nacimiento","Nacionalidad"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Futbolistas");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Futbolistas");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][6];
         while(res.next()){
             
                data[i][0] = res.getString( "id" );
                data[i][1] = res.getString( "NIF" );
                data[i][2] = res.getString( "Nombre" );
                data[i][3] = res.getString( "Apellido" );
                data[i][4] = res.getString( "Nacimiento" );
                data[i][5] = res.getString( "Nacionalidad" );
               
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }

//MODELO PARTIPACIONES
         public DefaultTableModel getTablaFutbolistaPartic()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"ID","Nombre","Apellido"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Futbolistas");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT id, Nombre, Apellido FROM Futbolistas");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][3];
         while(res.next()){
                data[i][0] = res.getString( "id" );
                data[i][1] = res.getString( "Nombre" );
                data[i][2] = res.getString( "Apellido" );

            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }
        
         private boolean valida_datos (String NIF, String Nombre, String Apellido, String añoNacimiento, String Nacionalidad) {
             
         if(NIF.equals("") || Nombre.equals("") || Apellido.equals("") || añoNacimiento.equals("") || Nacionalidad.equals("")){
            return false;
        }else return true;
        
        } 

    

    

   

    

    

    
         
      

}
