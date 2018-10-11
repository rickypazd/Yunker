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

public class CARRERA {

    private int ID;
    private double LATINICIAL;
    private double LNGINICIAL;
    private double LATFINAL;
    private double LNGFINAL;
    private int ID_USUARIO;
    private int ID_TURNO;
    private int ID_TIPO;
    private int TIPO_PAGO;
    private int ESTADO;
    private Date FECHA_PEDIDO;
    private Date FECHA_CONFIRMACION;
    private Date FECHA_INICIO;
    private Date FECHA_FIN;
    private Date FECHA_LLEGADA;
    private double DISTANCIA;
    private int COSTO_FINAL;
    private double COMISION;
    private JSONArray PRODUCTOS;
    private Conexion con = null;

    public CARRERA(Conexion con) {
        this.con = con;
    }

    public int insertar_pedir_carrera() throws SQLException {
        String consulta = "INSERT INTO public.carrera(\n"
                + "latinicial, lnginicial, latfinal, lngfinal, id_usuario, id_tipo, estado, fecha_pedido, tipo_pago)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setDouble(1, getLATINICIAL());
        ps.setDouble(2, getLNGINICIAL());
        ps.setDouble(3, getLATFINAL());
        ps.setDouble(4, getLNGFINAL());
        ps.setInt(5, getID_USUARIO());
        ps.setInt(6, getID_TIPO());
        ps.setInt(7, 1);
        ps.setTimestamp(8, new Timestamp(getFECHA_PEDIDO().getTime()));
        ps.setInt(9, getTIPO_PAGO());
        ps.execute();

        consulta = "select last_value from carrera_id_seq ";
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

    public int confirmar_carrera() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET id_turno=?, estado=?, fecha_confirmacion=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        int id_turno = getID_TURNO();
        ps.setInt(1, id_turno);
        ps.setInt(2, 2);
        ps.setTimestamp(3, new Timestamp(new Date().getTime()));
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int cobrar() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, 7);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int getid_conductor(int id_carrera) throws SQLException {
        String consulta = "select tu.id_conductor from carrera ca, turnos tu\n"
                + "where ca.id_turno=tu.id and ca.id = " + id_carrera;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id_conductor");
        }
        rs.close();
        ps.close();
        return id;
    }

    public int insertarTotal(int costo) throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET costo_final=?, estado=?, distancia=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, costo);
        ps.setInt(2, 6);
        ps.setDouble(3, getDISTANCIA());
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int insertarComision(double comision) throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET comision=?"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        int id_turno = getID_TURNO();
        ps.setDouble(1, comision);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int update_llegada() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET fecha_llegada=?, estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setTimestamp(1, new Timestamp(getFECHA_LLEGADA().getTime()));
        ps.setInt(2, 3);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int iniciar_Carrera() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET fecha_inicio=?, estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setTimestamp(1, new Timestamp(getFECHA_INICIO().getTime()));
        ps.setInt(2, 4);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int confirmarCompra() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET fecha_inicio=?, estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setTimestamp(1, new Timestamp(getFECHA_INICIO().getTime()));
        ps.setInt(2, 3);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int terminar_carrera() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET fecha_fin=?, estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setTimestamp(1, new Timestamp(getFECHA_FIN().getTime()));
        ps.setInt(2, 5);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int cancelar_carrera() throws SQLException {
        String consulta = "UPDATE public.carrera\n"
                + "SET fecha_fin=?, estado=?\n"
                + "WHERE id =" + getID();
        PreparedStatement ps = con.statamet(consulta);
        ps.setTimestamp(1, new Timestamp(getFECHA_FIN().getTime()));
        ps.setInt(2, 7);
        int rows = ps.executeUpdate();
        ps.close();
        return rows;
    }

    public int getConfirmoCarrera(int id) throws SQLException, JSONException {
        String consulta = "select count(id) num from carrera\n"
                + "where id=" + id + " and id_turno is null";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        int num = 0;
        if (rs.next()) {
            num = rs.getInt("num");
        }
        ps.close();
        rs.close();
        return num;
    }

    public JSONObject getUsuarioCarrera(int id) throws SQLException, JSONException {
        String consulta = "select id_usuario from carrera where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj.put("id_usuario", rs.getInt("id_usuario"));           
        }
        ps.close();
        rs.close();
        return obj;
    }
    public JSONObject getCarreraId(int id) throws SQLException, JSONException {
        String consulta = "select * from carrera where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        DETALLE_COSTO_CARRERA deta = new DETALLE_COSTO_CARRERA(con);
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
            obj.put("distancia", rs.getDouble("distancia") != 0 ? rs.getDouble("distancia") : "0");
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("detalle_costo", deta.get_costo_x_id_tipo(rs.getInt("id")));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getCarreraId_togo(int id) throws SQLException, JSONException {
        String consulta = "select * from carrera where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        PRODUCTO productos = new PRODUCTO(con);
        DETALLE_COSTO_CARRERA deta = new DETALLE_COSTO_CARRERA(con);
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
            obj.put("distancia", rs.getDouble("distancia") != 0 ? rs.getDouble("distancia") : "0");
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("detalle_costo", deta.get_costo_x_id_tipo(rs.getInt("id")));
            obj.put("productos", productos.get_productos_id_carrera(rs.getInt("id")));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_detalle_carrera(int id) throws SQLException, JSONException {
        String consulta = "select firs.*, \n"
                + "                case when pos.lat is null then firs.latinicial else pos.lat end as latfinalreal , \n"
                + "                case when pos.lon is null then firs.lnginicial else pos.lon end as lngfinalreal \n"
                + "                from\n"
                + "                (select \n"
                + "					ca.id as id_carrera,\n"
                + "					ca.fecha_pedido, \n"
                + "					ca.costo_final, \n"
                + "					ca.estado, \n"
                + "					ca.tipo_pago,\n"
                + "					ca.latinicial, \n"
                + "					ca.lnginicial,\n"
                + "					ca.latfinal, \n"
                + "					ca.lngfinal,\n"
                + "					ve.id as id_vehiculo, \n"
                + "					ve.placa, \n"
                + "					ve.marca,\n"
                + "					ve.modelo, \n"
                + "					ca.fecha_inicio,\n"
                + "					ca.fecha_fin,\n"
                + "					usc.nombre,\n"
                + "					usc.apellido_pa,\n"
                + "					usc.apellido_ma,\n"
                + "					usc.telefono,\n"
                + "					usc.foto_perfil\n"
                + "                from carrera ca, turnos tu, vehiculo ve, usuario usc\n"
                + "                where ca.id=" + id + " and ca.id_turno=tu.id and tu.id_vehiculo=ve.id and usc.id=tu.id_conductor) firs \n"
                + "                left join lateral\n"
                + "                (\n"
                + "                	select pv.lat, pv.lon, pv.fecha from posicion_vehiculo pv\n"
                + "                                where pv.id_vehiculo=firs.id_vehiculo\n"
                + "                                and pv.fecha BETWEEN firs.fecha_inicio and firs.fecha_fin\n"
                + "                				order by fecha desc LIMIT 1\n"
                + "                ) pos on true\n"
                + "                order by firs.fecha_pedido desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        DETALLE_COSTO_CARRERA deta = new DETALLE_COSTO_CARRERA(con);
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id_carrera", rs.getInt("id_carrera"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("latfinalreal", rs.getDouble("latfinalreal"));
            obj.put("lngfinalreal", rs.getDouble("lngfinalreal"));
            obj.put("placa", rs.getString("placa"));
            obj.put("marca", rs.getString("marca"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("apellido_pa", rs.getString("apellido_pa"));
            obj.put("apellido_ma", rs.getString("apellido_ma"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("detalle_costo", deta.get_costo_x_id_tipo(rs.getInt("id_carrera")));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject get_detalle_carrera_conductor(int id) throws SQLException, JSONException {
        String consulta = "select firs.*, \n"
                + "                case when pos.lat is null then firs.latinicial else pos.lat end as latfinalreal , \n"
                + "                case when pos.lon is null then firs.lnginicial else pos.lon end as lngfinalreal \n"
                + "                from\n"
                + "                (select \n"
                + "					ca.id as id_carrera,\n"
                + "					ca.fecha_pedido, \n"
                + "					ca.costo_final, \n"
                + "					ca.estado, \n"
                + "					ca.tipo_pago,\n"
                + "					ca.latinicial, \n"
                + "					ca.lnginicial,\n"
                + "					ca.latfinal, \n"
                + "					ca.lngfinal,\n"
                + "					ve.id as id_vehiculo, \n"
                + "					ve.placa, \n"
                + "					ve.marca,\n"
                + "					ve.modelo, \n"
                + "					ca.fecha_inicio,\n"
                + "					ca.fecha_fin,\n"
                + "					usc.nombre,\n"
                + "					usc.apellido_pa,\n"
                + "					usc.apellido_ma,\n"
                + "					usc.telefono,\n"
                + "					usc.foto_perfil\n"
                + "                from carrera ca, turnos tu, vehiculo ve, usuario usc\n"
                + "                where ca.id=" + id + " and ca.id_turno=tu.id and tu.id_vehiculo=ve.id and usc.id=ca.id_usuario) firs \n"
                + "                left join lateral\n"
                + "                (\n"
                + "                	select pv.lat, pv.lon, pv.fecha from posicion_vehiculo pv\n"
                + "                                where pv.id_vehiculo=firs.id_vehiculo\n"
                + "                                and pv.fecha BETWEEN firs.fecha_inicio and firs.fecha_fin\n"
                + "                				order by fecha desc LIMIT 1\n"
                + "                ) pos on true\n"
                + "                order by firs.fecha_pedido desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        DETALLE_COSTO_CARRERA deta = new DETALLE_COSTO_CARRERA(con);
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id_carrera", rs.getInt("id_carrera"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("latfinalreal", rs.getDouble("latfinalreal"));
            obj.put("lngfinalreal", rs.getDouble("lngfinalreal"));
            obj.put("placa", rs.getString("placa"));
            obj.put("marca", rs.getString("marca"));
            obj.put("modelo", rs.getString("modelo"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("apellido_pa", rs.getString("apellido_pa"));
            obj.put("apellido_ma", rs.getString("apellido_ma"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("foto_perfil", rs.getString("foto_perfil"));
            obj.put("detalle_costo", deta.get_costo_x_id_tipo(rs.getInt("id_carrera")));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray get_historial_ubic(int id) throws SQLException, JSONException {
        String consulta = "select latfinal,lngfinal from carrera \n"
                + "where id_usuario="+id+"\n"
                + "and estado=7\n"
                + "order by fecha_pedido desc\n"
                + "LIMIT 10";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray get_mis_viajes(int id) throws SQLException, JSONException {
        String consulta = "select firs.*, \n"
                + "case when pos.lat is null then firs.latinicial else pos.lat end as latfinalreal , \n"
                + "case when pos.lon is null then firs.lnginicial else pos.lon end as lngfinalreal \n"
                + "from\n"
                + "(select ca.id as id_carrera, ca.fecha_pedido, ca.tipo_pago ,ca.costo_final, ca.estado, ca.latinicial, ca.lnginicial,ca.latfinal, ca.lngfinal, ve.id as id_vehiculo, ve.placa, ve.marca, ve.modelo, ca.fecha_inicio, ca.fecha_fin\n"
                + "from carrera ca, turnos tu, vehiculo ve\n"
                + "where ca.id_usuario=" + id + " and ca.estado>6 and ca.id_turno=tu.id and tu.id_vehiculo=ve.id) firs \n"
                + "left join lateral\n"
                + "(\n"
                + "	select pv.lat, pv.lon, pv.fecha from posicion_vehiculo pv\n"
                + "                where pv.id_vehiculo=firs.id_vehiculo\n"
                + "                and pv.fecha BETWEEN firs.fecha_inicio and firs.fecha_fin\n"
                + "				order by fecha desc LIMIT 1\n"
                + ") pos on true\n"
                + "order by firs.fecha_pedido desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_carrera", rs.getInt("id_carrera"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("latfinalreal", rs.getDouble("latfinalreal"));
            obj.put("lngfinalreal", rs.getDouble("lngfinalreal"));
            obj.put("placa", rs.getString("placa"));
            obj.put("marca", rs.getString("marca"));
            obj.put("modelo", rs.getString("modelo"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray get_mis_viajes_conductor(int id) throws SQLException, JSONException {
        String consulta = "select firs.*, \n"
                + "case when pos.lat is null then firs.latinicial else pos.lat end as latfinalreal , \n"
                + "case when pos.lon is null then firs.lnginicial else pos.lon end as lngfinalreal \n"
                + "from\n"
                + "(select ca.id as id_carrera, ca.fecha_pedido, ca.tipo_pago ,ca.costo_final, ca.estado, ca.latinicial, ca.lnginicial,ca.latfinal, ca.lngfinal, ve.id as id_vehiculo, ve.placa, ve.marca, ve.modelo, ca.fecha_inicio, ca.fecha_fin\n"
                + "from carrera ca, turnos tu, vehiculo ve\n"
                + "where tu.id_conductor=" + id + " and ca.estado>6 and ca.id_turno=tu.id and tu.id_vehiculo=ve.id) firs \n"
                + "left join lateral\n"
                + "(\n"
                + "	select pv.lat, pv.lon, pv.fecha from posicion_vehiculo pv\n"
                + "                where pv.id_vehiculo=firs.id_vehiculo\n"
                + "                and pv.fecha BETWEEN firs.fecha_inicio and firs.fecha_fin\n"
                + "				order by fecha desc LIMIT 1\n"
                + ") pos on true\n"
                + "order by firs.fecha_pedido desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_carrera", rs.getInt("id_carrera"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("latfinalreal", rs.getDouble("latfinalreal"));
            obj.put("lngfinalreal", rs.getDouble("lngfinalreal"));
            obj.put("placa", rs.getString("placa"));
            obj.put("marca", rs.getString("marca"));
            obj.put("modelo", rs.getString("modelo"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONObject get_mis_viajes_fecha(int id, String fecha) throws SQLException, JSONException {
        String consulta = "select firs.*, \n"
                + "                case when pos.lat is null then firs.latinicial else pos.lat end as latfinalreal , \n"
                + "                case when pos.lon is null then firs.lnginicial else pos.lon end as lngfinalreal \n"
                + "                from\n"
                + "                (select ca.id as id_carrera, ca.fecha_pedido, ca.tipo_pago ,ca.costo_final, ca.estado, ca.latinicial, ca.lnginicial,ca.latfinal, ca.lngfinal, ve.id as id_vehiculo, ve.placa, ve.marca, ve.modelo, ca.fecha_inicio, ca.fecha_fin\n"
                + "                from carrera ca, turnos tu, vehiculo ve\n"
                + "                where ca.id_usuario=" + id + " and ca.estado>6 and ca.id_turno=tu.id and tu.id_vehiculo=ve.id) firs \n"
                + "                left join lateral\n"
                + "                (\n"
                + "                	select pv.lat, pv.lon, pv.fecha from posicion_vehiculo pv\n"
                + "                                where pv.id_vehiculo=firs.id_vehiculo\n"
                + "                                and pv.fecha BETWEEN firs.fecha_inicio and firs.fecha_fin\n"
                + "                				order by fecha desc LIMIT 1\n"
                + "                ) pos on true\n"
                + "				where firs.fecha_pedido > '" + fecha + "'\n"
                + "                order by firs.fecha_pedido desc";
        PreparedStatement ps = con.getConnection().prepareStatement(consulta, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject objresp = new JSONObject();
        JSONObject obj;
        int row = getRowCount(rs);
        if (row <= 0) {
            objresp.put("tipo", "1");
        } else if (row <= 10) {
            objresp.put("tipo", "2");
            while (rs.next()) {
                obj = new JSONObject();
                obj.put("id_carrera", rs.getInt("id_carrera"));
                obj.put("fecha_pedido", rs.getString("fecha_pedido"));
                obj.put("costo_final", rs.getInt("costo_final"));
                obj.put("tipo_pago", rs.getInt("tipo_pago"));
                obj.put("estado", rs.getInt("estado"));
                obj.put("latinicial", rs.getDouble("latinicial"));
                obj.put("lnginicial", rs.getDouble("lnginicial"));
                obj.put("latfinal", rs.getDouble("latfinal"));
                obj.put("lngfinal", rs.getDouble("lngfinal"));
                obj.put("latfinalreal", rs.getDouble("latfinalreal"));
                obj.put("lngfinalreal", rs.getDouble("lngfinalreal"));
                obj.put("placa", rs.getString("placa"));
                obj.put("marca", rs.getString("marca"));
                obj.put("modelo", rs.getString("modelo"));
                arr.put(obj);
            }
            objresp.put("arr", arr);
        } else {
            objresp.put("tipo", "3");
        }

        ps.close();
        rs.close();
        return objresp;
    }

    public JSONObject getCarreraIdrecorrido(int id) throws SQLException, JSONException {
        String consulta = "select * from carrera where id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        DETALLE_COSTO_CARRERA deta = new DETALLE_COSTO_CARRERA(con);
        POSICION_VEHICULO vei = new POSICION_VEHICULO(con);

        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
            obj.put("distancia", rs.getDouble("distancia") != 0 ? rs.getDouble("distancia") : "0");
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("detalle_costo", deta.get_costo_x_id_tipo(rs.getInt("id")));
            obj.put("recorrido", vei.get_pos_id_Carrera(rs.getInt("id")));

        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getCarreraConductor(int id_usr) throws SQLException, JSONException {
        String consulta = "select * from carrera ca, turnos tu where ca.id_turno=tu.id and tu.id_conductor=" + id_usr + " and ca.estado < 7";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("distancia", rs.getDouble("distancia"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("exito", "true");
        } else {
            obj.put("exito", "false");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getCarreraDatosCon(int id) throws SQLException, JSONException {
        String consulta = "select ca.*, us.nombre, us.apellido_pa, us.apellido_ma, us.telefono, vehi.placa from carrera ca, turnos tu, usuario us, vehiculo vehi\n"
                + "where ca.id_turno = tu.id and tu.id_conductor=us.id and tu.id_vehiculo=vehi.id and ca.id=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("tipo_pago", rs.getInt("tipo_pago"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
            obj.put("distancia", rs.getDouble("distancia"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("apellido_pa", rs.getString("apellido_pa"));
            obj.put("apellido_ma", rs.getString("apellido_ma"));
            obj.put("telefono", rs.getString("telefono"));
            obj.put("placa", rs.getString("placa"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONObject getCarreraCliente(int id_usr) throws SQLException, JSONException {
        String consulta = "select * from carrera ca where ca.id_usuario=" + id_usr + " and ca.estado < 6 and ca.estado > 1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        TURNOS tur = new TURNOS(con);
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("latinicial", rs.getDouble("latinicial"));
            obj.put("lnginicial", rs.getDouble("lnginicial"));
            obj.put("latfinal", rs.getDouble("latfinal"));
            obj.put("lngfinal", rs.getDouble("lngfinal"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_turno", rs.getInt("id_turno"));
            obj.put("turno", tur.getTurno(rs.getInt("id_turno")));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("fecha_pedido", rs.getString("fecha_pedido"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("distancia", rs.getDouble("distancia"));
            obj.put("costo_final", rs.getInt("costo_final"));
            obj.put("comision", rs.getDouble("comision"));
            obj.put("exito", "true");
        } else {
            obj.put("exito", "false");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray getConductoresActivos() throws SQLException, JSONException {
        String consulta = "﻿select tu.* from turno tu left join (\n"
                + "select tu.* from turnos tu, carrera ca \n"
                + "where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "on tu.id=tur.id \n"
                + "where tur.id is null\n"
                + "and tu.estado =1";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getConductoresActivos_ESTANDAR() throws SQLException, JSONException {
        String consulta = "select tu.* from tcarrera_activo tc, (select tu.* from turnos tu left join (\n"
                + "select tu.* from turnos tu, carrera ca \n"
                + "where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "on tu.id=tur.id \n"
                + "where tur.id is null\n"
                + "and tu.estado=1 and tu.tipo=0) tu\n"
                + "where tc.id_conductor=tu.id_conductor\n"
                + "and tc.estandar = true";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getConductoresActivos_TOGO() throws SQLException, JSONException {
        String consulta = "select tu.* from tcarrera_activo tc, (select tu.* from turnos tu left join (\n"
                + "select tu.* from turnos tu, carrera ca \n"
                + "where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "on tu.id=tur.id \n"
                + "where tur.id is null\n"
                + "and tu.estado=1 and tu.tipo=0) tu\n"
                + "where tc.id_conductor=tu.id_conductor\n"
                + "and tc.togo = true";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getConductoresActivos_MARAVILLA() throws SQLException, JSONException {
        String consulta = "select tu.* from tcarrera_activo tc, (select tu.* from turnos tu left join (\n"
                + "select tu.* from turnos tu, carrera ca \n"
                + "where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "on tu.id=tur.id \n"
                + "where tur.id is null\n"
                + "and tu.estado=1 and tu.tipo=0) tu\n"
                + "where tc.id_conductor=tu.id_conductor\n"
                + "and tc.maravilla = true";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getConductoresActivos_SUPER() throws SQLException, JSONException {
        String consulta = "select tu.* from tcarrera_activo tc, (select tu.* from turnos tu left join (\n"
                + "select tu.* from turnos tu, carrera ca \n"
                + "where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "on tu.id=tur.id \n"
                + "where tur.id is null\n"
                + "and tu.estado=1) tu\n"
                + "where tc.id_conductor=tu.id_conductor\n"
                + "and tc.super = true";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray getConductoresActivos_carrera(int idCat) throws SQLException, JSONException {
        String consulta = "select tu.* from tcarrera_activo tc, (select tu.* from turnos tu left join (\n"
                + "                select tu.* from turnos tu, carrera ca\n"
                + "                where tu.id=ca.id_turno and tu.estado =1 and ca.estado <6) tur\n"
                + "                on tu.id=tur.id \n"
                + "                where tur.id is null\n"
                + "                and tu.estado =1 and tu.tipo=0) tu, vehiculo_to_categoria vc\n"
                + "                where tc.id_conductor=tu.id_conductor\n"
                + "                and tc.estandar = true\n"
                + "				and vc.id_vehiculo=tu.id_vehiculo\n"
                + "				and vc.id_categoria =" + idCat;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        VEHICULO vehiculo = new VEHICULO(con);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = new JSONArray();
        JSONArray token;
        JSONObject obj;
        JSONObject obj_vehiculo;
        while (rs.next()) {
            obj = new JSONObject();
            obj_vehiculo = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_conductor", rs.getInt("id_conductor"));
            obj.put("fecha_inicio", rs.getString("fecha_inicio"));
            obj.put("fecha_fin", rs.getString("fecha_fin"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj_vehiculo = vehiculo.get_ve_ultima_pos(rs.getInt("id_vehiculo"));
            obj.put("vehiculo", obj_vehiculo);
            token = tok.get_Token_usr(rs.getInt("id_conductor"));
            obj.put("tokens", token);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONObject getCliente_por_id(int id) throws SQLException, JSONException {
        String consulta = "select us.id, us.nombre,us.apellido_pa,us.apellido_ma,us.usuario,us.correo,us.sexo,us.fecha_nac,us.id_rol, ro.nombre as nombrerol \n"
                + "from usuario us, rol ro\n"
                + "where us.id=? \n"
                + "and ro.id=4 and us.id_rol=ro.id";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);

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
            obj.put("exito", "si");
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public double getTiempoCarreraMinutos(int id_carrera) throws SQLException, JSONException {
        String consulta = "SELECT EXTRACT('epoch' FROM ca.fecha_fin - ca.fecha_inicio) / 60 AS minutes\n"
                + "FROM carrera ca\n"
                + "where ca.id=?;";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id_carrera);

        ResultSet rs = ps.executeQuery();
        double minutes = 0;
        if (rs.next()) {
            minutes = rs.getDouble("minutes");
        }
        ps.close();
        rs.close();
        return minutes;
    }

    public JSONObject toJSOnobject() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("id", getID());
        obj.put("latinicial", getLATINICIAL());
        obj.put("lnginicial", getLNGINICIAL());
        obj.put("latfinal", getLATFINAL());
        obj.put("lngfinal", getLNGFINAL());
        obj.put("id_usuario", getID_USUARIO());
        obj.put("id_turno", getID_TURNO());
        obj.put("id_tipo", getID_TIPO());
        obj.put("tipo", get_tipo(getID_TIPO()));
        obj.put("tipo_pago", getTIPO_PAGO());
        if (PRODUCTOS != null) {
            obj.put("productos", getPRODUCTOS());
        }
        return obj;

    }

    public String get_tipo(int tipo) {
        switch (tipo) {
            case 1:
                return "Siete Estandar";
            case 2:
                return "Siete ToGo";
            case 3:
                return "Siete Maravilla";
            case 4:
                return "Super Siete";
            case 5:
                return "Siete 4x4";
            case 6:
                return "Siete Camioneta";
            case 7:
                return "Siete 6 Pasajeros";
        }
        return "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getLATINICIAL() {
        return LATINICIAL;
    }

    public void setLATINICIAL(double LATINICIAL) {
        this.LATINICIAL = LATINICIAL;
    }

    public double getLNGINICIAL() {
        return LNGINICIAL;
    }

    public void setLNGINICIAL(double LNGINICIAL) {
        this.LNGINICIAL = LNGINICIAL;
    }

    public double getLATFINAL() {
        return LATFINAL;
    }

    public void setLATFINAL(double LATFINAL) {
        this.LATFINAL = LATFINAL;
    }

    public double getLNGFINAL() {
        return LNGFINAL;
    }

    public void setLNGFINAL(double LNGFINAL) {
        this.LNGFINAL = LNGFINAL;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getID_TURNO() {
        return ID_TURNO;
    }

    public void setID_TURNO(int ID_TURNO) {
        this.ID_TURNO = ID_TURNO;
    }

    public int getID_TIPO() {
        return ID_TIPO;
    }

    public void setID_TIPO(int ID_TIPO) {
        this.ID_TIPO = ID_TIPO;
    }

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public Date getFECHA_PEDIDO() {
        return FECHA_PEDIDO;
    }

    public void setFECHA_PEDIDO(Date FECHA_PEDIDO) {
        this.FECHA_PEDIDO = FECHA_PEDIDO;
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

    public double getDISTANCIA() {
        return DISTANCIA;
    }

    public void setDISTANCIA(double DISTANCIA) {
        this.DISTANCIA = DISTANCIA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public Date getFECHA_LLEGADA() {
        return FECHA_LLEGADA;
    }

    public void setFECHA_LLEGADA(Date FECHA_LLEGADA) {
        this.FECHA_LLEGADA = FECHA_LLEGADA;
    }

    public int getCOSTO_FINAL() {
        return COSTO_FINAL;
    }

    public void setCOSTO_FINAL(int COSTO_FINAL) {
        this.COSTO_FINAL = COSTO_FINAL;
    }

    public double getCOMISION() {
        return COMISION;
    }

    public void setCOMISION(double COMISION) {
        this.COMISION = COMISION;
    }

    public Date getFECHA_CONFIRMACION() {
        return FECHA_CONFIRMACION;
    }

    public void setFECHA_CONFIRMACION(Date FECHA_CONFIRMACION) {
        this.FECHA_CONFIRMACION = FECHA_CONFIRMACION;
    }

    public void setID_TURNOS(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getTIPO_PAGO() {
        return TIPO_PAGO;
    }

    public void setTIPO_PAGO(int TIPO_PAGO) {
        this.TIPO_PAGO = TIPO_PAGO;
    }

    public JSONArray getPRODUCTOS() {
        return PRODUCTOS;
    }

    public void setPRODUCTOS(JSONArray PRODUCTOS) {
        this.PRODUCTOS = PRODUCTOS;
    }

    private int getRowCount(ResultSet resultSet) {
        if (resultSet == null) {
            return 0;
        }
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        return 0;
    }
}
