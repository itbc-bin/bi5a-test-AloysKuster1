import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileChooserDemo extends JFrame
        implements ActionListener {

    private JButton openButton, visButton;
    private JFileChooser fileChooser;
    private JTextField nameField;
    private JTextArea textArea;
    private BufferedReader inFile;
    private JPanel panel;

    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }


        FileChooserDemo frame = new FileChooserDemo();
        frame.setSize(400, 350);
        frame.createGUI();
        frame.setVisible(true);
    }

    private void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        openButton = new JButton("open");
        window.add(openButton);
        openButton.addActionListener(this);

        visButton = new JButton("Kowalski analyze");
        window.add(visButton);
        visButton.addActionListener(this);

        nameField = new JTextField(25);
        window.add(nameField);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(200,200));
        window.add(textArea);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 40));
        panel.setBackground(Color.white);
        window.add(panel);

    }

    public void readFile() {

        try {
            inFile = new BufferedReader(new FileReader(nameField.getText()));
            textArea.setText("");
            String line;
            while ((line = inFile.readLine()) != null) {
                textArea.append(line + "\n");
            }
            inFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileChooserDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileChooserDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        String seq = textArea.getText();
        seq.replace("\n", "");

        if (event.getSource() == visButton) {
            for( int i = 0; i < seq.length(); i ++) {
                String amino = String.valueOf(seq.charAt(i));
                if( amino.matches("[AFILMPWV]")){
                    Graphics paper = panel.getGraphics();
                    //int loc = i * 2;
                    paper.setColor(Color.red);
                    paper.fillRect(i, 0, 1, 40);
                }

                if( amino.matches("[RNDCQEGHKSTY]")) {
                    Graphics paper = panel.getGraphics();
                    //int loc = i * 2;
                    paper.setColor(Color.BLUE);
                    paper.fillRect(i, 0, 1, 40);
                }
            }
        }
        if (event.getSource() == openButton) {
            fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                nameField.setText(selectedFile.getAbsolutePath());
                readFile();
            }
        }
    }
}
