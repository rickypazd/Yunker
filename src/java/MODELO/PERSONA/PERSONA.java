package MODELO.PERSONA;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PERSONA {

    private int ID;
    private String NOMBRE;
    private String AP_RS;
    private String CI_NIT;
    private String TELEFONO;
    private String CORREO;
    private int ID_USR;
    private int TIPO;
    private int TIPO_ROL;
    private Conexion con = null;
    private String TBL="persona";
    
    public PERSONA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public."+TBL+"(\n"
                + "	nombre, ap_rs, ci_nit, telefono, correo, tipo, tipo_rol)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getNOMBRE());
        ps.setString(2, getAP_RS());
        ps.setString(3, getCI_NIT());
        ps.setString(4, getTELEFONO());
        ps.setString(5, getCORREO());
        ps.setInt(6, getTIPO());
        ps.setInt(7, getTIPO_ROL());
     
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

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("ap_rs", rs.getString("ap_rs") != null ? rs.getString("ap_rs") : "");
        parseObj.put("ci_nit", rs.getString("ci_nit") != null ? rs.getString("ci_nit") : "");
        parseObj.put("telefono", rs.getString("telefono") != null ? rs.getString("telefono") : "");
        parseObj.put("correo", rs.getString("correo") != null ? rs.getString("correo") : "");
        parseObj.put("tipo", rs.getInt("tipo"));
        parseObj.put("tipo_rol", rs.getInt("tipo_rol"));
        parseObj.put("id_usr", rs.getInt("id_usr"));
        parseObj.put("tipo", getTipoStr(rs.getInt("tipo")));
        return parseObj;
    }
   public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("nombre", getNOMBRE());
        obj.put("ap_rs", getAP_RS());
        obj.put("ci_nit", getCI_NIT());
        obj.put("telefono", getTELEFONO());
        obj.put("correo", getCORREO());
        obj.put("tipo", getTIPO());
        obj.put("tipo_rol", getTIPO_ROL());
        obj.put("id_usr", getID_USR());
        obj.put("tipo", getTipoStr(getTIPO()));
        
        return obj;
    }
   public String getTipoStr(int tipo){
       switch(tipo){
           case 1:
               return "Persona natural";
           case 2:
               return "Persona juridica";
       }
       return "Undefined";
   }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAP_RS() {
        return AP_RS;
    }

    public void setAP_RS(String AP_RS) {
        this.AP_RS = AP_RS;
    }

    public String getCI_NIT() {
        return CI_NIT;
    }

    public void setCI_NIT(String CI_NIT) {
        this.CI_NIT = CI_NIT;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public int getID_USR() {
        return ID_USR;
    }

    public void setID_USR(int ID_USR) {
        this.ID_USR = ID_USR;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public int getTIPO_ROL() {
        return TIPO_ROL;
    }

    public void setTIPO_ROL(int TIPO_ROL) {
        this.TIPO_ROL = TIPO_ROL;
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
