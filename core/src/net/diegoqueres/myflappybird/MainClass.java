package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.passaroIniX;
import static net.diegoqueres.myflappybird.Constantes.screenY;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainClass extends ApplicationAdapter {
	private SpriteBatch batch;
	private Fundo fundo;
	private Passaro passaro;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
		passaro = new Passaro(passaroIniX, screenY/2);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		draw();
		batch.end();
	}

	private void update(float deltaTime) {
		fundo.update(deltaTime);
		passaro.update(deltaTime);
	}

	private void draw() {
		fundo.draw(batch);
		passaro.draw(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		fundo.dispose();
	}
}
