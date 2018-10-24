package MODELO.SEGURIDAD;

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
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SEG_MODIFICACIONES {

    private int ID;
    private int ID_USR;
    private String TBL_NOMBRE;
    private int TBL_ID;
    private String MENSAJE;
    private Date FECHA;
    private String IP;
    private int TIPO;

    private Conexion con = null;
    private String TBL = "seg_modificaciones";

    public SEG_MODIFICACIONES(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "﻿	id_usr, tbl_nombre, tbl_id, mensaje, fecha, ip, tipo)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_USR());
        ps.setString(2, getTBL_NOMBRE());
        ps.setInt(3, getTBL_ID());
        ps.setString(4, getMENSAJE());
        ps.setTimestamp(5, new Timestamp(new Date().getTime()));
        ps.setString(6, getIP());
        ps.setInt(7, getTIPO());
        ps.execute();
        consulta = "select last_value from Seg_Modificaciones_id_seq";
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

    public JSONObject getByid(int id) throws SQLException, JSONException {
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

    public JSONArray getAll() throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj = parseJson(rs);
        }
        ps.close();
        rs.close();
        return arr;
    }

    private JSONObject parseObj;

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("id_art_marca", rs.getInt("id_art_marca"));
        parseObj.put("estado", rs.getInt("estado"));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("id_usr", getID_USR());
        obj.put("tbl_nombre", getTBL_NOMBRE());
        obj.put("tbl_id", getTBL_ID());
        obj.put("mensaje", getMENSAJE());
        obj.put("fecha", new Timestamp(new Date().getTime()));
        obj.put("ip", getIP());
        obj.put("tipo", getTIPO());

        return obj;
    }

    public String get_tipo_String(int tipo) {
        switch (tipo) {
            case 1:
                return "Inserto";
            case 2:
                return "Modifico";
            case 3:
                return "Elimino";
     
        }
        return "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_USR() {
        return ID_USR;
    }

    public void setID_USR(int ID_USR) {
        this.ID_USR = ID_USR;
    }

    public String getTBL_NOMBRE() {
        return TBL_NOMBRE;
    }

    public void setTBL_NOMBRE(String TBL_NOMBRE) {
        this.TBL_NOMBRE = TBL_NOMBRE;
    }

    public int getTBL_ID() {
        return TBL_ID;
    }

    public void setTBL_ID(int TBL_ID) {
        this.TBL_ID = TBL_ID;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getTBL() {
        return TBL;
    }

    public void setTBL(String TBL) {
        this.TBL = TBL;
    }

    public JSONObject getParseObj() {
        return parseObj;
    }

    public void setParseObj(JSONObject parseObj) {
        this.parseObj = parseObj;
    }

}
