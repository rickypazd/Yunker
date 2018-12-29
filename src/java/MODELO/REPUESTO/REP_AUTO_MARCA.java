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

public class REP_AUTO_MARCA {

    private int ID;
    private String NOMBRE;
    private String URL_FOTO;
    private Conexion con = null;
    private String TBL = "rep_auto_marca";

    public REP_AUTO_MARCA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	nombre)\n"
                + "	VALUES (?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getNOMBRE());
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
    
     public int subir_foto_perfil() throws SQLException {
        String consulta = "UPDATE public."+ TBL +" \n"
                + "	SET url_foto=?\n"
                + "	WHERE id=" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getURL_FOTO());
        int row = ps.executeUpdate();
        ps.close();
        return row;
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
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("url_foto", rs.getString("url_foto") != null ? rs.getString("url_foto") : "img/Sin_imagen.png");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("nombre", getNOMBRE());
         obj.put("url_foto", getURL_FOTO());
        return obj;
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

    public String getURL_FOTO() {
        return URL_FOTO;
    }

    public void setURL_FOTO(String URL_FOTO) {
        this.URL_FOTO = URL_FOTO;
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
