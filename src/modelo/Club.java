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
public class Club extends Conexion{
    
    String id;
    String Nombre;
    String Estadio;
    String añoCreacion;
    
    

    public Club() {
        id="";
        Nombre="";
        Estadio="";
        añoCreacion="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getEstadio() {
        return Estadio;
    }

    public void setEstadio(String Estadio) {
        this.Estadio = Estadio;
    }

    public String getAñoCreacion() {
        return añoCreacion;
    }

    public void setAñoCreacion(String añoCreacion) {
        this.añoCreacion = añoCreacion;
    }
    
    //Metodo para crear una pelicula
    public boolean RegistrarClub(String id,String Nombre, String Estadio, String añoCreacion)
    {
        //Metodo para validar los datos
         if( valida_datos( Nombre,  Estadio, añoCreacion ) )   
        {
            //Se arma la consulta
           String q=" INSERT INTO Clubes (id, Nombre, Estadio , Creacion )"
                    + "VALUES (default, '"+ Nombre + "','" + Estadio+ "','" + añoCreacion + "') ";
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
    //Metodo para modificar una pelicula
       public boolean ModificarClub(String id, String Nombre, String Estadio, String Creacion) throws SQLException{
           //Metodo para validar los datos
           if( valida_datos( Nombre, Estadio,
             Creacion ) )
        {
            //se arma la consulta
            String q=" UPDATE Clubes SET Nombre='" + Nombre+ "', Estadio='"
                        + Estadio + "', Creacion='" +Creacion + "' WHERE id='"+id+"'";
            //se ejecuta la consulta
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
       
       //metodo para eliminar una pelicula
        public boolean EliminarClub(String id)
    {
         boolean res=false;
        //se arma la consulta
        String q = " DELETE FROM Clubes WHERE id='"+id+"'";
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
       
        public DefaultTableModel getTablaClub()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"ID","Nombre","Estadio","Creacion"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Clubes");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Clubes");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][4];
         while(res.next()){
                data[i][0] = res.getString("id");
                data[i][1] = res.getString("Nombre");
                data[i][2] = res.getString("Estadio");
                data[i][3] = res.getString("Creacion");
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
        //Metodo para insertar los datos en la tabla de asociar
         public DefaultTableModel getTablaClubPartic()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"ID","Club"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Clubes");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT id, Nombre FROM Clubes");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][2];
         while(res.next()){
                data[i][0] = res.getString( "id" );
                data[i][1] = res.getString( "Nombre" );
                

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
           
            private boolean valida_datos(String Nombre, String Estadio, String Creacion) {
             
         if( Nombre.equals("") || Estadio.equals("") || Creacion.equals("") ){
            return false;
        }else return true;
        
        } 

   
    
}
