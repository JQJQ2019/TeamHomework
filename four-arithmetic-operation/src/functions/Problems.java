package functions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Problems{
	
    public void ConstructProblem(){
        System.out.println("----------欢迎来到四则运算生成器----------\n");
        try {
        	//用户输入生成的题目个数和自然数取值范围
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入生成题目个数：");
            int num = scanner.nextInt();
            System.out.print("请输入最大自然数：");
            int range = scanner.nextInt();
            generateProblem(num, range);
        }catch (InputMismatchException e){
            System.out.println("请输入数字。\n\n\n");
            ConstructProblem();
        } catch (IOException e) {
            System.out.println("文件创建失败");
        }
    }
    /**
     * @param num
     * @param range
     * @throws IOException
     */
    public void generateProblem(int num, int range) throws IOException {
        //文件会在项目根目录生成
    	File exercises = new File("Exercises.txt");
        File answers = new File("Answers.txt");
        //如果原来存在文件，先删除
        if (exercises.exists() || answers.exists()){
            exercises.delete();
            answers.delete();
            //这里我弄这两个输出的原因是因为两台电脑运行的状态不一样，这一一个解决办法
            System.out.println(exercises.createNewFile());
            System.out.println(answers.createNewFile());
        }
        if (!exercises.createNewFile() && !answers.createNewFile()){
            FileOutputStream 	exercisesOutput 		= new FileOutputStream(exercises);
            PrintStream 		exercisesPrintStream 	= new PrintStream(exercisesOutput);
            FileOutputStream 	answersOutput 			= new FileOutputStream(answers);
            PrintStream 		answersPrintStream 		= new PrintStream(answersOutput);
            Problems_creator 	PC 						= new Problems_creator();
            String[] 			problem 				= new String[2];
            for(int i = 1; i <= num; i++){
            	//题目生成的方法
                problem = PC.createProblem(range);
                outputFile(i, problem, exercisesPrintStream, answersPrintStream);
            }
            exercisesOutput.close();
            answersOutput.close();
            exercisesPrintStream.close();
            answersPrintStream.close();
            System.out.println("文件创建成功 ");
        }
    }
    
    //题目序号
    public void outputFile(int i, String problem[], PrintStream... var){
        try {
            var[0].println(i + "、 " + problem[0]);
            var[1].println(i + "、 " + problem[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("程序内部出错了");
        }
    }
}

