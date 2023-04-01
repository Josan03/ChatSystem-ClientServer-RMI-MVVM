package chat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
  private ArrayList<User> users;

  public UserList()
  {
    this.users = new ArrayList<User>();
  }

  public void setUserList(ArrayList<User> userList)
  {
    this.users = userList;
  }

  public ArrayList<User> getUsers()
  {
    return users;
  }
}
