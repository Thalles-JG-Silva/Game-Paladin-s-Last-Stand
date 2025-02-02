import java.util.Iterator;
import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Player player;
    private Array<Zombie> zombies;
    private long lastSpawnTime;
    private int score;

    @Override
    public void create() {
        batch = new SpriteBatch();

        if (!Gdx.files.internal("assets/Castle_Background.png").exists()) {
            throw new RuntimeException("Erro: Arquivo assets/Castle_Background.png não encontrado!");
        }
        background = new Texture(Gdx.files.internal("assets/Castle_Background.png"));

        player = new Player();
        zombies = new Array<>();
        spawnZombie();
        score = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        update();

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.draw(batch);

        for (Zombie zombie : zombies) {
            zombie.draw(batch);
        }

        batch.end();
    }

    private void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.attack();
        }

        player.update();

        if (TimeUtils.nanoTime() - lastSpawnTime > 2000000000) {
            spawnZombie();
        }

        for (Iterator<Zombie> iterator = zombies.iterator(); iterator.hasNext();) {
            Zombie zombie = iterator.next();
            zombie.update();

            if (zombie.getBounds().overlaps(player.getBounds())) {
                player.die();
                Gdx.app.log("Game", "Game Over! O paladino foi pego pelos zumbis!");
                resetGame();
                break;
            }

            if (zombie.getPosition().x < -zombie.getWidth()) {
                score++;
                iterator.remove();
            }
        }
    }

    private void spawnZombie() {
        zombies.add(new Zombie(Gdx.graphics.getWidth(), 100));
        lastSpawnTime = TimeUtils.nanoTime();
    }

    private void resetGame() {
        zombies.clear();
        player.reset();
        score = 0;
        spawnZombie();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
        for (Zombie zombie : zombies) {
            zombie.dispose();
        }
    }
}
