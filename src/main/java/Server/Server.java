package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import commands.Command;
import managers.CommandManager;
import managers.utils.FileReader;
import managers.utils.HashTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

    public static DatagramChannel start() throws IOException{
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("localhost", 666);
        server.bind(address);
        System.out.println("Сервер запущен тут: " + address);
        return server;
    }

    public static Command receive(DatagramChannel server, ByteBuffer receiveBuffer) throws IOException {
        receiveBuffer.clear();
            receiveBuffer.flip();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonReceive = new String(receiveBuffer.array(), 0, receiveBuffer.remaining());
        Command command = objectMapper.readValue(jsonReceive, Command.class);
        return command;
    }


    public static void main(String[] args) throws IOException {
        try{
            CommandManager manager = new CommandManager();
            HashTable collection = new HashTable();
            FileReader fileReader = new FileReader();
            collection.putAll(fileReader.saveCollaction("C:\\Users\\Yakov\\IdeaProjects\\Server\\FileCollection.txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DatagramChannel server = start();
            manager.run(server, collection, reader);
        }catch ( Exception e){
            e.printStackTrace();
        }
    }
}

