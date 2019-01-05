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

public class REP_SUB_CATEGORIA_ACTIVA {

    private int ID;
    private int ID_REP_SUB_CATEGORIA;
    private int ID_REP_AUTO_TO_REP_VERSION;
    private Conexion con = null;
    private String TBL = "rep_sub_categoria_activa";

    public REP_SUB_CATEGORIA_ACTIVA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + " id_rep_sub_categoria, id_rep_auto_to_rep_version)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_REP_SUB_CATEGORIA());
        ps.setInt(2, getID_REP_AUTO_TO_REP_VERSION());
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

    public JSONObject getById_rep_version(int id_sub_cat, int id_verion) throws SQLException, JSONException {
        String consulta = "select subcat.* \n"
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
//id_rep_sub_categoria, id_rep_auto_to_rep_version

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("id_rep_sub_categoria", rs.getInt("id_rep_sub_categoria"));
        parseObj.put("id_rep_auto_to_rep_version", rs.getInt("id_rep_auto_to_rep_version"));

        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("id_rep_sub_categoria", getID_REP_SUB_CATEGORIA());
        obj.put("id_rep_auto_to_rep_version", getID_REP_AUTO_TO_REP_VERSION());
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_REP_SUB_CATEGORIA() {
        return ID_REP_SUB_CATEGORIA;
    }

    public void setID_REP_SUB_CATEGORIA(int ID_REP_SUB_CATEGORIA) {
        this.ID_REP_SUB_CATEGORIA = ID_REP_SUB_CATEGORIA;
    }

    public int getID_REP_AUTO_TO_REP_VERSION() {
        return ID_REP_AUTO_TO_REP_VERSION;
    }

    public void setID_REP_AUTO_TO_REP_VERSION(int ID_REP_AUTO_TO_REP_VERSION) {
        this.ID_REP_AUTO_TO_REP_VERSION = ID_REP_AUTO_TO_REP_VERSION;
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
