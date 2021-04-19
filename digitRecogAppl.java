
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class Digit_Recognizer extends JFrame implements ActionListener{

	String pwd = System.getProperty("user.dir");
	String IMG_PATH = "";
	String MODEL_PATH = pwd+"\\model\\CNN_model.h5";
	boolean IMG_DRAWEN = true;

	String[] btnTexts = {"Draw Image", "Open Image", "Recognize ->"};

	JPanel pnlNorth,pnlCenter,pnlCommon;
	JLabel lblTitle,lblRes;
	JButton[] btnArray = new JButton[btnTexts.length];
	Dimension dimension = new Dimension(700,400);
	Font font = new Font("Arial", Font.ITALIC, 20);

	Digit_Recognizer(){

		super("Digit Recognizer");

		setLayout(new BorderLayout());

		pnlNorth = new JPanel( new FlowLayout(FlowLayout.CENTER,20,20));
		pnlCenter = new JPanel( new GridLayout(2,2,50,50));
		pnlCommon = new JPanel( new FlowLayout(FlowLayout.CENTER,20,10));

		lblTitle = new JLabel("Digit Recognizer");
		lblTitle.setFont( new Font("Times New Roman", Font.BOLD, 32));

		int index = 0;
		for(String btnText: btnTexts){
			btnArray[index] = new JButton(btnText);
			btnArray[index].setFont(font);
			btnArray[index].addActionListener(this);
			index++;
		}

		lblRes = new JLabel("Result : ");
		lblRes.setFont( new Font("Times New Roman", Font.BOLD, 20));

		pnlNorth.add(lblTitle);
		
		for(int i=0 ; i<btnTexts.length ; i++){
			pnlCenter.add(btnArray[i]);
		}

		pnlCenter.add(lblRes);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCommon, BorderLayout.WEST);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlCommon, BorderLayout.EAST);
		add(pnlCommon, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,100);
		setSize(dimension);
		setMinimumSize(dimension);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent evt){
		String cmd = evt.getActionCommand();

		// btnDraw
		if (cmd == btnTexts[0]){
			String s = null;
			try{
				
				String command="python paint.py";

				Process p = Runtime.getRuntime().exec(command);
	            		
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				//System.out.println("got output from paint.py i.e. saved file name\n");
				s = stdInput.readLine();
				//System.out.println(s);
			} catch (IOException e) {
			    System.out.println("exception happened : ");
			    e.printStackTrace();
			    System.exit(-1);
			}
			//System.out.println("draw button pressed");
			IMG_PATH = pwd+"\\"+s;
			lblRes.setText("Image path: "+IMG_PATH);
			IMG_DRAWEN = true;
		}

		// btnOpen
		else if (cmd == btnTexts[1]){
			//System.out.println("open button pressed");
		
			FileNameExtensionFilter filtr1 = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
			FileNameExtensionFilter filtr2 = new FileNameExtensionFilter("PNG file", "png");
			
			JFileChooser openDialog = new JFileChooser(pwd);

			//#set not all file filter to be used only manually added will be used
			openDialog.setAcceptAllFileFilterUsed(false);

			//#adding file filters
			openDialog.addChoosableFileFilter(filtr1);
			openDialog.addChoosableFileFilter(filtr2);

			openDialog.setDialogType(JFileChooser.OPEN_DIALOG);

			int returnVal = openDialog.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File filename = openDialog.getSelectedFile();
				IMG_PATH = filename.getPath();
				
				lblRes.setText("Image path: "+IMG_PATH);
				IMG_DRAWEN = false;

				//System.out.println(IMG_PATH);
			}
		}

		// btnRecog
		else if (cmd == btnTexts[2]){
			String s = null;
			//String s2 = null;

			if (IMG_PATH == "")		lblRes.setText("Please draw or select image.");
			else {
				lblRes.setText("Recognizing . . .");

				String command = "python load_model.py "+IMG_PATH+" "+MODEL_PATH;
				try{
					if(IMG_DRAWEN)	command += " True";
					else	command += " False";

					Process p = Runtime.getRuntime().exec(command);

					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

					BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					
					//System.out.println("got output from load_model.py");

					s = stdInput.readLine();
					System.out.println(s);
					
				} catch (IOException e) {
				    System.out.println("exception happened : ");
				    e.printStackTrace();
				    System.exit(-1);
				}
				lblRes.setText("Result : "+s);
				}
				//System.out.println("recog button pressed");
		}
	}
}

class digitRecogAppl{
	public static void main(String[] a){
		Digit_Recognizer d = new Digit_Recognizer();
	}
}
