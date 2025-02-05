import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private final float GRAVITY = -0.3f;
    private final float JUMP_VELOCITY = 15f;
    private final float SCALE = 0.8f; // Ajustado para um tamanho maior

    private Animation<TextureRegion> runAnimation, jumpAnimation, deathAnimation;
    private float stateTime;
    private boolean isDead;
    private long lastHitTime;
    private final long INVULNERABILITY_TIME = 2000; // 2 segundos de invulnerabilidade

    private enum PlayerState { RUNNING, JUMPING, DYING }
    private PlayerState currentState;

    public Player() {
        position = new Vector2(100, 150);
        velocity = new Vector2(0, 0);
        stateTime = 0;
        isDead = false;
        loadAnimations();
        currentState = PlayerState.RUNNING;
        lastHitTime = 0;
        
        // Ajustando a hitbox para o novo tamanho maior
        bounds = new Rectangle(position.x + 15, position.y, 60 * SCALE, 75 * SCALE);
    }

    private void loadAnimations() {
        runAnimation = loadAnimation("assets/Player/Run", 10, Animation.PlayMode.LOOP);
        jumpAnimation = loadAnimation("assets/Player/Jump", 10, Animation.PlayMode.NORMAL);
        deathAnimation = loadAnimation("assets/Player/Dead", 10, Animation.PlayMode.NORMAL);
    }

    private Animation<TextureRegion> loadAnimation(String path, int frameCount, Animation.PlayMode mode) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= frameCount; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal(path + " (" + i + ").png"))));
        }
        Animation<TextureRegion> animation = new Animation<>(0.1f, frames);
        animation.setPlayMode(mode);
        return animation;
    }

    public void update(float delta) {
        stateTime += delta;

        if (currentState == PlayerState.DYING && deathAnimation.isAnimationFinished(stateTime)) {
            isDead = true;
        }

        if (currentState != PlayerState.DYING) {
            velocity.y += GRAVITY;
            position.add(velocity);
            if (position.y < 100) {
                position.y = 100;
                velocity.y = 0;
                currentState = PlayerState.RUNNING;
            }
            bounds.setPosition(position.x + 15, position.y); // Ajustando a hitbox corretamente
        }
    }

    public void jump() {
        if (currentState == PlayerState.RUNNING) {
            velocity.y = JUMP_VELOCITY;
            currentState = PlayerState.JUMPING;
        }
    }

    public void die() {
        if (currentState != PlayerState.DYING) {
            currentState = PlayerState.DYING;
            stateTime = 0;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean canBeHit() {
        return TimeUtils.millis() - lastHitTime > INVULNERABILITY_TIME;
    }

    public void takeHit() {
        lastHitTime = TimeUtils.millis();
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, position.x, position.y, 60 * SCALE, 75 * SCALE);
    }

    private TextureRegion getCurrentFrame() {
        if (currentState == PlayerState.DYING) {
            return deathAnimation.getKeyFrame(stateTime);
        }
        return currentState == PlayerState.JUMPING ? jumpAnimation.getKeyFrame(stateTime) : runAnimation.getKeyFrame(stateTime);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void reset() {
        position.set(100, 150);
        velocity.set(0, 0);
        isDead = false;
        currentState = PlayerState.RUNNING;
        stateTime = 0;
    }
}
