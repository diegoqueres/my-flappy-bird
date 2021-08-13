package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Passaro implements Elemento {
    public Circle corpo;

    private Texture[] frames;
    private float auxFrames;

    private Vector2 velocidade;

    public Passaro(int posX, int posY) {
        corpo = new Circle(posX, posY, passaroRad);

        frames = new Texture[PASSARO_NUM_TEXTURAS];
        for (int i=1; i<=PASSARO_NUM_TEXTURAS; i++) {
            frames[i-1] = new Texture(String.format("felpudo/felpudoVoa%d.png", i));
        }

        velocidade = new Vector2(0, 0);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(frames[(int)auxFrames % PASSARO_NUM_TEXTURAS],
                corpo.x - corpo.radius, corpo.y - corpo.radius,
                corpo.radius * 2, corpo.radius * 2
        );
    }

    public void update(float deltaTime) {
        auxFrames += 6 * deltaTime;

        corpo.x += velocidade.x * deltaTime;
        corpo.y += velocidade.y * deltaTime;
        velocidade.y -= acelGravidade * deltaTime;

        adjustOutEdges();
    }

    private void adjustOutEdges() {
        if (corpo.y + corpo.radius >= screenY) {
            corpo.y = screenY - corpo.radius;
            velocidade.y = -impulso;
        }
        if (corpo.y - corpo.radius <= 0) {
            corpo.y = 0 + corpo.radius;
            velocidade.y = impulso;
        }
    }

    public POSICAO getPosicao() {
        if (corpo.x + corpo.radius <= 0) {
            return POSICAO.FORA_TELA;
        }
        return POSICAO.DENTRO_TELA;
    }

    public void impulso() {
        velocidade.y += impulso;
    }

    public void dispose() {
        for (Texture frame : frames) {
            frame.dispose();
        }
    }

    public void perdeu() {
        velocidade.x = 2*canoVelX;
        velocidade.y = 0;
    }

    public void restart(int posX, int posY) {
        corpo = new Circle(posX, posY, passaroRad);
        velocidade = new Vector2(0, 0);
    }
}
