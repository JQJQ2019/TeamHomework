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
        System.out.println("----------��ӭ������������������----------\n");
        try {
        	//�û��������ɵ���Ŀ��������Ȼ��ȡֵ��Χ
            Scanner scanner = new Scanner(System.in);
            System.out.print("������������Ŀ������");
            int num = scanner.nextInt();
            System.out.print("�����������Ȼ����");
            int range = scanner.nextInt();
            generateProblem(num, range);
        }catch (InputMismatchException e){
            System.out.println("���������֡�\n\n\n");
            ConstructProblem();
        } catch (IOException e) {
            System.out.println("�ļ�����ʧ��");
        }
    }
    /**
     * @param num
     * @param range
     * @throws IOException
     */
    public void generateProblem(int num, int range) throws IOException {
        //�ļ�������Ŀ��Ŀ¼����
    	File exercises = new File("Exercises.txt");
        File answers = new File("Answers.txt");
        //���ԭ�������ļ�����ɾ��
        if (exercises.exists() || answers.exists()){
            exercises.delete();
            answers.delete();
            //������Ū�����������ԭ������Ϊ��̨�������е�״̬��һ������һһ������취
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
            	//��Ŀ���ɵķ���
                problem = PC.createProblem(range);
                outputFile(i, problem, exercisesPrintStream, answersPrintStream);
            }
            exercisesOutput.close();
            answersOutput.close();
            exercisesPrintStream.close();
            answersPrintStream.close();
            System.out.println("�ļ������ɹ� ");
        }
    }
    
    //��Ŀ���
    public void outputFile(int i, String problem[], PrintStream... var){
        try {
            var[0].println(i + "�� " + problem[0]);
            var[1].println(i + "�� " + problem[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("�����ڲ�������");
        }
    }
}

