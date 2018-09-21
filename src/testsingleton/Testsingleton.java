/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsingleton;

import com.jhopes.configbd.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author P&D
 */
public class Testsingleton {

    static ConexionBD cx = ConexionBD.getInstance();
    static ResultSet rs;

    public static void main(String[] args) throws SQLException {

        listaDatos();
        insertDatos(4,"dfdf","1");
        ConexionBD.deleteInstance();
    }

    public static void listaDatos() {

        try {
            String sql = "SELECT id_categoria, name_cat, estado "
                    + " FROM public.categoria ";
            rs = cx.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("name_cat"));//nombre es el campo de la base de datos
            }
        } catch (SQLException ex) {
            Logger.getLogger(Testsingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertDatos(int id, String name, String estado) {
        String sql = "INSERT INTO categoria (id_categoria, name_cat, estado) "
                + "VALUES(" + id + ",'" + name + "','" + estado + "')";
        boolean e = cx.executeInsertUpdate(sql);
        if (e) {
            System.out.println("insertado");
        } else {
            System.out.println("nada");
        }
    }

}
