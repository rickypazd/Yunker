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

public class REPUESTO {

    private int ID;
    private int ID_SUB_CATEGORIA;
    private String NOMBRE;
    private String OTROS_NOMBRES;
    private String DESCRIPCION;
    private String FABRICANTE;
    private String SERIE;
    private String URL_FOTO;
    private double PRECIO;
    private Conexion con = null;
    private String TBL = "repuesto";//rep_parte

    public REPUESTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	fabricante, precio, serie, descripcion, nombre, otros_nombres, id_sub_categoria)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getFABRICANTE());
        ps.setDouble(2, getPRECIO());
        ps.setString(3, getSERIE());
        ps.setString(4, getDESCRIPCION());
        ps.setString(5, getNOMBRE());
        ps.setString(6, getOTROS_NOMBRES());
        ps.setInt(7, getID_SUB_CATEGORIA());
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

    public int subir_foto_perfil() throws SQLException {
        String consulta = "UPDATE public." + TBL + " \n"
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

    public JSONArray getAll_by_id_sub_cat_activa(int id_sub_cat) throws SQLException, JSONException {
        String consulta = "SELECT rep.* \n"
                + "FROM rep_sub_categoria_disponible rscd, repuesto rep\n"
                + "where rscd.id_rep_sub_categoria_activa = "+id_sub_cat+"\n"
                + "AND rep.id = rscd.id_repuesto\n";
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
        parseObj.put("id_sub_categoria", rs.getInt("id_sub_categoria"));
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("otros_nombres", rs.getString("otros_nombres") != null ? rs.getString("otros_nombres") : "");
        parseObj.put("descripcion", rs.getString("descripcion") != null ? rs.getString("descripcion") : "");
        parseObj.put("fabricante", rs.getString("fabricante") != null ? rs.getString("fabricante") : "");
        parseObj.put("serie", rs.getString("serie") != null ? rs.getString("serie") : "");
        parseObj.put("url_foto", rs.getString("url_foto") != null ? rs.getString("url_foto") : "img/Sin_imagen.png");
        parseObj.put("precio", rs.getDouble("precio"));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("id_sub_categoria", getID_SUB_CATEGORIA());
        obj.put("nombre", getNOMBRE());
        obj.put("otros_nombres", getOTROS_NOMBRES());
        obj.put("descripcion", getDESCRIPCION());
        obj.put("fabricante", getFABRICANTE());
        obj.put("serie", getSERIE());
        obj.put("url_foto", getURL_FOTO());
        obj.put("precio", getPRECIO());
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_SUB_CATEGORIA() {
        return ID_SUB_CATEGORIA;
    }

    public void setID_SUB_CATEGORIA(int ID_SUB_CATEGORIA) {
        this.ID_SUB_CATEGORIA = ID_SUB_CATEGORIA;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getOTROS_NOMBRES() {
        return OTROS_NOMBRES;
    }

    public void setOTROS_NOMBRES(String OTROS_NOMBRES) {
        this.OTROS_NOMBRES = OTROS_NOMBRES;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getFABRICANTE() {
        return FABRICANTE;
    }

    public void setFABRICANTE(String FABRICANTE) {
        this.FABRICANTE = FABRICANTE;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getURL_FOTO() {
        return URL_FOTO;
    }

    public void setURL_FOTO(String URL_FOTO) {
        this.URL_FOTO = URL_FOTO;
    }

    public double getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(double PRECIO) {
        this.PRECIO = PRECIO;
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
