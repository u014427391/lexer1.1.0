package com.lexer;

/**
 * 
 *	可以识别的标识符
 */
public class Word {

	//标识符所在的行
	private int row;
	
	//获取到的标识符
	private String word;
	
	public Word(int row,String word){
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