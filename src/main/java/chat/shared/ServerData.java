package chat.shared;

import chat.model.Message;
import chat.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerData implements Serializable
{
  private final String response;
  private final ArrayList<User> users;
  private final ArrayList<Message> messages;

  public ServerData(String response, ArrayList<User> users, ArrayList<Message> messages)
  {
    this.response = response;
    this.users = users;
    this.messages = messages;
  }

  public ArrayList<User> getUsers()
  {
    return users;
  }

  public ArrayList<Message> getMessages()
  {
    return messages;
  }

  public String getResponse()
  {
    return response;
  }
}
