package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;
import models.StudyGroup;

import java.io.BufferedReader;
import java.io.IOException;

public class ReplaceIfLowe extends Command{
    private String key;
    private Integer value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ReplaceIfLowe(){
        setTitle("replace_if_lowe");
        setDescription("replace_if_lowe null {element} - заменяет studentsCount значение по ключу, если новое значение меньше старого."
                + "\n" + "Напишите название команды, ключ элемента коллекции и новое количество студентов");
        setType(CommandType.REPLACELOWE);
    }
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public Integer newStudentCount(BufferedReader reader) {
        while (true) {
            try {
                System.out.print(ANSI_GREEN + "Введите новое количество студентов: ");
                Integer count = Integer.parseInt(reader.readLine());
                while (count == null || count < 0) {
                    if (count == null){
                        System.out.print("\n");
                    }
                    System.out.println(ANSI_RED + "Такое вряд ли возможно((");
                    System.out.print(ANSI_GREEN + "Так сколько же человек в вашей группе?: ");
                    count = Integer.parseInt(reader.readLine());
                }
                return count;
            } catch (NumberFormatException numberFormatException) {
                System.out.println(ANSI_RED + "Запишите количество цифрами пожалуйста");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Answer execute(HashTable collection, String key, Integer newValue) throws IOException {
        Answer answer = new Answer();
        Integer value = collection.get(key).getStudentsCount();
        if (newValue < value){
            collection.get(key).setStudentsCount(newValue);
            answer.setText("У элемента под ключем " + key + " количество студентов в группе измененно с " + value + " на " + newValue);
        } else {
            answer.setText("У элемента под ключем " + key + " количество студентов в группе не измененно");
        }
        return answer;
    }
}
