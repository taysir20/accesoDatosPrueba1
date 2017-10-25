package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;


import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	
	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto(){
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = null;
		depositosCreados = new HashMap<Integer, Deposito>();
		try {
			FileReader leerFichero;
			String moneda = null;
			int valor=0;
			int cantidad=0;
			Deposito deposito=null;
			fDep = new File("Ficheros/datos/depositos.txt");
			leerFichero = new FileReader(fDep);
			BufferedReader br = new BufferedReader(leerFichero);
			String lineaDeposito="";
			while((lineaDeposito=br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(lineaDeposito,";");
				
			    while (st.hasMoreTokens()){
			    	moneda= st.nextToken();
			    	valor= Integer.parseInt(st.nextToken());
			    	 cantidad= Integer.parseInt(st.nextToken());
			    
			    
			    }
			    	
				deposito = new Deposito(moneda,valor,cantidad);
				depositosCreados.put(deposito.getValor(), deposito);
		    
			        
				}
		
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		
		return depositosCreados;
	}
	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		HashMap<String, Dispensador> dispensadoresCreados = null;
		dispensadoresCreados = new HashMap<String, Dispensador>();
		try {
			FileReader leerFichero;
			String clave=null;
	    	String nombre= null;
	    	int precio = 0;
	    	int cantidad = 0;
	    	 Dispensador dispensador=null;
			
			fDep = new File("Ficheros/datos/dispensadores.txt");
			leerFichero = new FileReader(fDep);
			BufferedReader br = new BufferedReader(leerFichero);
			String lineaDispensador="";
			while((lineaDispensador=br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(lineaDispensador,";");
			
			    while (st.hasMoreTokens()){
			    	clave= st.nextToken();
			    	nombre= st.nextToken();
			    	precio = Integer.parseInt(st.nextToken());
			    	 cantidad = Integer.parseInt(st.nextToken());
			    	
			    	
			    	
			    }
			   dispensador = new Dispensador(clave,nombre,precio,cantidad);
				 dispensadoresCreados.put(dispensador.getClave(), dispensador);
			        
				}
		
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return dispensadoresCreados;
		
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		
		boolean todoOK = true;
		try {
			
			
			
			
			FileReader leerFichero;
			fDep = new File("Ficheros/datos/depositos.txt");
			leerFichero = new FileReader(fDep);
			 BufferedReader br = new BufferedReader(leerFichero);
			 String lineaCompletaDeposito="";
			
			
			
	
		
								
			
			
				FileWriter fw = new FileWriter(fDep, false);
			     BufferedWriter bw = new BufferedWriter(fw);
			     PrintWriter pw = new PrintWriter(bw);
			     for ( int key : depositos.keySet() ) {
			    	 pw.println(String.valueOf(depositos.get(key).getNombreMoneda()) + ";" + String.valueOf(depositos.get(key).getValor()) + ";" + String.valueOf(depositos.get(key).getCantidad()));
			    	 
			     }
			       
			        pw.close();
			       	
			
			 
			
		} catch (IOException e) {
			todoOK=false;
			e.printStackTrace();
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		try {
			
			
			
			
			FileReader leerFichero;
			fDep = new File("Ficheros/datos/dispensadores.txt");
			leerFichero = new FileReader(fDep);
			 BufferedReader br = new BufferedReader(leerFichero);
			 String lineaCompletaDeposito="";
			
			
			
	
		
								
			
			
				FileWriter fw = new FileWriter(fDep, false);
			     BufferedWriter bw = new BufferedWriter(fw);
			     PrintWriter pw = new PrintWriter(bw);
			     for ( String key : dispensadores.keySet() ) {
			    	 pw.println(String.valueOf(dispensadores.get(key).getClave()) + ";" + String.valueOf(dispensadores.get(key).getNombreProducto()) + ";" + String.valueOf(dispensadores.get(key).getPrecio()) + ";" + String.valueOf(dispensadores.get(key).getCantidad()));
			    	 
			     }
			       
			        pw.close();
			       	
			
			 
			
		} catch (IOException e) {
			todoOK=false;
			e.printStackTrace();
		}

		

		return todoOK;
	}

} // Fin de la clase