import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Creating instance of JFrame
        JFrame frame = new JFrame("Signature Generator");
        // Setting the width and height of frame
        frame.setSize(750, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        // adding panel to frame
        frame.add(panel);

        placeComponents(panel);

        // Setting the frame visibility to true
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // Creating JLabel
        final JLabel privKeyLabel = new JLabel("Private Key :");
        privKeyLabel.setBounds(4, 4, 80, 25);
        panel.add(privKeyLabel);

        final JTextArea privKeyText = new JTextArea();
        privKeyText.setBounds(80, 10, 600, 200);
        panel.add(privKeyText);

        final JLabel fileLabel = new JLabel("File path :");
        fileLabel.setBounds(100, 280, 80, 25);
        panel.add(fileLabel);

        final JTextField pathText = new JTextField();
        pathText.setBounds(160, 280, 500, 25);
        //pathText.setBackground(Color.lightGray);
        pathText.setEditable(false);
        panel.add(pathText);

        // Creating Select File button
        JButton FileButton = new JButton("Choose File");
        FileButton.setBounds(190, 230, 120, 30);
        panel.add(FileButton);

        // Creating Genarate button
        final JButton genarateButton = new JButton("Generate Signature");
        genarateButton.setBounds(320, 230, 150, 30);
        genarateButton.setBackground(Color.pink);
        genarateButton.setForeground(Color.black);
        genarateButton.setEnabled(false);
        panel.add(genarateButton);

        // Creating Copy button
        final JButton copyButton = new JButton("Copy Signature");
        copyButton.setBounds(300, 460, 140, 30);
        copyButton.setEnabled(false);
        panel.add(copyButton);

        // Creating Clear button
        final JButton clearButton = new JButton("Clear Text");
        clearButton.setBounds(480, 230, 120, 30);
        clearButton.setEnabled(false);
        panel.add(clearButton);

        // Creating JLabel
        final JLabel signatureLabel = new JLabel("Signature");
        signatureLabel.setBounds(350, 325, 80, 35);
        panel.add(signatureLabel);

        // Creating JLabel
        final JLabel errorLabel = new JLabel("");
        errorLabel.setBounds(10, 425, 700, 25);
        errorLabel.setForeground(Color.red);
        panel.add(errorLabel);

        // Creating Keys area
        final JTextArea sign = new JTextArea();
        sign.setBounds(4, 280, 600, 65);
        panel.add(sign);

        JScrollPane scrollPane = new JScrollPane(sign);
        scrollPane.setBounds(80, 360, 600, 65);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);

        FileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    pathText.setText(file.getAbsolutePath());
                }

                if (pathText.getText().isEmpty()) {
                    genarateButton.setEnabled(false);
                    clearButton.setEnabled(false);
                } else {
                    genarateButton.setEnabled(true);
                    clearButton.setEnabled(true);
                }

                if (privKeyText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must complete the Private key !!!");
                }

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathText.setText("");
                privKeyText.setText("");
                sign.setText("");
                errorLabel.setText("");
                genarateButton.setEnabled(false);
                clearButton.setEnabled(false);
                copyButton.setEnabled(false);
            }
        });

        genarateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (privKeyText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must complete the Private key !!!");
                } else {
                    RSAGenDigiSign getsign = new RSAGenDigiSign();

                    String signature = getsign.GenerateSignature(pathText.getText(), privKeyText.getText());
                    String checkSign = signature.substring(0, Math.min(signature.length(), 5));


                    if (checkSign.equals("ERROR")) {
                        copyButton.setEnabled(false);
                        sign.setEnabled(false);
                        sign.setEditable(false);
                        sign.setText("");
                        errorLabel.setText(signature);
                    } else {
                        copyButton.setEnabled(true);
                        sign.setEnabled(true);
                        sign.setEditable(true);
                        sign.setText(signature);
                        errorLabel.setText("");
                    }
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myString = sign.getText().toString();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(null, "Successfully copied Signature !!!");
            }
        });

    }

}
