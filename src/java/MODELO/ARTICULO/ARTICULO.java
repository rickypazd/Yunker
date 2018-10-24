package MODELO.ARTICULO;

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

public class ARTICULO {

    private int ID;
    private String CLAVE;
    private String NOMBRE;
    private String DESCRIPCION;
    private double PRECIO_COMPRA_REF;
    private double PRECIO_VENTA_REF;
    private double MARGEN_DE_UTILIDAD;
    private String UNIDAD_DE_COMPRA;
    private String UNIDAD_DE_VENTA;
    private int FACTOR;
    private int ID_CATEGORIA;
    private int ID_MARCA;
    private int ID_DEPARTAMENTO;
    private Conexion con = null;

    public ARTICULO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.articulo(\n"
                + "	clave, nombre, descripcion, precio_compra_ref, precio_venta_ref, margen_de_utilidad, unidad_de_compra, unidad_de_venta, factor, id_categoria, id_marca, id_departamento)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getCLAVE());
        ps.setString(2, getNOMBRE());
        ps.setString(3, getDESCRIPCION());
        ps.setDouble(4, getPRECIO_COMPRA_REF());
        ps.setDouble(5, getPRECIO_VENTA_REF());
        ps.setDouble(6, getMARGEN_DE_UTILIDAD());
        ps.setString(7, getUNIDAD_DE_COMPRA());
        ps.setString(8, getUNIDAD_DE_VENTA());
        ps.setInt(9, getFACTOR());
        ps.setInt(10, getID_CATEGORIA());
        ps.setInt(11, getID_MARCA());
        ps.setInt(12, getID_DEPARTAMENTO());
        ps.execute();
        consulta = "select last_value from articulo_id_seq ";
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
    public JSONObject getArticulo(int id) throws SQLException, JSONException {
        String consulta = "select articulo.* "
                + "from articulo ar\n"
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

    public JSONArray getArticulos() throws SQLException, JSONException {
        String consulta = "select articulo.* "
                + "from articulo ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
         JSONObject obj;
        while(rs.next()) {
            obj= new JSONObject();
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
        parseObj.put("clave", rs.getString("clave") != null ? rs.getString("clave") : "");
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("descripcion", rs.getString("descripcion") != null ? rs.getString("descripcion") : "");
        parseObj.put("precio_compra_ref", rs.getDouble("precio_compra_ref"));
        parseObj.put("precio_venta_ref", rs.getDouble("precio_venta_ref"));
        parseObj.put("margen_de_utilidad", rs.getDouble("margen_de_utilidad"));
        parseObj.put("unidad_de_compra", rs.getString("unidad_de_compra") != null ? rs.getString("unidad_de_compra") : "");
        parseObj.put("unidad_de_venta", rs.getString("unidad_de_venta") != null ? rs.getString("unidad_de_venta") : "");
        parseObj.put("factor", rs.getInt("factor"));
        parseObj.put("id_categoria", rs.getInt("id_categoria"));
        parseObj.put("id_marca", rs.getInt("id_marca"));
        parseObj.put("id_departamento", rs.getInt("id_departamento"));
        return parseObj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public double getPRECIO_COMPRA_REF() {
        return PRECIO_COMPRA_REF;
    }

    public void setPRECIO_COMPRA_REF(double PRECIO_COMPRA_REF) {
        this.PRECIO_COMPRA_REF = PRECIO_COMPRA_REF;
    }

    public double getPRECIO_VENTA_REF() {
        return PRECIO_VENTA_REF;
    }

    public void setPRECIO_VENTA_REF(double PRECIO_VENTA_REF) {
        this.PRECIO_VENTA_REF = PRECIO_VENTA_REF;
    }

    public double getMARGEN_DE_UTILIDAD() {
        return MARGEN_DE_UTILIDAD;
    }

    public void setMARGEN_DE_UTILIDAD(double MARGEN_DE_UTILIDAD) {
        this.MARGEN_DE_UTILIDAD = MARGEN_DE_UTILIDAD;
    }

    public String getUNIDAD_DE_COMPRA() {
        return UNIDAD_DE_COMPRA;
    }

    public void setUNIDAD_DE_COMPRA(String UNIDAD_DE_COMPRA) {
        this.UNIDAD_DE_COMPRA = UNIDAD_DE_COMPRA;
    }

    public String getUNIDAD_DE_VENTA() {
        return UNIDAD_DE_VENTA;
    }

    public void setUNIDAD_DE_VENTA(String UNIDAD_DE_VENTA) {
        this.UNIDAD_DE_VENTA = UNIDAD_DE_VENTA;
    }

    public int getFACTOR() {
        return FACTOR;
    }

    public void setFACTOR(int FACTOR) {
        this.FACTOR = FACTOR;
    }

    public int getID_CATEGORIA() {
        return ID_CATEGORIA;
    }

    public void setID_CATEGORIA(int ID_CATEGORIA) {
        this.ID_CATEGORIA = ID_CATEGORIA;
    }

    public int getID_MARCA() {
        return ID_MARCA;
    }

    public void setID_MARCA(int ID_MARCA) {
        this.ID_MARCA = ID_MARCA;
    }

    public int getID_DEPARTAMENTO() {
        return ID_DEPARTAMENTO;
    }

    public void setID_DEPARTAMENTO(int ID_DEPARTAMENTO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
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