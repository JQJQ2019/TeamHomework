package main;

import java.util.Scanner;

import functions.Judge;
import functions.Problems;

public class Operation_Main {
	static boolean TRUE_OR_NOT = true;
	
	//程序支持真分数和整数的四则运算
	public static void main(String[] args){
		System.out.println("----------四则运算生成器----------\n");
        System.out.println("生成器功能：");
        System.out.println("    1. 生成四则运算题目");
        System.out.println("    2. 验证答案");
        System.out.println("    3. 退出程序");
        System.out.println("输入你的选择执行功能\n");
        System.out.println("---------------------------------\n");
        while(TRUE_OR_NOT){
        	System.out.println("请输入你的选择[1  -  2  -  3]：");
	        int choose = new Scanner(System.in).nextInt();
	        switch (choose){
		        case 1:
		        	//选择1，是生成题目的功能
		            Problems P = new Problems();
		            P.ConstructProblem();break;
		        case 2:
		        	//选择2，是验证答案的功能
		            Judge J = new Judge();
		            J.start();break;
		        case 3:
		        	//选择3，退出程序
		            Operation_Main.TRUE_OR_NOT = false;
		            System.out.println("学习愉快，亲~");
		            break;
		        default:
		        	//用户输入错误提示
		            System.out.println("您的输入不正确，请输入正确功能选项");main(args);break;
	        }
        }
    }

}
