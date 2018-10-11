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

public class CANCELACION {

    private int ID_CARRERA;
    private Date FECHA;
    private int ID_USUARIO;
    private int ID_MOTIVO;
    private int TIPO;
    private double TOTAL;
    private Conexion con = null;

    public CANCELACION(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.cancelacion(\n"
                + "	id_carrera, id_usr, tipo, total, id_motivo, fecha)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setDouble(4, getTOTAL());
        ps.setInt(5, getID_MOTIVO());
        ps.setTimestamp(6, new Timestamp(getFECHA().getTime()));
        ps.execute();
        ps.close();
        return getID_CARRERA();
    }

    

    public int getID_CARRERA() {
        return ID_CARRERA;
    }

    public void setID_CARRERA(int ID_CARRERA) {
        this.ID_CARRERA = ID_CARRERA;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getID_MOTIVO() {
        return ID_MOTIVO;
    }

    public void setID_MOTIVO(int ID_MOTIVO) {
        this.ID_MOTIVO = ID_MOTIVO;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public double getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(double TOTAL) {
        this.TOTAL = TOTAL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
