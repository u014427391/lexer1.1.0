package com.lexer;

import java.util.ArrayList;

/** 
 *	封装进行词法分析的方法
 *	1~20号为关键字,用下标表示,i+1就是其机器码;21~40号为操作符,用下标表示,i+21就是其机器码;41~60号为分界符,
 *  用下标表示,i+41就是其机器码;用户自定义的标识符，其机器码为51;常数的机器码为52；不可以识别的标识符，其机器码为0
 */
public class Analyze {

	//关键字
	private String keyword[]={"int","long","char","if","else","for","while","return","break","continue",
			"switch","case","default","float","double","void","struct","static","do","short"};
	
	//运算符
	private String operator[]={"+","-","*","/","%","=",">","<","!","==","!=",">=","<=","++","--","&","&&","||","[","]"};
	
	//分界符
	private String delimiter[]={",",";","(",")","{","}","\'","\"",":","#"};
	
	public Analyze() {
		
	}
	
	/**
	 * 判断是否是数字
	 */
	public boolean isDigit(char ch){
		if(ch>='0'&&ch<='9'){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否是字母的函数
	 */
	public boolean isLetter(char ch){
		if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否由两个运算符组成
	 */
	public boolean isTwoOperator(String str,char ch){
		char lc;
		int flag=0;
		if(str.length()>1||str.length()==0){//字符数大于2和无字符的情况
			return false;
		}else{//字符数等于2的情况
			lc=str.charAt(str.length()-1);
			if(ch=='='&&(lc=='>'||lc=='<'||lc=='='||lc=='!')){
				
			}else if(ch=='+'&&lc=='+'){
				
			}else if(ch=='-'&&lc=='-'){
				
			}else if(ch=='|'&&lc=='|'){
				
			}else if(ch=='&'&&lc=='&'){
				
			}else{
				return false;//否就返回false
			}
			return true;//其它符号的情况都返回true
		}
	}
	
	/**
	 * 获取关键字的机器码
	 */
	public int getKeywordOpcodes(String str){
		
		int i;	
		for(i=0;i<keyword.length;i++){
			if(str.equals(keyword[i]))
				break;
		}
		if(i<keyword.length){
			return i+1;//返回关键字的机器码
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取操作符的机器码
	 */
	public int getOperatorOpcodes(String str){
		int i;
		for(i=0;i<operator.length;i++){
			if(str.equals(operator[i]))
				break;
		}
		
		if(i<operator.length)
			return i+21;//返回操作符的机器码
		else
			return 0;
	}
	
	/**
	 * 获取分界符的机器码
	 */
	public int getDelimiterOpcodes(String str){
		int i;
		for(i=0;i<delimiter.length;i++){
			if(str.equals(delimiter[i]))
				break;
		}
		
		if(i<delimiter.length)
			return i+41;//返回分界符的机器码
		else
			return 0;
	}
		
	/**
	 * 判断字符是否可以识别
	 */
	public boolean isIdent(String str){
		char ch;
		int i;
		for(i=0;i<str.length();i++){
			ch=str.charAt(i);
			//非数字串的情况和非由英文字母组成的字符串
			if((i==0&&!isLetter(ch))||(!isDigit(ch)&&!isLetter(ch))){
				break;
			}
		}
		
		if(i<str.length()){
			return false;
	    }else{
			return true;
	    }
	}
	
	/**
	 * 
	 * 预处理函数
	 */
	public String preFunction(String str){
		String ts="";
		
		int i;
		
		char ch,nc;
		//这里的i<str.length()-1
		for(i=0;i<str.length()-1;i++){	
			ch=str.charAt(i);
			nc=str.charAt(i+1);
			
			if(ch=='\n'){//如果字符是换行符,将\n换成$
				ch='$';
				ts=ts+ch;
			}else if(ch==' '||ch=='\r'||ch=='\t'){
				if(nc==' '||nc=='\r'||ch=='\t'){
					continue;//连续' '或者'\t'或者'\r'的情况，直接跳过
				}else{
					ch=' ';//一个' '或者'\t'或者'\r'的情况，将这些字符换成' '
					ts=ts+ch;
				}
			}else{
				ts=ts+ch;//将字符连起来
			}
		}
		
		//
		ch=str.charAt(str.length()-1);
		if(ch!=' '&&ch!='\r'&&ch!='\t'&&ch!='\n'){
			ts=ts+ch;
		}
		return ts;
	}
	
	/**
	 * 将字符串分成一个个单词，存放在数组列表
	 */
	public ArrayList<Word> divide(String str){
		ArrayList<Word> list=new ArrayList<Word>();
		
		String s="";
		char ch;
		int i;
		int row=1;
		
		for(i=0;i<str.length();i++){
			ch=str.charAt(i);
			if(i==0&&ch==' ')//字符串的第一个字符
				continue;
			if(ch==' '){//' '或者'\t'或者'\r'的情况
				if(s!=""){
					list.add(new Word(row, s));
					s="";//置空
				}else{
					continue;
				}
			}else if(isDigit(ch)||isLetter(ch)){
				if(s==""||isDigit(s.charAt(s.length()-1))||isLetter(s.charAt(s.length()-1))){
					s = s + ch;
				}else{
					list.add(new Word(row, s));
					s = "";
					s=s + ch;
				}	
			}else{
				if(isTwoOperator(s, ch)){//两个运算符的情况
					s = s + ch;
				}else{
					if(s==""&&ch!='$'){
						s = s + ch;
					}else if(s==""&&ch=='$'){//若检测到$符号，就换行
						row++;//行数加一
					}else{
						list.add(new Word(row, s));
						s = "";
						if(ch!='$'){
							s=s + ch;
						}else{
							row++;
						}
					}
				}	
			}
		}
		if(s!=""){
			list.add(new Word(row, s));
		}
		return list;
	}
	
	/**
	 * 判断字符串是数字串，单个字符，还是一个字符串
	 */
	public int check(String str){
		char ch;
		ch=str.charAt(0);
		if(ch>='0'&&ch<='9'){
			return 1;//数字串
		}
		if(str.length()==1)
			return 2;//单个字符
		else
			return 3;//一个字符串
	}
	
	/**
	 * 
	 * 检查字符串是否为数字串，返回其机器码
	 */
	public int checkDigit(String str){
		int i;
		char ch;
		for(i=0;i<str.length();i++){
			ch=str.charAt(i);
			if(ch>'9'||ch<'0')
				break;
		}
		if(i<str.length()){
			return 0;//不可识别的情况
		}else{
			return 52;//常数
		}
	}
	
	/**
	 * 
	 * 检查字符串是否为单个字符，返回其机器码
	 */
	public int checkChar(String str){
		if(getOperatorOpcodes(str)!=0){//操作符
			return getOperatorOpcodes(str);
		}else if(getDelimiterOpcodes(str)!=0){//分界符
			return getDelimiterOpcodes(str);
		}else if(isIdent(str)){
			return 51;//用户自定义标识符的机器码
		}else{
			return 0;//不可以被识别的标识符，机器码为0
		}
	}
	
	/**
	 * 
	 * 检查字符串是否为字符串，返回其机器码
	 */
	public int checkString(String str){
		if(getOperatorOpcodes(str)!=0){//操作符
			return getOperatorOpcodes(str);
		}else if(getKeywordOpcodes(str)!=0){//关键字
			return getKeywordOpcodes(str);
	   }else if(isIdent(str)){
			return 51;//用户自定义标识符的机器码
	   }else{
			return 0;//不可以被识别的标识符，机器码为0
	   }
	}
	
	
}
