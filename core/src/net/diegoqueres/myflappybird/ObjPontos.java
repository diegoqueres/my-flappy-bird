package net.diegoqueres.myflappybird;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static net.diegoqueres.myflappybird.Constantes.*;

public class ObjPontos implements Elemento {
    public Rectangle corpo;

    public ObjPontos(float posX, float posY) {
        corpo = new Rectangle(posX, posY, 10, gap);
    }

    @Override
    public void update(float deltaTime) {
        corpo.x += canoVelX * deltaTime;
    }

    @Override
    public POSICAO getPosicao() {
        if (corpo.x + corpo.getWidth() < 0) {
            return POSICAO.FORA_TELA;
        }
        return POSICAO.DENTRO_TELA;
    }

    public void draw(SpriteBatch batch) {}

    public void dispose() {}
}
