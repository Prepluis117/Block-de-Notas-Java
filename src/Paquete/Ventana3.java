package Paquete;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;


public class Ventana3 extends Ventana implements ActionListener{
	private JLabel l1,l2,l3,l4;
	private JTextField t1;
	private JTextArea t2;
	private JButton b1, b2, b3,b4;
	private ButtonModel z1;
	private JRadioButton r1,r2,r3,r4;
	private ButtonGroup bg1;
	private JComboBox c1,c2,c3;
	private JScrollPane s1;
	
	private int bandera;
	private int indice;
	private ArrayList<String> datos=new ArrayList<String>();
	private String[] separador=new String[3];
	
	public Ventana3(int bandera,String usuario,String contra){
		super();
		this.bandera=bandera;
		this.usuario=usuario;
		this.contra=contra;
		this.setSize(500,500);
		this.setResizable(false); 
		setTitle("Nota");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		componentes();
	}
	public Ventana3(int bandera, String usuario,String contra,int indice) {
		this.bandera=bandera;
		this.usuario=usuario;
		this.contra=contra;
		this.indice=indice;
		datos=Buscar(usuario,indice,1);
		separador=datos.get(0).split("   ");
		this.setSize(500,500);
		setTitle("Nota");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		l1=new JLabel("Titulo:");
		l1.setBounds(20,40,60,20);
		p1.add(l1);
	    t1=new JTextField();
		t1.setBounds(80,40,350,20);
		p1.add(t1);
		if(bandera==1) t1.setText(separador[0]);
		
		l3=new JLabel("Nota:");
		l3.setBounds(20,120,60,20);
		p1.add(l3);
		t2=new JTextArea();
		t2.setLineWrap(true);
		//t2.setBounds(80,120,350,260);
		s1=new JScrollPane(t2);
		s1.setBounds(80,120,350,260);
		p1.add(s1);
		if(bandera==1) t2.setText(separador[2].replaceAll("/n","\n"));
		
		l4=new JLabel("Prioridad:");
		l4.setBounds(20,400,60,20);
		p1.add(l4);
	}
	private void botones(JPanel p1){
		b1=new JButton("Guardar");
		b1.setBounds(0,0,125,20);
		p1.add(b1);
		b1.addActionListener(this);
		if(bandera==1)b1.setEnabled(false);
		
		b2=new JButton("Editar");
		b2.setBounds(125,0,125,20);
		p1.add(b2);
		b2.addActionListener(this);
		if(bandera==0)b2.setEnabled(false);
		
		b3=new JButton("Borrar");
		b3.setBounds(250,0,125,20);
		p1.add(b3);
		b3.addActionListener(this);
		if(bandera==0)b3.setEnabled(false);
		
		b4=new JButton("Regresar");
		b4.setBounds(375,0,125,20);
		p1.add(b4);
		b4.addActionListener(this);
		
		r1=new JRadioButton("Nula",false);
		r1.setActionCommand("Nula");
		r1.setBounds(100,400,60,20);
		p1.add(r1);
		
		r2=new JRadioButton("Baja",false);
		r2.setActionCommand("Baja");
		r2.setBounds(180,400,60,20);
		p1.add(r2);
		
		r3=new JRadioButton("Moderada",false);
		r3.setActionCommand("Moderada");
		r3.setBounds(260,400,60,20);
		p1.add(r3);
		
		r4=new JRadioButton("Alta",false);
		r4.setActionCommand("Alta");
		r4.setBounds(340,400,60,20);
		p1.add(r4);
		
		bg1=new ButtonGroup();
		bg1.add(r1);
		bg1.add(r2);
		bg1.add(r3);
		bg1.add(r4);
		if(bandera==1) {
			if(separador[1].compareTo(r1.getActionCommand())==0) r1.setSelected(rootPaneCheckingEnabled);
			else if(separador[1].compareTo(r2.getActionCommand())==0) r2.setSelected(rootPaneCheckingEnabled);
			else if(separador[1].compareTo(r3.getActionCommand())==0) r3.setSelected(rootPaneCheckingEnabled);
			else if(separador[1].compareTo(r4.getActionCommand())==0) r4.setSelected(rootPaneCheckingEnabled);
		}
	}
	public void actionPerformed(ActionEvent e){
		String titulo,nota,urgencia=null;
		if(e.getActionCommand()=="Guardar"){
			titulo=t1.getText();
			nota=t2.getText().replaceAll("\n","/n");
			if(bg1.getSelection()!=null) urgencia=bg1.getSelection().getActionCommand();
			if(titulo == null || titulo.isEmpty() || titulo.substring(0,1).compareTo(" ")==0 || 
			   nota == null || nota.isEmpty() || nota.substring(0,1).compareTo(" ")==0 ||
			   urgencia == null) JOptionPane.showMessageDialog(this,"Debe de poner todos los datos");
			else GuardarNota(usuario,contra,titulo,nota,urgencia);
		}else if(e.getActionCommand()=="Regresar"){
			this.setVisible(false);
		    Ventana2 v=new Ventana2(usuario,contra);
		    v.setVisible(true);
		}else if(e.getActionCommand()=="Borrar") {
			Borrar(usuario,contra,indice,0);
			this.setVisible(false);
		    Ventana2 v=new Ventana2(usuario,contra);
		    v.setVisible(true);
		}else if(e.getActionCommand()=="Editar") {
			titulo=t1.getText();
			nota=t2.getText().replaceAll("\n","/n");
			if(bg1.getSelection()!=null) urgencia=bg1.getSelection().getActionCommand();
			if(titulo == null || titulo.isEmpty() || titulo.substring(0,1).compareTo(" ")==0 || 
			   nota == null || nota.isEmpty() || nota.substring(0,1).compareTo(" ")==0 ||
			   urgencia == null) JOptionPane.showMessageDialog(this,"Debe de poner todos los datos");
			else Remplazar(usuario,contra,titulo,nota,urgencia,Integer.valueOf(datos.get(1)));
			this.setVisible(false);
		    Ventana2 v=new Ventana2(usuario,contra);
		    v.setVisible(true);
		}
	}
}
