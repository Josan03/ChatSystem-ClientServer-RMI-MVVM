package chat.model;

import chat.client.ChatListener;
import chat.shared.ServerData;
import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelManager implements RemotePropertyChangeListener<ServerData>
{
  private final MessageList messageList;
  private final UserList userList;
  private final PropertyChangeSupport support;
  private final ChatListener client;
  private User user;
  private static ModelManager instance;

  private ModelManager(ChatListener client) throws RemoteException
  {
    this.messageList = new MessageList();
    this.userList = new UserList();
    this.client = client;
    this.support = new PropertyChangeSupport(this);

    this.client.addPropertyChangeListener("add", this);
    this.client.addPropertyChangeListener("remove", this);
  }

  public static ModelManager getInstance(ChatListener client)
      throws RemoteException
  {
    if (instance == null)
    {
      instance = new ModelManager(client);
    }

    return instance;
  }

  public static ModelManager getInstance()
  {
    return instance;
  }

  public ArrayList<Message> getMessageList()
  {
    return messageList.getMessages();
  }

  public ArrayList<User> getUserList()
  {
    return userList.getUsers();
  }

  public User getUser()
  {
    return user;
  }

  public boolean addUser(User user)
  {
    try
    {
      boolean ok = client.connect(user);
      if (ok)
      {
        this.user = user;
        return true;
      }
      else return false;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  public boolean addMessage(Message message)
  {
    try
    {
      return client.send(user, message);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  public void removeUser()
  {
    try
    {
      client.close(user);
      client.removePropertyChangeListener("add", this);
      client.removePropertyChangeListener("remove", this);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  public void info()
  {
    try
    {
      messageList.add(new Message(new User("Server"), client.info()));
      support.firePropertyChange("info", null, messageList);
    }
    catch (RemoteException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(propertyName, listener);
  }

  @Override public void propertyChange(
      RemotePropertyChangeEvent remotePropertyChangeEvent)
      throws RemoteException
  {
    Platform.runLater(() -> {
      ServerData serverData = (ServerData) remotePropertyChangeEvent.getNewValue();
      if (remotePropertyChangeEvent.getPropertyName().equals("add"))
      {
        userList.setUserList(serverData.getUsers());
        messageList.setMessageList(serverData.getMessages());
        support.firePropertyChange("users", null, userList);
        support.firePropertyChange("messages", null, messageList);
      }
      else if (remotePropertyChangeEvent.getPropertyName().equals("remove"))
      {
        userList.setUserList(serverData.getUsers());
        messageList.setMessageList(serverData.getMessages());
        support.firePropertyChange("users", null, userList);
        support.firePropertyChange("messages", null, messageList);
      }
    });
  }
}
