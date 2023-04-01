package chat.server;

import chat.server.logs.FileLog;
import chat.shared.ChatClient;
import chat.shared.ServerData;
import chat.model.Message;
import chat.model.User;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatImplementation extends UnicastRemoteObject implements
    ChatClient
{
  private final ArrayList<User> users;
  private final ArrayList<Message> messages;
  private final RemotePropertyChangeSupport<ServerData> support;
  private final FileLog logs;

  public ChatImplementation(ArrayList<User> users, ArrayList<Message> messages) throws RemoteException
  {
    this.support = new RemotePropertyChangeSupport<>();
    this.users = users;
    this.messages = messages;
    logs = FileLog.getInstance(new File("C:/Users/crist/IdeaProjects/ViaUniversity/Semester2/Assignment3_ChatSystem_RMI/src/main/java/chat/server/logs/Logs.txt"));
  }

  @Override public boolean connect(User user) throws RemoteException
  {
    if (user != null)
    {
      for (User item : users)
      {
        if (user.equals(item))
        {
          return false;
        }
      }
      users.add(user);
      Message message = new Message(user.getName() + " has joined the chat");
      messages.add(message);
      logs.log(message.getMessage());
      ServerData serverData = new ServerData("addUser", users, messages);
      support.firePropertyChange("addUser", null, serverData);
      return true;
    }
    return false;
  }

  @Override public boolean send(User user, Message message) throws RemoteException
  {
    if (message != null)
    {
      messages.add(message);
      ServerData serverData = new ServerData("addMessage", users, messages);
      logs.log(message.toString());
      support.firePropertyChange("addMessage", null, serverData);
      return true;
    }
    return false;
  }

  @Override public void close(User user) throws RemoteException
  {
    Message message = new Message(user.getName() + " has left the chat");
    messages.add(message);
    users.remove(user);
    logs.log(message.getMessage());
    ServerData serverData = new ServerData("removeUser", users, messages);
    support.firePropertyChange("removeUser", null, serverData);
  }

  @Override public void addPropertyChangeListener(
      RemotePropertyChangeListener<ServerData> listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(
      RemotePropertyChangeListener<ServerData> listener)
  {
    support.removePropertyChangeListener(listener);
  }
}
