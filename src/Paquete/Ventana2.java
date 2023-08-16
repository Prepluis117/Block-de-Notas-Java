package Paquete;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Ventana2 extends Ventana implements ActionListener, ListSelectionListener{
	private JPanel p2;
	private JList l1;
	private JButton b1, b2, b3[];
	private JScrollPane s1;
	
	private ArrayList<String> datos=new ArrayList<String>();
	
	public Ventana2(String usuario,String contra) {
		super();
		this.usuario=usuario;
		this.contra=contra;
		datos=Contar(usuario);
		b3=new JButton[datos.size()];
		this.setSize(500,500);
		this.setResizable(false); 
		setTitle("Cosas por hacer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		componentes();
	}
	private void componentes() {
	    p1=new JPanel();
	    p1.setBounds(0,0,500,20);
	    this.getContentPane().add(p1);
		p1.setLayout(null);
		p2=new JPanel();
	    p2.setBounds(0,20,500,500);
	    this.getContentPane().add(p2);
		p2.setLayout(null);
		botones(p1,p2);
	}
	private void botones(JPanel p1,JPanel p2){
		String separador[][]=new String[1][3];
		Vector <Auxiliar> v=new Vector <Auxiliar>();
		aux=new File(ruta+usuario+".txt");
		int dimension=50;
		b1=new JButton("Nueva nota");
		b1.setBounds(0,0,250,20);
		p1.add(b1);
		b1.addActionListener(this);
		b2=new JButton("Volver al inicio");
		b2.setBounds(250,0,250,20);
		p1.add(b2);
		b2.addActionListener(this);
		for(int i=0;i<datos.size();i++) {
			separador[0]=datos.get(i).split("   ");
			v.add(new Auxiliar(separador[0][0],separador[0][1]));
		}
		l1=new JList(v);
		l1.setFixedCellHeight(50);
		l1.setFont(new Font("Dialog",Font.PLAIN,20));
		l1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		l1.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Auxiliar a=(Auxiliar) value;
				setText(a.titulo);
				if(a.urgencia.compareTo("Nula")==0){
					setBackground(Color.green);
				}else if(a.urgencia.compareTo("Baja")==0) {
					setBackground(Color.yellow);
				}else if(a.urgencia.compareTo("Moderada")==0) {
					setBackground(Color.orange);
				}else if(a.urgencia.compareTo("Alta")==0){
					setBackground(Color.red);
				}
			    return c;
			}
		});
		l1.addListSelectionListener(this);
		s1=new JScrollPane(l1);
		s1.setBounds(0,20,485,445);
		p2.add(s1);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Nueva nota"){
			this.setVisible(false);
		    Ventana3 v1=new Ventana3(0,usuario,contra);
		    v1.setVisible(true);
		}else if(e.getActionCommand()=="Volver al inicio") {
			this.setVisible(false);
		    Ventana1 v=new Ventana1();
		    v.setVisible(true);
		}
	}
	public void valueChanged(ListSelectionEvent e) {
		this.setVisible(false);
		System.out.print(l1.getSelectedIndex());
	    Ventana3 v2=new Ventana3(1,usuario,contra,l1.getSelectedIndex());
	    v2.setVisible(true);
	}
}