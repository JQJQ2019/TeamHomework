package functions;

import java.util.Random;
import java.util.Stack;

public class Algorithm {
	
	//这是一个生成操作符号下表的函数
	public int[] symbolCreate(int symbolCount,int Total, Random random){
        int 	similar = 0;
        int[] 	SCreate = new int[symbolCount];
        for(int i = 0; i < symbolCount; i++){
            SCreate[i] = random.nextInt(Total);
        }
        //保证所有的操作符号不会相同
        for (int i : SCreate) {
            if(SCreate[0] == i) similar++;
        }
        if(similar == symbolCount && symbolCount != 1) return symbolCreate(symbolCount, Total, random);
        else return SCreate;
    }
	
	//化简真分数的函数
	public String Simplification(int[] F){
		String Num	= "";
		F = Proper_Fraction(F);
		if (F[2]!=0){
			int reduction = Reduction(F[1], F[2]);
			F[1] /= reduction;
			F[2] /= reduction;
			//真分数和整数的区别在于F[1]和F[2]是否为0，如都不是，便是真分数，否则为整数
			//但是程序运算过程仍然会保留整数的0分母
    		if(F[1] == F[2]) {
    			if(F[0] == 0) Num = "1";
    			else Num = F[0]+"";
    		}else if (F[0] == 0){
    			if(F[1] == F[2]) Num = "1";
    			else Num = F[1] + "/" + F[2];
    		}
    		else if(F[1] == 0||F[2]==0) Num = F[0]+"";
    		else Num = F[0] + "'" + F[1] + "/" + F[2];
		}else Num = F[0] + "";
		return Num;
	}
	
	//最大公因数的函数
	public int Reduction(int x,int y) {
        while(true){
            if(x % y == 0) 
            	return y;
            int temp = y;
            y = x % y;
            x = temp;
        }
    }
	
	//形成真分数形式
	public int[] Proper_Fraction(int[] F_Num){
		int[] f_Num = F_Num;
		int h = f_Num[0];
	    int x = f_Num[1];
	    int y = f_Num[2];
	    if(y==0||x==0) {
	    	f_Num[0]=h;
	    	f_Num[1]=0;
	    	f_Num[2]=0;
	    	return f_Num;
	    }
		int n = x / y;
		int sum = h+n;
		if (x > y){
            x = (x - n * y);
            f_Num[0] = sum;
    	    f_Num[1] = x;
    	    f_Num[2] = y;
            return f_Num;
        }
        else if (x == y) {
        	f_Num[0] = sum;
    	    f_Num[1] = 0;
    	    f_Num[2] = 0;
        	return f_Num;
        }
        else if (y == 1) {
        	f_Num[0] = sum+x;
    	    f_Num[1] = 0;
    	    f_Num[2] = 0;
        	return f_Num;
        }
        else if (x == 0) {
        	f_Num[0] = sum;
    	    f_Num[1] = 0;
    	    f_Num[2] = y;
        	return f_Num;
        }
        return f_Num;
    }
	
