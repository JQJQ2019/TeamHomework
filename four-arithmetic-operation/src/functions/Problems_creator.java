package functions;

import java.util.Random;
import java.util.Stack;

public class Problems_creator {
	private static final String[]  ARITHMETIC_SYMBOL = {"+", "-", "*", "÷"};
	//这里的"+", "-", "*", "÷","(",")"六个符号的下表各自为0，1，2，3，5，6
	
	public 	static boolean 	W 					= false;					//一个重要的判断标志
	Algorithm 				AL 					= new Algorithm();			
	Fraction_creator 		FC 					= new Fraction_creator();
	Integer_creator 		IC 					= new Integer_creator();
	static 	int[] 			Numbers 			= {0,0,0};										
	static 	int				BracketCount		= 0;						//括号统计数
	static 	boolean 		head				= false;					//判断开头是否有括号
	StringBuilder 			formula 			= new StringBuilder();		//可修改的String类式子
	Stack<int[]> 			numStack 			= new Stack<>();			//操作数栈
	Stack<Integer > 		symbolStack 		= new Stack<>();			//操作符栈
	
	public String[] createProblem(int range){
		Random 	S_random 		= new Random();				//符号数量随机数
		Random 	I_OR_F_random 	= new Random();				//真分数与整数标志随机数
		Random 	Bracket_random 	= new Random();				//括号生成随机数
	    int 	symbolCount 	= 1 + S_random.nextInt(3);	//生成算数符号个数
	    int[] 	symbol 			= AL.symbolCreate(symbolCount, 4, S_random);//符号数组
	    int		i				= 0; 
	    int 	far				= 0;						//括号距离，必须要大于0才能生成")"
	    
	    //考虑到小学生的运算难度，不能全部生成真分数，所以这里随机生成的真分数和整数的概率都为50%
	    //左括号"("生成判断。
	    for( i=0; i<symbolCount+1; i++){
	    	//左符号"("是不可以在最后一个操作数左右的。
	    	if(i<symbolCount&&symbolCount!=1){
	    		int Bracket = Bracket_random.nextInt(2);
	    		if( Bracket==0 && head==true && i==symbolCount-1);
	    		else if( Bracket==0 ){
	    			formula.append("( ");
	    			symbolStack.push(5);
	    			if(i==0) head=true;	
	    			BracketCount++;far=0;
	    		}else far=1;
	    	}
	    	int I_OR_F = I_OR_F_random.nextInt(2);
	    	String 	Num =	"";
	    	String 	smb =	"";
	    	//真分数与整数的随机生成，各自几率50%
	    	if (I_OR_F == 0){
	    		int[] 	F = FC.Fraction(range);
	    				F = AL.Proper_Fraction(F);
	    		Num = AL.Simplification(F);
	    		if( i!=symbolCount ) smb = ARITHMETIC_SYMBOL[symbol[i]];
	    		numStack.push(F);
	    		formula.append(Num+" ");
	    		//右括号")"生成判断
	    		if( i>0 && i<symbolCount-1 && symbolCount!=1){
		    		int Bracket = Bracket_random.nextInt(2);
		    		//BracketCount存在代表前面有"("，如果不加以判断会出现e1-e2)(e3-e4，是错误的
		    		//far必须大于0，括号不能只括在一个操作数身上
		    		if( Bracket==0 && far!=0 && BracketCount!=0){
		    			formula.append(") ");
		    			symbolStack.push(6);
		    			BracketCount--;
		    			if(BracketCount==0) far=0;
		    		}
		    	}
	    		//head存在，式子最开头有左括号，式子最后就不能有右括号，并且在倒数第二个操作数后面加上剩下的右括号
	    		if( head==true ){
		    		if( BracketCount!=0 && i==symbolCount-1 ){
		    			for (int n=0; n<BracketCount;n++){
		    				formula.append(") ");
		    				symbolStack.push(6);
		    			}
		    		}
		    	}
	    		//在最后补上剩下的右括号
	    		else if ( BracketCount!=0 && i==symbolCount){
	    			for (int n=0; n<BracketCount;n++){
	    				formula.append(") ");
	    				symbolStack.push(6);
	    			}
	    		}
	    		if( i!=symbolCount ) formula.append(smb+" ");
	    		else formula.append("= ");
	    		if( i!=symbolCount ) symbolStack.push(symbol[i]);		
	    	}else {
	    		int[] I = IC.Integer(range);
	    		Num = I[0]+" ";
	    		if(i!=symbolCount) smb = ARITHMETIC_SYMBOL[symbol[i]];
	    		numStack.push(I);
	    		formula.append(Num+" ");
	    		if( i>0 && i<symbolCount-1 && symbolCount!=1){
		    		int Bracket = Bracket_random.nextInt(2);
		    		if(Bracket==0 && far!=0 && BracketCount!=0){
		    			formula.append(") ");
		    			symbolStack.push(6);
		    			BracketCount--;
		    			if(BracketCount==0) far=0;
		    		}
		    	}
	    		if( head==true ){
		    		if(BracketCount!=0 && i==symbolCount-1){
		    			for (int n=0; n<BracketCount;n++){
		    				formula.append(") ");
		    				symbolStack.push(6);
		    			}
		    		}
		    	}
	    		else if (BracketCount!=0 && i==symbolCount){
	    			for (int n=0; n<BracketCount;n++){
	    				formula.append(") ");
	    				symbolStack.push(6);
	    			}
	    		}
	    		if(i!=symbolCount) formula.append(smb+" ");
	    		else formula.append("= ");
	    		if( i!=symbolCount ) symbolStack.push(symbol[i]);  		
	    	}
	    }
	    //此时的formula如果不出现问题，是一个完整的式子
	    int[] 	n 		= AL.CalculatorStack(numStack, symbolStack);
	    		n 		= AL.Proper_Fraction(n);
	    //字符串tail是这一条式子的答案
	    String 	tail 	= "";
	    		tail 	= AL.Simplification(n);	    
	    BracketCount	= 0;
	    String formulaRes[] = {formula.toString(), tail};
	    //formula不及时清空会出错
	    formula.replace(0, formula.length(),"");
	    //计算中途出现负数，formula就有问题，布尔W处理这种情况，W的修改可在Algorithm减法操作看得到
	    if(Problems_creator.W==true){
		    while(Problems_creator.W){
		    	//不符合要求就重新在来一次
		    	Problems_creator P = new Problems_creator();
		    	formulaRes = P.createProblem(range);
		    }
	    }
	    return 	formulaRes;
	}
	
	//main方法是用来调试用的
	public static void main(String[] args){
		Problems_creator P = new Problems_creator();
		for(int i=0;i<20;i++){
			P.createProblem(7);
		}
	}
}
