package net.diegoqueres.myflappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Botao implements Elemento {
    private Rectangle corpo;

    public boolean pressed;
    private float dimensionUnpressed;
    private float timerPressButton;

    private Texture texture;

    public Botao(Texture texture, int posX, int posY, int size) {
        this.corpo = new Rectangle(posX, posY, size, size);
        this.texture = texture;

        this.pressed = false;
        this.timerPressButton = 0f;
        this.dimensionUnpressed = 1.15f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (pressed) {
            float increaseWidth = corpo.getWidth() * (dimensionUnpressed -1f);
            float increaseWidthToSide = increaseWidth * .5f;
            float increaseHeight = corpo.getHeight() * (dimensionUnpressed -1f);
            float increaseHeightToSide = increaseHeight * .5f;
            batch.draw(texture,
                    corpo.x - increaseWidthToSide, corpo.y - increaseHeightToSide,
                    corpo.getWidth() * dimensionUnpressed, corpo.getHeight() * dimensionUnpressed);
        } else {
            batch.draw(texture, corpo.x, corpo.y, corpo.getWidth(), corpo.getHeight());
        }
    }

    public void setPressed(int x, int y) {
        pressed = (corpo.x <= x && corpo.x + corpo.width >= x)
                && (corpo.y <= y && corpo.y + corpo.height >= y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void update(float deltaTime) {}

    public Constantes.POSICAO getPosicao() {
        return null;
    }
}
