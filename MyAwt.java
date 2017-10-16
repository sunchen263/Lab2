import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.awt.FileDialog;
//import  org.eclipse.swt.widgets.FileDialog;

import javax.swing.*;

public class MyAwt {
	
	Hello1 Main = new Hello1();
	
	String txtFilePath;
	boolean isOpen = false;
	
	Frame f = new Frame("文本分析工具1.0");
	
	Panel pFunc1 = new Panel();	//	pFunc1.add(new Label("111"));
	Panel pFunc2 = new Panel();	//	pFunc2.add(new Label("222"));
	Panel pFunc3 = new Panel();	//	pFunc3.add(new Label("333"));
	Panel pFunc4 = new Panel();	//	pFunc3.add(new Label("444"));
	Panel pFunc5 = new Panel();	//	pFunc3.add(new Label("555"));
	Panel pFunc6 = new Panel();	//	pFunc3.add(new Label("666"));
	
	Panel pNorth = new Panel();	//	pNorth.add(new Label("当前文本路径："));
	
	Panel pWest = new Panel();	
	Panel pEast = new Panel();
	
	Button b1 = new Button("选择文件");
	Button b2 = new Button("显示图文件");
	Button b3 = new Button("查询桥接词");
	Button b4 = new Button("生成新句子");
	Button b5 = new Button("最短路径");
	Button b6 = new Button("随机游走");
	
	Button openfile = new Button("选择文件");
	Label pathLabel = new Label("");
	
	CardLayout card = new CardLayout();
	
	ButtonListener listener = new ButtonListener();
	
	public static void main(String arg[]) throws IOException
	{
		new MyAwt();
	}
	
	MyAwt()
	{
		setWest();
		setEast();
		setNorth();
		setPanelFunc1();
		setPanelFunc2();
		setPanelFunc3();
		setPanelFunc4();
		setPanelFunc5();
		setPanelFunc6();
		
		setListener();
		setFrame();
	}
	
//		JLabel L1 = new JLabel("功能1：");
//		JButton Open = new JButton("打开文件");
//		TextArea tf = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
//		tf.setSize(100, 100);
		
	private void setNorth()
	{
	//	pNorth.add(new Label("当前文本文档路径："));
	//	Label pathLabel = new Label("");
	//	pathLabel.setText("nihao");
	//	openfile.addActionListener(ee -> {  
     //      pathLabel.setText(txtFilePath);
     //       System.out.println("54u56u6");
     //   }); 
		
	//	pNorth.add(pathLabel);
		
	}
	
	private void setWest()
	{	
		b1.setBackground(Color.white);
		b2.setBackground(Color.white);
		b3.setBackground(Color.white);
		b4.setBackground(Color.white);
		b5.setBackground(Color.white);
		b6.setBackground(Color.white);
		
		pWest.setLayout(new GridLayout(6, 1));
		pWest.add(b1);
		pWest.add(b2);
		pWest.add(b3);
		pWest.add(b4);
		pWest.add(b5);
		pWest.add(b6);	
	}
	
	private void setEast()
	{
		
		pEast.setLayout(card);
		
		pEast.add(pFunc1, "1");
		pEast.add(pFunc2, "2");
		pEast.add(pFunc3, "3");
		pEast.add(pFunc4, "4");
		pEast.add(pFunc5, "5");
		pEast.add(pFunc6, "6");		
	}
	
	private void setFrame()
	{
		f.add(pNorth, BorderLayout.NORTH);
		f.add(pWest, BorderLayout.WEST);
		f.add(pEast, BorderLayout.EAST);
		
		pWest.setSize(500, 200);
		pEast.setSize(500,400);
		
		f.setSize(500, 500);
		f.setResizable(false);
		
		f.setVisible(true);
	}
		
	private void setPanelFunc1()
	{
	//	Button openfile = new Button("打开文件");
		TextField tField = new TextField(20);
		TextArea tArea = new TextArea("", 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		GridBagConstraints gbc = new GridBagConstraints();
		pFunc1.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc1.getLayout()).setConstraints(openfile,gbc);
		pFunc1.add(openfile);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc1.getLayout()).setConstraints(tField,gbc);
		pFunc1.add(tField);
		
