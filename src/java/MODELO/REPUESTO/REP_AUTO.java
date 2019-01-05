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

public class REP_AUTO {

    private int ID;
    private int ID_MARCA;
    private int ID_MODELO;
    private String ANHO;
    private String CLAVE;
    private String MARCA;
    private String MODELO;
    private Conexion con = null;
    private String TBL = "rep_auto";

    public REP_AUTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	anho, id_marca, marca, id_modelo, modelo, clave)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getANHO());
        ps.setInt(2, getID_MARCA());
        ps.setString(3, getMARCA());
        ps.setInt(4, getID_MODELO());
        ps.setString(5, getMODELO());
        ps.setString(6, getCLAVE());
        ps.execute();
        consulta = "select last_value from " + TBL + "_id_seq ";
        ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("last_value");
        }
        rs.close();
        ps.close();
        return id;
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

    public JSONObject getByIdVersion(int id) throws SQLException, JSONException {
        String consulta = "﻿select auto.* , rav.nombre as version \n"
                + "from \n"
                + "rep_auto_version_to_rep_auto ravta,\n"
                + "rep_auto auto,\n"
                + "rep_auto_version rav\n"
                + "where \n"
                + "ravta.id = ? and\n"
                + "ravta.id_rep_auto = auto.id and\n"
                + "ravta.id_rep_auto_version = rav.id\n";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
            obj.put("version", rs.getString("version") != null ? rs.getString("version") : "");
        } else {
            obj.put("exito", "no");
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

    public JSONArray getAllById_repuesto(int id_repuesto) throws SQLException, JSONException {
        String consulta = "﻿select ra.* , rav.nombre as version\n"
                + "from \n"
                + "	repuesto re, \n"
                + "	rep_sub_categoria_disponible rscd, \n"
                + "	rep_sub_categoria_activa rsca,\n"
                + "	rep_auto_version_to_rep_auto atv,\n"
                + "	rep_auto ra,\n"
                + "	rep_auto_version rav\n"
                + "where \n"
                + "re.id = " + id_repuesto + " and \n"
                + "rscd.id_repuesto = re.id and\n"
                + "rscd.id_rep_sub_categoria_activa = rsca.id and\n"
                + "rsca.id_rep_auto_to_rep_version = atv.id and\n"
                + "atv.id_rep_auto = ra.id and\n"
                + "atv.id_rep_auto_version = rav.id";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = parseJson(rs);
            obj.put("version", rs.getString("version") != null ? rs.getString("version") : "");
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getBy_id_marca_and_id_modelo(int id_marca, int id_modelo) throws SQLException, JSONException {
        String consulta = "select * from rep_auto ra\n"
                + "where ra.id_modelo = " + id_modelo + " and ra.id_marca = " + id_marca + " order by (ra.anho) desc";
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

//    private int ID;
//    private int ID_MARCA;
//    private int ID_MODELO;
//    private String ANHO;
//    private String CLAVE;
//    private String MARCA;
//    private String MODELO;
    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("id_marca", rs.getInt("id_marca"));
        parseObj.put("id_modelo", rs.getInt("id_modelo"));
        parseObj.put("anho", rs.getString("anho") != null ? rs.getString("anho") : "");
        parseObj.put("clave", rs.getString("clave") != null ? rs.getString("clave") : "");
        parseObj.put("marca", rs.getString("marca") != null ? rs.getString("marca") : "");
        parseObj.put("modelo", rs.getString("modelo") != null ? rs.getString("modelo") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("id_marca", getID_MARCA());
        obj.put("id_modelo", getID_MODELO());
        obj.put("anho", getANHO());
        obj.put("clave", getCLAVE());
        obj.put("marca", getMARCA());
        obj.put("modelo", getMODELO());
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_MARCA() {
        return ID_MARCA;
    }

    public void setID_MARCA(int ID_MARCA) {
        this.ID_MARCA = ID_MARCA;
    }

    public int getID_MODELO() {
        return ID_MODELO;
    }

    public void setID_MODELO(int ID_MODELO) {
        this.ID_MODELO = ID_MODELO;
    }

    public String getANHO() {
        return ANHO;
    }

    public void setANHO(String ANHO) {
        this.ANHO = ANHO;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
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
