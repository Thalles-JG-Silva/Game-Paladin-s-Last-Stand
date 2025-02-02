import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Texture idleTexture;
    private Texture jumpTexture;
    private Texture attackTexture;
    private Texture deadTexture;
    private Texture currentTexture;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    
    private final float GRAVITY = -0.3f;
    private final float JUMP_VELOCITY = 15f;
    private final float SCALE = 0.2f; 
    
    private boolean attacking;
    private boolean isDead;

    public Player() {
        // Verifica se todos os arquivos necessários existem antes de carregar
        checkFileExists("assets/Player/Idle (1).png");
        checkFileExists("assets/Player/Jump (1).png");
        checkFileExists("assets/Player/Attack (1).png");
        checkFileExists("assets/Player/Dead (1).png");

        idleTexture = new Texture(Gdx.files.internal("assets/Player/Idle (1).png"));
        jumpTexture = new Texture(Gdx.files.internal("assets/Player/Jump (1).png"));
        attackTexture = new Texture(Gdx.files.internal("assets/Player/Attack (1).png"));
        deadTexture = new Texture(Gdx.files.internal("assets/Player/Dead (1).png"));

        currentTexture = idleTexture;
        
        position = new Vector2(50, 100);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(position.x, position.y, currentTexture.getWidth() * SCALE, currentTexture.getHeight() * SCALE);
        
        attacking = false;
        isDead = false;
    }

    public void update() {
        if (isDead) {
            return;
        }

        velocity.y += GRAVITY;
        position.add(velocity);

        if (position.y < 100) {
            position.y = 100;
            velocity.y = 0;
            currentTexture = idleTexture;
        }

        bounds.setPosition(position);
    }

    public void jump() {
        if (position.y == 100) {
            velocity.y = JUMP_VELOCITY;
            currentTexture = jumpTexture;
        }
    }

    public void attack() {
        if (!attacking) {
            attacking = true;
            currentTexture = attackTexture;
            Gdx.app.log("Game", "Paladino atacando!");
        }
    }

    public void die() {
        isDead = true;
        currentTexture = deadTexture;
        Gdx.app.log("Game", "Paladino morreu!");
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentTexture, position.x, position.y, currentTexture.getWidth() * SCALE, currentTexture.getHeight() * SCALE);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void reset() {
        position.set(50, 100);
        velocity.set(0, 0);
        isDead = false;
        attacking = false;
        currentTexture = idleTexture;
    }

    public void dispose() {
        idleTexture.dispose();
        jumpTexture.dispose();
        attackTexture.dispose();
        deadTexture.dispose();
    }

    private void checkFileExists(String path) {
        if (!Gdx.files.internal(path).exists()) {
            throw new RuntimeException("Erro: Arquivo " + path + " não encontrado!");
        }
    }
}
