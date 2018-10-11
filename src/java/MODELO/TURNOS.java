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

public class TURNOS {

    private int ID;
    private int ID_CONDUCTOR;
    private int ID_VEHICULO;
    private int ESTADO;
    private Date FECHA_INICIO;
    private Date FECHA_FIN;

    private Conexion con = null;

    public TURNOS(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.turnos(\n"
                + "	id_conductor, id_vehiculo, estado, fecha_inicio)\n"
                + "	VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_CONDUCTOR());
        ps.setInt(2, getID_VEHICULO());
        ps.setInt(3, getESTADO());
        ps.setTimestamp(4, new Timestamp(getFECHA_INICIO().getTime()));
        ps.executeUpdate();

        consulta = "select last_value from turnos_id_seq";
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

    public int Insertar_tipo_sin_ve() throws SQLException {
        String consulta = "INSERT INTO public.turnos(\n"
                + "	id_conductor, id_vehiculo, estado, fecha_inicio, tipo)\n"
                + "	VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_CONDUCTOR());
        ps.setInt(2, getID_VEHICULO());
        ps.setInt(3, getESTADO());
        ps.setTimestamp(4, new Timestamp(getFECHA_INICIO().getTime()));
        ps.setInt(5, 1);
        ps.executeUpdate();
        consulta = "select last_value from turnos_id_seq";
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

    public JSONObject getTurno(int id) throws SQLException, JSONException {
        String consulta = "select * from turnos \n"
                + "where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();

        if (rs.next()) {

            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("tipo", rs.getInt("tipo"));

        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getTurnoActivoConductor(int id_usr) throws SQLException, JSONException {
        String consulta = "select * from public.turnos \n"
                + "where estado =1 and id_conductor=" + id_usr;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();

        if (rs.next()) {

            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("tipo", rs.getInt("tipo"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public void Terminar_turno_8h(String fecha) throws SQLException {
        String consulta = "UPDATE public.turnos\n"
                + "	SET estado=?, fecha_fin=?\n"
                + "	WHERE estado=1\n"
                + "	and fecha_inicio < '" + fecha + "'";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, 2);
        ps.setTimestamp(2, new Timestamp(new Date().getTime()));
        ps.executeUpdate();
        ps.close();
    }
    public void Terminar_turno_conductor(int id) throws SQLException {
        String consulta = "UPDATE public.turnos\n" +
"                  SET estado=?, fecha_fin=?\n" +
"                  WHERE estado=1 and id_conductor="+id;
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, 2);
        ps.setTimestamp(2, new Timestamp(new Date().getTime()));
        ps.executeUpdate();
        ps.close();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_CONDUCTOR() {
        return ID_CONDUCTOR;
    }

    public void setID_CONDUCTOR(int ID_CONDUCTOR) {
        this.ID_CONDUCTOR = ID_CONDUCTOR;
    }

    public int getID_VEHICULO() {
        return ID_VEHICULO;
    }

    public void setID_VEHICULO(int ID_VEHICULO) {
        this.ID_VEHICULO = ID_VEHICULO;
    }

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public Date getFECHA_INICIO() {
        return FECHA_INICIO;
    }

    public void setFECHA_INICIO(Date FECHA_INICIO) {
        this.FECHA_INICIO = FECHA_INICIO;
    }

    public Date getFECHA_FIN() {
        return FECHA_FIN;
    }

    public void setFECHA_FIN(Date FECHA_FIN) {
        this.FECHA_FIN = FECHA_FIN;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
