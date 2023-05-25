package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;

import java.time.ZonedDateTime;

public class Info extends Command{

    public Info(){
        setTitle("info");
        setDescription("info - выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        setType(CommandType.INFO);
    }
    public static final String ANSI_GREEN = "\u001B[32m";
    public static Answer execute(HashTable collection){
        Answer answer = new Answer();
        answer.setText(ANSI_GREEN + "Тип коллекции - Hashtable" + '\n' + ANSI_GREEN + "Дата инициализации - " + collection.getTimeTnitialization() + '\n'
                + ANSI_GREEN + "Количество элементов - " + collection.size() + '\n');
        return answer;
    }
}
