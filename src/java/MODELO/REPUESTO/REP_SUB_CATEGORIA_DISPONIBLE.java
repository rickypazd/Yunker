package MODELO.REPUESTO;

import MODELO.ARTICULO.*;
import Conexion.Conexion;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class REP_SUB_CATEGORIA_DISPONIBLE {

    
    private int ID_REPUESTO;
    private int ID_REP_SUB_CATEGORIA_ACTIVA;
    private Conexion con = null;
    private String TBL = "rep_sub_categoria_disponible";

    public REP_SUB_CATEGORIA_DISPONIBLE(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + " id_repuesto, id_rep_sub_categoria_activa)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_REPUESTO());
        ps.setInt(2, getID_REP_SUB_CATEGORIA_ACTIVA());
        ps.execute();
        ps.close();
        return 0;
    }

    public JSONObject getById(int id) throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar\n"
                + "where ar.id=?";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getById_rep_version(int id_sub_cat, int id_verion) throws SQLException, JSONException {
        String consulta = "﻿select subcat.* \n"
                + "from rep_sub_categoria_activa subcat\n"
                + "where subcat.id_rep_sub_categoria = "+id_sub_cat+"\n"
                + "and subcat.id_rep_auto_to_rep_version = " + id_verion;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
            obj.put("exito", true);
        } else {
            obj.put("exito", false);
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray getAll() throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = parseJson(rs);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    private JSONObject parseObj;
// id_repuesto, id_rep_sub_categoria_activa

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        
        parseObj.put("id_repuesto", rs.getInt("id_repuesto"));
        parseObj.put("id_rep_sub_categoria_activa", rs.getInt("id_rep_sub_categoria_activa"));

        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
       
        obj.put("id_repuesto", getID_REPUESTO());
        obj.put("id_rep_sub_categoria_activa", getID_REP_SUB_CATEGORIA_ACTIVA());
        return obj;
    }

  

    public int getID_REPUESTO() {
        return ID_REPUESTO;
    }

    public void setID_REPUESTO(int ID_REPUESTO) {
        this.ID_REPUESTO = ID_REPUESTO;
    }

    public int getID_REP_SUB_CATEGORIA_ACTIVA() {
        return ID_REP_SUB_CATEGORIA_ACTIVA;
    }

    public void setID_REP_SUB_CATEGORIA_ACTIVA(int ID_REP_SUB_CATEGORIA_ACTIVA) {
        this.ID_REP_SUB_CATEGORIA_ACTIVA = ID_REP_SUB_CATEGORIA_ACTIVA;
    }

   

    public String getTBL() {
        return TBL;
    }

    public void setTBL(String TBL) {
        this.TBL = TBL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public JSONObject getParseObj() {
        return parseObj;
    }

    public void setParseObj(JSONObject parseObj) {
        this.parseObj = parseObj;
    }

}
