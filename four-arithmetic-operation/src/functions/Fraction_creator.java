package functions;

import java.util.Random;

public class Fraction_creator {
	Algorithm 	AL 	= new Algorithm();
	
	//�����������һ�������
	public int[] Fraction(int range){
		Random 	r = new Random();
		int h = r.nextInt(range);
		int x = 1 + r.nextInt(range);
        int y = 1 + r.nextInt(range);
        int reduction = AL.Reduction(x, y);
        x /= reduction;
        y /= reduction;
        int numbers[] = {h,x,y};
		return numbers;
	}
}
