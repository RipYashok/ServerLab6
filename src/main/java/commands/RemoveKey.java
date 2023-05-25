package commands;

import commands.utils.CommandType;
import communication.Answer;
import managers.utils.HashTable;

public class RemoveKey extends Command{
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public RemoveKey(){
        setTitle("remove_key");
        setDescription("remove_key null - удаляет элемент из коллекции по его ключу");
        setType(CommandType.REMOVEKEY);
    }
    public static Answer execute(String key){
        Answer answer = new Answer();
        answer.setText("Удален элемент с ключем " + key);
        return answer;
    }
}
