import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerGUI extends JFrame {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private JTextArea chatArea;
    private JTextField inputField;

    public ServerGUI() {
        setTitle("Chat with Customer");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                broadcastMessage(message);
                inputField.setText("");
            }
        });
        inputPanel.add(inputField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                broadcastMessage(message);
                inputField.setText("");
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        try {
            serverSocket = new ServerSocket(1234); // 실제 사용할 포트번호로 변경
            clients = new ArrayList<>();

            // 클라이언트 접속을 기다리는 스레드 실행
            Thread acceptThread = new Thread(new AcceptThread());
            acceptThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
        //appendMessage(message);
    }

    private void appendMessage(String message) {
        chatArea.append(message + "\n");
    }


    private class AcceptThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(clientSocket);
                    clients.add(client);

                    // 클라이언트로부터 메시지를 받는 스레드 실행
                    Thread receiveThread = new Thread(client);
                    receiveThread.start();

                    appendMessage("구매자가 접속했습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    appendMessage(message);
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                    clients.remove(this);
                    appendMessage("구매자와의 연결이 종료되었습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
            appendMessage("Administrator : " + message);
            
        }
    }
}
/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ServerGUI serverGUI = new ServerGUI();
                serverGUI.setVisible(true);
            }
        });
    }
}
*/
