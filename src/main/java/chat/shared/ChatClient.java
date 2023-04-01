package chat.shared;

import chat.model.Message;
import chat.model.User;
import dk.via.remote.observer.RemotePropertyChangeListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote
{
  boolean connect(User user) throws RemoteException;
  boolean send(User user, Message message) throws RemoteException;
  void close(User user) throws RemoteException;
  void addPropertyChangeListener(RemotePropertyChangeListener<ServerData> listener) throws RemoteException;
  void removePropertyChangeListener(RemotePropertyChangeListener<ServerData> listener) throws RemoteException;
}
