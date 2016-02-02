package com.lexer;

/**
 * 
 *	JavaBean类
 *   不可以识别的标识符
 */
public class Unidentifiable {

	//不可以识别的标识符行
	int row;
	
	//获取到的不可以识别的标识符
	String word;
	
	public Unidentifiable(int row,String word){
		this.row=row;
		this.word=word;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
