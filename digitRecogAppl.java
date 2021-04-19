
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;


//import paint import paint
//from prepare_image import prepare_image

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

	//
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

	            // read the output from the command
				System.out.println("got output from paint.py i.e. saved file name\n");

				s = stdInput.readLine();
				System.out.println(s);

	            //while ((s = stdInput.readLine()) != null) {
	            //    System.out.println(s);
	            //}
	            
	            // read any errors from the attempted command
	            //System.out.println("errors from paint.py (if any):\n");
	            //while ((s = stdError.readLine()) != null) {
	            //    System.out.println(s);
	            //}
	            
	            //System.exit(0);
	        } catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
	        }
			System.out.println("draw button pressed");
			IMG_PATH = pwd+"\\"+s;
			lblRes.setText("Image path: "+IMG_PATH);
			IMG_DRAWEN = true;
		}

		// btnOpen
		else if (cmd == btnTexts[1]){
			System.out.println("open button pressed");
		
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

				System.out.println(IMG_PATH);
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

		            // read the output from the command
					System.out.println("got output from load_model.py");

					s = stdInput.readLine();
					System.out.println(s);

		            //while ((s = stdInput.readLine()) != null) {
		            //	s2 = s;
		            //    System.out.println(s);
		            //}
		            
		            // read any errors from the attempted command
		            //System.out.println("errors from load_model.py (if any):\n");
		            //while ((stdError.readLine()) != null) {
		            //    System.out.println(s);
		            //}
		            
		            //System.exit(0);
		        } catch (IOException e) {
		            System.out.println("exception happened - here's what I know: ");
		            e.printStackTrace();
		            System.exit(-1);
		        }

		        lblRes.setText("Result : "+s);
			}

			System.out.println("recog button pressed");
			//#print(event)
		}
	}
}

class digitRecogAppl{
	public static void main(String[] a){
		Digit_Recognizer d = new Digit_Recognizer();
	}
}
	
	/*def recog(self, event):
		#img_array = prepare_image(IMG_PATH)
		#print(img_array)

		py = ProcessBuilder(["python", "prepare_image.py", IMG_PATH])
		py.start()
		bfr = BufferedReader(InputStreamReader(py.getInputStream()))
		print(bfr)
		
		print("recog button pressed")
		
		lblRes.setText("Result : "+"predicted result here")
		#print(event)*/
