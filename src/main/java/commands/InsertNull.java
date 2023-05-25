package commands;

import commands.utils.CommandType;
import communication.Answer;
import models.*;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class InsertNull extends Command {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public InsertNull() {
        setTitle("insert");
        setDescription("insert null {element} - добавляет новый элемент с заданным целочисленным ключом в коллекцию");
        setType(CommandType.INSERTNULL);

    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static Answer execute(){
        Answer answer = new Answer();
        answer.setText("Добавлен новый элемент в коллекцию");
        return answer;
    }
}