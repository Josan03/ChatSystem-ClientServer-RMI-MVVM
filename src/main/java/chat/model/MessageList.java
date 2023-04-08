package chat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageList implements Serializable
{
  private ArrayList<Message> messages;

  public MessageList()
  {
    this.messages = new ArrayList<Message>();
  }

  public void setMessageList(ArrayList<Message> messageList)
  {
    this.messages = messageList;
  }

  public void add(Message message)
  {
    if (message != null)
    {
      messages.add(message);
    }
  }

  public ArrayList<Message> getMessages()
  {
    return messages;
  }
}
