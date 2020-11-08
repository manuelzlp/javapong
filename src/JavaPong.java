import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * 
 * 
 * Code by Manu Gimenez
 * github.com/manuelzlp
 * 
 * 08/11/2020 Torino, Italia
 * 
 */

public class JavaPong {
    public static void main(String[] args) throws Exception {

        // Start of program

        // Configure main window
        State.window.getContentPane().add(State.window.g);
        State.window.setTitle("Java Pong!");
        State.window.setVisible(true);

        // Game loop
        final long fpsMs = 1000 / Config.FPS;
        long startLoopMs;
        long endLoopMs;
        boolean componentsDeclared = false;

        // Game variables
        Player player1 = null;
        Player player2 = null;
        Ball[] balls = new Ball[9];
        Integer player1_score = 0;
        Integer player2_score = 0;
        int player1_score_text = 0;
        int player2_score_text = 0;
        int score_message = 0;
        Integer frame_last_ball = 0;
        boolean showTitle = true;
        Integer showTitleFrame = 0;
        int titleTextId = 0;

        while (true) { // Game loop start
            /**
             * Start game loop
             */

            startLoopMs = System.currentTimeMillis();
            State.currentFrame++;

            switch (State.state) {
                case MAINMENU:
                 /**
                 * START MAIN MENU
                 */

                 if (!componentsDeclared) {
                     componentsDeclared = true;

                     // Clear all objects
                     G.texts.clear();
                     G.blocks.clear();
                     G.circles.clear();

                     showTitle = true;
                     showTitleFrame = State.currentFrame;

                     G.texts.add(new Text("", 0, 400, Color.white, 20, true));
                     titleTextId = G.texts.size() - 1;

                     // Rest of text
                     G.texts.add(new Text("PONG!", -5, 195, new Color(255, 128, 0), 140, true));
                     G.texts.add(new Text("PONG!", 2, 208, new Color(50, 50, 255), 140, true));
                     G.texts.add(new Text("PONG!", 0, 200, Color.white, 140, true));
                     G.texts.add(new Text("Java", -200, 250, Color.white, 40, true));

                     G.texts.add(new Text("Player One", -300, 350, new Color(255, 128, 0), 20, true));
                     G.texts.add(new Text("Controls: W, S", -300, 400, Color.white, 18, true));
                     G.texts.add(new Text("Player Two", 280, 350, new Color(255, 128, 0), 20, true));
                     G.texts.add(new Text("Controls: UP, DOWN", 280, 400, Color.white, 18, true));

                     G.texts.add(new Text("Score 6 goals to win!", 0, 350, new Color(50, 50, 255), 20, true));

                     G.texts.add(new Text("Press ESC to exit", 0, 450, Color.white, 20, true));

                     G.texts.add(new Text("By Manu Gimenez | github.com/manuelzlp", 0, 550, Color.white, 18, true));
                 }

                 if (showTitleFrame + 30 <= State.currentFrame) { // Show and hide the menu every 60 frames
                    showTitleFrame = State.currentFrame;
                    showTitle = !showTitle;
                 } 

                 if (showTitle) {
                     G.texts.get(titleTextId).text = "Press ENTER to play!!";
                 } else {
                    G.texts.get(titleTextId).text = "";   
                 }

                 // Check keyboard
                 if (Keyboard.keys[KeyEvent.VK_ENTER]) {
                     player1_score = 0;
                     player2_score = 0;
                     State.state = Config.GAMESTATES.GAMEPLAY;
                     componentsDeclared = false;
                 }

                 /**
                  * END MAIN MENU
                  */
                break;

                case GAMEPLAY:

                /**
                 * START GAMEPLAY
                 */

                 if (!componentsDeclared) {
                     // Declare components
                     componentsDeclared = true;

                     G.texts.clear();

                     // Add texts to main texts array and store the collection index
                     G.texts.add(new Text(player1_score.toString(), -100, 200, Color.white, 50, true)); 
                     player1_score_text = G.texts.size() - 1;
                     G.texts.add(new Text(player2_score.toString(), 100, 200, Color.white, 50, true));
                     player2_score_text = G.texts.size() - 1;
                     G.texts.add(new Text("", 0, 400, new Color(255, 128, 0), 40, true));
                     score_message = G.texts.size() - 1;

                     // Restore positions
                     G.circles.clear();
                     G.blocks.clear();

                     // Delete all balls
                     for (int n=0;n<9;n++)
                        balls[n] = new Ball(n);
                     
                     balls[0].active = true;
                     player1 = new Player(true);
                     player2 = new Player(false);

                     frame_last_ball = State.currentFrame;

                     player1.paint();
                     player2.paint();

                     G.texts.get(score_message).text = "3";
                     State.window.g.repaint();
                     Thread.sleep(500);
                     G.texts.get(score_message).text = "2";
                     State.window.g.repaint();
                     Thread.sleep(500);
                     G.texts.get(score_message).text = "1";
                     State.window.g.repaint();
                     Thread.sleep(500);
                     G.texts.get(score_message).text = "GO!!";
                     State.window.g.repaint();
                     Thread.sleep(1000);
                     G.texts.get(score_message).text = "";

                 }

                 // Key events
                 // Player 1
                 if (Keyboard.keys[KeyEvent.VK_S]) {
                    player1.moveDown();
                    player1.movement = Player.Movement.DOWN;
                } else if (Keyboard.keys[KeyEvent.VK_W]) {
                   player1.moveUp();
                   player1.movement = Player.Movement.UP;
                } else {
                    player1.movement = Player.Movement.NULL;
                }

                 // Player 2
                 if (Keyboard.keys[KeyEvent.VK_DOWN]) {
                     player2.moveDown();
                     player2.movement = Player.Movement.DOWN;
                 } else if (Keyboard.keys[KeyEvent.VK_UP]) {
                    player2.moveUp();
                    player2.movement = Player.Movement.UP;
                 } else {
                    player2.movement = Player.Movement.NULL;
                 }

                 // Process the ball physics
                 for (int n=0;n<9;n++) {
                     if (balls[n].active) {
                        balls[n].checkCollision(player1);
                        balls[n].checkCollision(player2);
                        balls[n].move();
                        balls[n].paint();
                     }
                 }                    

                 // Add new balls
                 if (frame_last_ball + Config.FRAMES_PER_BALL <= State.currentFrame) {
                     frame_last_ball = State.currentFrame;

                     // Search for next inactive ball
                     for (int n=0;n<9;n++) {
                         if (!balls[n].active) {
                            balls[n].active = true;
                            break;
                         }
                     }
                 }
                 
                 // Repaint elements
                 player1.paint();
                 player2.paint();

                /**
                 * END GAMEPLAY
                 */
                    
                break;
                case P1_SCORE:
                /** 
                 * P1 SCORE
                 */

                 // Update score
                 player1_score++;
                 G.texts.get(player1_score_text).text = player1_score.toString();

                 // Show message
                 G.texts.get(score_message).text = "PLAYER 1 SCORED!!";

                 // Paint ball
                 for (int n=0;n<6;n++) {
                    G.circles.get(balls[State.score_ball_id].id).color = Color.white;
                    State.window.g.repaint();
                    Thread.sleep(200);
                    G.circles.get(balls[State.score_ball_id].id).color = Color.red;
                    State.window.g.repaint();
                    Thread.sleep(200);
                 }

                 if (player1_score > Config.GAMEOVER_SCORE-1) {
                    State.state = Config.GAMESTATES.GAMEOVER;
                 } else {
                    componentsDeclared = false;
                    State.state = Config.GAMESTATES.GAMEPLAY;
                 }

                /** 
                 * END P1 SCORE
                 */
                break;

                case P2_SCORE:
                /** 
                 * P2 SCORE
                 */
                
                 // Update score
                 player2_score++;
                 G.texts.get(player2_score_text).text = player2_score.toString();

                 // Show message
                 G.texts.get(score_message).text = "PLAYER 2 SCORED!!";
                 
                 // Paint ball
                 for (int n=0;n<6;n++) {
                    G.circles.get(balls[State.score_ball_id].id).color = Color.white;
                    State.window.g.repaint();
                    Thread.sleep(200);
                    G.circles.get(balls[State.score_ball_id].id).color = Color.red;
                    State.window.g.repaint();
                    Thread.sleep(200);
                 }

                 if (player2_score > Config.GAMEOVER_SCORE-1) {
                    State.state = Config.GAMESTATES.GAMEOVER;
                 } else {
                    componentsDeclared = false;
                    State.state = Config.GAMESTATES.GAMEPLAY;
                 }
                 
                /** 
                 * END P2 SCORE
                 */
                break;

                case GAMEOVER:
                /**
                 * GAMEOVER
                 */

                 if (player1_score > player2_score)
                    G.texts.get(score_message).text = "PLAYER 1 WON!!";
                else
                    G.texts.get(score_message).text = "PLAYER 2 WON!!";
                
                 Thread.sleep(4000);
                 componentsDeclared = false;
                 State.state = Config.GAMESTATES.MAINMENU;

                /**
                 * END GAMEOVER
                 */
                break;
            } // End game switch

            // Clear all keys on this loop
            //Arrays.fill(Keyboard.keys, false);

            // Check ESC key to end the game at any moment
            if (Keyboard.keys[27]) // Escape == quit
            System.exit(0);
           
            State.window.g.repaint();
            endLoopMs = System.currentTimeMillis() - startLoopMs;

            // If faster than expected then wait
            if (endLoopMs < fpsMs)
                Thread.sleep(fpsMs - endLoopMs);

            /**
             * End game loop
             */
        } // Game loop end
    }
}
