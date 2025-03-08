import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Gui implements ActionListener {

    JFrame frame = new JFrame();
    JButton uploadButton = new JButton("Attach File");
    JButton clearButton = new JButton("Clear");
    JLabel title = new JLabel("CLite Lexical Analyzer");
    JLabel subTitle = new JLabel("Please upload a CLite file");
    JTextArea outputField = new JTextArea(10,40);
    JScrollPane scrollPane = new JScrollPane(outputField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    Gui(){
        File file = new File("test.txt");
        uploadButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setPreferredSize(new Dimension(150, 40));
        outputField.setLineWrap(true);

        title.setFont(new Font("Arial", Font.PLAIN, 18));
        subTitle.setFont(new Font("Arial", Font.PLAIN, 14));

        outputField.setWrapStyleWord(true);
        outputField.setEditable(false);
        outputField.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setPreferredSize(new Dimension(600, 300));

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                File file = filePicker();
                fileToOutput(file);// Add text to output
            }
        });

        // Add action listener to clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputField.setText(""); // Clear outputField
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700,600);
        frame.setLayout(new FlowLayout());
        
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
        panelOuter.add(panelInner);

        frame.add(panelOuter);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private File filePicker(){
        JFileChooser filePicker = new JFileChooser();
        int rv = filePicker.showSaveDialog(null);
        File file = null;
        if(rv == JFileChooser.APPROVE_OPTION){
            file = filePicker.getSelectedFile();
            return file;
        }
        else {
            return file;
        }
    }
    private void fileToOutput(File file){
        Scanner sc;
        try {
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                outputField.append(sc.nextLine());
                outputField.append("\n");
            }
        } catch (FileNotFoundException ex) {
            outputField.append(ex.getMessage());
        }
        
    }

}