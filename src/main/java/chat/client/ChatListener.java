package chat.client;

import chat.model.Message;
import chat.model.User;
import chat.shared.ChatClient;
import chat.shared.ServerData;
import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatListener extends UnicastRemoteObject implements
    RemotePropertyChangeListener<ServerData>
{
  private final RemotePropertyChangeSupport<ServerData> support;
  private final ChatClient client;

  public ChatListener(ChatClient chatClient) throws RemoteException
  {
    support = new RemotePropertyChangeSupport<>();
    this.client = chatClient;
    this.client.addPropertyChangeListener(this);
  }

  public boolean connect(User user) throws RemoteException
  {
    return client.connect(user);
  }

  public boolean send(User user, Message message) throws RemoteException
  {
    return client.send(user, message);
  }

  public void close(User user) throws RemoteException
  {
    client.close(user);
  }

  public void addPropertyChangeListener(String propertyName,
      RemotePropertyChangeListener<ServerData> listener)
  {
    support.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(String propertyName,
      RemotePropertyChangeListener<ServerData> listener)
  {
    support.removePropertyChangeListener(propertyName, listener);
  }

  @Override public void propertyChange(
      RemotePropertyChangeEvent<ServerData> remotePropertyChangeEvent)
      throws RemoteException
  {
    Platform.runLater(() -> {
      if (remotePropertyChangeEvent.getPropertyName().equals("addUser") || remotePropertyChangeEvent.getPropertyName().equals("addMessage"))
      {
        ServerData serverData = remotePropertyChangeEvent.getNewValue();
        try
        {
          support.firePropertyChange("add", null, serverData);
        }
        catch (RemoteException e)
        {
          throw new RuntimeException(e);
        }
      }
      else if (remotePropertyChangeEvent.getPropertyName().equals("removeUser"))
      {
        ServerData serverData = remotePropertyChangeEvent.getNewValue();
        try
        {
          support.firePropertyChange("remove", null, serverData);
        }
        catch (RemoteException e)
        {
          throw new RuntimeException(e);
        }
      }
    });
  }
}
