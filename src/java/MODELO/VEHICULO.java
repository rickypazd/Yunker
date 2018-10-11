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

public class VEHICULO {

    private int ID;
    private String PLACA;
    private String MARCA;
    private String MODELO;
    private String ANO;
    private String COLOR;
    private String MOTOR;
    private String CHASIS;
    private int ESTADO;
    private int NPUERTAS;
    private int TIPO;
    private String FOTO_PERFIL;

    private Conexion con = null;

    public VEHICULO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.vehiculo(\n"
                + "	placa, modelo, ano, color, estado, marca, n_puertas, chasis, motor, tipo)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getPLACA());
        ps.setString(2, getMODELO());
        ps.setString(3, getANO());
        ps.setString(4, getCOLOR());
        ps.setInt(5, getESTADO());
        ps.setString(6, getMARCA());
        ps.setInt(7, getNPUERTAS());
        ps.setString(8, getCHASIS());
        ps.setString(9, getMOTOR());
        ps.setInt(10, getTIPO());
        ps.execute();

        consulta = "select last_value from vehiculo_id_seq ";
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
    public boolean validar_placa(String placa) throws SQLException, JSONException {
        String consulta = "select vehiculo.id from vehiculo where vehiculo.placa=?";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
      boolean exist=false;
        if (rs.next()) {
            exist=true;
        } 
        ps.close();
        rs.close();
        return exist;
    }
    public int subir_foto_perfil() throws SQLException {
        String consulta = "UPDATE public.vehiculo\n"
                + "	SET foto_perfil=?\n"
                + "	WHERE id=" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getFOTO_PERFIL());
        int row = ps.executeUpdate();
        ps.close();
        return row;
    }

    public int update() throws SQLException {
        String consulta = "UPDATE public.vehiculo\n"
                + "	SET placa=?, modelo=?, ano=?, color=?, marca=?, chasis=?, n_puertas=?, motor=?\n"
                + "	WHERE id=" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getPLACA());
        ps.setString(2, getMODELO());
        ps.setString(3, getANO());
        ps.setString(4, getCOLOR());
        ps.setString(5, getMARCA());
        ps.setString(6, getCHASIS());
        ps.setInt(7, getNPUERTAS());
        ps.setString(8, getMOTOR());
        int row = ps.executeUpdate();
        ps.close();
        return row;
    }

    public JSONArray todos_vehiculos_activos_ultima_posicion() throws SQLException, JSONException {
        String consulta = "select * from vehiculo vei join (\n"
                + "select v.* from posicion_vehiculo v join (\n"
                + "	select pv.id_vehiculo, max(fecha) fecha\n"
                + "	from posicion_vehiculo pv \n"
                + "	 group by(pv.id_vehiculo)\n"
                + ")vp\n"
                + "on v.id_vehiculo=vp.id_vehiculo\n"
                + "where v.fecha= vp.fecha\n"
                + ") pos on vei.id=pos.id_vehiculo";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("lat", rs.getDouble("lat"));
            obj.put("lon", rs.getDouble("lon"));
            obj.put("fecha", rs.getTimestamp("fecha"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray buscar_por_placa(String placa) throws SQLException, JSONException {
        String consulta = "select * from public.vehiculo ve \n"
                + "where ve.tipo=0\n"
                + "and (upper(ve.placa) like upper('%" + placa + "%'))";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("tipo", rs.getInt("tipo"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));

            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONObject get_vehiculo_id(int id) throws SQLException, JSONException {
        String consulta = "select * from public.vehiculo ve where ve.id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        VEHICULO_TO_CATEGORIA vtc = new VEHICULO_TO_CATEGORIA(con);
        JSONArray arrcar;
        if (rs.next()) {
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("tipo", rs.getInt("tipo"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            arrcar = vtc.get_categorias_de_vehiculo(id);
            obj.put("categorias", arrcar);
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_no_vehiculo_conductor(int id) throws SQLException, JSONException {
        String consulta = "select * from vehiculo ve, vehiculo_de_usuario vdu \n"
                + "where vdu.id_conductor="+id+" and vdu.id_vehiculo=ve.id and ve.tipo=1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        VEHICULO_TO_CATEGORIA vtc = new VEHICULO_TO_CATEGORIA(con);
        JSONArray arrcar;
        if (rs.next()) {
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("tipo", rs.getInt("tipo"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            arrcar = vtc.get_categorias_de_vehiculo(id);
            obj.put("categorias", arrcar);
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray asignados_conductor(int id) throws SQLException, JSONException {
        String consulta = "select * from vehiculo ve, vehiculo_de_usuario vu\n"
                + "where ve.id=vu.id_vehiculo and ve.tipo=0 and vu.id_conductor=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));

            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray disponibles_conductor(int id) throws SQLException, JSONException {
        String consulta = "select ve.* from (select * from vehiculo ve, vehiculo_de_usuario vu\n"
                + "where ve.id=vu.id_vehiculo and vu.id_conductor=" + id + ") ve left join (\n"
                + "select * from turnos tu\n"
                + "where tu.estado=1 \n"
                + ") vt on vt.id_vehiculo = ve.id\n"
                + "where vt.id_vehiculo is null\n"
                + "and ve.tipo=0";
        //String consulta = "select * from vehiculo ve, vehiculo_de_usuario vu\n"
        //        + "where ve.id=vu.id_vehiculo and vu.id_conductor="+id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray sin_asignar_conductor(int id, String bus) throws SQLException, JSONException {
        String consulta = "select * from vehiculo v where v.tipo=0"
                + " and (upper(v.placa) like upper('%" + bus + "%'))";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONObject get_ve_ultima_pos(int id) throws SQLException, JSONException {
        String consulta = "select * from vehiculo v, posicion_vehiculo pos where v.id=pos.id_vehiculo\n"
                + "and v.id=" + id + " order by (pos.fecha) desc limit 1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();

        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id"));
            obj.put("placa", rs.getString("placa"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("ano", rs.getString("ano"));
            obj.put("color", rs.getString("color"));
            obj.put("marca", rs.getString("marca"));
            obj.put("chasis", rs.getString("chasis"));
            obj.put("motor", rs.getString("motor"));
            obj.put("n_puertas", rs.getInt("n_puertas"));
            obj.put("lat", rs.getDouble("lat"));
            obj.put("lng", rs.getDouble("lon"));
            obj.put("fecha", rs.getString("fecha"));

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

    public String getPLACA() {
        return PLACA;
    }

    public void setPLACA(String PLACA) {
        this.PLACA = PLACA;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getANO() {
        return ANO;
    }

    public void setANO(String ANO) {
        this.ANO = ANO;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public Conexion getCon() {
        return con;
    }

    public String getMOTOR() {
        return MOTOR;
    }

    public void setMOTOR(String MOTOR) {
        this.MOTOR = MOTOR;
    }

    public String getCHASIS() {
        return CHASIS;
    }

    public void setCHASIS(String CHASIS) {
        this.CHASIS = CHASIS;
    }

    public int getNPUERTAS() {
        return NPUERTAS;
    }

    public void setNPUERTAS(int NPUERTAS) {
        this.NPUERTAS = NPUERTAS;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getFOTO_PERFIL() {
        return FOTO_PERFIL;
    }

    public void setFOTO_PERFIL(String FOTO_PERFIL) {
        this.FOTO_PERFIL = FOTO_PERFIL;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

  

}
