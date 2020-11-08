import java.awt.Color;

public class Player {
    
    int x;
    int y = (Config.WINDOW_HEIGHT / 2) - 43;
    int width = Config.PLAYER_W;
    int height = Config.PLAYER_H;
    Color color = Color.white;
    int speed = 10;
    Integer id = -1;

    enum Movement {
        NULL, UP, DOWN
    } 

    Movement movement = Movement.NULL;

    Player(boolean left) {
        if (left) {
            this.x = 40;
        } else {
            this.x = Config.WINDOW_WIDTH - 65;
        }
    }

    void move(boolean down) {
        if (down) {
            if (this.y+this.height+this.speed < Config.WINDOW_HEIGHT)
                this.y += speed;
        } else {
            if (this.y-this.speed > 0)
                this.y -= speed;
        }
    }

    public void moveDown() {
        move(true);       
    }

    public void moveUp() {
        move(false);
    }

    public void paint() {
        if (this.id.equals(-1)) {
            // Declare graphic
            if (Config.DEBUG)
                System.out.println("Objecto declarado");
            G.blocks.add(new Block(this.x, this.y, this.width, this.height, this.color));
            this.id = G.blocks.size() - 1;
            if (Config.DEBUG)
                System.out.println("Nuevo id: " + this.id);
        } else {
            G.blocks.set(this.id, new Block(this.x, this.y, this.width, this.height, this.color));
        }
    }

}
