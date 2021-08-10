package net.diegoqueres.myflappybird;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Elemento {
    void draw(SpriteBatch batch);
    void update(float deltaTime);
    void dispose();
}
