package Paquete;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ventana extends JFrame{
	protected final String llave="Prepluis",ruta="C:/Notas/";
	protected final File folder=new File(ruta);
	protected JPanel p1;
	protected String usuario,contra,linea;
	
	protected static File aux;
	protected BufferedReader bufer1;
	protected BufferedWriter bufer2;
	private FileWriter archivo;
	private FileReader lector;
	
	private ArrayList<String> datos=new ArrayList<String>();
	
	@Override
	public Image getIconImage() {
	   Image retValue = Toolkit.getDefaultToolkit().
	         getImage(ClassLoader.getSystemResource("Imagenes/icono.png"));
	   return retValue;
	}
	
	public Ventana() {
		setIconImage(getIconImage());
	}
	
	protected void CrearArchivo(String usuario, String contra) {
		usuario=encriptacion(llave,usuario,true);
		aux=new File(ruta+usuario+".txt");
		if(!aux.exists()) {
			try {
				archivo=new FileWriter(ruta+usuario+".txt");
				contra=encriptacion(llave,contra,true);
			    archivo.write(contra);
			    archivo.close();
			    JOptionPane.showMessageDialog(this,"Usuario creado");
		    }catch(IOException e){
		    	System.out.print("Hay problemas para acceder o leer al archivo");
		    }
		}else {
			JOptionPane.showMessageDialog(this,"El usuario ya existe");
		}
	}
	
	protected void RevisarUsuario(String usuario, String contra) {
		usuario=encriptacion(llave,usuario,true);
		contra=encriptacion(llave,contra,true);
		aux=new File(ruta+usuario+".txt");
		if(aux.exists()) {
			try {
				lector=new FileReader(aux);
				bufer1=new BufferedReader(lector);
				linea=bufer1.readLine();
				if(contra.compareTo(linea)==0) {
					this.setVisible(false);
				    Ventana2 v=new Ventana2(usuario,contra);
				    v.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(this,"Contraseña equibocada");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(this,"El usuario no existe");
		}
	}
	
	protected void GuardarNota(String usuario,String contra,String titulo, String nota, String urgencia) {
		String cadena=titulo+"   "+urgencia+"   "+nota;
		cadena=encriptacion(llave,cadena,true);
		try {
			archivo=new FileWriter(aux,true);
			bufer2=new BufferedWriter(archivo);
			bufer2.newLine();
			bufer2.write(cadena);
			bufer2.close();
			this.setVisible(false);
		    Ventana2 v=new Ventana2(usuario,contra);
		    v.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected ArrayList<String> Contar(String usuario) {
		datos.clear();
		try {
			lector=new FileReader(aux);
			bufer1=new BufferedReader(lector);
		    linea=bufer1.readLine();
			while(linea!=null){
				linea=bufer1.readLine();
				if(linea==null) break; 
				else {
					linea=encriptacion(llave,linea,false);
					datos.add(linea);
				}
			}
			bufer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datos;
	} 
	
	protected void Borrar(String usuario,String contra,int indice,int bandera) {
		datos=Buscar(usuario,indice,bandera);
		try {
			archivo=new FileWriter(aux,false);
			archivo.write(contra);
			archivo.close();
			archivo=new FileWriter(aux,true);
			bufer2=new BufferedWriter(archivo);
			for(int i=0;i<datos.size();i++){
				bufer2.newLine();
				bufer2.write(encriptacion(llave,datos.get(i),true));
			}
			bufer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected ArrayList<String> Buscar(String usuario,int indice,int bandera) {
		String cadena;
		datos=Contar(usuario);
		if(bandera==0)datos.remove(indice);
		else{
			cadena=datos.get(indice);
			datos.clear();
			datos.add(cadena);
			datos.add(String.valueOf(indice));
		}
		return datos;
	}
	protected void Remplazar(String usuario,String contra,String titulo, String nota, String urgencia,int numero){
		String cadena;
		datos=Contar(usuario);
		datos.set(numero,titulo+"   "+urgencia+"   "+nota);
		try {
			archivo=new FileWriter(aux,false);
			archivo.write(contra);
			archivo.close();
			archivo=new FileWriter(aux,true);
			bufer2=new BufferedWriter(archivo);
			for(int i=0;i<datos.size();i++){
				bufer2.newLine();
				cadena=encriptacion(llave,datos.get(i),true);
				bufer2.write(cadena);
			}
			bufer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected String encriptacion(String llave,String palabra,boolean cripto) {//Metodo para crear clave de encriptacion
		try {
			byte [] cadena=llave.getBytes("UTF-8");
			MessageDigest md= MessageDigest.getInstance("SHA-1");
			cadena=md.digest(cadena);
			cadena=Arrays.copyOf(cadena,16);
			SecretKeySpec clave=new SecretKeySpec(cadena,"AES");
			Cipher ciper=Cipher.getInstance("AES/ECB/PKCS5Padding");
			if(cripto==true) {
				ciper.init(Cipher.ENCRYPT_MODE,clave);
				byte [] aux1=palabra.getBytes("UTF-8");
				byte [] aux2=ciper.doFinal(aux1);
				palabra=Base64.getEncoder().encodeToString(aux2);
			}else {
				ciper.init(Cipher.DECRYPT_MODE,clave);
				byte [] aux1=Base64.getDecoder().decode(palabra);
				byte [] aux2=ciper.doFinal(aux1);
				palabra=new String(aux2,"UTF-8");
			}
			return palabra;
		}catch(Exception e) {
			return null;
		}
	} 
}
