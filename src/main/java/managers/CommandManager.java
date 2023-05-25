package managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commands.*;
import commands.utils.CommandType;
import managers.utils.HashTable;
import models.StudyGroup;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {
    InsertNull insertNull = new InsertNull();
    Save save = new Save();
    Show show = new Show();
    Exit exit = new Exit();
    Clear clear = new Clear();
    RemoveKey removeKey = new RemoveKey();
    ExecuteScript executeScript = new ExecuteScript();
    MaxByID maxByID = new MaxByID();
    AverageOfStudentsCount averageOfStudentsCount = new AverageOfStudentsCount();
    RemoveAllByOfEducation removeAllByOfEducation = new RemoveAllByOfEducation();
    ReplaceIfLowe replaceIfLowe = new ReplaceIfLowe();
    History history = new History();
    RemoveLower removeLower = new RemoveLower();
    Update update = new Update();
    Info info = new Info();
    Help help = new Help();
    ArrayList<String> commandNameList = new ArrayList<>(Arrays.asList(insertNull.getTitle(), save.getTitle(), show.getTitle(), exit.getTitle(),
            clear.getTitle(), removeKey.getTitle(), executeScript.getTitle(), maxByID.getTitle(), averageOfStudentsCount.getTitle(), removeAllByOfEducation.getTitle(),
            replaceIfLowe.getTitle(), history.getTitle(), removeLower.getTitle(), update.getTitle(), info.getTitle(), help.getTitle()));

    public void run(DatagramChannel server, HashTable collection, BufferedReader reader){
        ArrayList<String> commandHistory = new ArrayList<>(Arrays.asList("", "", "", "", "", "", "", "", "", "", ""));
        try{
            ByteBuffer sendBuffer = ByteBuffer.allocate(10240);
            ByteBuffer receiveBuffer = ByteBuffer.allocate(10240);
            Integer timer = 0;
            outherLoop:
            while (true){
                SocketAddress client = null;
                while (client == null) {
                    receiveBuffer.clear();
                    client = server.receive(receiveBuffer);
                    if (client == null){
                        try{
                            System.out.println("Ожидание подключения клиента...");
                            Thread.sleep(5000);
                            timer += 1;
                            if (timer == 100){
                                break outherLoop;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                    System.out.println("Соединение установленно");
                    timer = 0;
                    receiveBuffer.flip();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    String jsonReceive = new String(receiveBuffer.array(), 0, receiveBuffer.limit());
                    Command command = mapper.readValue(jsonReceive, Command.class);
                    history.createHistory(commandHistory, command.getTitle());
                    if (command.getType().equals(CommandType.INSERTNULL)){
                        receiveBuffer.clear();
                        server.receive(receiveBuffer);
                        receiveBuffer.flip();
                        String jsonGroup = new String(receiveBuffer.array(), 0, receiveBuffer.limit());
                        System.out.println(jsonGroup);
                        StudyGroup group =  mapper.readValue(jsonGroup, StudyGroup.class);
                        collection.put(((InsertNull) command).getKey(), group);
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(InsertNull.execute()).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                        receiveBuffer.flip();
                    } else if (command.getType().equals(CommandType.SHOW)) {
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(Show.execute(collection)).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                        receiveBuffer.flip();
                    } else if (command.getType().equals(CommandType.CLEAR)){
                        collection.clear();
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(Clear.execute()).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    } else if (command.getType().equals(CommandType.REMOVEKEY)){
                        collection.remove(((RemoveKey) command).getKey());
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((RemoveKey) command).execute(((RemoveKey) command).getKey())).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }  else if (command.getType().equals(CommandType.MAXID)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((MaxByID) command).execute(collection)).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }  else if (command.getType().equals(CommandType.AVERAGEOFSTUDENTSCOUNT)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((AverageOfStudentsCount) command).execute(collection)).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }  else if (command.getType().equals(CommandType.REMOVEALLBYOFEDUCATION)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((RemoveAllByOfEducation) command).execute(collection, ((RemoveAllByOfEducation) command).getEducation())).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }  else if (command.getType().equals(CommandType.REMOVELOWER)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((RemoveLower) command).execute(collection, ((RemoveLower) command).getValue())).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    } else if (command.getType().equals(CommandType.REPLACELOWE)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((ReplaceIfLowe) command).execute(collection, ((ReplaceIfLowe) command).getKey(), ((ReplaceIfLowe) command).getValue())).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    } else if (command.getType().equals(CommandType.HISTORY)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((History) command).execute(commandHistory, commandNameList)).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }  else if (command.getType().equals(CommandType.INFO)){
                        sendBuffer.clear();
                        sendBuffer.put(mapper.writeValueAsString(((Info) command).execute(collection)).getBytes());
                        sendBuffer.flip();
                        server.send(sendBuffer,client);
                    }


            }
            save.excute(collection);
            server.close();
            System.out.println("Сервер выключен");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}