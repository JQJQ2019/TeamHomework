package functions;

import java.awt.FileDialog;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

//这里继承JFrame是因为文本选择框方法得构造函数需要它
public class Judge extends JFrame{
	//文件路径名
		static String path = null;
		static String name = null;
    public void start(){
    	//用户可以通过命令行手动输入文件路径或者打开文件选择框
    	Scanner scanner = new Scanner(System.in);
    	String exerciseFilePath = null;
        String answerFilePath = null;
        System.out.println("输入 Choose 可以打开文本选择器");
        System.out.print("请输入待验证答案路径：");
        if(scanner.next().equals("Choose")){
        	FileDialog filedialog = new FileDialog(this,"打开",FileDialog.LOAD);
    		filedialog.setVisible(true);
    		this.path = filedialog.getDirectory();
    		this.name = filedialog.getFile();
    		System.out.println(this.path+this.name);
    		exerciseFilePath = this.path+this.name;
        }
        System.out.print("请输入程序生成答案文件路径：");
        if(scanner.next().equals("Choose")){
        	FileDialog filedialog = new FileDialog(this,"打开",FileDialog.LOAD);
    		filedialog.setVisible(true);
    		this.path = filedialog.getDirectory();
    		this.name = filedialog.getFile();
    		System.out.println(this.path+this.name);
    		answerFilePath = this.path+this.name;
        }
        else answerFilePath = scanner.next();
        try {
        	//exerciseAnswers和answers是两个读入文本的方法
            List<String> exerciseAnswers = exerciseFileReader(exerciseFilePath);
            List<String> answers = answerReader(answerFilePath);
            List<String> correct = new ArrayList<>();
            List<String> wrong = new ArrayList<>();
            int min = Math.min(exerciseAnswers.size(), answers.size());
            int num = 1;
            for (int i = 0; i < min; i++){
                if (exerciseAnswers.get(i).equals(answers.get(i))) correct.add(String.valueOf(num++));
                else wrong.add(String.valueOf(num++));
            }

            File grade = new File("Grade.txt");
            if (grade.exists()){
                grade.delete();
            }
            //这里我弄这两个输出的原因是因为两台电脑运行的状态不一样，这一一个解决办法
            System.out.println(!grade.createNewFile());
            if (!grade.createNewFile()){
                FileOutputStream gradeOutput = new FileOutputStream(grade);
                PrintStream gradePrintStream = new PrintStream(gradeOutput);
                //以逗号隔开每个题目编号，然后统一后呈现
                String corrects = String.join(",", correct);
                System.out.println("Correct：" + correct.size() +
                        " (" + corrects + ")");
                gradePrintStream.println("Correct：" + correct.size() +
                        " (" + corrects + ")");      
                String wrongs = String.join(",", wrong);
                System.out.println("Wrong：" + wrong.size() +
                        " (" + wrongs + ")");
                gradePrintStream.println("Wrong：" + wrong.size() +
                        " (" + wrongs + ")");
            }
            System.out.println("判定完成");
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e) {
            System.out.println("文件读入异常");
        }
    }

    public List<String> exerciseFileReader(String path) throws IOException {
        BufferedReader exerciseReader = new BufferedReader(new FileReader(path));
        String exerciseAnswer = "";
        List<String> exerciseAnswers = new ArrayList<>();
        while ((exerciseAnswer = exerciseReader.readLine()) != null){
            //去掉题目，留下答案
        	String[] split = exerciseAnswer.split("= ");
            if (split.length >= 2){
                exerciseAnswers.add(split[1]);
            }else {
                exerciseAnswers.add(" ");
            }
        }
        return exerciseAnswers;
    }

    public List<String> answerReader(String path) throws IOException {
        BufferedReader answerReader = new BufferedReader(new FileReader(path));
        String answer = "";
        List<String> answers = new ArrayList<>();
        while ((answer = answerReader.readLine()) != null){
            //去掉编号
        	String[] split = answer.split(" ");
            answers.add(split[1]);
        }
        return answers;
    }
}
