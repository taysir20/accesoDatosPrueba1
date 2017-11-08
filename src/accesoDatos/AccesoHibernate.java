package accesoDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import auxiliares.HibernateUtil;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;


public class AccesoHibernate implements I_Acceso_Datos {

	Session session;

	public AccesoHibernate() {

		HibernateUtil util = new HibernateUtil();

		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		int clave;

		///////////////////////////////////

		Query q = session.createQuery("select depos from Deposito depos"); // cuando
																			// s
																			// ehace
																			// un
																			// select
																			// no
																			// necesitamos
																			// hacer
																			// una
																			// transaccion.
																			// "Equipo"
																			// es
																			// el
																			// nombre
																			// de
																			// la
																			// entidad
																			// y
																			// no
																			// de
																			// la
																			// tabla
		List results = q.list();// esa lista almacenañra objetos equipo de la
								// base de datos

		Iterator depositosIterator = results.iterator(); // mete esos objetos en
															// un iterador

		while (depositosIterator.hasNext()) { // recorremos ese iterador
			Deposito deposito = (Deposito) depositosIterator.next();

			clave = deposito.getValor();
			depositosCreados.put(clave, deposito);

		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreado = new HashMap<String, Dispensador>();
		String clave;

		Query q = session.createQuery("select dispen from Dispensador dispen"); // cuando
		// s
		// ehace
		// un
		// select
		// no
		// necesitamos
		// hacer
		// una
		// transaccion.
		// "Equipo"
		// es
		// el
		// nombre
		// de
		// la
		// entidad
		// y
		// no
		// de
		// la
		// tabla
		List results = q.list();// esa lista almacenañra objetos equipo de la
								// base de datos

		Iterator dispensadorIterator = results.iterator(); // mete esos objetos
															// en un iterador

		while (dispensadorIterator.hasNext()) { // recorremos ese iterador
			Dispensador dispensador = (Dispensador) dispensadorIterator.next();

			clave = dispensador.getClave();
			dispensadoresCreado.put(clave, dispensador);

		}

		/////////////////////////
		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		
		try{
			session.beginTransaction();
			for ( int key : depositos.keySet() ) {
				
			        session.save(depositos.get(key));
			       
			} 
	        session.getTransaction().commit();//si se ejecuta toda la transacciones se realzia un commit es decir un "yo firmo"

		}catch(Exception e){
			todoOK = false;
		}
		
	
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		try{
			session.beginTransaction();
			for ( String key : dispensadores.keySet() ) {
			        session.save(dispensadores.get(key));
			      
			       
			} 
			  session.getTransaction().commit();//si se ejecuta toda la transacciones se realzia un commit es decir un "yo firmo"
		}catch(Exception e){
			todoOK = false;
		}
		
	
		return todoOK;
		
	}

}
