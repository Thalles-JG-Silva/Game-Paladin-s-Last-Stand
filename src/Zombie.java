import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Zombie {
    private Texture texture;
    private TextureRegion textureRegion;
    private Vector2 position;
    private Rectangle bounds;
    private final float SPEED = 2f;
    private final float SCALE = 0.2f; 
    private static final String[] ZOMBIE_TEXTURES = {
        "assets/Zombies/male/Walk (1).png",
        "assets/Zombies/female/Walk (1).png"
    };

    public Zombie(float x, float y) {
        String zombieTexturePath = ZOMBIE_TEXTURES[new Random().nextInt(ZOMBIE_TEXTURES.length)];
        if (!Gdx.files.internal(zombieTexturePath).exists()) {
            throw new RuntimeException("Erro: Arquivo " + zombieTexturePath + " não encontrado!");
        }

        texture = new Texture(Gdx.files.internal(zombieTexturePath));
        textureRegion = new TextureRegion(texture);
        textureRegion.flip(true, false);

        position = new Vector2(x, y);
        bounds = new Rectangle(position.x, position.y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public void update() {
        position.x -= SPEED;
        bounds.setPosition(position);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(textureRegion, position.x, position.y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return texture.getWidth() * SCALE;
    }

    public void dispose() {
        texture.dispose();
    }
}
