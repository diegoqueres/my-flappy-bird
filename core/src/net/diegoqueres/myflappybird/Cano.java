package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.canoVelX;
import static net.diegoqueres.myflappybird.Constantes.canoWidth;
import static net.diegoqueres.myflappybird.Constantes.screenY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cano implements Elemento {
    public enum POSICAO_CANO { DENTRO_TELA, FORA_TELA }

    private Texture tex;

    public Rectangle corpo;

    private boolean posicionadoEncima;

    public Cano(float posX, float posY, boolean posicionadoEncima) {
        this.posicionadoEncima = posicionadoEncima;
        if (posicionadoEncima) {
            corpo = new Rectangle(posX, posY, canoWidth, screenY);
        } else {
            corpo = new Rectangle(posX, posY-screenY, canoWidth, screenY);
        }

        tex = new Texture("cano.png");
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tex, corpo.x, corpo.y, corpo.getWidth(), corpo.getHeight(),
                0, 0, tex.getWidth(), tex.getHeight(), false, isPosicionadoEncima());
    }

    public void update(float deltaTime) {
        corpo.x += canoVelX * deltaTime;
    }

    public POSICAO_CANO getPosicaoCano() {
        if (corpo.x + corpo.getWidth() < 0) {
            return POSICAO_CANO.FORA_TELA;
        }
        return POSICAO_CANO.DENTRO_TELA;
    }

    public void dispose() {
        tex.dispose();
    }

    public boolean isPosicionadoEncima() {
        return posicionadoEncima;
    }
}
