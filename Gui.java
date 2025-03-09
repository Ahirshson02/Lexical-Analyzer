import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

public class Gui implements ActionListener {

    JFrame frame = new JFrame();
    JButton uploadButton = new JButton("Attach File");
    JButton clearButton = new JButton("Clear");
    JButton saveButton = new JButton("Save Output");
    JLabel title = new JLabel("CLite Lexical Analyzer");
    JLabel subTitle = new JLabel("Please upload a CLite file");
    JTextArea outputField = new JTextArea(10, 40);
    JScrollPane scrollPane = new JScrollPane(outputField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private static final Map<String, String> SYMBOL_TABLE = new HashMap<>();

    static {
        SYMBOL_TABLE.put("x", "IDENTIFIER");
        SYMBOL_TABLE.put("y", "IDENTIFIER");
        SYMBOL_TABLE.put("addNumbers", "FUNCTION");
        SYMBOL_TABLE.put("result", "IDENTIFIER");
        SYMBOL_TABLE.put("int", "KEYWORD");
        SYMBOL_TABLE.put("float", "KEYWORD");
        SYMBOL_TABLE.put("double", "KEYWORD");
        SYMBOL_TABLE.put("return", "KEYWORD");
        SYMBOL_TABLE.put("main", "FUNCTION");
        SYMBOL_TABLE.put("(", "LEFT_PAREN");
        SYMBOL_TABLE.put(")", "RIGHT_PAREN");
        SYMBOL_TABLE.put("{", "LEFT_BRACE");
        SYMBOL_TABLE.put("}", "RIGHT_BRACE");
        SYMBOL_TABLE.put("=", "ASSIGN_OP");
        SYMBOL_TABLE.put("+", "ADD_OP");
        SYMBOL_TABLE.put("-", "SUB_OP");
        SYMBOL_TABLE.put("*", "MUL_OP");
        SYMBOL_TABLE.put("/", "DIV_OP");
        SYMBOL_TABLE.put(";", "SEMICOLON");
    }

    public Gui() {
        uploadButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setPreferredSize(new Dimension(150, 40));
        saveButton.setPreferredSize(new Dimension(150, 40));
        outputField.setLineWrap(true);
        outputField.setWrapStyleWord(true);
        outputField.setEditable(false);
        outputField.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setPreferredSize(new Dimension(600, 300));

        title.setFont(new Font("Arial", Font.PLAIN, 18));
        subTitle.setFont(new Font("Arial", Font.PLAIN, 14));

        uploadButton.addActionListener(e -> {
            File file = filePicker();
            if (file != null) {
                analyzeFile(file);
            }
        });

        clearButton.addActionListener(e -> outputField.setText(""));

        saveButton.addActionListener(e -> saveOutputToFile());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(700, 600);

        JPanel panelOuter = new JPanel();
        JPanel panelInner = new JPanel();
        panelOuter.setLayout(new BoxLayout(panelOuter, BoxLayout.Y_AXIS));
        panelInner.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInner.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelOuter.add(title);
        panelOuter.add(subTitle);
        panelOuter.add(scrollPane);
        panelInner.add(uploadButton);
        panelInner.add(clearButton);
        panelInner.add(saveButton);
        panelOuter.add(panelInner);

        frame.add(panelOuter);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private File filePicker() {
        JFileChooser filePicker = new JFileChooser();
        int rv = filePicker.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            return filePicker.getSelectedFile();
        }
        return null;
    }

    private void analyzeFile(File file) {
        try (Scanner sc = new Scanner(file)) {
            outputField.setText(""); // Clear previous output
            
            // Regex to separate identifiers, numbers, and symbols correctly
            String regex = "[a-zA-Z_][a-zA-Z0-9_]*|\\d+|[+\\-*/=();{}]";
            
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Matcher matcher = Pattern.compile(regex).matcher(line);

                while (matcher.find()) {
                    String lexeme = matcher.group();
                    String token = SYMBOL_TABLE.getOrDefault(lexeme, 
                            lexeme.matches("\\d+") ? "NUMBER" : "UNKNOWN");

                    outputField.append(lexeme + " → " + token + "\n");
                }
            }
        } catch (IOException ex) {
            outputField.append("Error: " + ex.getMessage());
        }
    }

    private void saveOutputToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Output");
        fileChooser.setSelectedFile(new File("lexer_output.txt"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(outputField.getText());
                JOptionPane.showMessageDialog(frame, "Output saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}