		gbc.insets = new Insets(5,0,0,30);
		((GridBagLayout)pFunc1.getLayout()).setConstraints(tArea,gbc);
		pFunc1.add(tArea);
		
		FileDialog d1 = new FileDialog(f, "Open File", FileDialog.LOAD); 
		
		tField.setEditable(false);
		tArea.setEditable(false);
		tArea.setFont(new Font("Times New Roman",0,13));
		
		openfile.addActionListener(e -> {  
            d1.setVisible(true);
            txtFilePath = d1.getDirectory() + d1.getFile();
            tField.setText(txtFilePath);
            
            Main = new Hello1();
            try {
				Hello1.InitMain(txtFilePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            isOpen = true;
            
            tArea.setText(Main.GetTxtStr());
            
        //    pathLabel.setText(txtFilePath);//
        //    System.out.println(txtFilePath);
            
        }); 
		
	}
	
	private void setPanelFunc2()
	{
	//	pFunc2.add(new Label("功能2"));
		Button pic = new Button("生成图文件");
		Button open = new Button("打开");
	//	Label lb = new Label();
	
		FileDialog d1 = new FileDialog(f, "Open File", FileDialog.SAVE); 
		
		pFunc2.add(pic);
		pFunc2.add(open);
		
		pic.addActionListener(e -> {
			try {
			//	String picPath = d1.getDirectory() + d1.getFile();
				d1.setVisible(true);
								
				Main.showDirectedGraph(d1.getDirectory());
		
				String cmdStr = "cmd.exe /c dot "+d1.getDirectory()+"Graph.dot -T png -o"
								+d1.getDirectory()+d1.getFile();
				
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec(cmdStr);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		open.addActionListener(e->{
			String picPath = d1.getDirectory() + d1.getFile();
			Runtime rt = Runtime.getRuntime();
			try {
				Process p = rt.exec("cmd.exe /c "+d1.getDirectory() + d1.getFile());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}
	
	private void setPanelFunc3()
	{
		Label lb1 = new Label("word1:");
		Label lb2 = new Label("word2:");
		Label lb3 = new Label("自动清除");
		TextField word1 = new TextField(10);
		TextField word2 = new TextField(10);
		TextArea tArea = new TextArea("", 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button OK = new Button("确定");
		
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox cb1 = new Checkbox("是", cbg, true);
		Checkbox cb2 = new Checkbox("否", cbg, false);
		
		tArea.setEditable(false);
		tArea.setFont(new Font("Times New Roman",0,13));
		
		GridBagConstraints gbc = new GridBagConstraints();
		pFunc3.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(lb1,gbc);
		pFunc3.add(lb1);
		
		gbc.gridwidth = 2; 
		((GridBagLayout)pFunc3.getLayout()).setConstraints(word1,gbc);
		pFunc3.add(word1);
		
		gbc.gridwidth = 3;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(lb2,gbc);
		pFunc3.add(lb2);
		
		gbc.gridwidth = 4;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(word2,gbc);
		pFunc3.add(word2);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(OK,gbc);
		pFunc3.add(OK);
		
		gbc.gridheight = 3;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(lb3,gbc);
		pFunc3.add(lb3);
		
	//	gbc.gridheight = 3;
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(cb1,gbc);
		pFunc3.add(cb1);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(cb2,gbc);
		pFunc3.add(cb2);
		
		
	//	gbc.insets = new Insets(5,0,0,30);
	//	((GridBagLayout)pFunc1.getLayout()).setConstraints(tArea,gbc);
	//	pFunc3.add(tArea);
		
	//	gbc.gridheight = 5;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc3.getLayout()).setConstraints(tArea,gbc);
		pFunc3.add(tArea);
		
	//	String strToArea = "";
		OK.addActionListener(e ->{
			String str1 = word1.getText();
			String str2 = word2.getText();
			if(!str1.isEmpty() || !str2.isEmpty() || isOpen==true){
				String str3 = Main.queryBridgeWords(str1, str2);
				if(cb1.getState())
					tArea.setText(str3);
				else
					tArea.append(str3);
			}
		});
	}
	
	private void setPanelFunc4()
	{
		Label lb1 = new Label("在文本框内输入句子：");
		Label lb2 = new Label("生成的文本:");
		Label lb3 = new Label("自动清除");
		TextArea txt1 = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		TextArea txt2 = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button OK = new Button("确定");
		
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox cb1 = new Checkbox("是", cbg, true);
		Checkbox cb2 = new Checkbox("否", cbg, false);
		
		txt1.setEditable(true);
		txt1.setFont(new Font("Times New Roman",0,13));
		txt2.setEditable(false);
		txt2.setFont(new Font("Times New Roman",0,13));
		
		GridBagConstraints gbc = new GridBagConstraints();
		pFunc4.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(lb1,gbc);
		pFunc4.add(lb1);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(OK,gbc);
		pFunc4.add(OK);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(txt1,gbc);
		pFunc4.add(txt1);
		
		gbc.gridheight = 3;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(lb2,gbc);
		pFunc4.add(lb2);
		
//		gbc.gridheight = 3;
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(lb3,gbc);
		pFunc4.add(lb3);
		
	//	gbc.gridheight = 3;
		gbc.gridwidth = 3;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(cb1,gbc);
		pFunc4.add(cb1);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(cb2,gbc);
		pFunc4.add(cb2);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc4.getLayout()).setConstraints(txt2,gbc);
		pFunc4.add(txt2);
		
	//	String strToArea = "";
		OK.addActionListener(e ->{
			String str1 = txt1.getText();
			String str2 = Main.generateNewText(str1) + "\r\n";//Main.queryBridgeWords(str1, str2);
			
			if(cb1.getState())
				txt2.setText(str2);
			else
				txt2.append(str2);
		});
	}
	
	private void setPanelFunc5()
	{
		//单源最短路径原件声明
		Label lb1 = new Label("word1:");
		Label lb2 = new Label("word2:");
		Label lb3 = new Label("单源最短路径：");
		Label lb4 = new Label("自动清除");
		TextField word1 = new TextField(10);
		TextField word2 = new TextField(10);
		TextArea tArea1 = new TextArea("", 5, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button OK1 = new Button("确定");
		CheckboxGroup cbg1 = new CheckboxGroup();
		Checkbox cb1 = new Checkbox("是", cbg1, true);
		Checkbox cb2 = new Checkbox("否", cbg1, false);
		tArea1.setEditable(false);
		tArea1.setFont(new Font("Times New Roman",0,13));
		
		
		//多源最短路径原件声明
		Label lb5 = new Label("word：");
		Label lb6 = new Label("多源最短路径");
		Label lb7 = new Label("自动清除：");
		TextField word3 = new TextField(10);
		TextArea tArea2 = new TextArea("", 15, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button OK2 = new Button("确定");
		CheckboxGroup cbg2 = new CheckboxGroup();
		Checkbox cb3 = new Checkbox("是", cbg2, true);
		Checkbox cb4 = new Checkbox("否", cbg2, false);
		tArea2.setEditable(false);
		tArea2.setFont(new Font("Times New Roman",0,13));
	//	布局声明
	//单源最短路径布局	
	//	
		GridBagConstraints gbc = new GridBagConstraints();
		pFunc5.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb1,gbc);
		pFunc5.add(lb1);
		
		gbc.gridwidth = 2; 
		((GridBagLayout)pFunc5.getLayout()).setConstraints(word1,gbc);
		pFunc5.add(word1);
		
		gbc.gridwidth = 3;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb2,gbc);
		pFunc5.add(lb2);
		
		gbc.gridwidth = 4;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(word2,gbc);
		pFunc5.add(word2);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(OK1,gbc);
		pFunc5.add(OK1);
		
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb3,gbc);
		pFunc5.add(lb3);
		
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb4,gbc);
		pFunc5.add(lb4);
		
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(cb1,gbc);
		pFunc5.add(cb1);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(cb2,gbc);
		pFunc5.add(cb2);
	
		gbc.gridheight = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(tArea1,gbc);
		pFunc5.add(tArea1);
		
	//	多源最短路径布局
		gbc.gridheight = 3;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb5,gbc);
		pFunc5.add(lb5);
		
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(word3,gbc);
		pFunc5.add(word3);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(OK2,gbc);
		pFunc5.add(OK2);
		
		gbc.gridheight = 3;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb6,gbc);
		pFunc5.add(lb6);
		
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(lb7,gbc);
		pFunc5.add(lb7);
		
		gbc.gridwidth = 3;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(cb3,gbc);
		pFunc5.add(cb3);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(cb4,gbc);
		pFunc5.add(cb4);
		
		gbc.gridheight = 4;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc5.getLayout()).setConstraints(tArea2,gbc);
		pFunc5.add(tArea2);
		
		OK1.addActionListener(e ->{
			if(isOpen){
				String str1 = word1.getText();
				String str2 = word2.getText();
				String str3 = Main.calcShortestPath(str1, str2);
				
				if(cb1.getState())
					tArea1.setText(str3);
				else
					tArea1.append(str3);
			}
		});
		
		OK2.addActionListener(e ->{
			if(isOpen){
				String str1 = word3.getText();
			//	String str2 = word2.getText();
				String str3 = Main.calcShortestPath(str1);
				
				if(cb2.getState())
					tArea2.setText(str3);
				else
					tArea2.append(str3);
			}
		});
		
	}
	
	private void setPanelFunc6()
	{
		Label lb1 = new Label("      点击开始随机游走：");
		Label lb2 = new Label("生成的文本:");
		Label lb3 = new Label("自动清除:");
		TextArea txt = new TextArea("", 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
	//	TextArea txt2 = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button OK = new Button("确定");
		Button save = new Button("点击保存");
		
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox cb1 = new Checkbox("是", cbg, true);
		Checkbox cb2 = new Checkbox("否", cbg, false);
		
		txt.setEditable(false);
		txt.setFont(new Font("Times New Roman",0,13));
	//	txt2.setEditable(false);
	//	txt2.setFont(new Font("Times New Roman",0,13));
		
		GridBagConstraints gbc = new GridBagConstraints();
		pFunc6.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(lb1,gbc);
		pFunc6.add(lb1);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(OK,gbc);
		pFunc6.add(OK);
		
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(lb2,gbc);
		pFunc6.add(lb2);
		
		gbc.gridwidth = 2;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(lb3,gbc);
		pFunc6.add(lb3);
		
		gbc.gridwidth = 3;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(lb3,gbc);
		pFunc6.add(lb3);
		
		gbc.gridwidth = 4;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(cb1,gbc);
		pFunc6.add(cb1);
		
		gbc.gridwidth = 5;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(cb2,gbc);
		pFunc6.add(cb2);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(save,gbc);
		pFunc6.add(save);
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		((GridBagLayout)pFunc6.getLayout()).setConstraints(txt,gbc);
		pFunc6.add(txt);
		
		OK.addActionListener(e ->{
			if(isOpen){
				String str = Main.randomWalk() + "\r\n";
				
				if(cb1.getState())
					txt.setText(str);
				else
					txt.append(str);
			}
		});
		
		save.addActionListener(e ->{
			if(isOpen){				
				String str = txt.getText();
				
				FileDialog d1 = new FileDialog(f, "Open File", FileDialog.SAVE);
				d1.setVisible(true);
				String savePath = d1.getDirectory() + d1.getFile();
				
				File saveFile = new File(savePath);
				FileWriter NewFile;
				try {
					NewFile = new FileWriter(saveFile);
					NewFile.write(str);
					
					NewFile.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	private void setListener()
	{
		b1.addActionListener(listener);
		b2.addActionListener(listener);
		b3.addActionListener(listener);
		b4.addActionListener(listener);
		b5.addActionListener(listener);
		b6.addActionListener(listener);
		
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent close){
				System.exit(0);
			};
		});
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == b1)
			{
				card.show(pEast, "1");
				
				b1.setBackground(Color.lightGray);
				b2.setBackground(Color.white);
				b3.setBackground(Color.white);
				b4.setBackground(Color.white);
				b5.setBackground(Color.white);
				b6.setBackground(Color.white);
				
			}
			
			if(e.getSource() == b2)
			{
				card.show(pEast, "2");

				b1.setBackground(Color.white);
				b2.setBackground(Color.lightGray);
				b3.setBackground(Color.white);
				b4.setBackground(Color.white);
				b5.setBackground(Color.white);
				b6.setBackground(Color.white);
			}
			
			if(e.getSource() == b3)
			{
				card.show(pEast, "3");
				
				b1.setBackground(Color.white);
				b2.setBackground(Color.white);
				b3.setBackground(Color.lightGray);
				b4.setBackground(Color.white);
				b5.setBackground(Color.white);
				b6.setBackground(Color.white);
			}
			
			if(e.getSource() == b4)
			{
				card.show(pEast, "4");
				
				b1.setBackground(Color.white);
				b2.setBackground(Color.white);
				b3.setBackground(Color.white);
				b4.setBackground(Color.lightGray);
				b5.setBackground(Color.white);
				b6.setBackground(Color.white);
			}
			
			if(e.getSource() == b5)
			{
				card.show(pEast, "5");
				
				b1.setBackground(Color.white);
				b2.setBackground(Color.white);
				b3.setBackground(Color.white);
				b4.setBackground(Color.white);
				b5.setBackground(Color.lightGray);
				b6.setBackground(Color.white);
			}
			
			if(e.getSource() == b6)
			{
				card.show(pEast, "6");
				
				b1.setBackground(Color.white);
				b2.setBackground(Color.white);
				b3.setBackground(Color.white);
				b4.setBackground(Color.white);
				b5.setBackground(Color.white);
				b6.setBackground(Color.lightGray);
			}
		}
	}
		
		
		
		
		
	//	ScrollPane sp = new ScrollPane();
	//	sp.add(tf);
	//	f.add(tf);
	//	f.setSize(500,500);
	//	f.setVisible(true);
		
				
/*	//	ButtonListener listener;// = new ButtonListener();
		Hello1 Main = new Hello1();
		
		JFrame f = new JFrame("Open File");
		
		JButton B1 = new JButton("打开文件");
		FileDialog fd = new FileDialog(f, "OpenFile", FileDialog.LOAD);
		
		JTextArea ta = new JTextArea(10, 30);  ta.setLineWrap(true);
	//	ta.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane taRoll = new JScrollPane(ta); 
		taRoll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		ta.setCaretPosition(ta.getText().length());
		taRoll.setVisible(true);
		
		TextField tf1 = new TextField(15);
		TextField tf2 = new TextField(15);

		Button B2 = new Button("确定");
		
		Button B3 = new Button("显示有向图");
		
		class ButtonListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == B1)
				{//	System.out.println("anzhang!");
				//	ta.setText("anzhang!");
				//	ta.append("jiayi");
					
					fd.setVisible(true);
					String filePath = fd.getDirectory() + fd.getFile();
					try {
						Main.GetFile(filePath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Main.strList = Main.strSplit(Main.txtStr);
					Main.setGraph();
					
				}
				
				if(e.getSource() == B2){
					String word1 = tf1.getText();
					String word2 = tf2.getText();
					
				//	String taTxt = "";
					ta.append(Main.queryBridgeWords(word1, word2));
					System.out.println(Main.queryBridgeWords(word1, word2));
					
				}
				
				if(e.getSource() == B3){
					try {
						Main.showDirectedGraph();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			//	if(e.getSource() == tf1)
			//	{
			//		String s1 = tf1.getText();
			//		System.out.println(s1);
			//	}
			}
		}
		
		ButtonListener listener = new ButtonListener();
		B1.addActionListener(listener);
		B2.addActionListener(listener);
		B3.addActionListener(listener);
			// func 1
		
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		

		
		f.setSize(600, 500);
		f.setLayout(new FlowLayout());
		
		f.add(B1);
		f.add(tf1);
		f.add(tf2);
		f.add(B2);
		f.add(ta);
		f.add(B3);
//		f.add(taRoll);
//		taRoll.setViewportView(ta);
		
		fd.setVisible(false);
		f.setVisible(true);
		

*/	
	
}
