/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CARBean;
import Connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos Filho
 */
public class GenericDAO {

    public Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    //CAR
    CARBean cb;
    List<CARBean> listcb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listcb = new ArrayList<>();
    }
}
