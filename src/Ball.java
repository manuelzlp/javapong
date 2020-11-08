import java.awt.Color;
import java.awt.Rectangle;

public class Ball {
    
    int x = (Config.WINDOW_WIDTH / 2) - Config.BALL_W / 2;
    int y = (Config.WINDOW_HEIGHT / 2) - Config.BALL_H / 2;
    int width = Config.BALL_W;
    int height = Config.BALL_H;
    Color color = Color.cyan;
    int speed = Config.BALL_SPEED_MIN;
    Integer id = -1;
    boolean moveUp = true;
    boolean moveLeft = true;
    boolean active = false;
    Color[] colors = {
        Color.red,
        Color.yellow,
        Color.green,
        Color.blue,
        Color.cyan,
        Color.pink,
        new Color(255, 128, 0)
    };
    Integer ball_id;

    Ball(Integer ball_id) {
        this.ball_id = ball_id;
        int rnd = (int) (Math.random() * (colors.length - 1));
        this.color = colors[rnd];       
        
        rnd = (int) (Math.random() * 2);
        if (rnd > 0)
            this.moveLeft = false;
        rnd = (int) (Math.random() * 2);
            if (rnd > 0)
                this.moveUp = false;

        // Random new speed
        rnd = (int) (Math.random() * (Config.BALL_SPEED_MAX - Config.BALL_SPEED_MIN)) + Config.BALL_SPEED_MIN;
        this.speed += rnd;
    }

    // Collisions
    public void checkCollision(Player player) {

        int px = player.x;
        int pw = player.width;
        int py = player.y;
        int ph = player.height;

        int bx = this.x;
        int bw = this.width;
        int by = this.y;
        int bh = this.height;

        Rectangle recPlayer = new Rectangle(px, py, pw, ph);
        Rectangle recBall = new Rectangle(bx, by, bw, bh);
       
        // Check if ball touches player on any point
        if (recBall.intersects(recPlayer)) {
            if (Config.DEBUG)
                System.out.println("Touching a player");

            // Check if collision is with top or bottom line
            if (recBall.intersectsLine(px, py, px+pw, py)) {
                // Top border
                if (Config.DEBUG)
                    System.out.println("Collision with TOP BORDER");
                this.moveLeft = !this.moveLeft;
                this.moveUp = true;
                // Avoid clipping
                if (this.moveLeft) {
                    this.x = px - bw - 1;
                } else {
                    this.x = px + pw + 1;
                }
            } else if (recBall.intersectsLine(px, py+ph, px+pw, py+ph)) {
                // Bottom border
                if (Config.DEBUG)
                    System.out.println("Collision with BOTTOM BORDER");
                this.moveLeft = !this.moveLeft;
                this.moveUp = false;
                // Avoid clipping
                if (this.moveLeft) {
                    this.x = px - bw - 1;
                } else {
                    this.x = px + pw + 1;
                }
            } else {
                if (player.movement == Player.Movement.NULL) {
                    this.moveLeft = !this.moveLeft;
                    if (Config.DEBUG)
                        System.out.println("Collision with player - not moving");
                } else {
                    this.moveUp = !this.moveUp;
                    this.moveLeft = !this.moveLeft;
                    if (Config.DEBUG)
                        System.out.println("Collision with player - MOVING");
                }
            }
        }

    }

    void move() {
        // Movement

        if (this.moveUp) {
            this.y -= this.speed;
        } else {
            this.y += this.speed;
        }

        if (this.moveLeft) {
            this.x -= this.speed;
        } else {
            this.x += this.speed;
        }

        // Collision with borders
        if (this.x <= 0) {
            this.moveLeft = false;
            if (Config.DEBUG)
                System.out.println("Player 1 lost!");
            State.score_ball_id = this.ball_id;
            State.state = Config.GAMESTATES.P2_SCORE;
        }
        if (this.x >= Config.WINDOW_WIDTH - this.width) {
            this.moveLeft = true;
            if (Config.DEBUG)
                System.out.println("Player 2 lost!");
            State.score_ball_id = this.ball_id;
            State.state = Config.GAMESTATES.P1_SCORE;
        }

        if (this.y <= 0) {
            this.moveUp = false;
        }
        if (this.y + this.height >= Config.WINDOW_HEIGHT) {
            this.moveUp = true;
        }
    }

    public void paint() {
        if (this.id.equals(-1)) {
            // Declare graphic
            if (Config.DEBUG)
                System.out.println("Objecto declarado");
            G.circles.add(new Circle(this.x, this.y, this.width, this.height, this.color));
            this.id = G.circles.size() - 1;
            if (Config.DEBUG)
                System.out.println("Nuevo id: " + this.id);
        } else {
            G.circles.set(this.id, new Circle(this.x, this.y, this.width, this.height, this.color));
        }
    }

}