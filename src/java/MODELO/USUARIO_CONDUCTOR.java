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

public class USUARIO_CONDUCTOR {

    private int ID;
    private int ID_CONDUCTOR_REFE;
    private String NOMBRE;
    private String APELLIDO_PA;
    private String APELLIDO_MA;
    private String USUARIO;
    private String CONTRASENHA;
    private String SEXO;
    private String CORREO;
    private String CI;
    private String TELEFONO;
    private String REFERENCIA;
    private String CIUDAD;
    private String NUMERO_LICENCIA;
    private String CATEGORIA;
    private Date FECHA_NAC;

    private int ID_ROL;

    private Conexion con = null;

    public USUARIO_CONDUCTOR(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.usuario(\n"
                + "	nombre, apellido_pa, apellido_ma, usuario, contrasenha, id_rol, correo, sexo, fecha_nac,ci, telefono, referecia, ciudad, numero_licencia, categoria_licencia, id_conductor_refe)\n"
                + "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getNOMBRE());
        ps.setString(2, getAPELLIDO_PA());
        ps.setString(3, getAPELLIDO_MA());
        ps.setString(4, getUSUARIO());
        ps.setString(5, getCONTRASENHA());
        ps.setInt(6, getID_ROL());
        ps.setString(7, getCORREO());
        ps.setString(8, getSEXO());
        ps.setDate(9, new java.sql.Date(getFECHA_NAC().getTime()));
        ps.setString(10, getCI());
        ps.setString(11, getTELEFONO());
        ps.setString(12, getREFERENCIA());
        ps.setString(13, getCIUDAD());
        ps.setString(14, getNUMERO_LICENCIA());
        ps.setString(15, getCATEGORIA());
        ps.setInt(16, getID_CONDUCTOR_REFE());
        ps.execute();
        consulta = "select last_value from usuario_id_seq ";
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

    public int updatePerfilConductor() throws SQLException {
        String consulta = "UPDATE public.usuario\n"
                + "	SET nombre=?, apellido_pa=?, apellido_ma=?, correo=?, sexo=?, fecha_nac=?,  ci=?, telefono=?, referecia=?, ciudad=?, numero_licencia=?, categoria_licencia=?\n"
                + "	WHERE id=" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getNOMBRE());
        ps.setString(2, getAPELLIDO_PA());
        ps.setString(3, getAPELLIDO_MA());
        ps.setString(4, getCORREO());
        ps.setString(5, getSEXO());
        ps.setDate(6, new java.sql.Date(getFECHA_NAC().getTime()));
        ps.setString(7, getCI());
        ps.setString(8, getTELEFONO());
        ps.setString(9, getREFERENCIA());
        ps.setString(10, getCIUDAD());
        ps.setString(11, getNUMERO_LICENCIA());
        ps.setString(12, getCATEGORIA());
        int row = ps.executeUpdate();
        ps.close();
        return row;
    }

    public JSONObject get_por_usr_y_pass(String usr, String pass) throws SQLException, JSONException {
        String consulta = "select us.id, us.foto_perfil, us.nombre,us.apellido_pa,us.apellido_ma,us.usuario,us.correo,us.sexo,us.fecha_nac,us.id_rol, ro.nombre as nombrerol \n"
                + "from usuario us, rol ro\n"
                + "where us.usuario=? and us.contrasenha=? \n"
                + "and us.id_rol=ro.id";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
            obj.put("apellido_pa", rs.getString("apellido_pa") != null ? rs.getString("apellido_pa") : "");
            obj.put("apellido_ma", rs.getString("apellido_ma") != null ? rs.getString("apellido_ma") : "");
            obj.put("usuario", rs.getString("usuario"));
            obj.put("id_rol", rs.getInt("id_rol"));
            obj.put("correo", rs.getString("correo") != null ? rs.getString("correo") : "");
            obj.put("sexo", rs.getString("sexo") != null ? rs.getString("sexo") : "");
            obj.put("fecha_nac", rs.getString("fecha_nac") != null ? rs.getString("fecha_nac") : "");
            obj.put("nombrerol", rs.getString("nombrerol"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("exito", "si");
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getConductor_por_usr_y_pass(String usr, String pass) throws SQLException, JSONException {
        String consulta = "select us.id, us.foto_perfil, us.nombre,us.apellido_pa,us.apellido_ma,us.usuario,us.correo,us.sexo,us.fecha_nac,us.id_rol, ro.nombre as nombrerol \n"
                + "from usuario us, rol ro\n"
                + "where us.usuario=? and us.contrasenha=? \n"
                + "and ro.id=2 and us.id_rol=ro.id";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
            obj.put("apellido_pa", rs.getString("apellido_pa") != null ? rs.getString("apellido_pa") : "");
            obj.put("apellido_ma", rs.getString("apellido_ma") != null ? rs.getString("apellido_ma") : "");
            obj.put("usuario", rs.getString("usuario"));
            obj.put("id_rol", rs.getInt("id_rol"));
            obj.put("correo", rs.getString("correo") != null ? rs.getString("correo") : "");
            obj.put("sexo", rs.getString("sexo") != null ? rs.getString("sexo") : "");
            obj.put("fecha_nac", rs.getString("fecha_nac") != null ? rs.getString("fecha_nac") : "");
            obj.put("nombrerol", rs.getString("nombrerol"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("exito", "si");
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray getConductoresPorCiOnomre(String busqueda) throws SQLException, JSONException {
        String consulta = "select usr.* \n"
                + "from \n"
                + "	usuario usr, \n"
                + "	vehiculo_de_usuario vdu ,\n"
                + "	vehiculo ve\n"
                + "where\n"
                + "	usr.id=vdu.id_conductor \n"
                + "	and vdu.id_vehiculo=ve.id \n"
                + "	and ve.tipo=0\n"
                + "	and usr.id_rol=2\n"
                + "	and \n"
                + "		(\n" 
               + "			upper(usr.nombre) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.apellido_pa) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.apellido_ma) like upper('%"+busqueda+"%')		\n"
                + "			OR upper(usr.ci) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.telefono) like upper('%"+busqueda+"%')\n"
                + "			OR upper(ve.placa) like upper('%"+busqueda+"%')\n"
                + "		)\n"
                + "UNION\n"
                + "select * \n"
                + "from \n"
                + "	usuario usr\n"
                + "where\n"
                + "	 usr.id_rol=2\n"
                + "	 and\n"
                + "		(\n"
                + "			upper(usr.nombre) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.apellido_pa) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.apellido_ma) like upper('%"+busqueda+"%')		\n"
                + "			OR upper(usr.ci) like upper('%"+busqueda+"%')\n"
                + "			OR upper(usr.telefono) like upper('%"+busqueda+"%')\n"
                + "		)";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
            obj.put("apellido_pa", rs.getString("apellido_pa") != null ? rs.getString("apellido_pa") : "");
            obj.put("apellido_ma", rs.getString("apellido_ma") != null ? rs.getString("apellido_ma") : "");
            obj.put("usuario", rs.getString("usuario"));
            obj.put("id_rol", rs.getInt("id_rol"));
            obj.put("correo", rs.getString("correo") != null ? rs.getString("correo") : "");
            obj.put("sexo", rs.getString("sexo") != null ? rs.getString("sexo") : "");
            obj.put("fecha_nac", rs.getString("fecha_nac") != null ? rs.getString("fecha_nac") : "");
            obj.put("ci", rs.getString("ci"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("creditos", rs.getString("creditos"));
            obj.put("exito", "si");
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONObject getConductorId(int id) throws SQLException, JSONException {
        String consulta = "select * from usuario where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj_parametros = new JSONObject();
        TCARRERA_ACTIVO tcarrera_activo = new TCARRERA_ACTIVO(con);
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
            obj.put("apellido_pa", rs.getString("apellido_pa") != null ? rs.getString("apellido_pa") : "");
            obj.put("apellido_ma", rs.getString("apellido_ma") != null ? rs.getString("apellido_ma") : "");
            obj.put("usuario", rs.getString("usuario"));
            obj.put("id_rol", rs.getInt("id_rol"));
            obj.put("correo", rs.getString("correo") != null ? rs.getString("correo") : "");
            obj.put("sexo", rs.getString("sexo") != null ? rs.getString("sexo") : "");
            obj.put("fecha_nac", rs.getString("fecha_nac") != null ? rs.getString("fecha_nac") : "");
            obj.put("ci", rs.getString("ci"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("referencia", rs.getString("referecia"));
            obj.put("ciudad", rs.getString("ciudad"));
            obj.put("numero_licencia", rs.getString("numero_licencia"));
            obj.put("categoria_licencia", rs.getString("categoria_licencia"));
            obj_parametros = tcarrera_activo.getTcActivo_parametros(id);
            obj.put("parametros", obj_parametros);
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("exito", "si");

        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_datos_con_carrera(int id_carrera) throws SQLException, JSONException {
        String consulta = "select us.id, us.foto_perfil, us.nombre,\n"
                + "us.apellido_pa, \n"
                + "us.apellido_ma, \n"
                + "us.telefono, \n"
                + "ve.marca, \n"
                + "ve.modelo, \n"
                + "ve.color, \n"
                + "ve.placa, \n"
                + "(\n"
                + "	select count(car.id) as cant\n"
                + " 	from turnos tur , carrera car\n"
                + "	where tur.id_conductor=us.id and car.id_turno=tur.id\n"
                + ") as cant_car,\n"
                + "(\n"
                + "	select sum(pc.star)/count(pc.id) as cant\n"
                + " 	from turnos tur , carrera car, puntuacion_carrera pc\n"
                + "	where tur.id_conductor=us.id and car.id_turno=tur.id and pc.id_carrera=car.id\n"
                + ") as promedio_cali\n"
                + "from carrera ca, turnos tu, usuario us, vehiculo ve\n"
                + "where ca.id=" + id_carrera + " AND ca.id_turno=tu.id AND tu.id_conductor=us.id and tu.id_vehiculo= ve.id ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getString("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("apellido_pa", rs.getString("apellido_pa"));
            obj.put("apellido_ma", rs.getString("apellido_ma"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("foto_perfil", rs.getString("foto_perfil")!= null ? rs.getString("foto_perfil") : "");
            obj.put("marca", rs.getString("marca"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("color", rs.getString("color"));
            obj.put("placa", rs.getString("placa"));
            obj.put("cant_car", rs.getInt("cant_car"));
            obj.put("promedio_cali", rs.getDouble("promedio_cali"));
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

    public String getNOMBRE() {
        return NOMBRE != null ? NOMBRE : "";
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAPELLIDO_PA() {
        return APELLIDO_PA != null ? APELLIDO_PA : "";
    }

    public void setAPELLIDO_PA(String APELLIDO_PA) {
        this.APELLIDO_PA = APELLIDO_PA;
    }

    public String getAPELLIDO_MA() {
        return APELLIDO_MA != null ? APELLIDO_MA : "";
    }

    public void setAPELLIDO_MA(String APELLIDO_MA) {
        this.APELLIDO_MA = APELLIDO_MA;
    }

    public String getUSUARIO() {
        return USUARIO != null ? USUARIO : "";
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getCONTRASENHA() {
        return CONTRASENHA;
    }

    public void setCONTRASENHA(String CONTRASENHA) {
        this.CONTRASENHA = CONTRASENHA;
    }

    public int getID_ROL() {
        return ID_ROL;
    }

    public void setID_ROL(int ID_ROL) {
        this.ID_ROL = ID_ROL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getSEXO() {
        return SEXO != null ? SEXO : "";
    }

    public void setSEXO(String SEXO) {
        this.SEXO = SEXO;
    }

    public String getCORREO() {
        return CORREO != null ? CORREO : "";
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public Date getFECHA_NAC() {
        return FECHA_NAC;
    }

    public void setFECHA_NAC(Date FECHA_NAC) {
        this.FECHA_NAC = FECHA_NAC;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getREFERENCIA() {
        return REFERENCIA;
    }

    public void setREFERENCIA(String REFERENCIA) {
        this.REFERENCIA = REFERENCIA;
    }

    public String getCIUDAD() {
        return CIUDAD;
    }

    public void setCIUDAD(String CIUDAD) {
        this.CIUDAD = CIUDAD;
    }

    public String getNUMERO_LICENCIA() {
        return NUMERO_LICENCIA;
    }

    public void setNUMERO_LICENCIA(String NUMERO_LICENCIA) {
        this.NUMERO_LICENCIA = NUMERO_LICENCIA;
    }

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public int getID_CONDUCTOR_REFE() {
        return ID_CONDUCTOR_REFE;
    }

    public void setID_CONDUCTOR_REFE(int ID_CONDUCTOR_REFE) {
        this.ID_CONDUCTOR_REFE = ID_CONDUCTOR_REFE;
    }

}
