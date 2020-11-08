public class Config {

    final static Integer FPS = 60;
    final static Integer WINDOW_WIDTH = 800;
    final static Integer WINDOW_HEIGHT = 600;
    final static boolean DEBUG = false; // Flag to activate some console text
    final static Integer PLAYER_W = 20;
    final static Integer PLAYER_H = 85;
    final static Integer BALL_W = 20;
    final static Integer BALL_H = 20;
    final static Integer BALL_SPEED_MIN = 2;
    final static Integer BALL_SPEED_MAX = 4;
    final static Integer FRAMES_PER_BALL = 300;
    final static Integer GAMEOVER_SCORE = 6;

    enum GAMESTATES {
        MAINMENU, GAMEPLAY, P1_SCORE, P2_SCORE, GAMEOVER
    }

}
