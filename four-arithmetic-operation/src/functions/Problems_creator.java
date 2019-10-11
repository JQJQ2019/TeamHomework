package functions;

import java.util.Random;
import java.util.Stack;

public class Problems_creator {
	private static final String[]  ARITHMETIC_SYMBOL = {"+", "-", "*", "��"};
	//�����"+", "-", "*", "��","(",")"�������ŵ��±����Ϊ0��1��2��3��5��6
	
	public 	static boolean 	W 					= false;					//һ����Ҫ���жϱ�־
	Algorithm 				AL 					= new Algorithm();			
	Fraction_creator 		FC 					= new Fraction_creator();
	Integer_creator 		IC 					= new Integer_creator();
	static 	int[] 			Numbers 			= {0,0,0};										
	static 	int				BracketCount		= 0;						//����ͳ����
	static 	boolean 		head				= false;					//�жϿ�ͷ�Ƿ�������
	StringBuilder 			formula 			= new StringBuilder();		//���޸ĵ�String��ʽ��
	Stack<int[]> 			numStack 			= new Stack<>();			//������ջ
	Stack<Integer > 		symbolStack 		= new Stack<>();			//������ջ
	
	public String[] createProblem(int range){
		Random 	S_random 		= new Random();				//�������������
		Random 	I_OR_F_random 	= new Random();				//�������������־�����
		Random 	Bracket_random 	= new Random();				//�������������
	    int 	symbolCount 	= 1 + S_random.nextInt(3);	//�����������Ÿ���
	    int[] 	symbol 			= AL.symbolCreate(symbolCount, 4, S_random);//��������
	    int		i				= 0; 
	    int 	far				= 0;						//���ž��룬����Ҫ����0��������")"
	    
	    //���ǵ�Сѧ���������Ѷȣ�����ȫ���������������������������ɵ�������������ĸ��ʶ�Ϊ50%
	    //������"("�����жϡ�
	    for( i=0; i<symbolCount+1; i++){
	    	//�����"("�ǲ����������һ�����������ҵġ�
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
	    	//�������������������ɣ����Լ���50%
	    	if (I_OR_F == 0){
	    		int[] 	F = FC.Fraction(range);
	    				F = AL.Proper_Fraction(F);
	    		Num = AL.Simplification(F);
	    		if( i!=symbolCount ) smb = ARITHMETIC_SYMBOL[symbol[i]];
	    		numStack.push(F);
	    		formula.append(Num+" ");
	    		//������")"�����ж�
	    		if( i>0 && i<symbolCount-1 && symbolCount!=1){
		    		int Bracket = Bracket_random.nextInt(2);
		    		//BracketCount���ڴ���ǰ����"("������������жϻ����e1-e2)(e3-e4���Ǵ����
		    		//far�������0�����Ų���ֻ����һ������������
		    		if( Bracket==0 && far!=0 && BracketCount!=0){
		    			formula.append(") ");
		    			symbolStack.push(6);
		    			BracketCount--;
		    			if(BracketCount==0) far=0;
		    		}
		    	}
	    		//head���ڣ�ʽ���ͷ�������ţ�ʽ�����Ͳ����������ţ������ڵ����ڶ����������������ʣ�µ�������
	    		if( head==true ){
		    		if( BracketCount!=0 && i==symbolCount-1 ){
		    			for (int n=0; n<BracketCount;n++){
		    				formula.append(") ");
		    				symbolStack.push(6);
		    			}
		    		}
		    	}
	    		//�������ʣ�µ�������
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
	    //��ʱ��formula������������⣬��һ��������ʽ��
	    int[] 	n 		= AL.CalculatorStack(numStack, symbolStack);
	    		n 		= AL.Proper_Fraction(n);
	    //�ַ���tail����һ��ʽ�ӵĴ�
	    String 	tail 	= "";
	    		tail 	= AL.Simplification(n);	    
	    BracketCount	= 0;
	    String formulaRes[] = {formula.toString(), tail};
	    //formula����ʱ��ջ����
	    formula.replace(0, formula.length(),"");
	    //������;���ָ�����formula�������⣬����W�������������W���޸Ŀ���Algorithm�����������õ�
	    if(Problems_creator.W==true){
		    while(Problems_creator.W){
		    	//������Ҫ�����������һ��
		    	Problems_creator P = new Problems_creator();
		    	formulaRes = P.createProblem(range);
		    }
	    }
	    return 	formulaRes;
	}
	
	//main���������������õ�
	public static void main(String[] args){
		Problems_creator P = new Problems_creator();
		for(int i=0;i<20;i++){
			P.createProblem(7);
		}
	}
}
