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

public class DETALLE_COMPRA {

    private int ID;
    private String DESCRIPCION;
    private int CANTIDAD;
    private int FACTOR;
    private int TOTAL;
    private double PRECIO_VENTA;
    private double PRECIO_COMPRA;
    private int ID_ALMACEN;
    private int ID_COMPRA;
    private int ID_ARTICULO;

    private Conexion con = null;
    private String TBL = "detalle_compra";

    public DETALLE_COMPRA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	descripcion, cantidad, factor, total, precio_venta, precio_compra, id_almacen, id_compra, id_articulo)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?););";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getDESCRIPCION());
        ps.setInt(2, getCANTIDAD());
        ps.setInt(3, getFACTOR());
        ps.setInt(4, getTOTAL());
        ps.setDouble(5, getPRECIO_VENTA());
        ps.setDouble(6, getPRECIO_COMPRA());
        ps.setInt(7, getID_ALMACEN());
        ps.setInt(8, getID_COMPRA());
        ps.setInt(9, getID_ARTICULO());
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

//        private int ID;
//    private String DESCRIPCION;
//    private int CANTIDAD;
//    private int FACTOR;
//    private int TOTAL;
//    private double PRECIO_VENTA;
//    private double PRECIO_COMPRA;
//    private int ID_ALMACEN;
//    private int ID_COMPRA;
//    private int ID_ARTICULO;
    private JSONObject parseObj;

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("descripcion", rs.getString("descripcion") != null ? rs.getString("descripcion") : "");
        parseObj.put("cantidad", rs.getInt("cantidad"));
        parseObj.put("factor", rs.getInt("factor"));
        parseObj.put("total", rs.getInt("total"));
        parseObj.put("precio_venta", rs.getDouble("precio_venta"));
        parseObj.put("precio_compra", rs.getDouble("precio_compra"));
        parseObj.put("id_almacen", rs.getInt("id_almacen"));
        parseObj.put("id_compra", rs.getInt("id_compra"));
        parseObj.put("id_articulo", rs.getInt("id_articulo"));

        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("descripcion", getDESCRIPCION());
        obj.put("cantidad", getCANTIDAD());
        obj.put("factor", getFACTOR());
        obj.put("total", getTOTAL());
        obj.put("precio_venta", getPRECIO_VENTA());
        obj.put("precio_compra", getPRECIO_COMPRA());
        obj.put("id_almacen", getID_ALMACEN());
        obj.put("id_compra", getID_COMPRA());
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

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public int getFACTOR() {
        return FACTOR;
    }

    public void setFACTOR(int FACTOR) {
        this.FACTOR = FACTOR;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public double getPRECIO_VENTA() {
        return PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(double PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public double getPRECIO_COMPRA() {
        return PRECIO_COMPRA;
    }

    public void setPRECIO_COMPRA(double PRECIO_COMPRA) {
        this.PRECIO_COMPRA = PRECIO_COMPRA;
    }

    public int getID_ALMACEN() {
        return ID_ALMACEN;
    }

    public void setID_ALMACEN(int ID_ALMACEN) {
        this.ID_ALMACEN = ID_ALMACEN;
    }

    public int getID_COMPRA() {
        return ID_COMPRA;
    }

    public void setID_COMPRA(int ID_COMPRA) {
        this.ID_COMPRA = ID_COMPRA;
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
