package main;

import java.util.Scanner;

import functions.Judge;
import functions.Problems;

public class Operation_Main {
	static boolean TRUE_OR_NOT = true;
	
	//����֧�����������������������
	public static void main(String[] args){
		System.out.println("----------��������������----------\n");
        System.out.println("���������ܣ�");
        System.out.println("    1. ��������������Ŀ");
        System.out.println("    2. ��֤��");
        System.out.println("    3. �˳�����");
        System.out.println("�������ѡ��ִ�й���\n");
        System.out.println("---------------------------------\n");
        while(TRUE_OR_NOT){
        	System.out.println("���������ѡ��[1  -  2  -  3]��");
	        int choose = new Scanner(System.in).nextInt();
	        switch (choose){
		        case 1:
		        	//ѡ��1����������Ŀ�Ĺ���
		            Problems P = new Problems();
		            P.ConstructProblem();break;
		        case 2:
		        	//ѡ��2������֤�𰸵Ĺ���
		            Judge J = new Judge();
		            J.start();break;
		        case 3:
		        	//ѡ��3���˳�����
		            Operation_Main.TRUE_OR_NOT = false;
		            System.out.println("ѧϰ��죬��~");
		            break;
		        default:
		        	//�û����������ʾ
		            System.out.println("�������벻��ȷ����������ȷ����ѡ��");main(args);break;
	        }
        }
    }

}
