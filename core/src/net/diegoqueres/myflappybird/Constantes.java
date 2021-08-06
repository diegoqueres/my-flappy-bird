package net.diegoqueres.myflappybird;

import com.badlogic.gdx.Gdx;

public class Constantes {
    public static int screenX = Gdx.graphics.getWidth();
    public static int screenY = Gdx.graphics.getHeight();

    public static float canoVelX = -0.3f * screenX;

    public static final int PASSARO_NUM_TEXTURAS = 6;
    public static int passaroRad = (int) (0.06f * screenY);
    public static int passaroIniX = (int) (0.2f * screenX);
}
