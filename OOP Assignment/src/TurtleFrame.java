import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import uk.ac.leedsbeckett.oop.TurtleGraphics;

//===============================================================================================

public class TurtleFrame extends JFrame implements ActionListener{

	//initiating key fields

	//MenuBr menubr = new MenuBr(this);
	//TurtleGraphics tGPnl = new TurtleGraphics ();
	TurtleGraphics tGPnl = new ExtendedTurtleGraphics();
	JFrame TurtleFrame = new JFrame();
	JTextField jtf = new JTextField("add commands here!");
	JButton commandsBtn = new JButton("Execute");
	JPanel commandsPnl = new JPanel();
	//JPanel allPnl = new JPanel();

	String isEnterPressed = new String("no");
	String switchInput;

	JMenuBar  MenuBar = new JMenuBar();

	JMenu     fileMnu = new JMenu("File");
	JMenu 	  helpMnu = new JMenu ("Help");

	JMenuItem newItmMnu  = new JMenuItem("New");
	JMenuItem saveItmMnu = new JMenuItem ("Save");
	JMenuItem openItmMnu = new JMenuItem ("Open");
	JMenuItem closItmMnu = new JMenuItem ("Close");
	JMenuItem aboutMnu = new JMenuItem ("About");
	JFrame frm = new JFrame("Menu");

	JFileChooser JFC = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	int FC = 0;
	int cmderror = 0;


	//===============================================================================================	
	//set the turtleframe

