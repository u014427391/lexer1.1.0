package com.lexer;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * 词法分析器界面类
 */
public class LexerFrame extends JFrame implements ActionListener{
	
	private JMenuBar menuBar;
	
	private JToolBar toolBar;
	
	private JMenu fileMenu;
	
	private JMenu editMenu;
	
	 //词法分析菜单
	private JMenu  lexerMenu;
	
	private JMenu helpMenu;
	
	private JMenuItem openMenuItem;
	
	private JMenuItem saveMenuItem;
	
	private JMenuItem exitMenuItem;
	
	private JMenuItem cutMenuItem;
	
	private JMenuItem copyMenuItem;
	
	private JMenuItem pasteMenuItem;
	
	private JMenuItem clearMenuItem;
	
	 // 词法分析器菜单项
	private JMenuItem lexerMenuItem;
	
	private JMenuItem helpMenuItem;
	
	private JMenuItem aboutMenuItem;
	
	private JButton openButton;
	
	//保存按钮
	private JButton saveButton;
	
	//词法分析按钮
	private JButton lexerButton;
	
	private JButton cutButton;
	
	private JButton copyButton;
	
	private JButton pasteButton;
	
	private JButton clearButton;
	
	private JButton exitButton;
	
	 // 分隔面板
	private JSplitPane splitPane;
	
	 //左右面板
	private JPanel leftPanel,rightPanel;
	
	//文件内容文本域
	private JTextArea fileContentTextArea;
	
	//分析内容
	private JTextArea analysisContentTextArea;
	
	
	//表格
	private JTable table;
	
	//表格模型
	private DefaultTableModel model;
		
	//控制台文本域
	private JTextArea consoleTextArea;
	
	//滚动面板
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	
	Analyze analyze=new Analyze();
	
	String title[]={"行","标识符","机器码"};
	String values[][]={};
	
	
	
	/**
	 * 创建菜单栏
	 */
	public void createMenu(){
		
		menuBar = new JMenuBar();
		
		//菜单
		fileMenu = new JMenu("文件");
		editMenu = new JMenu("编辑");
		lexerMenu = new JMenu("词法分析");
		helpMenu = new JMenu("帮助");
		
		//菜单项
		ImageIcon openIcon=new ImageIcon("../lexer/image/open.png");
		openMenuItem = new JMenuItem("打开",openIcon);
		openMenuItem.addActionListener(this);
		
		ImageIcon saveIcon=new ImageIcon("../lexer/image/save.png");
		saveMenuItem = new JMenuItem("保存",saveIcon);
		saveMenuItem.addActionListener(this);
		
		ImageIcon exitIcon=new ImageIcon("../lexer/image/exit.png");
		exitMenuItem = new JMenuItem("退出",exitIcon);
		exitMenuItem.addActionListener(this);
		
		ImageIcon cutIcon=new ImageIcon("../lexer/image/cut.png");
		cutMenuItem = new JMenuItem("剪切",cutIcon);
		
		ImageIcon copyIcon=new ImageIcon("../lexer/image/copy.png");
		copyMenuItem = new JMenuItem("复制",copyIcon);
		
		ImageIcon pasteIcon=new ImageIcon("../lexer/image/paste.png");
		pasteMenuItem = new JMenuItem("黏贴",pasteIcon);
		
		ImageIcon clearIcon=new ImageIcon("../lexer/image/clear.png");
		clearMenuItem = new JMenuItem("清除",clearIcon);
		clearMenuItem.addActionListener(this);
		
		ImageIcon lexerIcon=new ImageIcon("../lexer/image/lexer.png");
		lexerMenuItem = new JMenuItem("词法分析器",lexerIcon);
		lexerMenuItem.addActionListener(this);
		
		ImageIcon helpIcon=new ImageIcon("../lexer/image/help.png");
		helpMenuItem = new JMenuItem("帮助",helpIcon);
		helpMenuItem.addActionListener(this);
		
		ImageIcon aboutIcon=new ImageIcon("../lexer/image/lexer.png");
		aboutMenuItem = new JMenuItem("关于",aboutIcon);
		aboutMenuItem.addActionListener(this);
		
		//添加菜单项到菜单
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.add(clearMenuItem);
		
		lexerMenu.add(lexerMenuItem);
		
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		
		//添加菜单到菜单栏
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(lexerMenu);
		menuBar.add(helpMenu);
		
		//设置菜单栏
		setJMenuBar(menuBar);
	}
	
