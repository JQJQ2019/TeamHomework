package functions;

import java.util.Random;

public class Integer_creator {
	Algorithm 	AL 	= new Algorithm();
	
	//�����������һ������
	public int[] Integer (int range){
		Random 	r = new Random();
		int h = 1+r.nextInt(range);
		int x = 0;
        int y = 0;
        int numbers[] = {h,x,y};
		return numbers;
	}
}
