package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;
import models.StudyGroup;

public class Show extends Command{

    public Show(){
        setTitle("show");
        setDescription("show - выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        setType(CommandType.SHOW);
    }



    public static Answer execute(HashTable collection){
        Answer answer = new Answer();
        answer.setText(collection.toString());
        return answer;
    }
}

