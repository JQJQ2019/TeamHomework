package ±¸·Ý;

import java.util.Random;
import java.util.Stack;

import functions.Problems_creator;

public class Algorithm {
	
	public int[] symbolCreate(int symbolCount,int Total, Random random){
        int 	similar = 0;
        int[] 	SCreate = new int[symbolCount];
        for(int i = 0; i < symbolCount; i++){
            SCreate[i] = random.nextInt(Total);
        }
        for (int i : SCreate) {
            if(SCreate[0] == i) similar++;
        }
        if(similar == symbolCount && symbolCount != 1) return symbolCreate(symbolCount, Total, random);
        else return SCreate;
    }
	
	public String Simplification(int[] F){
		String Num	= "";
		F = Proper_Fraction(F);
		if (F[2]!=0){
			int reduction = Reduction(F[1], F[2]);
			F[1] /= reduction;
			F[2] /= reduction;
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
	
	public int Reduction(int x,int y) {
        while(true){
            if(x % y == 0) 
            	return y;
            int temp = y;
            y = x % y;
            x = temp;
        }
    }
	
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
	
	public int[] CalculatorStack(Stack<int[]> numStack,Stack<Integer> symbolStack){
		Stack<int[]> 	num 		= new Stack<>();
		Stack<Integer > symbol 		= new Stack<>();
		Stack<Integer > judge 		= new Stack<>();
		int[] 			barcketRes 	= null;
		while(!symbolStack.empty()){
			int s = symbolStack.pop();
			if(s==6){
				int j1 = 0;
				judge.push(j1);
			}else if(s==5){
				if(!numStack.empty()){
					int head[] = numStack.pop();
					num.push(head);
				}
				int j2 = judge.pop();
				if(j2 ==1){
					int[] n1= num.pop();
					int[] n2= num.pop();
					int s1 = symbol.pop();
					int[] sum = Calculator(s1,n1,n2);
					num.push(sum);
					barcketRes = sum;
					System.out.println(s1+"!");
					String Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
					System.out.println(Num1+"!");
					String Num2 = n2[0] + "'" + n2[1] + "/" + n2[2];
					System.out.println(Num2+"!");
					String Num = sum[0] + "'" + sum[1] + "/" + sum[2];
					System.out.println(Num+"!");
				}else if(j2 ==2){
					int[] 	sum	= {0,0,0};
					int[] 	n1	= num.pop();
					int[] 	n2	= num.pop();
					int[] 	n3	= num.pop();
					int 	s1 	= symbol.pop();
					int 	s2 	= symbol.pop();
					if(( s1==0&&s2==0 )|| s2==2 || s2==3 ){
						sum = Calculator(s2,n2,n3);
						sum = Calculator(s1,n1,sum);
						String Num = sum[0] + "'" + sum[1] + "/" + sum[2];
						System.out.println(Num+"@+1");
					}else{
						sum = Calculator(s1,n1,n2);
						String Num = sum[0] + "'" + sum[1] + "/" + sum[2];
						System.out.println(Num+"@+2");
						sum = Calculator(s2,sum,n3);
						Num = sum[0] + "'" + sum[1] + "/" + sum[2];
						System.out.println(Num+"@+22");
					}					
					num.push(sum);
					barcketRes = sum;
					System.out.println(s1+"@++4");
					String Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
					System.out.println(Num1+"@++1");
					String Num2 = n2[0] + "'" + n2[1] + "/" + n2[2];
					System.out.println(Num2+"@++2");
					String Num3 = n3[0] + "'" + n3[1] + "/" + n3[2];
					System.out.println(Num3+"@++3");
					String Num = sum[0] + "'" + sum[1] + "/" + sum[2];
					System.out.println(Num+"@++4");
				}
			}else {
				int[] n1 ;
				if (barcketRes!=null){
					String Num1 = barcketRes[0] + "'" + barcketRes[1] + "/" + barcketRes[2];
					System.out.println(Num1+"***+2");
					n1=num.pop();
					Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
					System.out.println(Num1+"#+2");
					barcketRes=null;
				}else n1= numStack.pop();
				num.push(n1);
				symbol.push(s);
				System.out.println(s+"#+1");
				String Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
				System.out.println(Num1+"#+2");
				if(!judge.empty()){
					int j3 = judge.pop();
					j3++;
					judge.push(j3);
				}
			}System.out.println(s+"$");	
		}
		if(!numStack.empty()){
			int[] r = numStack.pop();
			num.push(r);
			String Num4 = r[0] + "'" +r[1] + "/" + r[2];
			System.out.println(Num4+"%");
		}
		Stack<int[]> Num = new Stack<>();
		Stack<Integer > Symbol = new Stack<>();
		while(!symbol.empty()){
			int s = symbol.pop();
			int[] n1= num.pop();
			if(s==2||s==3){
				int[] n2= num.pop();
				int[] n = Calculator(s,n1,n2);
				num.push(n);
				System.out.println(s+"&1");
				String Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
				System.out.println(Num1+"&2");
				String Num2 = n2[0] + "'" + n2[1] + "/" + n2[2];
				System.out.println(Num2+"&3");
				String Num3 = n[0] + "'" + n[1] + "/" + n[2];
				System.out.println(Num3+"&4");
				//Num.push(n);
			}else{
				Symbol.push(s);
				System.out.println(s+"|||");
				Num.push(n1);
				String Num3 = n1[0] + "'" + n1[1] + "/" + n1[2];
				System.out.println(Num3+"&-1...");
			}	
		}
		if(!num.empty()){
			int[] n = num.pop();
			Num.push(n);
			String Num5 = n[0] + "'" + n[1] + "/" + n[2];
			System.out.println(Num5+"...");
		}
		while(!Symbol.empty()){
			int[] n1 = Num.pop();
			int[] n2 = Num.pop();
			int s = Symbol.pop();
			int[] n3 = Calculator(s,n2,n1);
			Num.push(n3);
			System.out.println(s+"#$1#");
			String Num1 = n1[0] + "'" + n1[1] + "/" + n1[2];
			System.out.println(Num1+"#$2#");
			String Num2 = n2[0] + "'" + n2[1] + "/" + n2[2];
			System.out.println(Num2+"#3#");
			String Num3 = n3[0] + "'" + n3[1] + "/" + n3[2];
			System.out.println(Num3+"#$4#");
		}
		int[] res = Num.pop(); 
		return res;
	}
	public int[] Calculator(int symbol,int[] n1,int[] n2){
		Algorithm AL = new Algorithm();
		int[] num = {0,0,0};
		switch(symbol){
			case 0:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n2[1];
					num[2] = n2[2];
					num[0] = n1[0]+n2[0];
					System.out.println(num[1]+"[+][1]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = n1[1];
					num[2] = n1[2];
					num[0] = n1[0]+n2[0];
					System.out.println(num[1]+"[+][2]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]+n2[0];
					System.out.println(num[1]+"[+][3]");	
					System.out.println(num[2]+"[][]");
				}else{
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])+((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
					System.out.println(num[1]+"[+][4]");	
					System.out.println(num[2]+"[][]");
				}
				break;
			case 1:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					n1[0]=n1[0]-1;
					n1[1]=1;
					n1[2]=1;
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
					System.out.println(num[1]+"[-][1]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					n2[0]=n2[0]-1;
					n2[1]=1;
					n2[2]=1;
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
					System.out.println(num[1]+"[-][2]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]-n2[0];
					System.out.println(num[1]+"[-][3]");	
					System.out.println(num[2]+"[][]");
				}else{
					num[1] = ((n1[0]*n1[2]+n1[1])*n2[2])-((n2[0]*n2[2]+n2[1])*n1[2]);
					num[2] = n1[2]*n2[2];
					System.out.println(num[1]+"[-][4]");	
					System.out.println(num[2]+"[][]");
				}
				if(num[1]<0||num[0]<0) Problems_creator.W=true;
				else Problems_creator.W=false;
				break;
			case 2:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n1[0]*(n2[0]*n2[2]+n2[1]);
					num[2] = n2[2];
					System.out.println(num[1]+"[*][1]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = (n1[0]*n1[2]+n1[1])*n2[0];
					num[2] = n1[2];
					System.out.println(num[1]+"[*][2]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[0] = n1[0]*n2[0];
					System.out.println(num[1]+"[*][3]");	
					System.out.println(num[2]+"[][]");
				}else{
					num[1] = (n1[0]*n1[2]+n1[1])*(n2[0]*n2[2]+n2[1]);
					num[2] = n1[2]*n2[2];
					System.out.println(num[1]+"[*][4]");	
					System.out.println(num[2]+"[][]");
				}
				break;
			case 3:
				if((n1[1]==0||n1[2]==0)&&(n2[1]!=0&&n2[2]!=0)){
					num[1] = n1[0]*n2[2];
					num[2] = (n2[0]*n2[2]+n2[1]);
					System.out.println(num[1]+"[/][1]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]!=0&&n1[2]!=0)&&(n2[1]==0||n2[2]==0)){
					num[1] = n1[0]*n1[2]+n1[1];
					num[2] = n1[2]*n2[0];
					System.out.println(num[1]+"[/][2]");	
					System.out.println(num[2]+"[][]");
				}else if((n1[1]==0||n1[2]==0)&&(n2[1]==0||n2[2]==0)){
					num[1]=n1[0];
					num[2]=n2[0];
					System.out.println(num[1]+"[/][3]");	
					System.out.println(num[2]+"[][]");
				}else{
					num[1] = (n1[0]*n1[2]+n1[1])*n2[2];
					num[2] = n1[2]*(n2[0]*n2[2]+n2[1]);
					System.out.println(num[1]+"[/][4]");	
					System.out.println(num[2]+"[][]");
				}
				break;
			default:break;
		}
		num = AL.Proper_Fraction(num);
		return num;
	}
	public static void main(String[] args){
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
	}
	
	
}
