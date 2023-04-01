package chat;

import chat.client.ChatListener;
import chat.model.ModelManager;
import chat.shared.ChatClient;
import chat.view.ViewHandler;
import chat.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartClient extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
    ChatClient chat = (ChatClient) registry.lookup("chat");
    ChatListener client = new ChatListener(chat);
    ModelManager modelManager = ModelManager.getInstance(client);
    ViewModelFactory viewModelFactory = new ViewModelFactory();
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args) throws IOException
  {
    launch();
  }
}
