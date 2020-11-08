import java.awt.Color;

public class Text {

    int x;
    int y;
    String text;
    Color color = Color.black;
    int size = 12;
    boolean centered;

    Text (String text, int x, int y, Color c, int s, boolean centered) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = c;
        this.size = s;
        this.centered = centered;
    }

}