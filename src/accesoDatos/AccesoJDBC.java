package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;


import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");
		
		try {
			HashMap<String,String> datosConexion;
			
			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();		
			
			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;
			
			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			} 

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;
		int clave;
		PreparedStatement pstmt;
		try {
			pstmt = conn1.prepareStatement("SELECT * FROM depositos");
			ResultSet rset = pstmt.executeQuery();
			
			
			while (rset.next()) {
				
				
				clave = rset.getInt("valor");
				auxDeposito = new Deposito(rset.getString("nombre"),rset.getInt("valor"), rset.getInt("cantidad"));
				depositosCreados.put(clave, auxDeposito);
				}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Terminados depositos");
		///////////////////////////
		
		
				
				
		
		

		return depositosCreados;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador dispensador;	
		PreparedStatement pstmt;
		try {
			pstmt = conn1.prepareStatement("SELECT * FROM dispensadores");
			ResultSet rset = pstmt.executeQuery();
			
			
			while (rset.next()) {
				
				clave = rset.getString("clave");
				dispensador = new Dispensador(clave,rset.getString("nombre"),rset.getInt("precio"), rset.getInt("cantidad"));
				dispensadoresCreado.put(clave, dispensador);
				}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
	
		
		try {
			PreparedStatement pstmt;
			for ( int key : depositos.keySet() ) {
				String query="UPDATE `depositos` SET `valor` = ?, `cantidad` = ? WHERE `depositos`.`valor` = '" +depositos.get(key).getValor()  + "'";
				pstmt = conn1.prepareStatement(query);
				pstmt.setInt(1, depositos.get(key).getValor());
				pstmt.setInt(2, depositos.get(key).getCantidad());
				pstmt.executeUpdate();
			}
			
		
			
					
			
			
			
		} catch (SQLException e) {
			
			todoOK=false;
		}

	
		
		
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(
			HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		try {
			PreparedStatement pstmt;
			
			for ( String key : dispensadores.keySet() ) {
				String query="UPDATE `dispensadores` SET `precio` = ?"
						+ ", `cantidad` = ? WHERE `dispensadores`.`clave` = '" + dispensadores.get(key).getClave() + "'";
				pstmt = conn1.prepareStatement(query);
				pstmt.setInt(1, dispensadores.get(key).getPrecio());
				pstmt.setInt(2, dispensadores.get(key).getCantidad());
				pstmt.executeUpdate();
			}
			
		
			
					
			
			
			
		} catch (SQLException e) {
			todoOK=false;
		}

		return todoOK;
	}

} // Fin de la clase