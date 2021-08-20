package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.volumeSons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import net.diegoqueres.myflappybird.Constantes.SOM;

import java.util.HashMap;
import java.util.Map;

public class Som {
    Map<SOM, Sound> mapSons;

    public Som() {
        mapSons = new HashMap<>();
        mapSons.put(SOM.VOA, Gdx.audio.newSound(Gdx.files.internal("sons/somVoa.mp3")));
        mapSons.put(SOM.BATE, Gdx.audio.newSound(Gdx.files.internal("sons/somBate.mp3")));
        mapSons.put(SOM.PONTUA, Gdx.audio.newSound(Gdx.files.internal("sons/somPontua.mp3")));
    }

    public void play(SOM som) {
        Sound sound = mapSons.get(som);
        if (sound != null)
            sound.play(volumeSons);
    }

    public void dispose() {
        for (Sound sound : mapSons.values())
            sound.dispose();

        mapSons = null;
    }
}
