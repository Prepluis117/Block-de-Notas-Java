package Paquete;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ventana1 extends Ventana implements ActionListener{
	private JLabel l1, l2;
	private JTextField t1, t2;
	private JButton b1, b2;
	
	public Ventana1(){
		super();
		if(!folder.exists()) {
			folder.mkdir();
		}
		this.setSize(300,250);
		this.setResizable(false); 
		this.setTitle("Cosas por hacer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		componentes();
	}
	private void componentes(){
	    p1=new JPanel();
	    this.getContentPane().add(p1);
		p1.setLayout(null);
		textos(p1);
		botones(p1);
	}
	private void textos(JPanel p1){
		l1=new JLabel("Usuario:");
		l1.setBounds(40,40,100,20);
		p1.add(l1);
	    t1=new JTextField();
		t1.setBounds(120,40,120,20);
		p1.add(t1);
		l2=new JLabel("Contraseña:");
		l2.setBounds(40,80,100,20);
		p1.add(l2);
		t2=new JPasswordField();
		t2.setBounds(120,80,120,20);
		p1.add(t2);
	}
	private void botones(JPanel p1) {
		b1=new JButton("Entrar");
		b1.setBounds(70,120,140,20);
		p1.add(b1);
		b1.addActionListener(this);
		b2=new JButton("Generar usuario");
		b2.setBounds(70,160,140,20);
		p1.add(b2);
		b2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		usuario=t1.getText();
		contra=t2.getText();
		if(e.getActionCommand()=="Entrar") {
			RevisarUsuario(usuario,contra);
		}else if(e.getActionCommand()=="Generar usuario") {
			if(usuario == null || usuario.isEmpty() || usuario.substring(0,1).compareTo(" ")==0) JOptionPane.showMessageDialog(this,"Debe haber un nombre de usuario");
			else if(contra == null || contra.isEmpty() || contra.substring(0,1).compareTo(" ")==0) JOptionPane.showMessageDialog(this,"Debe haber una contraseña");
			else CrearArchivo(usuario,contra);
		}
	}
}