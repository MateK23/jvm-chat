package client;

import javax.swing.*;
import java.awt.*;

public class ChatUI {
    private JPanel mainPanel;
    private JTextField mainTextField;
    private JButton btnSend;
    private JTextArea chatTextArea;
    private JLabel usernameLabel;
    private JButton refreshButton;
    ChatClient chatClient = new ChatClient("localhost", 9090, this);

    public ChatUI(String username) {
        chatClient.setUser(username);

        JFrame frame = new JFrame();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Java Chat by MateK");

        initButtons();

        usernameLabel.setText("Chatting as: " + username);
    }

    public void addStringToChat(String messageToAdd) {


        if (chatTextArea.getText().equals("")) {
            chatTextArea.append(messageToAdd);
        } else {
            chatTextArea.append("\n" + messageToAdd);
        }
    }

    private void initButtons(){

        btnSend.addActionListener(e -> {
            String stringToSend = mainTextField.getText();
            if (!stringToSend.equals("")){

                chatClient.sendMessageToServer(stringToSend);
            }
            mainTextField.setText("");

            chatClient.receiveMessageFromServer();
        });

        refreshButton.addActionListener(e -> chatClient.receiveMessageFromServer());
    }
}
