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

public class COSTO {

    private int ID;
    private double COSTO_MINUTO;
    private double COSTO_METRO;
    private double COSTO_BASICO;
    private double COSTO_BASICO_CANCELACION;
    private double COSTO_MINUTO_CANCELACION;
    private double COSTO_MINUTO_CANCELACION_CONDUCTOR;
    private double COSTO_BASICO_CANCELACION_CONDUCTOR;
    private int MINUTOS_PARA_CANCELAR_CONDUCTOR;
    private int MINUTOS_PARA_CANCELAR;
    private int COMISION;
    private Conexion con = null;

    public COSTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.costo(\n"
                + "	 costo_minuto, costo_metro, costo_basico, comision, costo_basico_cancelacion, costo_minuto_cancelacion, minutos_para_cancelar, costo_basico_cancelacion_conductor, costo_minuto_cancelacion_conductor, minutos_para_cancelar_conductor)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setDouble(1, getCOSTO_MINUTO());
        ps.setDouble(2, getCOSTO_METRO());
        ps.setDouble(3, getCOSTO_BASICO());
        ps.setInt(4, getCOMISION());
        ps.setDouble(5, getCOSTO_BASICO_CANCELACION());
        ps.setDouble(6, getCOSTO_MINUTO_CANCELACION());
        ps.setInt(7, getMINUTOS_PARA_CANCELAR());
        ps.setDouble(8, getCOSTO_BASICO_CANCELACION_CONDUCTOR());
        ps.setDouble(9, getCOSTO_MINUTO_CANCELACION_CONDUCTOR());
        ps.setInt(10, getMINUTOS_PARA_CANCELAR_CONDUCTOR());
        ps.execute();
        consulta = "select last_value from costo_id_seq ";
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

    public JSONObject get_costo_x_id_tipo(int id) throws SQLException, JSONException {
        String consulta = "select * from tipo_carrera tc, costo co\n"
                + "where tc.id="+id+" and tc.id_costo=co.id";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("descripcion", rs.getString("descripcion"));
            obj.put("id_costo", rs.getInt("id_costo"));
            obj.put("costo_minuto", rs.getDouble("costo_minuto"));
            obj.put("costo_metro", rs.getDouble("costo_metro"));
            obj.put("costo_basico", rs.getDouble("costo_basico"));
            obj.put("costo_basico_cancelacion", rs.getDouble("costo_basico_cancelacion"));
            obj.put("costo_minuto_cancelacion", rs.getDouble("costo_minuto_cancelacion"));
            obj.put("costo_basico_cancelacion_conductor", rs.getDouble("costo_basico_cancelacion_conductor"));
            obj.put("costo_minuto_cancelacion_conductor", rs.getDouble("costo_minuto_cancelacion_conductor"));
            obj.put("minutos_para_cancelar_conductor", rs.getInt("minutos_para_cancelar_conductor"));
            obj.put("minutos_para_cancelar", rs.getInt("minutos_para_cancelar"));
            obj.put("comision", rs.getInt("comision"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_costo_x_id_carrera(int id) throws SQLException, JSONException {
        String consulta = "select co.* from carrera ca, tipo_carrera tc, costo co\n"
                + "where ca.id=" + id + " and ca.id_tipo=tc.id and tc.id_costo=co.id";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
        
            obj.put("id_costo", rs.getInt("id"));
            obj.put("costo_minuto", rs.getDouble("costo_minuto"));
            obj.put("costo_metro", rs.getDouble("costo_metro"));
            obj.put("costo_basico", rs.getDouble("costo_basico"));
            obj.put("costo_basico_cancelacion", rs.getDouble("costo_basico_cancelacion"));
            obj.put("costo_minuto_cancelacion", rs.getDouble("costo_minuto_cancelacion"));
            obj.put("costo_basico_cancelacion_conductor", rs.getDouble("costo_basico_cancelacion_conductor"));
            obj.put("costo_minuto_cancelacion_conductor", rs.getDouble("costo_minuto_cancelacion_conductor"));
            obj.put("minutos_para_cancelar_conductor", rs.getInt("minutos_para_cancelar_conductor"));
            obj.put("minutos_para_cancelar", rs.getInt("minutos_para_cancelar"));
            obj.put("comision", rs.getInt("comision"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCOSTO_MINUTO() {
        return COSTO_MINUTO;
    }

    public void setCOSTO_MINUTO(double COSTO_MINUTO) {
        this.COSTO_MINUTO = COSTO_MINUTO;
    }

    public double getCOSTO_METRO() {
        return COSTO_METRO;
    }

    public void setCOSTO_METRO(double COSTO_METRO) {
        this.COSTO_METRO = COSTO_METRO;
    }

    public double getCOSTO_BASICO() {
        return COSTO_BASICO;
    }

    public void setCOSTO_BASICO(double COSTO_BASICO) {
        this.COSTO_BASICO = COSTO_BASICO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public int getCOMISION() {
        return COMISION;
    }

    public void setCOMISION(int COMISION) {
        this.COMISION = COMISION;
    }

    public double getCOSTO_BASICO_CANCELACION() {
        return COSTO_BASICO_CANCELACION;
    }

    public void setCOSTO_BASICO_CANCELACION(double COSTO_BASICO_CANCELACION) {
        this.COSTO_BASICO_CANCELACION = COSTO_BASICO_CANCELACION;
    }

    public double getCOSTO_MINUTO_CANCELACION() {
        return COSTO_MINUTO_CANCELACION;
    }

    public void setCOSTO_MINUTO_CANCELACION(double COSTO_MINUTO_CANCELACION) {
        this.COSTO_MINUTO_CANCELACION = COSTO_MINUTO_CANCELACION;
    }

    public double getCOSTO_MINUTO_CANCELACION_CONDUCTOR() {
        return COSTO_MINUTO_CANCELACION_CONDUCTOR;
    }

    public void setCOSTO_MINUTO_CANCELACION_CONDUCTOR(double COSTO_MINUTO_CANCELACION_CONDUCTOR) {
        this.COSTO_MINUTO_CANCELACION_CONDUCTOR = COSTO_MINUTO_CANCELACION_CONDUCTOR;
    }

    public double getCOSTO_BASICO_CANCELACION_CONDUCTOR() {
        return COSTO_BASICO_CANCELACION_CONDUCTOR;
    }

    public void setCOSTO_BASICO_CANCELACION_CONDUCTOR(double COSTO_BASICO_CANCELACION_CONDUCTOR) {
        this.COSTO_BASICO_CANCELACION_CONDUCTOR = COSTO_BASICO_CANCELACION_CONDUCTOR;
    }

    public int getMINUTOS_PARA_CANCELAR_CONDUCTOR() {
        return MINUTOS_PARA_CANCELAR_CONDUCTOR;
    }

    public void setMINUTOS_PARA_CANCELAR_CONDUCTOR(int MINUTOS_PARA_CANCELAR_CONDUCTOR) {
        this.MINUTOS_PARA_CANCELAR_CONDUCTOR = MINUTOS_PARA_CANCELAR_CONDUCTOR;
    }

    public int getMINUTOS_PARA_CANCELAR() {
        return MINUTOS_PARA_CANCELAR;
    }

    public void setMINUTOS_PARA_CANCELAR(int MINUTOS_PARA_CANCELAR) {
        this.MINUTOS_PARA_CANCELAR = MINUTOS_PARA_CANCELAR;
    }

}
