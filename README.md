# lexer1.1.0
Java编写的C语言词法分析器

1~20号为关键字,用下标表示,i+1就是其机器码;
21~40号为操作符,用下标表示,i+21就是其机器码;
41~60号为分界符, 用下标表示,i+41就是其机器码;
用户自定义的标识符，其机器码为51;
常数的机器码为52；
不可以识别的标识符，其机器码为0


 选择打开按钮，选择文件所在的路径，单击打开，分析器会获取文件的内容，并将其复制到文本域里，本词法分析器就可以对文本域里的内容进行剪切复制黏贴和清除等编辑操作
 
保存文件，选择文件的保存路径，点击保存，即可进行文件保存
 

进行词法分析，输出标识符所在的行及其机器码，不可以被识别的在控制台输出
 
     用户可以查看帮助文档，点击帮助按钮就可以打开
 


附录：核心代码
 

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

