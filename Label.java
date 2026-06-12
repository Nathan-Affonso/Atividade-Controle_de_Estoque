import javax.swing.*;
import java.awt.*;
    public class Label extends JLabel{

        Label(String ltexto){
        setText(ltexto);
        setFont(new Font(null,Font.PLAIN,16));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        }

    }
