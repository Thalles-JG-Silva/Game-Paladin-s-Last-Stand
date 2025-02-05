import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;
    private static float speed = 2f;
    private final float SCALE = 0.5f;
    private static final float SPEED_INCREMENT = 0.1f;

    public Obstacle(float x, float y){
        texture = new Texture(Gdx.files.internal("assets/obstacle.png"));
        position = new Vector2(x, y);
        bounds = new Rectangle(position.x + 10, position.y, (texture.getWidth() - 20) * SCALE, texture.getHeight() * SCALE);
    }

    public void update(){
        position.x -= speed;
        bounds.setPosition(position.x + 10, position.y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean isOffScreen(){
        return position.x + bounds.getWidth() < 0;
    }

    public static void increaseSpeed() {
        speed += SPEED_INCREMENT;
    }

    public void dispose(){
        texture.dispose();
    }
}