	/**
	 * 创建工具栏
	 */
	public void createToolBar(){
		toolBar=new JToolBar();
		
		//工具栏上的按钮
		openButton=new JButton(new ImageIcon("image/open.png"));
		saveButton=new JButton(new ImageIcon("image/save.png"));
		lexerButton=new JButton(new ImageIcon("image/lexer.png"));
		cutButton=new JButton(new ImageIcon("image/cut.png"));
		copyButton=new JButton(new ImageIcon("image/copy.png"));
		pasteButton=new JButton(new ImageIcon("image/paste.png"));
		clearButton=new JButton(new ImageIcon("image/clear.png"));
		exitButton=new JButton(new ImageIcon("image/exit.png"));
		
		//添加事件监听器
		openButton.addActionListener(this);
		saveButton.addActionListener(this);
		lexerButton.addActionListener(this);
		cutButton.addActionListener(this);
		copyButton.addActionListener(this);
		pasteButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		//添加按钮到工具栏
		toolBar.add(openButton);
		toolBar.add(saveButton);
		toolBar.add(lexerButton);
		toolBar.add(cutButton);
		toolBar.add(copyButton);
		toolBar.add(pasteButton);
		toolBar.add(clearButton);
		toolBar.add(exitButton);
		
	}
	
	
	/**
	 * 打开文件
	 */
	public void openFile(){
		JFileChooser fileChooser=new JFileChooser("打开文件");
        int isOpen=fileChooser.showOpenDialog(null);
        fileChooser.setDialogTitle("打开文件");
		if(isOpen==JFileChooser.APPROVE_OPTION){
        	String path=fileChooser.getSelectedFile().getPath();
        	fileContentTextArea.setText(readFromFile(path));
		}
	}
	
