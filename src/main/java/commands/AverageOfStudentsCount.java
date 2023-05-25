package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;
import models.StudyGroup;

import java.util.Enumeration;

public class AverageOfStudentsCount extends Command{
    public AverageOfStudentsCount(){
        setTitle("average_of_students_count");
        setDescription("average_of_students_count - выводит среднее значение поля studentsCount для всех элементов коллекции");
        setType(CommandType.AVERAGEOFSTUDENTSCOUNT);
    }
    public static final String ANSI_GREEN = "\u001B[32m";

    public static Answer execute(HashTable collection) {
        Enumeration<StudyGroup> groups = collection.elements();
        Integer studentsCount = 0;
        while (groups.hasMoreElements() == true){
            studentsCount += groups.nextElement().getStudentsCount();
        }
        Double average = Double.valueOf(studentsCount/collection.size());
        Answer answer = new Answer();
        answer.setText(ANSI_GREEN + "Среднее количество студентов во всех группах: " + average);
        return answer;
    }
}
