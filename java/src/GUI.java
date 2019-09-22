
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class IListener implements ItemListener {
    GUI gui;

    public IListener(GUI gui) {
        this.gui = gui;
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            gui.selectedMicro = e.getItem().toString();
            gui.play.setText(gui.selectedMicro);
        }
    }
}

class BListener implements ActionListener {
    GUI gui;

    public BListener(GUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        gui.audioHandler.playSound("hatch_lock.wav", gui.selectedMicro);
    }
}

public class GUI extends JFrame {
    JComboBox<String> avaliableMicros;
    AudioHandler audioHandler;
    String selectedMicro;
    JButton play;

    public GUI() {
        super();
        this.setContentPane(new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("background.png"), 0, 0, null);
            }
        });
        audioHandler = new AudioHandler();
        selectedMicro = "None";
        setLayout(null);
        init();
    }

    private void init() {
        setTitle("Soundpad by ANDERSONARC");
        setSize(500, 539);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        avaliableMicros = new JComboBox<>(audioHandler.mixerInfoToString());
        avaliableMicros.addItemListener(new IListener(this));
        avaliableMicros.setBounds(143, 201, 319, 25);
        avaliableMicros.setFocusable(true);
        avaliableMicros.setBackground(new Color(102, 102, 102));
        add(avaliableMicros);
        play = new JButton() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("button.png"), 0, 0, null);
            }
        };
        play.addActionListener(new BListener(this));
        play.setBounds(38, 203, 93, 93);
        add(play);
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        repaint();
    }

}
