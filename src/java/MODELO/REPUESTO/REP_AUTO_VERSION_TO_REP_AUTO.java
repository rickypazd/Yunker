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

public class REP_AUTO_VERSION_TO_REP_AUTO {

    private int ID;
    private int ID_REP_AUTO;
    private int ID_REP_AUTO_VERSION;
    private Conexion con = null;
    private String TBL = "rep_auto_version_to_rep_auto";

    public REP_AUTO_VERSION_TO_REP_AUTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	id_rep_auto, id_rep_auto_version)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_REP_AUTO());
        ps.setInt(2, getID_REP_AUTO_VERSION());
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

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("id_rep_auto", rs.getInt("id_rep_auto"));
        parseObj.put("id_rep_auto_version", rs.getInt("id_rep_auto_version"));
     
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("id_rep_auto", getID_REP_AUTO());
        obj.put("id_rep_auto_version", getID_REP_AUTO_VERSION());
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_REP_AUTO() {
        return ID_REP_AUTO;
    }

    public void setID_REP_AUTO(int ID_REP_AUTO) {
        this.ID_REP_AUTO = ID_REP_AUTO;
    }

    public int getID_REP_AUTO_VERSION() {
        return ID_REP_AUTO_VERSION;
    }

    public void setID_REP_AUTO_VERSION(int ID_REP_AUTO_VERSION) {
        this.ID_REP_AUTO_VERSION = ID_REP_AUTO_VERSION;
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
