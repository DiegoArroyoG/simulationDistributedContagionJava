import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class GUIDistribuidos extends JFrame {


	private HashMap <String, List<String>> vecinos;
	
	private JPanel contentPane;
	private JTable IngresoTabla;
	private JTable tableInicial;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDistribuidos frame = new GUIDistribuidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIDistribuidos() {
		LeerFichero();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 685, 455);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Configuracion", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 660, 336);
		panel.add(scrollPane);
		
		
		Object[][] matriz = new Object[vecinos.size()][7];
		for (int x=0; x < matriz.length; x++) {
			  for (int y=0; y < matriz[x].length; y++) {
				  if(y == 0) 
					  matriz[x][y] = vecinos.keySet().toArray()[x];
				  else
					  matriz[x][y] = null;
			  }
		}
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			matriz,
			new String[] {
				"Nombre", "Poblacion", "Infectados", "Aislamiento", "Vulnerabilidad", "IP", "Vecinos"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, Object.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnGenerar = new JButton("Generar TXT");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String[][] matriz = new String[vecinos.size()][7];
				for(int i = 0; i < table.getRowCount(); i++) {
					for(int j = 0; j < table.getColumnCount(); j++) {
						matriz[i][j] = (String) table.getValueAt(i,j);
					}
				}
				
				for(int i = 0; i < table.getRowCount(); i++) {
					int puerto = (int) (Math.random() * 65535) + 7778;
	                List<String> valores = new ArrayList<String>();
	                valores.add(Integer.toString(puerto));
	                valores.add(matriz[i][5].trim());
	                vecinos.put(matriz[i][0], valores);
				}
				
				File fichero = new File("Config.txt");
				fichero.delete();
				BufferedWriter bw = null;
				String str = "";
				String[] paisesVecinos;
				try {
					bw = new BufferedWriter(new FileWriter("Config.txt",true));
					bw.write("");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					for(int i = 0; i < table.getRowCount(); i++) {
						str = "";
						for(int j = 0; j < table.getColumnCount(); j++) {
							if(j == 6) {								
								paisesVecinos = matriz[i][j].split(",");
								for(int k = 0; k < paisesVecinos.length; k++) {
									List <String> valores = vecinos.get(paisesVecinos[k].trim());
									str = str + valores.get(0) + "-" + valores.get(1) + ",";
								}
							} else {
								str = str + matriz[i][j] + ",";
							}
						}
						List<String> port = vecinos.get(matriz[i][0]);
						str = str + port.get(0) + "\n";
						
						bw.append(str);
					}
					bw.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      
			}
		});
		btnGenerar.setBounds(149, 374, 129, 23);
		panel.add(btnGenerar);
		
		JButton btnBuscarTXT = new JButton("Buscar TXT");
		btnBuscarTXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.init();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscarTXT.setBounds(388, 374, 129, 23);
		panel.add(btnBuscarTXT);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Broker", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnBrokerInit = new JButton("Iniciar Broker");
		btnBrokerInit.setFont(new Font("Lucida Sans Unicode", Font.ITALIC, 14));
		btnBrokerInit.setToolTipText("Inicia el Broker para comunicarse con los dem\u00E1s");
		btnBrokerInit.setBounds(10, 11, 660, 23);
		panel_1.add(btnBrokerInit);
		
		JLabel lblNewLabel = new JLabel("Direccion IP:");
		lblNewLabel.setBounds(10, 66, 74, 14);
		panel_1.add(lblNewLabel);
		
		/*JLabel lblDireccionIP = new JLabel(Main.dir_origen.getHostAddress());
		lblDireccionIP.setBounds(81, 66, 148, 14);
		panel_1.add(lblDireccionIP);*/
		
		JLabel lblNewLabel_1 = new JLabel("Puerto:");
		lblNewLabel_1.setBounds(10, 91, 46, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("7777");
		lblNewLabel_2.setBounds(81, 91, 148, 14);
		panel_1.add(lblNewLabel_2);
		
	}

	private void LeerFichero() {
		// TODO Auto-generated method stub
		String line;
        vecinos = new HashMap<String, List<String>>();
        try {
            Scanner input = new Scanner(new File("config2.txt"));
            while (input.hasNextLine()) {       
                line = input.nextLine();
                vecinos.put(line.trim(), new ArrayList<String>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
}
