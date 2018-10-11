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

public class PUNTUACION_CARRERA {

    private int ID;
    private double STAR;
    private int ID_CARRERA;
    private String MENSAJE;
    private boolean AMABLE;
    private boolean BUENA_RUTA;
    private boolean AUTO_LIMPIO;
    private Conexion con = null;

    public PUNTUACION_CARRERA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.puntuacion_carrera(\n"
                + "	star, id_carrera, mensaje, amable, buena_ruta, auto_limpio)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setDouble(1, getSTAR());
        ps.setInt(2, getID_CARRERA());
        ps.setString(3, getMENSAJE());
        ps.setBoolean(4, isAMABLE());
        ps.setBoolean(5, isBUENA_RUTA());
        ps.setBoolean(6, isAUTO_LIMPIO());
        ps.execute();
        consulta = "select last_value from puntuacion_carrera_id_seq";
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getSTAR() {
        return STAR;
    }

    public void setSTAR(double STAR) {
        this.STAR = STAR;
    }

    public int getID_CARRERA() {
        return ID_CARRERA;
    }

    public void setID_CARRERA(int ID_CARRERA) {
        this.ID_CARRERA = ID_CARRERA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }

    public boolean isAMABLE() {
        return AMABLE;
    }

    public void setAMABLE(boolean AMABLE) {
        this.AMABLE = AMABLE;
    }

    public boolean isBUENA_RUTA() {
        return BUENA_RUTA;
    }

    public void setBUENA_RUTA(boolean BUENA_RUTA) {
        this.BUENA_RUTA = BUENA_RUTA;
    }

    public boolean isAUTO_LIMPIO() {
        return AUTO_LIMPIO;
    }

    public void setAUTO_LIMPIO(boolean AUTO_LIMPIO) {
        this.AUTO_LIMPIO = AUTO_LIMPIO;
    }

}
