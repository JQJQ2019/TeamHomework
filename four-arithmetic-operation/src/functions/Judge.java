package functions;

import java.awt.FileDialog;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

//����̳�JFrame����Ϊ�ı�ѡ��򷽷��ù��캯����Ҫ��
public class Judge extends JFrame{
	//�ļ�·����
		static String path = null;
		static String name = null;
    public void start(){
    	//�û�����ͨ���������ֶ������ļ�·�����ߴ��ļ�ѡ���
    	Scanner scanner = new Scanner(System.in);
    	String exerciseFilePath = null;
        String answerFilePath = null;
        System.out.println("���� Choose ���Դ��ı�ѡ����");
        System.out.print("���������֤��·����");
        if(scanner.next().equals("Choose")){
        	FileDialog filedialog = new FileDialog(this,"��",FileDialog.LOAD);
    		filedialog.setVisible(true);
    		this.path = filedialog.getDirectory();
    		this.name = filedialog.getFile();
    		System.out.println(this.path+this.name);
    		exerciseFilePath = this.path+this.name;
        }
        System.out.print("������������ɴ��ļ�·����");
        if(scanner.next().equals("Choose")){
        	FileDialog filedialog = new FileDialog(this,"��",FileDialog.LOAD);
    		filedialog.setVisible(true);
    		this.path = filedialog.getDirectory();
    		this.name = filedialog.getFile();
    		System.out.println(this.path+this.name);
    		answerFilePath = this.path+this.name;
        }
        else answerFilePath = scanner.next();
        try {
        	//exerciseAnswers��answers�����������ı��ķ���
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
            //������Ū�����������ԭ������Ϊ��̨�������е�״̬��һ������һһ������취
            System.out.println(!grade.createNewFile());
            if (!grade.createNewFile()){
                FileOutputStream gradeOutput = new FileOutputStream(grade);
                PrintStream gradePrintStream = new PrintStream(gradeOutput);
                //�Զ��Ÿ���ÿ����Ŀ��ţ�Ȼ��ͳһ�����
                String corrects = String.join(",", correct);
                System.out.println("Correct��" + correct.size() +
                        " (" + corrects + ")");
                gradePrintStream.println("Correct��" + correct.size() +
                        " (" + corrects + ")");      
                String wrongs = String.join(",", wrong);
                System.out.println("Wrong��" + wrong.size() +
                        " (" + wrongs + ")");
                gradePrintStream.println("Wrong��" + wrong.size() +
                        " (" + wrongs + ")");
            }
            System.out.println("�ж����");
        } catch (FileNotFoundException e) {
            System.out.println("�ļ�������");
        } catch (IOException e) {
            System.out.println("�ļ������쳣");
        }
    }

    public List<String> exerciseFileReader(String path) throws IOException {
        BufferedReader exerciseReader = new BufferedReader(new FileReader(path));
        String exerciseAnswer = "";
        List<String> exerciseAnswers = new ArrayList<>();
        while ((exerciseAnswer = exerciseReader.readLine()) != null){
            //ȥ����Ŀ�����´�
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
            //ȥ�����
        	String[] split = answer.split(" ");
            answers.add(split[1]);
        }
        return answers;
    }
}
