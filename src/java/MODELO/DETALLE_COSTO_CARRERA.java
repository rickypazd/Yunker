package MODELO;

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

public class DETALLE_COSTO_CARRERA {

    private int ID;
    private double COSTO;
    private int TIPO;
    private int ID_CARRERA;
    private String nombre;
    private Conexion con = null;

    public DETALLE_COSTO_CARRERA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.detalle_costo_carrera(\n"
                + "	tipo, costo, id_carrera)\n"
                + "	VALUES (?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getTIPO());
        ps.setDouble(2, getCOSTO());
        ps.setInt(3, getID_CARRERA());
        ps.execute();
        consulta = "select last_value from detalle_costo_carrera_id_seq";
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
    public int Insertar_con_nombre() throws SQLException {
        String consulta = "INSERT INTO public.detalle_costo_carrera(\n"
                + "	tipo, costo, id_carrera, nombre)\n"
                + "	VALUES (?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getTIPO());
        ps.setDouble(2, getCOSTO());
        ps.setInt(3, getID_CARRERA());
        ps.setString(4, getNombre());
        ps.execute();
        consulta = "select last_value from detalle_costo_carrera_id_seq";
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

    public JSONArray get_costo_x_id_tipo(int id) throws SQLException, JSONException {
        String consulta = "select * from detalle_costo_carrera\n"
                + "where id_carrera=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("tipo", rs.getInt("tipo"));
            obj.put("costo", rs.getDouble("costo"));
            obj.put("nombre", rs.getString("nombre"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public double getCOSTO() {
        return COSTO;
    }

    public void setCOSTO(double COSTO) {
        this.COSTO = COSTO;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public int getID_CARRERA() {
        return ID_CARRERA;
    }

    public void setID_CARRERA(int ID_CARRERA) {
        this.ID_CARRERA = ID_CARRERA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String detalle_nombre(int tipo) {
        switch (tipo) {
            case 1:
                return "Distancia";
            case 2:
                return "Tiempo";
            case 3:
                return "Basico";
            case 4:
                return "Deuda";
        }
        return "Undefined";
    }
}
