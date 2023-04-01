package chat.viewmodel;

import chat.model.Message;
import chat.model.ModelManager;
import chat.model.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatViewModel implements PropertyChangeListener
{
  private final ModelManager modelManager;
  private final SimpleListProperty<User> users;
  private final SimpleListProperty<Message> messages;
  private final SimpleStringProperty message;
  private final PropertyChangeSupport support;

  public ChatViewModel(ModelManager modelManager)
  {
    this.modelManager = modelManager;
    this.message = new SimpleStringProperty("");
    this.users = new SimpleListProperty<>(FXCollections.observableArrayList());
    this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
    this.support = new PropertyChangeSupport(this);

    this.modelManager.addPropertyChangeListener("users", this);
    this.modelManager.addPropertyChangeListener("messages", this);
  }

  public void bindUsers(ObjectProperty<ObservableList<User>> property)
  {
    property.bindBidirectional(users);
  }

  public void bindMessages(ObjectProperty<ObservableList<Message>> property)
  {
    property.bindBidirectional(messages);
  }

  public void bindMessage(StringProperty property)
  {
    property.bindBidirectional(message);
  }

  public boolean addMessage(User user, String text)
  {
    Message message = new Message(user, text);
    return modelManager.addMessage(message);
  }

  public User getUser()
  {
    return modelManager.getUser();
  }

  public void removeUser()
  {
    modelManager.removeUser();
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

  public void update()
  {
    users.setAll(modelManager.getUserList());
    messages.setAll(modelManager.getMessageList());
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      update();
      support.firePropertyChange("update", false, true);
    });
  }
}
