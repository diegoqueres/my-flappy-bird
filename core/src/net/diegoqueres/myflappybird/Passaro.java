package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Passaro {
    public Circle corpo;

    private Texture[] frames;
    private float auxFrames;

    public Passaro(int posX, int posY) {
        corpo = new Circle(posX, posY, passaroRad);

        frames = new Texture[PASSARO_NUM_TEXTURAS];
        for (int i=1; i<=PASSARO_NUM_TEXTURAS; i++) {
            frames[i-1] = new Texture(String.format("felpudo/felpudoVoa%d.png", i));
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(frames[(int)auxFrames % PASSARO_NUM_TEXTURAS],
                corpo.x - corpo.radius, corpo.y - corpo.radius,
                corpo.radius * 2, corpo.radius * 2
        );
    }

    public void update(float deltaTime) {
        auxFrames += 6 * deltaTime;
    }

    public void dispose() {
        for (Texture frame : frames) {
            frame.dispose();
        }
    }
}
