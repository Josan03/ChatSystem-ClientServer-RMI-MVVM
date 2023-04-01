module Assignment3_ChatSystem_RMI {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.rmi;
  requires java.desktop;
  requires remoteobserver;

  opens chat.view to javafx.fxml;
  opens chat.client to java.rmi;
  opens chat to java.rmi, javafx.fxml;

  exports chat.client;
  exports chat.server;
  exports chat.model;
  exports chat.viewmodel;
  exports chat.view;
  exports chat.shared;
  exports chat;
  opens chat.server to java.rmi, javafx.fxml;
}