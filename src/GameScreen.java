import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends ApplicationAdapter {
    private enum GameState { MENU, PLAYING, DYING, GAME_OVER }
    private GameState gameState = GameState.MENU;

    private SpriteBatch batch;
    private Texture background;
    private Player player;
    private Array<Obstacle> obstacles;
    private long lastObstacleTime;
    private int score, lives;
    private BitmapFont font;
    private Sound deathSound, gameOverSound, bgMusic;
    private long deathTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("assets/city_background.png"));
        player = new Player();
        obstacles = new Array<>();
        font = new BitmapFont();

        // Sons
        deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/morte.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("assets/gameover.mp3"));
        bgMusic = Gdx.audio.newSound(Gdx.files.internal("assets/ambiente.mp3"));
        bgMusic.loop();

        lives = 3;
        score = 0;
        spawnObstacle();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == GameState.MENU) {
            font.draw(batch, "PRESSIONE ENTER PARA INICIAR", Gdx.graphics.getWidth() / 2 - 80, Gdx.graphics.getHeight() / 2);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                startGame();
            }
        } else if (gameState == GameState.PLAYING) {
            float delta = Gdx.graphics.getDeltaTime();
            player.update(delta);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                player.jump();
            }

            player.draw(batch);

            for (Obstacle obstacle : obstacles) {
                obstacle.update();
                obstacle.draw(batch);
                if (obstacle.getBounds().overlaps(player.getBounds()) && player.canBeHit()) {
                    lives--;
                    player.takeHit();
                    if (lives <= 0) {
                        deathSound.play();
                        player.die();
                        deathTime = TimeUtils.millis();
                        gameState = GameState.DYING;
                    }
                }
            }

            if (TimeUtils.nanoTime() - lastObstacleTime > 4000000000L) {
                spawnObstacle();
            }

            score++;
            font.draw(batch, "Vidas: " + lives, 20, Gdx.graphics.getHeight() - 20);
            font.draw(batch, "Pontos: " + score, 20, Gdx.graphics.getHeight() - 50);

        } else if (gameState == GameState.DYING) {
            player.update(Gdx.graphics.getDeltaTime());
            player.draw(batch);
            if (TimeUtils.millis() - deathTime > 2000) {
                gameState = GameState.GAME_OVER;
                gameOverSound.play();
            }
        } else if (gameState == GameState.GAME_OVER) {
            font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                gameState = GameState.MENU;
            }
        }

        batch.end();
    }

    private void spawnObstacle() {
        obstacles.add(new Obstacle(Gdx.graphics.getWidth(), 100));
        lastObstacleTime = TimeUtils.nanoTime();
    }

    private void startGame() {
        gameState = GameState.PLAYING;
        lives = 3;
        score = 0;
        player.reset();
        obstacles.clear();
        spawnObstacle();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
        deathSound.dispose();
        gameOverSound.dispose();
        bgMusic.dispose();
    }
}
