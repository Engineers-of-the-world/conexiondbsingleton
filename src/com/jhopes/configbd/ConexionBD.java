/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhopes.configbd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author P&D
 */
public final class ConexionBD {
    public static Connection con; // objeto conexión.
    private static ConexionBD INSTANCE = null;
    public static Statement st;
    static ResultSet rs;
    /**Método constructor que ejecuta el método para conectar con la base de datos
     *
     */
    public ConexionBD() {
        performConnection();
    }

    /**Crea una instancia de la base de datos en caso de no estar creada.
     *
     */
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConexionBD();
        }
    }

    /**Metodo para retorna una instancia de la conexion. Si no esta creada la crea, y si esta creada la retorna
     * @return retorna una instancia de la conexión a la base de datos
     */
    public static ConexionBD getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    /**Método para eliminar la instancia de la conexión
     *
     */
    public static void deleteInstance() {
        INSTANCE = null;
        CloseConexion();
    }

    /**Método que crea la conexión a la base de datos
     *
     */
    public void performConnection() {

        try { // preparamos la conexión            
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/dbprueba",
                    "postgres", "123456");
            st = (Statement) con.createStatement();
            System.out.println("conexión abierta.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al abrir la conexión.");
        }
    }
    
    public ResultSet executeQuery(String sql){
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public boolean executeInsertUpdate(String sql){
        boolean e;
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            e = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            e = false;
        }
        return e;
    }
    /**Método para cerrar la conexión con la base de dades
     *
     */
    public static void CloseConexion(){
        try {
            con.close();
            System.out.println("Conexión cerrada.");
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
        }
    }
}
