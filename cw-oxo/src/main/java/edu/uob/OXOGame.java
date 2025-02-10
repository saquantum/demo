package edu.uob;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public final class OXOGame extends Frame implements WindowListener, ActionListener, MouseListener, KeyListener {
    @Serial private static final long serialVersionUID = 1;
    private static Font FONT = new Font("SansSerif", Font.PLAIN, 14);

    OXOController controller;
    TextField inputBox;
    OXOView view;

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        new OXOGame(250, 300);
    }

    public OXOGame(int width, int height) {
        super("OXO Board");
        OXOModel model = new OXOModel(3, 3, 3);
        model.addPlayer(new OXOPlayer('X'));
        model.addPlayer(new OXOPlayer('O'));
        controller = new OXOController(model);
        inputBox = new TextField("");
        inputBox.addActionListener(this);
        inputBox.setFont(FONT);
        inputBox.addKeyListener(this);
        view = new OXOView(model);
        view.addMouseListener(this);
        view.addKeyListener(this);
        Panel contentPane = new Panel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputBox, BorderLayout.SOUTH);
        contentPane.add(view, BorderLayout.CENTER);
        this.setLayout(new GridLayout(1, 1));
        this.add(contentPane);
        this.setSize(width, height);
        this.setVisible(true);
        this.addWindowListener(this);
        this.setLocationRelativeTo(null);
    }

    public Insets getInsets() {
        return new Insets(30, 7, 7, 7);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            String command = inputBox.getText();
            inputBox.setText("");
            controller.handleIncomingCommand(command);
            view.repaint();
        } catch (OXOMoveException exception) {
            System.out.println("Game move exception: " + exception);
        }
    }

    public void mousePressed(MouseEvent event) {
        if (event.getX() < 35) {
            if (event.isPopupTrigger()) controller.removeRow();
            else if (event.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) controller.removeRow();
            else controller.addRow();
        }
        if (event.getY() < 35) {
            if (event.isPopupTrigger()) controller.removeColumn();
            else if (event.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) controller.removeColumn();
            else controller.addColumn();
        }
        view.repaint();
    }

    public void keyPressed(KeyEvent event) {
        inputBox.setText(inputBox.getText().replace("=",""));
        inputBox.setText(inputBox.getText().replace("-",""));
        inputBox.setText(inputBox.getText().replace("[",""));
        inputBox.setText(inputBox.getText().replace("]",""));
        view.repaint();
    }

    public void keyReleased(KeyEvent event) {
        inputBox.setText(inputBox.getText().replace("=",""));
        inputBox.setText(inputBox.getText().replace("-",""));
        inputBox.setText(inputBox.getText().replace("[",""));
        inputBox.setText(inputBox.getText().replace("]",""));
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) controller.reset();
        // my save and load
        if(inputBox.getText().equals("s")){
            inputBox.setText(inputBox.getText().replace("s",""));
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream((new FileOutputStream("savegame")));
                oos.writeObject(controller.gameModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(inputBox.getText().equals("l")){
            inputBox.setText(inputBox.getText().replace("l",""));
            File file = new File("savegame");
            ObjectInputStream ois = null;
            if(file.exists()){
                try {
                    ois = new ObjectInputStream(new FileInputStream("savegame"));
                    OXOModel newState = (OXOModel) ois.readObject();
                    controller = new OXOController(newState);
                    inputBox = new TextField("");
                    inputBox.addActionListener(this);
                    inputBox.setFont(FONT);
                    inputBox.addKeyListener(this);
                    view = new OXOView(newState);
                    view.addMouseListener(this);
                    view.addKeyListener(this);
                    Panel contentPane = new Panel();
                    contentPane.setLayout(new BorderLayout());
                    contentPane.add(inputBox, BorderLayout.SOUTH);
                    contentPane.add(view, BorderLayout.CENTER);
                    this.setLayout(new GridLayout(1, 1));
                    this.remove(this.getComponent(0));
                    this.add(contentPane);
                    this.setSize(250, 300);
                    this.setVisible(true);
                    this.addWindowListener(this);
                    this.setLocationRelativeTo(null);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }finally {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        view.repaint();
    }

    public void keyTyped(KeyEvent event) {
        if (event.getKeyChar() == '=') controller.increaseWinThreshold();
        if (event.getKeyChar() == '-') controller.decreaseWinThreshold();
        if (event.getKeyChar() == ']') controller.addAnotherPlayer();
        if (event.getKeyChar() == '[') controller.removePlayer();
        view.repaint();
    }

    public void mouseClicked(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void windowActivated(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowClosed(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}

    public void windowClosing(WindowEvent e) {
        this.dispose();
        System.exit(0);
    }
}
