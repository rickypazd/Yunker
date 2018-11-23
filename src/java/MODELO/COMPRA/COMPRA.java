package MODELO.COMPRA;

import MODELO.PERSONA.*;
import MODELO.ALMACEN.*;
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

public class COMPRA {

    private int ID;
    private String DOCUMENTO;
    private String SERIE;
    private String AUTORIZACION;
    private String CODIGO_CONTROL;
    private Date FECHA;
    private int FORMA_PAGO;
    private int ID_PERSONA;
  
    private Conexion con = null;
    private String TBL="compra";
    
    public COMPRA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public."+TBL+"(\n"
                + "	documento, serie, autorizacion, codigo_control, fecha, forma_pago, id_persona)\n"
                + "	VALUES (﻿?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
    
        ps.setString(1, getDOCUMENTO());
        ps.setString(2, getSERIE());
        ps.setString(3, getAUTORIZACION());
        ps.setString(4, getCODIGO_CONTROL());
        ps.setTimestamp(5, new Timestamp(getFECHA().getTime()));
        ps.setInt(6, getFORMA_PAGO());
        ps.setInt(7, getID_PERSONA());
     
        ps.execute();
        consulta = "select last_value from "+TBL+"_id_seq ";
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
                + "from "+TBL+" ar\n"
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
                + "from "+TBL+" ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj = parseJson(rs);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    private JSONObject parseObj;
//    private int ID;
//    private String DOCUMENTO;
//    private String SERIE;
//    private String AUTORIZACION;
//    private String CODIGO_CONTROL;
//    private Date FECHA;
//    private int FORMA_PAGO;
//    private int ID_PERSONA;
    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("documento", rs.getString("documento") != null ? rs.getString("documento") : "");
        parseObj.put("serie", rs.getString("serie") != null ? rs.getString("serie") : "");
        parseObj.put("autorizacion", rs.getString("autorizacion") != null ? rs.getString("autorizacion") : "");
        parseObj.put("codigo_control", rs.getString("codigo_control") != null ? rs.getString("codigo_control") : "");
        parseObj.put("fecha", rs.getString("fecha") != null ? rs.getString("fecha") : "");
        parseObj.put("forma_pago", rs.getInt("forma_pago"));
        parseObj.put("id_persona", rs.getInt("id_persona"));
        
        return parseObj;
    }
   public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id",getID());
        obj.put("documento",getDOCUMENTO());
        obj.put("serie",getSERIE());
        obj.put("autorizacion", getAUTORIZACION());
        obj.put("codigo_control", getCODIGO_CONTROL());
        obj.put("fecha", getFECHA());
        obj.put("forma_pago", getFORMA_PAGO());
        obj.put("id_persona", getID_PERSONA());
        return obj;
    }
   public String getTipoStr(int tipo){
       switch(tipo){
           case 1:
               return "Al contado";
           case 2:
               return "Credito";
       }
       return "Undefined";
   }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDOCUMENTO() {
        return DOCUMENTO;
    }

    public void setDOCUMENTO(String DOCUMENTO) {
        this.DOCUMENTO = DOCUMENTO;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getAUTORIZACION() {
        return AUTORIZACION;
    }

    public void setAUTORIZACION(String AUTORIZACION) {
        this.AUTORIZACION = AUTORIZACION;
    }

    public String getCODIGO_CONTROL() {
        return CODIGO_CONTROL;
    }

    public void setCODIGO_CONTROL(String CODIGO_CONTROL) {
        this.CODIGO_CONTROL = CODIGO_CONTROL;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public int getFORMA_PAGO() {
        return FORMA_PAGO;
    }

    public void setFORMA_PAGO(int FORMA_PAGO) {
        this.FORMA_PAGO = FORMA_PAGO;
    }

    public int getID_PERSONA() {
        return ID_PERSONA;
    }

    public void setID_PERSONA(int ID_PERSONA) {
        this.ID_PERSONA = ID_PERSONA;
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
