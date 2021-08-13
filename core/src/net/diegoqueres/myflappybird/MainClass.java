package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.*;
import static net.diegoqueres.myflappybird.Constantes.ESTADO.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MainClass extends ApplicationAdapter {
	private SpriteBatch batch;
	private Fundo fundo;
	private Passaro passaro;
	private Array<Cano> canos;
	private float tempoCano;
	private ESTADO estadoJogo;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
		passaro = new Passaro(passaroIniX, screenY/2);
		canos = new Array<>();
		tempoCano = tempoCanos;
		estadoJogo = PARADO;
	}

	@Override
	public void render () {
		input();

		update(Gdx.graphics.getDeltaTime());

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		draw();
		batch.end();
	}

	private void update(float deltaTime) {
		if (estadoJogo == RODANDO) {
			fundo.update(deltaTime);

			for (int i = 0; i < canos.size; i++) {
				canos.get(i).update(deltaTime);
				if (canos.get(i).getPosicao() == POSICAO.FORA_TELA) {
					canos.removeIndex(i);
					i--;
				}
			}

			tempoCano -= deltaTime;
			if (tempoCano <= 0) {
				Random random = new Random();
				int pos = random.nextInt(posMaxCano);
				pos -= posMaxCano / 2;
				canos.add(new Cano(screenX, screenY / 2 + pos + gap / 2, true));
				canos.add(new Cano(screenX, screenY / 2 + pos - gap / 2, false));
				tempoCano = tempoCanos;
			}

			for (Cano cano : canos) {
				if (Intersector.overlaps(passaro.corpo, cano.corpo)) {
					Gdx.app.log("Log", "Bateeeeuuuuu!");
					passaro.perdeu();
					estadoJogo = PERDEU;
				}
			}
		}

		if (estadoJogo == RODANDO || estadoJogo == PERDEU) {
			passaro.update(deltaTime);
			if (passaro.getPosicao() == POSICAO.FORA_TELA) {
				estadoJogo = AGUARDANDO_RESTART;
			}
		}
	}

	private void draw() {
		fundo.draw(batch);

		for (Cano c : canos) {
			c.draw(batch);
		}

		passaro.draw(batch);
	}

	private void input() {
		if (Gdx.input.justTouched()) {
			switch (estadoJogo) {
				case PARADO:
					estadoJogo = RODANDO;
					break;
				case RODANDO:
					passaro.impulso();
					break;
				case AGUARDANDO_RESTART:
					restart();
					break;
			}
		}
	}

	private void restart() {
		estadoJogo = RODANDO;
		passaro = new Passaro(passaroIniX, screenY/2);
		canos.clear();
		tempoCano = tempoCanos;
	}

	@Override
	public void dispose () {
		batch.dispose();
		disposeElements(new Elemento[]{fundo, passaro});
		disposeElements(canos.toArray());
	}

	private void disposeElements(Elemento[] elementos) {
		for (Elemento el : elementos)
			el.dispose();
	}
}
