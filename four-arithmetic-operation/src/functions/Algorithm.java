package functions;

import java.util.Random;
import java.util.Stack;

public class Algorithm {
	
	//����һ�����ɲ��������±�ĺ���
	public int[] symbolCreate(int symbolCount,int Total, Random random){
        int 	similar = 0;
        int[] 	SCreate = new int[symbolCount];
        for(int i = 0; i < symbolCount; i++){
            SCreate[i] = random.nextInt(Total);
        }
        //��֤���еĲ������Ų�����ͬ
        for (int i : SCreate) {
            if(SCreate[0] == i) similar++;
        }
        if(similar == symbolCount && symbolCount != 1) return symbolCreate(symbolCount, Total, random);
        else return SCreate;
    }
	
	//����������ĺ���
	public String Simplification(int[] F){
		String Num	= "";
		F = Proper_Fraction(F);
		if (F[2]!=0){
			int reduction = Reduction(F[1], F[2]);
			F[1] /= reduction;
			F[2] /= reduction;
			//���������������������F[1]��F[2]�Ƿ�Ϊ0���綼���ǣ����������������Ϊ����
			//���ǳ������������Ȼ�ᱣ��������0��ĸ
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
	
	//��������ĺ���
	public int Reduction(int x,int y) {
        while(true){
            if(x % y == 0) 
            	return y;
            int temp = y;
            y = x % y;
            x = temp;
        }
    }
	
	//�γ��������ʽ
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
	
	/*������������Ҫ�����㺯��
	 * �ļ�ĩβ����main������ע�ͣ������������Ե�
	 * �����������������int[]{a,b,c}�ķ�ʽ���������
	 * aΪ�������֣�bΪ���ӣ�cΪ��ĸ
	 */
	public int[] CalculatorStack(Stack<int[]> numStack,Stack<Integer> symbolStack){
		Stack<int[]> 	num 		= new Stack<>();	//��Ҫ�ò������������¼��������ջ
		Stack<Integer > symbol 		= new Stack<>();	//��Ҫ�ò������������¼��������ջ
		Stack<Integer > judge 		= new Stack<>();	//�����ж�ջ
		int[] 			barcketRes 	= null;
		while(!symbolStack.empty()){
			int s = symbolStack.pop();
			//�������±�Ϊ"6"�������������ţ����������������
			if(s==6){
				int j1 = 0;
				judge.push(j1);
			}
			//�������±�Ϊ"5"�������������ţ����涨�㷨����
			else if(s==5){
				//���������ұߵĲ�������ջ
				if(!numStack.empty()){
					int head[] = numStack.pop();
					num.push(head);
				}
				//judge���ڣ��������ţ�judge��ֵ�������Ų���������
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
					//�˳������ȣ�Ȼ���жϼ���λ�ã���Ū�ӷ���������ȷ����������
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
			//û������ֱ����ջ
			else {
				int[] n1 ;
				if (barcketRes!=null){
					n1=num.pop();
					barcketRes=null;
				}else n1= numStack.pop();
				num.push(n1);
				symbol.push(s);
				//��;û�����ţ��Լ�ȴ�ֱ�һ�����Ű�Χ��judge���Ӿ���
				if(!judge.empty()){
					int j3 = judge.pop();
					j3++;
					judge.push(j3);
				}
			}	
		}
		//��ʣ�µĲ�������ջ
		if(!numStack.empty()){
			int[] r = numStack.pop();
			num.push(r);
		}
		Stack<int[]> 	Num 	= new Stack<>();
		Stack<Integer > Symbol 	= new Stack<>();
		//��ʱ�������Ŷ�ȥ���ˣ��������������еĳ˳���
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
		//���ʣ�µľ��ǼӼ���
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
	
	/*������int[]{a,b,c}����������
	 * ÿ�����㶼���ĸ��жϣ��ֱ�Ϊn1��������n2��������n1��n2��Ϊ������n1��n2��Ϊ�����
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
	//�����õ�main����
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
