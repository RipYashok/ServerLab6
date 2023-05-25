package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;

public class Clear extends Command{
    public Clear(){
        setTitle("clear");
        setDescription("clear - очищает коллекцию");
        setType(CommandType.CLEAR);
    }
    public static Answer execute(){
        Answer answer = new Answer();
        answer.setText("Коллекция очищена");
        return answer;
    }
}
