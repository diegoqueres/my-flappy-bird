package net.diegoqueres.myflappybird;

import com.badlogic.gdx.Gdx;

public class Constantes {
    public static int screenX = Gdx.graphics.getWidth();
    public static int screenY = Gdx.graphics.getHeight();

    public static float canoVelX = -0.3f * screenX;

    public static final int PASSARO_NUM_TEXTURAS = 6;
    public static int passaroRad = (int) (0.06f * screenY);
    public static int passaroIniX = (int) (0.2f * screenX);
    public static float acelGravidade = screenY/1.5f;     //gravidade
    public static float impulso = screenY/5f;

    public static int canoWidth = (int) (0.2f * screenX);

    public static final float tempoCanos = 3f;
    public static final float rangePosCano = 0.85f - 0.15f;
    public static int posMaxCano = (int) (rangePosCano * screenY);
    public static int gap = (int) (0.2f * screenY);

    public static int btnTamanho = (int) (0.4f * screenX);
    public static int btnX = (int) ((0.6f/2f) * screenX);
    public static int btnY = (screenY - btnTamanho)/2;

    public static final float volumeSons = 0.9f;

    public enum ESTADO { PARADO, RODANDO, PERDEU, AGUARDANDO_RESTART }
    public enum POSICAO { DENTRO_TELA, FORA_TELA }
    public enum SOM { VOA, BATE, PONTUA }
}
