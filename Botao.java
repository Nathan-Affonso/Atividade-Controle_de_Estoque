import javax.swing.*;
import java.awt.*;


    public class Botao extends JButton{

        Botao(String bnome){
        setText(bnome);
        setFont(new Font(null,Font.BOLD,20));
        setForeground(Color.BLACK);

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(0,50));

        }
    }
