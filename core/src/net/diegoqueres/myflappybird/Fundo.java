package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.canoVelX;
import static net.diegoqueres.myflappybird.Constantes.screenX;
import static net.diegoqueres.myflappybird.Constantes.screenY;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fundo implements Elemento {
    private Texture texture;

    private float posX1;
    private float posX2;

    public Fundo() {
        texture = new Texture("fundo.png");

        posX1 = 0;
        posX2 = screenX;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, posX1, 0, screenX, screenY);
        batch.draw(texture, posX2, 0, screenX, screenY);
    }

    @Override
    public void update(float deltaTime) {
        posX1 += deltaTime * canoVelX;
        posX2 += deltaTime * canoVelX;

        if (posX1 + screenX <= 0) {
            posX1 = screenX;
            posX2 = 0;
        }
        if (posX2 + screenX <= 0) {
            posX2 = screenX;
            posX1 = 0;
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Constantes.POSICAO getPosicao() {
        return null;
    }
}
