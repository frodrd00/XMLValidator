package unileon.pl.xml;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * 
 * @author Flavio Rodrigues Dias
 *
 */

public class Application extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JTextField textField;
	private  File file, fileLog;
	private static JTextArea textArea;
	private static boolean encontrado = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
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
	public Application() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Application.class.getResource("/unileon/pl/images/iconXML.png")));
		setTitle("Validar XML");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 380);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem abrirXML = new JMenuItem("Cargar XML");
		abrirXML.setIcon(new ImageIcon(Application.class.getResource("/unileon/pl/images/abrir.png")));
		
		mnArchivo.add(abrirXML);
		
		JMenuItem btnEditar = new JMenuItem("Editar XML");
		btnEditar.setEnabled(false);
		btnEditar.setIcon(new ImageIcon(Application.class.getResource("/unileon/pl/images/icon_edit.gif")));
		mnArchivo.add(btnEditar);
		
		JMenuItem btnGuardar = new JMenuItem("Guardar Log");
		btnGuardar.setEnabled(false);
		btnGuardar.setIcon(new ImageIcon(Application.class.getResource("/unileon/pl/images/save.png")));
		mnArchivo.add(btnGuardar);
			
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Como utilizar");
		mntmNewMenuItem.setIcon(new ImageIcon(Application.class.getResource("/unileon/pl/images/question.png")));
		mnAyuda.add(mntmNewMenuItem);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.setIcon(new ImageIcon(Application.class.getResource("/unileon/pl/images/information.png")));
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnValidar = new JButton("Validar");
		btnValidar.setBounds(186, 106, 118, 39);
		btnValidar.setEnabled(false);
		contentPane.add(btnValidar);
		
		JLabel lblArchivoXml = new JLabel("Documento XML");
		lblArchivoXml.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblArchivoXml.setBounds(199, 27, 105, 23);
		contentPane.add(lblArchivoXml);
		
		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblResultado.setBounds(29, 167, 74, 14);
		contentPane.add(lblResultado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 192, 432, 110);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(88, 61, 312, 21);
		contentPane.add(textField);
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setColumns(10);
		
		JLabel resultLabel = new JLabel("");
		resultLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		resultLabel.setBounds(90, 167, 164, 14);
		contentPane.add(resultLabel);
	
		/**********************************************************************************/
		
		// acciones de los botones
	
		//menu abrir archivo xml
		abrirXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 JFileChooser fileChooser;
				 String dir ="";
				 
				 if(file!=null)
					dir = file.getParent();
				
				 if(dir!=null)
					 fileChooser = new JFileChooser(dir);
				 else
					 fileChooser = new JFileChooser();
				
				 FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
					     "xml files (*.xml)", "xml");
				 
				 fileChooser.setFileFilter(xmlfilter);
				 
				
				 if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					
					encontrado = true;
					btnEditar.setEnabled(true);
					btnGuardar.setEnabled(false);
					textArea.setText("");
					resultLabel.setText("");
	
					file = fileChooser.getSelectedFile();
					   
					String nameFile = file.getAbsolutePath();
					   
					textField.setText(nameFile);
					  
					btnValidar.setEnabled(true); 
					 
				}
				else{
					  
					 JOptionPane.showMessageDialog(contentPane, "No has seleccionado nada", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		//guardar resultado
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				 JFileChooser fileChooser;
				 String dir ="";
				
				 if(fileLog!=null)
						dir = fileLog.getParent();
					
					 if(dir!=null)
						 fileChooser = new JFileChooser(dir);
					 else
						 fileChooser = new JFileChooser();
				 
				 FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
					     "txt files (*.txt)", "txt");
				 
				 fileChooser.setFileFilter(xmlfilter);
				 
				 if(file!=null){
					 String f = file.getName();
					 String sp[] = f.split("\\.");
					 String nameSaveFile = sp[0] + "LogErrores.txt";
					 fileChooser.setSelectedFile(new File(nameSaveFile));
				 }
				 
				 if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

				      fileLog = fileChooser.getSelectedFile();

				      if (!fileLog.getName().endsWith(".txt")) {
				    	  fileLog = new File(fileLog.getAbsolutePath() + ".txt");
				      }

				      BufferedWriter outFile = null;
				      try {
				         outFile = new BufferedWriter(new FileWriter(fileLog));

				         textArea.write(outFile);
				         
				         JOptionPane.showMessageDialog(contentPane, "Resultado guardado", "Aviso", JOptionPane.INFORMATION_MESSAGE);

				      } catch (IOException ex) {
				         ex.printStackTrace();
				      } finally {
				         if (outFile != null) {
				            try {
				               outFile.close();
				            } catch (IOException e) {}
				         }
				      }
					 
					  
					}
				 else{
					  
					 JOptionPane.showMessageDialog(contentPane, "No has guardado nada.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		
		//boton validar documento xml
		btnValidar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				
				btnGuardar.setEnabled(false);
				textArea.setText("");
				resultLabel.setText("");
				
				String f = file.getName();
				String sp[] = f.split("\\.");
				
				if(sp[1].equals("xml"))
				{
					try {
						boolean resultado = Application.validateWithDTDUsingDOM(file.getAbsolutePath());
						if(!resultado){
							resultLabel.setText("Se han encontrado errores.");
							resultLabel.setForeground(Color.RED);
							if(encontrado){
								btnGuardar.setEnabled(true);
							}
							else{
								btnGuardar.setEnabled(false);
								btnValidar.setEnabled(false);
								btnEditar.setEnabled(false);
							}
							
						}else{
							resultLabel.setText("Sin errores.");
							resultLabel.setForeground(Color.GREEN);
						}
					} catch (ParserConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(contentPane,
						    "El documento tiene que ser un XML.",
						    "Documento Erróneo",
						    JOptionPane.ERROR_MESSAGE);
					
					btnValidar.setEnabled(false);
					resultLabel.setText("");
					textArea.setText("");
				}
					
			}	
			
		});

		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(contentPane,
					    "Darle a Archivo.\n"
						+ "Cargar XML.\n"
					    + "Seleccionar un documento XML que queramos validar.\n"
					    + "Darle al boton Validar.\n"
					    + "El resultado aparecerá en el cuadro de Resultado.\n"
					    + "Se puede Editar el XML una vez cargado.\n"
					    + "Se puede Guardar el Resultado, darle a Guardar Log.\n"
					    ,"Ayuda",JOptionPane.QUESTION_MESSAGE);
			}
		});
		
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane,
					    "Autor Flavio Rodrigues Dias.\n"
					    + "Asignatura PL, Año 2017.\n",
					    "Acerca del programa",JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			try {
				
				Runtime obj = Runtime.getRuntime();
	            obj.exec("notepad " + file.getAbsolutePath());
	           
		     }catch (IOException ex) {
		    	 JOptionPane.showMessageDialog(contentPane, "Documento no encontrado", "Aviso", JOptionPane.ERROR_MESSAGE);
		         ex.printStackTrace();    
		     }catch (IllegalArgumentException e) {
		    	 JOptionPane.showMessageDialog(contentPane, "Documento no encontrado", "Aviso", JOptionPane.ERROR_MESSAGE);
		         e.printStackTrace();    
			}
				
			
			
			}
		});
		
	}
	
	 public static boolean validateWithDTDUsingDOM(String xml) 
			    throws ParserConfigurationException, IOException
	 {
	    try {
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      factory.setValidating(true);
	      factory.setNamespaceAware(true);

	      DocumentBuilder builder = factory.newDocumentBuilder();
	      
	      builder.setErrorHandler(
	          new ErrorHandler() {
	            public void warning(SAXParseException e) throws SAXException {
	            	textArea.setText(textArea.getText() + "AVISO:" 
            		  			+ " Linea " + e.getLineNumber()
            		  			+ ", Columna " + e.getColumnNumber()
              					+  ", " +  e.getMessage() + "\n"); 
	            }

	            public void error(SAXParseException e) throws SAXException {
	            	textArea.setText(textArea.getText() + "ERROR:" 
	        		  			+ " Linea " + e.getLineNumber()
	        		  			+ ", Columna " + e.getColumnNumber()
	          					+  ", " +  e.getMessage() + "\n"); 
	            }

	            public void fatalError(SAXParseException e) throws SAXException {
	            	textArea.setText(textArea.getText() + "FATAL ERROR:" 
	        		  			+ " Linea " + e.getLineNumber()
	        		  			+ ", Columna " + e.getColumnNumber()
	          					+  ", " +  e.getMessage() + "\n"); 
	            }
	          }
	          );
	      
	      builder.parse(xml);
	      

	    }
	    catch (ParserConfigurationException e) {
	    	e.printStackTrace();
	    	return false;
	    } 
	    catch (IOException e) {
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(contentPane, "Documento no encontrado", "Aviso", JOptionPane.ERROR_MESSAGE);
	    	textArea.setText("\n\n\n\tNo se ha encontrado el documento XML.");
	    	encontrado = false;
	    	return false;
	    }
	    catch (SAXException e){
	    	e.printStackTrace();
	    	return false;
	    }
	    
	    
      if(textArea.getText().isEmpty()){
    	  textArea.setText("\n\n\n\tEnhorabuena, el documento XML es válido :)");
    	  return true;
      }
      else{
    	  return false;
      }
     

	}
}
