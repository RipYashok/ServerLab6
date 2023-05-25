package commands;

import commands.utils.CommandType;
import communication.Answer;

import java.util.ArrayList;

public class History extends Command{
    public History(){
        setTitle("history");
        setDescription("history - выводит последние 11 команд (без их аргументов)");
        setType(CommandType.HISTORY);
    }
    public static final String ANSI_GREEN = "\u001B[32m";

    public void createHistory(ArrayList<String> commandHistory, String command){
        if (command.isBlank() == false){
            commandHistory.add(command);
        }
    }

    public static Answer execute(ArrayList<String> commandHistory, ArrayList<String> commandNameList){
        Answer answer = new Answer();
        String message = "";
        for (int i = commandHistory.size()-11; i < commandHistory.size() - 1; i++){
            if (commandNameList.contains(commandHistory.get(i))){
                message += ANSI_GREEN + commandHistory.get(i) + '\n';
            }
        }
        answer.setText(message);
        return answer;
    }
}
