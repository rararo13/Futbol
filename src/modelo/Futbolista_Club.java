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
import javax.swing.table.TableModel;

/**
 *
 * @author Rafael
 */
public class Futbolista_Club extends Conexion {
    
    String idFutbolista;
    String idClub;
    String Temporada;
    
    public Futbolista_Club(){
        idFutbolista= "";
        idClub = "";
        Temporada = "";
    }

    public String getIdFutbolista() {
        return idFutbolista;
    }

    public void setIdFutbolista(String idFutbolista) {
        this.idFutbolista = idFutbolista;
    }

    

    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    public String getTemporada() {
        return Temporada;
    }

    public void setTemporada(String Temporada) {
        this.Temporada = Temporada;
    }
    
    public boolean AsociarFutbolistaClub(String idFutbolista, String idClub, String Temporada)
    {
            //Se arma la consulta
           String q=" INSERT INTO Futbolista_Club(id_futbolista,id_club,Temporada )"
                    + "VALUES ( '"+ idFutbolista+ "','" + idClub+ "','" + Temporada+ "') ";
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
    
         
    public  DefaultTableModel getTablaRelacion(String idFutbolista)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"ID Futbolista","Apellido","ID Club","Club","Temporada"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "Participaciones"
      //para formar la matriz de datos
      try{
      
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Futbolista_Club");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT Futbolista_Club.id_futbolista, Futbolistas.Apellido, Futbolista_Club.id_club, Clubes.Nombre, Futbolista_Club.Temporada FROM Futbolista_Club INNER JOIN Futbolistas ON Futbolista_Club.id_futbolista = Futbolistas.id JOIN Clubes ON Futbolista_Club.id_club = Clubes.id");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][5];
         while(res.next()){
                data[i][0] = res.getString(1);
                data[i][1] = res.getString(2);
                data[i][2] = res.getString(3);
                data[i][3] = res.getString(4);
                data[i][4] = res.getString(5);
                
                
                
                

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
    
    public  DefaultTableModel getTablaFutbolistaClub(String idFutbolista)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Nombre Futbolista","Apellido Futbolista","Club","Temporada"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "Participaciones"
      //para formar la matriz de datos
      try{
      
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Futbolista_Club");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT Futbolistas.Nombre, Futbolistas.Apellido, Clubes.Nombre, Futbolista_Club.Temporada FROM Futbolista_Club INNER JOIN Clubes ON Futbolista_Club.id_club = Clubes.id JOIN Futbolistas ON Futbolista_Club.id_futbolista = Futbolistas.id WHERE Futbolista_Club.id_futbolista= '"+idFutbolista+"'");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][4];
         while(res.next()){
                data[i][0] = res.getString(1);
                data[i][1] = res.getString(2);
                data[i][2] = res.getString(3);
                data[i][3] = res.getString(4);
                

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
         
      public  DefaultTableModel getTablaClubFutbolista(String idClub)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Club","Nombre Futbolista","Apellido Futbolista","Temporada"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "Participaciones"
      //para formar la matriz de datos
      try{
      
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Futbolista_Club");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT Clubes.Nombre, Futbolistas.Nombre, Futbolistas.Apellido, Futbolista_Club.Temporada FROM Futbolista_Club INNER JOIN Clubes ON Futbolista_Club.id_club = Clubes.id JOIN Futbolistas ON Futbolista_Club.id_futbolista = Futbolistas.id WHERE Futbolista_Club.id_club= '"+idClub+"'");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][5];
         while(res.next()){
                data[i][0] = res.getString(1);
                data[i][1] = res.getString(2);
                data[i][2] = res.getString(3);
                data[i][3] = res.getString(4);
             

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
       public boolean EliminarRelacion(String idFutbolista, String idClub, String Temporada )
    {
         boolean res=false;
        //se arma la consulta
        String q = " DELETE FROM Futbolista_Club WHERE  id_futbolista='" + idFutbolista +"'";
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
       
       
   

   
      
      
      
        
    
        

}