	/*整个程序最重要的运算函数
	 * 文件末尾保留main函数的注释，是用来做调试的
	 * 这个函数用整形数组int[]{a,b,c}的方式进行运算的
	 * a为整数部分，b为分子，c为分母
	 */
	public int[] CalculatorStack(Stack<int[]> numStack,Stack<Integer> symbolStack){
		Stack<int[]> 	num 		= new Stack<>();	//需要用操作数计算而记录操作数的栈
		Stack<Integer > symbol 		= new Stack<>();	//需要用操作符计算而记录操作符的栈
		Stack<Integer > judge 		= new Stack<>();	//括号判断栈
		int[] 			barcketRes 	= null;
		while(!symbolStack.empty()){
			int s = symbolStack.pop();
			//右括号下表为"6"，遇上了右括号，则继续往里面深推
			if(s==6){
				int j1 = 0;
				judge.push(j1);
			}
			//左括号下表为"5"，遇上了左括号，按规定算法运算
			else if(s==5){
				//把左括号右边的操作数入栈
				if(!numStack.empty()){
					int head[] = numStack.pop();
					num.push(head);
				}
				//judge存在，既有括号，judge数值代表括号操作数个数
				int j2 = judge.pop();
				if(j2 ==1){
					int[] 	n1	= num.pop();
					int[] 	n2	= num.pop();
					int 	s1 	= symbol.pop();
					int[] 	sum = Calculator(s1,n1,n2);
					num.push(sum);
					barcketRes = sum;
				}else if(j2 ==2){
					int[] 	sum	= {0,0,0};
					int[] 	n1	= num.pop();
					int[] 	n2	= num.pop();
					int[] 	n3	= num.pop();
					int 	s1 	= symbol.pop();
					int 	s2 	= symbol.pop();
					//乘除法优先，然后判断减法位置，再弄加法，做出正确的算数过程
					if(( s1==0&&s2==0 )|| s2==2 || s2==3 ){
						sum = Calculator(s2,n2,n3);
						sum = Calculator(s1,n1,sum);
					}else{
						sum = Calculator(s1,n1,n2);
						sum = Calculator(s2,sum,n3);
					}					
					num.push(sum);
					barcketRes = sum;
				}
			}
			//没有括号直接入栈
			else {
				int[] n1 ;
				if (barcketRes!=null){
					n1=num.pop();
					barcketRes=null;
				}else n1= numStack.pop();
				num.push(n1);
				symbol.push(s);
				//中途没有括号，自己却又被一个括号包围，judge增加距离
				if(!judge.empty()){
					int j3 = judge.pop();
					j3++;
					judge.push(j3);
				}
			}	
		}
		//把剩下的操作数入栈
		if(!numStack.empty()){
			int[] r = numStack.pop();
			num.push(r);
		}
		Stack<int[]> 	Num 	= new Stack<>();
		Stack<Integer > Symbol 	= new Stack<>();
		//此时所有括号都去掉了，下面是运算所有的乘除法
		while(!symbol.empty()){
			int s = symbol.pop();
			int[] n1= num.pop();
			if(s==2||s==3){
				int[] n2= num.pop();
				int[] n = Calculator(s,n1,n2);
				num.push(n);
			}else{
				Symbol.push(s);
				Num.push(n1);
			}	
		}
		if(!num.empty()){
			int[] n = num.pop();
			Num.push(n);
		}
		//最后剩下的就是加减法
		while(!Symbol.empty()){
			int[] n1 = Num.pop();
			int[] n2 = Num.pop();
			int s = Symbol.pop();
			int[] n3 = Calculator(s,n2,n1);
			Num.push(n3);
		}
		int[] res = Num.pop(); 
		return res;
	}
	
	/*适用于int[]{a,b,c}的四则运算
	 * 每个运算都有四个判断，分别为n1是整数，n2是整数，n1、n2均为整数和n1、n2均为真分数
	 */
	public int[] Calculator(int symbol,int[] n1,int[] n2){
		Algorithm AL = new Algorithm();
		int[] num = {0,0,0};
		switch(symbol){
			case 0:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n2[1];
					num[2] = n2[2];
					num[0] = n1[0]+n2[0];
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = n1[1];
					num[2] = n1[2];
					num[0] = n1[0]+n2[0];
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]+n2[0];
				}else{
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])+((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
				}
				break;
			case 1:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					n1[0]=n1[0]-1;
					n1[1]=1;
					n1[2]=1;
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					n2[0]=n2[0]-1;
					n2[1]=1;
					n2[2]=1;
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]-n2[0];
				}else{
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
				}
				if(num[1]<0||num[0]<0) Problems_creator.W=true;
				else Problems_creator.W=false;
				break;
			case 2:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n1[0]*(n2[0]*n2[2]+n2[1]);
					num[2] = n2[2];
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = (n1[0]*n1[2]+n1[1])*n2[0];
					num[2] = n1[2];
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]*n2[0];
				}else{
					num[1] = (n1[0]*n1[2]+n1[1])*(n2[0]*n2[2]+n2[1]);
					num[2] = n1[2]*n2[2];
				}
				break;
			case 3:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n1[0]*n2[2];
					num[2] = (n2[0]*n2[2]+n2[1]);
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = n1[0]*n1[2]+n1[1];
					num[2] = n1[2]*n2[0];
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[1]=n1[0];
					num[2]=n2[0];
				}else{
					num[1] = (n1[0]*n1[2]+n1[1])*n2[2];
					num[2] = n1[2]*(n2[0]*n2[2]+n2[1]);
				}
				break;
			default:break;
		}
		num = AL.Proper_Fraction(num);
		return num;
	}
	//调试用的main函数
	/*public static void main(String[] args){
		Algorithm P = new Algorithm();
		Stack<int[]> num = new Stack<>();
		Stack<Integer > symbol = new Stack<>();
		int[] n1 = {3,0,0};
		int[] n2 = {1,2,3};
		int[] n3 = {2,0,0};
		int[] n4 = {2,0,0};
		num.push(n1);
		num.push(n2);
		num.push(n3);
		num.push(n4);
		symbol.push(0);
		symbol.push(0);
		symbol.push(2);
		//symbol.push(1);
		//symbol.push(6);
		//symbol.push(6);
		//symbol.push(1);
		int[] n = P.CalculatorStack(num, symbol);
		//int[] n = P.Calculator(0, n4, n4);
		n = P.Proper_Fraction(n);
		String Num = n[0] + "'" + n[1] + "/" + n[2];
		System.out.println(Num);
	}*/
	
	
}
