/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import View.TelaLogin;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcos Filho
 */
public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //Conectar ao Servidor
    private static final String URL = "jdbc:mysql://192.168.1.11:3306/mike";
    private static final String USER = "Speeduser";
    private static final String PASS = "Speed271113=";
    //Conectar localmente
    private static final String URLLOCAL = "jdbc:mysql://localhost:3306/mike";
    private static final String USERLOCAL = "root";
    private static final String PASSLOCAL = "";
    //Conectar na nuvem
    private static final String URLNUVEM = "jdbc:mysql://dberpsc.mysql.dbaas.com.br/dberpsc";
    private static final String USERNUVEM = "dberpsc";
    private static final String PASSNUVEM = "Speed271113=";

    public static Connection getConnection() {
        String tipologin = Session.db;

        int logintype = 0;

        if (tipologin.equals("NUVEM")) {
            logintype = 0;
        }
        if (tipologin.equals("LOCAL")) {
            logintype = 1;
        }
        if (tipologin.equals("TESTE")) {
            logintype = 2;
        }

        switch (logintype) {
            case 1:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URLLOCAL, USERLOCAL, PASSLOCAL);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
            case 2:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URL, USER, PASS);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
            default:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URLNUVEM, USERNUVEM, PASSNUVEM);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
        }
    }

    public static Connection getConnectionlogin() {
        String tipologin = TelaLogin.cblogin.getSelectedItem().toString();

        int logintype = 0;

        if (tipologin.equals("NUVEM")) {
            logintype = 0;
        }
        if (tipologin.equals("LOCAL")) {
            logintype = 1;
        }
        if (tipologin.equals("TESTE")) {
            logintype = 2;
        }

        switch (logintype) {
            case 1:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URLLOCAL, USERLOCAL, PASSLOCAL);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
            case 2:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URL, USER, PASS);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
            default:
                try {
                    Class.forName(DRIVER);

                    return DriverManager.getConnection(URLNUVEM, USERNUVEM, PASSNUVEM);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException("Erro na conexão!\n" + e);
                }
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
