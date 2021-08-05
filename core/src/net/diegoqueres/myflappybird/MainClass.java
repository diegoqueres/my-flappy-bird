package net.diegoqueres.myflappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainClass extends ApplicationAdapter {
	private SpriteBatch batch;
	private Fundo fundo;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		draw();
		batch.end();
	}

	private void update(float time) {
		fundo.update(time);
	}

	private void draw() {
		fundo.draw(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		fundo.dispose();
	}
}
