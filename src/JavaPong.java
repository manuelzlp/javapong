import java.util.Arrays;
import java.awt.Color;
import java.awt.event.KeyEvent;

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

        while (true) { // Game loop start
            /**
             * Start game loop
             */

            startLoopMs = System.currentTimeMillis();
            State.currentFrame++;

            /*
            switch (GameState.state) {
            } // End game switch*/

            // Clear all keys on this loop
            Arrays.fill(Keyboard.keys, false);
           
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