	TurtleFrame(){

		MenuBar.add(fileMnu);
		MenuBar.add(helpMnu);

		helpMnu.add(aboutMnu);
		fileMnu.add(newItmMnu);
		fileMnu.add(saveItmMnu);
		fileMnu.add(openItmMnu);
		fileMnu.add(closItmMnu);

		newItmMnu.addActionListener(this);
		saveItmMnu.addActionListener(this);
		openItmMnu.addActionListener(this);
		closItmMnu.addActionListener(this);
		helpMnu.addActionListener(this);

		setVisible(true);
		setTitle("Turtle App");
		setSize(new Dimension(800, 400));
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setJMenuBar(MenuBar);

		commandsPnl.setLayout(new BorderLayout());
		commandsPnl.add(jtf,BorderLayout.CENTER);
		commandsPnl.add(commandsBtn,BorderLayout.NORTH);
		add(tGPnl, BorderLayout.CENTER);
		add(commandsPnl, BorderLayout.NORTH);
		commandsBtn.addActionListener(this);
		jtf.addActionListener(this);
		jtf.addKeyListener(

				new KeyListener() { 
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							isEnterPressed = "yes";
						}
					}
					@Override
					public void keyReleased(KeyEvent e) {
					}
					@Override
					public void keyTyped(KeyEvent e) {
					}

				});

		pack();
		setResizable(false);
	}


	//============================================================================================
	//command inputs




	private void save() {

		if(JOptionPane.showConfirmDialog(this, "Would You like to save this project?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			JFC.setDialogTitle("Please Select A File");
			JFC.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".png", ".png");
			JFC.addChoosableFileFilter(fileFilter);
			int RV = JFC.showSaveDialog(null);
			if (RV == JFileChooser.APPROVE_OPTION) {
				File selectedFile = JFC.getSelectedFile();
				String FCR = (selectedFile.getAbsolutePath());
				File f = new File(FCR);
				if (f.exists()) {
					JOptionPane.showMessageDialog(frm, "File name alredy exists, please use a diffrent name");
				}
				else {
					try {
						ImageIO.write(tGPnl.getBufferedImage(), "png", new File(selectedFile.getAbsolutePath() + ".png"));
						System.out.println("File Saved");
						FC = 0;
					}					
					catch (IOException e1) {
						System.out.println("File Failed to Save");
						e1.printStackTrace();
					}
				}
			}
		}
	}



	private void loadWrkNotSaved() {

		int Res = JOptionPane.showConfirmDialog(this, "Work Unsaved!, would you like to save your work?" ,"new" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (Res == JOptionPane.YES_OPTION) {save(); FC = 0;}
		else if (Res == JOptionPane.NO_OPTION) {loadWrkSaved(); FC = 1;}
	}

	private void loadWrkSaved() {

		JFC.setDialogTitle("Select a file");
		JFC.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("png", "png");
		JFC.addChoosableFileFilter(fileFilter);
		int RV = JFC.showOpenDialog(null);
		if (RV == JFileChooser.APPROVE_OPTION) {
			File selectedFile = JFC.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			try {
				BufferedImage newImage = ImageIO.read(new File(selectedFile.getAbsolutePath()));
				tGPnl.setBufferedImage(newImage);
			}catch (IOException e1) {
				e1.printStackTrace();}
		}
	}

	private void exit() {
		if(FC == 0) {
			System.exit(0);
		}
		if(FC == 1) {
			save();
			System.exit(0);
		}
	}
	
	


	//save();}

	//================================================================================================================================================================

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == newItmMnu) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to create a new project?", "New",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if(FC == 0) {
					tGPnl.clear();
					tGPnl.reset();
					tGPnl.penDown();
					tGPnl.turnLeft(90);
					FC = 0;
					cmderror=1;
				}	
				else if(FC == 1) {
					save();
					tGPnl.clear();
					tGPnl.reset();
					tGPnl.penDown();
					tGPnl.turnLeft(90);
					FC = 0;
					cmderror=1;
				}
			}
		}
		
		if(e.getSource() == saveItmMnu) {save();}
		

		if(e.getSource() == openItmMnu) {if(FC == 0){loadWrkSaved();} if(FC == 1) {loadWrkNotSaved();}}

		
		if(e.getSource() == closItmMnu) {exit();}

		//Turtle say Hi
		/*System.out.println("About button works");
			tGPnl.penUp();
			tGPnl.forward(200);
			tGPnl.turnRight(90);
			tGPnl.forward(-150);
			tGPnl.penDown();
			tGPnl.forward(300);
			tGPnl.penUp();
			tGPnl.turnRight(180);
			tGPnl.forward(150);
			tGPnl.turnLeft(90);
			tGPnl.penDown();
			tGPnl.forward(150);
			tGPnl.penUp();
			tGPnl.turnRight(90);
			tGPnl.forward(150);
			tGPnl.turnRight(180);
			tGPnl.penDown();
			tGPnl.forward(300);
			tGPnl.penUp();
			tGPnl.turnRight(90);
			tGPnl.forward(150);
			tGPnl.turnRight(90);
			tGPnl.penDown();
			tGPnl.forward(300);*/




		//============================================================================================================================================================================

		if(e.getSource() == commandsBtn || isEnterPressed.equals("yes")) {
			String text = jtf.getText();
			String inputOne = "input";
			String inputTwo = "inputTwo";
			int v = 0;   //v = value



			if (jtf.getText().contains(" ")) {

				String[] PT = text.split(" "); //PT = Processed Text
				inputOne = PT[0];
				inputTwo = PT[1];
				switchInput = inputOne.toLowerCase();	
			}


			else { inputOne = text; 
			switchInput = inputOne.toLowerCase();
			}


			switch(switchInput) {

			case "about":
				tGPnl.about();
				FC = 1;
				cmderror = 1;
				break;

			case "clear":
				tGPnl.clear();
				cmderror = 1;
				break;

			case "reset":
				tGPnl.reset();
				tGPnl.clear();
				cmderror = 1;
				break;

			case "penup":
				tGPnl.penUp();
				cmderror = 1;
				break;

			case "pendown":
				tGPnl.penDown();
				cmderror = 1;
				break;

			case "red":
				tGPnl.setPenColour(Color.RED);
				cmderror = 1;
				break;

			case "green":
				tGPnl.setPenColour(Color.GREEN);
				cmderror = 1;
				break;


			case "black":
				tGPnl.setPenColour(Color.BLACK);
				cmderror = 1;
				break;

			case "blue":
				tGPnl.setPenColour(Color.BLUE);
				cmderror = 1;
				break;

			case "orange":
				tGPnl.setPenColour(Color.ORANGE);
				cmderror = 1;
				break;

			case "pink":
				tGPnl.setPenColour(Color.PINK);
				cmderror = 1;
				break;

			case "yellow":
				tGPnl.setPenColour(Color.YELLOW);
				cmderror = 1;
				break;

			case "forward":
				if (inputTwo.matches(".*\\d.*")) {
					v = Integer.parseInt(inputTwo);
					tGPnl.forward(v);}
				else {JOptionPane.showMessageDialog(this, "Invalid Parameters");
				}

				FC = 1;
				cmderror = 1;
				break;

			case "backward":
				if (inputTwo.matches(".*\\d.*")) {
					v = Integer.parseInt(inputTwo);
					tGPnl.forward(-v);}
				else {JOptionPane.showMessageDialog(this, "Invalid Parameters");
				}

				FC = 1;
				cmderror = 1;
				break;  

			case "turnleft":
				if (inputTwo.matches(".*\\d.*")) { 
					v = Integer.parseInt(inputTwo);
					tGPnl.turnLeft(v);}
				else {JOptionPane.showMessageDialog(this, "Invalid Parameters");
				}

				cmderror = 1;
				break;

			case "turnright":
				if (inputTwo.matches(".*\\d.*")) {
					v = Integer.parseInt(inputTwo);
					tGPnl.turnRight(v);}
				else {JOptionPane.showMessageDialog(this, "Invalid Parameters");
				}

				cmderror = 1;
				break;

			case "circle":
				Graphics g = tGPnl.getGraphicsConext();
				if (inputTwo.matches(".*\\d.*")) {
					v = Integer.parseInt(inputTwo);
					tGPnl.circle(v);}
				else {JOptionPane.showMessageDialog(this, "Invalid Parameters");
				}

				cmderror = 1;
				break;


			}
			if(cmderror == 0) {
				JOptionPane.showMessageDialog(this, "Invalid Command");	}
		}

	}	
	
	
	public static void main(String[] args) {

		new TurtleFrame();	
	} 
}


//============================================================================================================		



//JOptionPane.showMessageDialog(this, "Invalid Command" , "Invalid Command", 1);






//=====================================================================================================



//Catch (ArrayIndexOutOfBoundsException p) {
//JOptionPane.showMessageDialog(this, "Enter a Number Please", "Invalid Parameter", 1);
//}



//=======================================================================================================







