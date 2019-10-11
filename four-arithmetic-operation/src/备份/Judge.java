package 备份;

import java.awt.FileDialog;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

//这里继承JFrame是因为文本选择框方法得构造函数需要它
public class Judge {

    public void start(){
        System.out.print("请输入待验证答案路径：");
        Scanner scanner = new Scanner(System.in);
        String exerciseFilePath = scanner.next();
        System.out.print("请输入程序生成答案文件路径：");
        String answerFilePath = scanner.next();

        try {
            List<String> exerciseAnswers = exerciseFileReader(exerciseFilePath);
            List<String> answers = answerReader(answerFilePath);

            List<String> correct = new ArrayList<>();
            List<String> wrong = new ArrayList<>();

            int min = Math.min(exerciseAnswers.size(), answers.size());
            int num = 1;
            for (int i = 0; i < min; i++){
                if (exerciseAnswers.get(i).equals(answers.get(i))){
                    correct.add(String.valueOf(num++));
                }else {
                    wrong.add(String.valueOf(num++));
                }
            }

            File grade = new File("Grade.txt");
            if (grade.exists()){
                grade.delete();
            }
            if (grade.createNewFile()){
                FileOutputStream gradeOutput = new FileOutputStream(grade);
                PrintStream gradePrintStream = new PrintStream(gradeOutput);
                String corrects = String.join(",", correct);
                gradePrintStream.println("Correct：" + correct.size() +
                        " (" + corrects + ")");
                String wrongs = String.join(",", wrong);
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
            String[] split = exerciseAnswer.split("=");
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
            String[] split = answer.split(" ");
            answers.add(split[1]);
        }
        return answers;
    }
}
