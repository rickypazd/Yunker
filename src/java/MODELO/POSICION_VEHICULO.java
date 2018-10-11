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
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class POSICION_VEHICULO {

    private int ID;
    private double LAT;
    private double LON;
    private Date FECHA;
    private int ID_VEHICULO;
    private float bearing;
    private Conexion con = null;

    public POSICION_VEHICULO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.posicion_vehiculo(\n"
                + "	lat, lon, fecha, id_vehiculo, bearing)\n"
                + "	VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setDouble(1, getLAT());
        ps.setDouble(2, getLON());
        ps.setTimestamp(3, new Timestamp(getFECHA().getTime()));
        ps.setInt(4, getID_VEHICULO());
        ps.setFloat(5, getBearing());
        ps.execute();

        consulta = "select last_value from posicion_vehiculo_id_seq";
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

    public JSONObject get_posicion_x_id_carrera(int id) throws SQLException, JSONException {
        String consulta = "select pv.* from carrera ca, turnos tu, posicion_vehiculo pv\n"
                + "where ca.id_turno=tu.id and tu.id_vehiculo=pv.id_vehiculo\n"
                + "order by pv.fecha desc limit 1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();

        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("lat", rs.getDouble("lat"));
            obj.put("lng", rs.getDouble("lon"));
            obj.put("fecha", rs.getString("fecha"));
            obj.put("bearing",rs.getFloat("bearing"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_posicion_x_id_vehiculo(int id) throws SQLException, JSONException {
        String consulta = "select * from posicion_vehiculo pv\n"
                + "where pv.id_vehiculo="+id+"\n"
                + "order by fecha desc limit 1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("lat", rs.getDouble("lat"));
            obj.put("lng", rs.getDouble("lon"));
            obj.put("fecha", rs.getString("fecha"));
            obj.put("bearing",rs.getFloat("bearing"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray get_pos_id_Carrera(int id_carrera) throws SQLException, JSONException {
        String consulta = "select pv.* from posicion_vehiculo pv,carrera ca, turnos tu\n"
                + "where ca.id=" + id_carrera + " and ca.id_turno=tu.id and pv.id_vehiculo=tu.id_vehiculo\n"
                + "and pv.fecha BETWEEN ca.fecha_inicio and ca.fecha_fin order by(fecha)";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("lat", rs.getDouble("lat"));
            obj.put("lng", rs.getDouble("lon"));
            obj.put("fecha", rs.getString("fecha"));
            obj.put("bearing",rs.getFloat("bearing"));
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

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLON() {
        return LON;
    }

    public void setLON(double LON) {
        this.LON = LON;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public int getID_VEHICULO() {
        return ID_VEHICULO;
    }

    public void setID_VEHICULO(int ID_VEHICULO) {
        this.ID_VEHICULO = ID_VEHICULO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
    

}