	/**
	 * 读取文件
	 */
	public String readFromFile(String path){
		File file=new File(path);
		String s=null;
		try {
		FileInputStream fin=new FileInputStream(file);
		
		int length=fin.available();
		
		byte arr[]=new byte[length];
		
		int len=fin.read(arr);
		
		s=new String(arr,0,len);
		
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 保存文件
	 */
	public void saveFile(){
		JFileChooser fileChooser=new JFileChooser("保存文件");
        int isSave=fileChooser.showSaveDialog(this);
        fileChooser.setDialogTitle("保存文件");
		String selectFileName="";
        if(isSave==JFileChooser.APPROVE_OPTION){
        	File file=fileChooser.getSelectedFile();
        	selectFileName=fileChooser.getName(file);
        	if(selectFileName.length()>0){
        		if(!selectFileName.endsWith(".txt")){
        			selectFileName=selectFileName.concat(".txt");
        		}
        		
        		file=fileChooser.getCurrentDirectory();
        		file=new File(file.getPath().concat(File.separator).concat(selectFileName));
        		
        		if(file.exists()){
        			int i=JOptionPane.showConfirmDialog(this, "文件已经存在,是否覆盖?");
        			if(i!=JOptionPane.YES_OPTION){
        				return;
        			}
        		}
        		
        		try {
					file.createNewFile();
					FileWriter out=new FileWriter(file);
					out.write(fileContentTextArea.getText());
					out.close();
					JOptionPane.showMessageDialog(this, "保存成功!");
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}
	}
	
	
	/**
	 * 剪切
	 */
	public void doCut(){
		fileContentTextArea.cut();
	}
	
	/**
	 * 复制
	 */
	public void doCopy(){
		int dot1,dot2;
		
		dot1=fileContentTextArea.getSelectionStart();
		
		dot2=fileContentTextArea.getSelectionEnd();
		
		if(dot1!=dot2){
			fileContentTextArea.copy();
			JOptionPane.showMessageDialog(null, "复制成功!");
		}
	}
	
	/**
	 * 黏贴
	 */
	public void doPaste(){
		
		//调用系统剪切板
		Clipboard clipboard=getToolkit().getSystemClipboard();
		
		Transferable content=clipboard.getContents(this);
		
		try {
			if(content.getTransferData(DataFlavor.stringFlavor) instanceof String){
				fileContentTextArea.paste();
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 清除文本框内容
	 */
	public void doClear(){
		fileContentTextArea.setText(null);
	}
	
	/**
	 * 词法分析
	 */
	public void doTokenizing(){
		
		consoleTextArea.setText(null);
		ArrayList<Word> wlist=new ArrayList<Word>();
		ArrayList<Unidentifiable> ulist=new ArrayList<Unidentifiable>();
		
		String s,ts,str;
		Word word;
		
		int i;
		int opcodes=-1;
		int errorNum=0;
		
		int count=0;
		
		s=fileContentTextArea.getText();
		if(s.length()>1){
			ts=analyze.preFunction(s);
			wlist=analyze.divide(ts);
			values=new String[wlist.size()][3];
			while(wlist.size()>0){
				word=(Word)wlist.remove(0);
				str=word.getWord();
				i=analyze.check(str);
				switch (i) {
				case 1:
					opcodes=analyze.checkDigit(str);
					break;
				case 2:
					opcodes=analyze.checkChar(str);
					break;
				case 3:
					opcodes=analyze.checkString(str);
					break;
				}
				
				if(opcodes==0){
					Unidentifiable u=new Unidentifiable(word.getRow(), str);
					ulist.add(u);
					errorNum++;
				}
				
				values[count][0]=String.valueOf(word.getRow());
				values[count][1]=str;
				values[count][2]=String.valueOf(opcodes);
				
				count++;
				
			}
			
			//更新表格内容
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			while(model.getRowCount()>0){
				model.removeRow(model.getRowCount()-1);
			}
		     
		      model.setDataVector(values,title);
			
			table=new JTable(model);
				
				consoleTextArea.append("共有"+errorNum+"处错误!"+"\n");
				while (ulist.size()>0) {
					int r;
					String string;
					Unidentifiable uni=ulist.remove(0);
					r=uni.getRow();
					string=uni.getWord();
					consoleTextArea.append("第"+r+"行:"+"错误,"+string+"\n");
				}
		}else{
			int j;
			j=JOptionPane.showConfirmDialog(this, "请输入程序!");
			if(j!=JOptionPane.YES_OPTION){
				return;
			}
		}
		
	}
	
	
	/**
	 * 打开使用说明书
	 */
	public void doHelp(){
		try {
			Runtime.getRuntime().exec("cmd /c start "+System.getProperty("user.dir")+"\\doc\\help.doc") ;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 关于
	 */
	public void showAbout(){
		JDialog dialog=new JDialog();
		dialog.setTitle("词法分析器 ");
		JLabel label=new JLabel("           基于Java的C语言词法分析器");
		dialog.add(label);
		dialog.setVisible(true);
		dialog.setSize(250, 160);
		dialog.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = dialog.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	/**
	 * 退出
	 */
	public void doExit(){
		
		int isExit=JOptionPane.showConfirmDialog(this, "您真的要关闭词法分析器?");
		if(isExit==JOptionPane.YES_OPTION){
			System.exit(0);
		}else{
			return;
		}
	}
	
	/**
	 * 点击事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==openMenuItem){
            openFile();
		}else if(e.getSource()==saveMenuItem){
			saveFile();
		}else if(e.getSource()==exitMenuItem){
			doExit();
		}else if(e.getSource()==cutMenuItem){
			doCut();
		}else if(e.getSource()==copyMenuItem){
			doCopy();
		}else if(e.getSource()==pasteMenuItem){
			doPaste();
		}else if(e.getSource()==clearMenuItem){
			doClear();
		}else if(e.getSource()==lexerMenuItem){
			doTokenizing();
		}else if(e.getSource()==helpMenuItem){
			 doHelp();
		}else if(e.getSource()==aboutMenuItem){
			showAbout();
		}else if(e.getSource()==openButton){
			openFile();
		}else if(e.getSource()==saveButton){
			saveFile();
		}else if(e.getSource()==lexerButton){
			doTokenizing();
		}else if(e.getSource()==cutButton){
			doCut();
		}else if(e.getSource()==copyButton){
			doCopy();
		}else if(e.getSource()==pasteButton){
			doPaste();
		}else if(e.getSource()==clearButton){
			doClear();
		}else if(e.getSource()==exitButton){
			doExit();
		}
	}

	/**
	 * 构造方法
	 */
	public  LexerFrame(){
		setTitle("词法分析器  版本  V 1.0");
		
		Image logo=Toolkit.getDefaultToolkit().getImage("../lexer/image/lexer.png");
		setIconImage(logo);
		
		//创建菜单栏
		createMenu();
		
		//创建工具栏
		createToolBar();
		
		//左面板
		leftPanel=new JPanel();
		
		fileContentTextArea=new JTextArea(60,60);
		fileContentTextArea.setFont(new Font("隶书",Font.BOLD,12));
		scrollPane1=new JScrollPane(fileContentTextArea);
    	scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    	scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane1.getViewport().add(fileContentTextArea);
    	scrollPane1.getViewport().setPreferredSize(new Dimension(300,530));
		leftPanel.add(scrollPane1);
		
		//右面板
		rightPanel=new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		
		//分析后的内容放在表格里
		
		model=new DefaultTableModel(values,title);
		table=new JTable(model);
		scrollPane2=new JScrollPane(table);
    	scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scrollPane2.getViewport().add(table);
    	scrollPane2.getViewport().setPreferredSize(new Dimension(100,100));
    	
    	//控制台
    	consoleTextArea = new JTextArea(100,100);
    	consoleTextArea.setFont(new Font("隶书",Font.BOLD,12));
    	scrollPane3=new JScrollPane(consoleTextArea);
    	scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scrollPane3.getViewport().add(consoleTextArea);
    	scrollPane3.getViewport().setPreferredSize(new Dimension(300,100));
    	
    	//添加到右部面板
    	rightPanel.add("Center",scrollPane2);
    	rightPanel.add("South",scrollPane3);

		
		//创建一个分隔面板
		splitPane=new JSplitPane();
		
		//左面板
		splitPane.setLeftComponent(leftPanel);
		
		//右面板
		splitPane.setRightComponent(rightPanel);
		
		add("North",toolBar);
		add("Center",splitPane);
		
		//设置运行时窗口的位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 6, (screenSize.height - frameSize.height) / 6);
		
		
		setSize(800,600);
		setResizable(false);
		setVisible(true);
	}
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		new LexerFrame();
	}

	
}
