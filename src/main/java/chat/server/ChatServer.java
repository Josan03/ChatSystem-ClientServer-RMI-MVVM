package chat.server;

import chat.model.Message;
import chat.model.User;
import chat.server.logs.FileLog;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ChatServer
{
  public static void main(String[] args) throws Exception
  {
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Message> messages = new ArrayList<Message>();
    Registry registry = LocateRegistry.createRegistry(1099);
    ChatImplementation chat = new ChatImplementation(users, messages);
    registry.bind("chat", chat);
    FileLog logs = FileLog.getInstance(new File("C:/Users/crist/IdeaProjects/ViaUniversity/Semester2/Assignment3_ChatSystem_RMI/src/main/java/chat/server/logs/Logs.txt"));
    System.out.println("Server is running");
    logs.log("Server is running");
  }
}
