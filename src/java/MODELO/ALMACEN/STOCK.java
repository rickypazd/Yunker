package MODELO.ALMACEN;

import MODELO.COMPRA.*;
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

public class STOCK {

    private int ID;
    private String DESCRIPCION;
    private int STOCK_INICIAL;
    private int STOCK_ACTUAL;
    private double PRECIO_VENTA;
    private int ID_DETALLE_COMPRA;
    private int ID_ALMACEN;
    private int ID_ARTICULO;

    private Conexion con = null;
    private String TBL = "stock";

    public STOCK(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	descripcion, stock_inicial, stock_actual, precio_venta,  id_detalle_compra, id_almacen, id_articulo)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getDESCRIPCION());
        ps.setInt(2, getSTOCK_INICIAL());
        ps.setInt(3, getSTOCK_ACTUAL());
        ps.setDouble(4, getPRECIO_VENTA());
        ps.setInt(5, getID_DETALLE_COMPRA());
        ps.setInt(6, getID_ALMACEN());
        ps.setInt(7, getID_ARTICULO());
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
        parseObj.put("descripcion", rs.getString("descripcion") != null ? rs.getString("descripcion") : "");
        parseObj.put("stock_inicial", rs.getInt("cantidad"));
        parseObj.put("stock_actual", rs.getInt("factor"));
        parseObj.put("precio_venta", rs.getDouble("precio_venta"));
        parseObj.put("id_detalle_compra", rs.getInt("id_detalle_compra"));
        parseObj.put("id_almacen", rs.getInt("id_almacen"));
        parseObj.put("id_articulo", rs.getInt("id_articulo"));

        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("descripcion", getDESCRIPCION());
        obj.put("stock_inicial", getSTOCK_INICIAL());
        obj.put("stock_actual", getSTOCK_ACTUAL());
        obj.put("precio_venta", getPRECIO_VENTA());
        obj.put("id_detalle_compra", getID_DETALLE_COMPRA());
        obj.put("id_almacen", getID_ALMACEN());
        obj.put("id_articulo", getID_ARTICULO());
       
        return obj;
    }

    public String getTipoStr(int tipo) {
        switch (tipo) {
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

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getSTOCK_INICIAL() {
        return STOCK_INICIAL;
    }

    public void setSTOCK_INICIAL(int STOCK_INICIAL) {
        this.STOCK_INICIAL = STOCK_INICIAL;
    }

    public int getSTOCK_ACTUAL() {
        return STOCK_ACTUAL;
    }

    public void setSTOCK_ACTUAL(int STOCK_ACTUAL) {
        this.STOCK_ACTUAL = STOCK_ACTUAL;
    }

    public double getPRECIO_VENTA() {
        return PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(double PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public int getID_DETALLE_COMPRA() {
        return ID_DETALLE_COMPRA;
    }

    public void setID_DETALLE_COMPRA(int ID_DETALLE_COMPRA) {
        this.ID_DETALLE_COMPRA = ID_DETALLE_COMPRA;
    }

    public int getID_ALMACEN() {
        return ID_ALMACEN;
    }

    public void setID_ALMACEN(int ID_ALMACEN) {
        this.ID_ALMACEN = ID_ALMACEN;
    }

   
    public int getID_ARTICULO() {
        return ID_ARTICULO;
    }

    public void setID_ARTICULO(int ID_ARTICULO) {
        this.ID_ARTICULO = ID_ARTICULO;
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
