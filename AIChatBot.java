import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class AIChatBot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;

    private HashMap<String, String> knowledge =
            new HashMap<>();

    public AIChatBot() {

        loadKnowledge();

        setTitle("AI ChatBot");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane =
                new JScrollPane(chatArea);

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        inputField = new JTextField();

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 2));

        sendButton = new JButton("Send");
        clearButton = new JButton("Clear");

        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(
                inputField,
                BorderLayout.CENTER);

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST);

        add(bottomPanel,
                BorderLayout.SOUTH);

        chatArea.append(
                "Bot : Hello! Ask me anything.\n\n");

        sendButton.addActionListener(
                e -> sendMessage());

        inputField.addActionListener(
                e -> sendMessage());

        clearButton.addActionListener(
                e -> chatArea.setText(""));

        setVisible(true);
    }

    private void sendMessage() {

        String message =
                inputField
                        .getText()
                        .trim();

        if (message.isEmpty())
            return;

        chatArea.append(
                "You : "
                        + message
                        + "\n");

        String reply =
                getBotResponse(message);

        chatArea.append(
                "Bot : "
                        + reply
                        + "\n\n");

        inputField.setText("");

        chatArea.setCaretPosition(
                chatArea.getDocument()
                        .getLength());
    }

    private String getBotResponse(
            String message) {

        message =
                message.toLowerCase().trim();

        if (message.contains("hi")
                || message.contains("hello")
                || message.contains("hey")) {

            return "Hello! How can I help you?";
        }

        else if (message.contains("how are you")) {

            return "I am doing great. Thank you!";
        }

        else if (message.contains("what is java")) {

            return "Java is an Object-Oriented Programming Language.";
        }

        else if (message.contains("who created java")) {

            return "James Gosling created Java.";
        }

        else if (message.contains("what is oop")) {

            return "OOP stands for Object Oriented Programming.";
        }

        else if (message.contains("happy")) {

            return "That's great to hear!";
        }

        else if (message.contains("sad")) {

            return "I am sorry to hear that.";
        }

        else if (message.equals("date")) {

            return "Today's Date : "
                    + LocalDate.now();
        }

        else if (message.equals("time")) {

            return "Current Time : "
                    + LocalTime.now()
                    .withNano(0);
        }

        else if (message.contains("bye")) {

            return "Goodbye! Have a nice day.";
        }

        else if (knowledge.containsKey(message)) {

            return knowledge.get(message);
        }

        else {

            String answer =
                    JOptionPane.showInputDialog(
                            this,
                            "I don't know this answer.\nTeach me:");

            if (answer != null
                    && !answer.trim().isEmpty()) {

                knowledge.put(
                        message,
                        answer);

                saveKnowledge();

                return "Thanks! I learned that.";
            }

            return "No answer provided.";
        }
    }

    private void saveKnowledge() {

        try {

            PrintWriter pw =
                    new PrintWriter(
                            new FileWriter(
                                    "knowledge.txt"));

            for (String key :
                    knowledge.keySet()) {

                pw.println(
                        key + "="
                                + knowledge.get(key));
            }

            pw.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void loadKnowledge() {

        knowledge.put(
                "what is java",
                "Java is an Object-Oriented Programming Language.");

        knowledge.put(
                "who created java",
                "James Gosling created Java.");

        knowledge.put(
                "what is oop",
                "OOP stands for Object Oriented Programming.");

        try {

            File file =
                    new File(
                            "knowledge.txt");

            if (!file.exists())
                return;

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(file));

            String line;

            while ((line =
                    br.readLine()) != null) {

                int pos =
                        line.indexOf("=");

                if (pos > 0) {

                    String question =
                            line.substring(
                                    0,
                                    pos);

                    String answer =
                            line.substring(
                                    pos + 1);

                    knowledge.put(
                            question,
                            answer);
                }
            }

            br.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                AIChatBot::new);
    }
}