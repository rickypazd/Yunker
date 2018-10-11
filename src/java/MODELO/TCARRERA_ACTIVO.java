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

public class TCARRERA_ACTIVO {

    private int ID_CONDUCTOR;
    private boolean estandar;
    private boolean togo;
    private boolean maravilla;
    private boolean ssuper;
    private boolean act_estandar;
    private boolean act_togo;
    private boolean act_maravilla;
    private boolean act_super;
    private Conexion con = null;

    public TCARRERA_ACTIVO(Conexion con) {
        this.con = con;
    }

    public String Insertar(int id) throws SQLException {
        String consulta = "INSERT INTO public.tcarrera_activo(\n"
                + "	id_conductor, act_estandar, act_togo, act_maravilla, act_supe)\n"
                + "	VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);
        ps.setBoolean(2, isAct_estandar());
        ps.setBoolean(3, isAct_togo());
        ps.setBoolean(4, isAct_maravilla());
        ps.setBoolean(5, isAct_super());
        ps.execute();
        ps.close();
        return "exito";
    }

    public String update() throws SQLException {
        String consulta = "UPDATE public.tcarrera_activo\n"
                + "	SET estandar=?, togo=?, maravilla=?, super=?\n"
                + "	WHERE id_conductor=" + getID_CONDUCTOR();
        PreparedStatement ps = con.statamet(consulta);
        ps.setBoolean(1, isEstandar());
        ps.setBoolean(2, isTogo());
        ps.setBoolean(3, isMaravilla());
        ps.setBoolean(4, isSsuper());
        int ex = ps.executeUpdate();
        if (ex <= 0) {
            Insertar(getID_CONDUCTOR());
        }
        ps.close();
        return "exito";
    }

    public String update_parametros() throws SQLException {
        String consulta = "UPDATE public.tcarrera_activo\n"
                + "	SET act_estandar=?, act_togo=?, act_supe=?, act_maravilla=?\n"
                + "	WHERE id_conductor=" + getID_CONDUCTOR();
        PreparedStatement ps = con.statamet(consulta);
        ps.setBoolean(1, isAct_estandar());
        ps.setBoolean(2, isAct_togo());
        ps.setBoolean(3, isAct_super());
        ps.setBoolean(4, isAct_maravilla());
        int ex = ps.executeUpdate();
        ps.close();
        return "exito";
    }

    public JSONObject getTcActivo(int id) throws SQLException, JSONException {
        String consulta = "select * from tcarrera_activo tc\n"
                + "where tc.id_conductor=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("exito", true);
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("estandar", rs.getBoolean("estandar"));
            obj.put("togo", rs.getBoolean("togo"));
            obj.put("maravilla", rs.getBoolean("maravilla"));
            obj.put("super", rs.getBoolean("super"));
            obj.put("act_estandar", rs.getBoolean("act_estandar"));
            obj.put("act_togo", rs.getBoolean("act_togo"));
            obj.put("act_maravilla", rs.getBoolean("act_maravilla"));
            obj.put("act_supe", rs.getBoolean("act_supe"));
        } else {
            obj.put("exito", false);
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getTcActivo_parametros(int id) throws SQLException, JSONException {
        String consulta = "select * from tcarrera_activo tc\n"
                + "where tc.id_conductor=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("exito", true);
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("act_estandar", rs.getBoolean("act_estandar"));
            obj.put("act_togo", rs.getBoolean("act_togo"));
            obj.put("act_maravilla", rs.getBoolean("act_maravilla"));
            obj.put("act_super", rs.getBoolean("act_supe"));
        } else {
            obj.put("exito", false);
        }
        ps.close();
        rs.close();
        return obj;
    }

    public int getID_CONDUCTOR() {
        return ID_CONDUCTOR;
    }

    public void setID_CONDUCTOR(int ID_CONDUCTOR) {
        this.ID_CONDUCTOR = ID_CONDUCTOR;
    }

    public boolean isEstandar() {
        return estandar;
    }

    public void setEstandar(boolean estandar) {
        this.estandar = estandar;
    }

    public boolean isTogo() {
        return togo;
    }

    public void setTogo(boolean togo) {
        this.togo = togo;
    }

    public boolean isMaravilla() {
        return maravilla;
    }

    public void setMaravilla(boolean maravilla) {
        this.maravilla = maravilla;
    }

    public boolean isSsuper() {
        return ssuper;
    }

    public void setSsuper(boolean ssuper) {
        this.ssuper = ssuper;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public boolean isAct_estandar() {
        return act_estandar;
    }

    public void setAct_estandar(boolean act_estandar) {
        this.act_estandar = act_estandar;
    }

    public boolean isAct_togo() {
        return act_togo;
    }

    public void setAct_togo(boolean act_togo) {
        this.act_togo = act_togo;
    }

    public boolean isAct_maravilla() {
        return act_maravilla;
    }

    public void setAct_maravilla(boolean act_maravilla) {
        this.act_maravilla = act_maravilla;
    }

    public boolean isAct_super() {
        return act_super;
    }

    public void setAct_super(boolean act_super) {
        this.act_super = act_super;
    }

}
