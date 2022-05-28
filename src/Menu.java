import view.BGM;
import view.ChessGameFrame;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {

    private final JButton Game_Start;
    private final JButton Game_Over;
    public Menu(){

        setLayout(new FlowLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setLocationRelativeTo(null);

        Game_Start = new JButton(" Start ");
        Game_Over = new JButton(" Over ");
        this.add(Game_Start);
        this.add(Game_Over);
        Game_Start.addActionListener(this);
        Game_Over.addActionListener(this);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Game_Start){
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                ChessGameFrame mainFrame = new ChessGameFrame(900, 600);
                mainFrame.setVisible(true);


                BGM audioPlayWave = new BGM("./images/Dream.wav");// 开音乐 音樂名
                audioPlayWave.start();
                @SuppressWarnings("unused")
                int musicOpenLab = 1;

            });
        }
        if(e.getSource() == Game_Over){
            this.dispose();
            System.exit(0);
        }
    }
}

