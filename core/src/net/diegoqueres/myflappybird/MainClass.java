package net.diegoqueres.myflappybird;

import static net.diegoqueres.myflappybird.Constantes.*;
import static net.diegoqueres.myflappybird.Constantes.ESTADO.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.google.gwt.core.shared.GwtIncompatible;

import java.util.Random;

public class MainClass extends ApplicationAdapter {
	private SpriteBatch batch;
	private Fundo fundo;
	private Passaro passaro;
	private Array<Cano> canos;
	private Array<ObjPontos> objPontos;
	private float tempoCano;
	private ESTADO estadoJogo;
	private int pontos;
	private BitmapFont fonte;
	private GlyphLayout glyphLayout;
	private Botao btnStart;
	private Botao btnRestart;
	private Som som;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
		passaro = new Passaro(passaroIniX, screenY/2);
		canos = new Array<>();
		objPontos = new Array<>();
		tempoCano = tempoCanos;
		estadoJogo = PARADO;
		pontos = 0;
		generateFont();

		btnStart = new Botao(new Texture("botoes/BotaoPlay.png"), btnX, btnY, btnTamanho);
		btnRestart = new Botao(new Texture("botoes/BotaoRestart.png"), btnX, btnY, btnTamanho);
		som = new Som();
	}

	private void generateFont() {
		if(Gdx.app.getType() != Application.ApplicationType.WebGL) {
			generateFreeTypeFont();
		} else {
			fonte = new BitmapFont(Gdx.files.internal("gwtFont.fnt"));
		}

		glyphLayout = new GlyphLayout();
	}

	@GwtIncompatible
	private void generateFreeTypeFont() {
		com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.setMaxTextureSize(2048);
		com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator generator =
				new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter parameter =
				new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int) (0.2f * screenX);
		parameter.color = Color.WHITE;
		fonte = generator.generateFont(parameter);
		generator.dispose();
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

			atualizaCanos(deltaTime);
			adicionaNovosCanos(deltaTime);
			detectaColisaoCanos(deltaTime);
		}

		if (estadoJogo == RODANDO || estadoJogo == PERDEU) {
			passaro.update(deltaTime);
			if (passaro.getPosicao() == POSICAO.FORA_TELA) {
				estadoJogo = AGUARDANDO_RESTART;
			}
		}
	}

	private void atualizaCanos(float deltaTime) {
		for (int i = 0; i < canos.size; i++) {
			canos.get(i).update(deltaTime);
			if (canos.get(i).getPosicao() == POSICAO.FORA_TELA) {
				canos.removeIndex(i);
				i--;
			}
		}
		for (int i = 0; i < objPontos.size; i++) {
			objPontos.get(i).update(deltaTime);
			if (objPontos.get(i).getPosicao() == POSICAO.FORA_TELA) {
				objPontos.removeIndex(i);
				i--;
			}
		}
	}

	private void adicionaNovosCanos(float deltaTime) {
		tempoCano -= deltaTime;
		if (tempoCano <= 0) {
			Random random = new Random();
			int pos = random.nextInt(posMaxCano);
			pos -= posMaxCano / 2;
			canos.add(new Cano(screenX, screenY / 2 + pos + gap / 2, true));
			canos.add(new Cano(screenX, screenY / 2 + pos - gap / 2, false));
			objPontos.add(new ObjPontos(screenX + canoWidth + 2*passaroRad, screenY / 2 + pos - gap / 2));
			tempoCano = tempoCanos;
		}
	}

	private void detectaColisaoCanos(float deltaTime) {
		for (Cano cano : canos) {
			if (Intersector.overlaps(passaro.corpo, cano.corpo)) {
				Gdx.app.log("Log", "Bateeeeuuuuu!");
				som.play(SOM.BATE);
				passaro.perdeu();
				estadoJogo = PERDEU;
			}
		}
		for (int i = 0; i < objPontos.size; i++) {
			ObjPontos o = objPontos.get(i);
			if (Intersector.overlaps(passaro.corpo, o.corpo)) {
				pontos++;
				Gdx.app.log("Log", "Pontuou. Nova pontuação: " + String.valueOf(pontos));
				som.play(SOM.PONTUA);
				objPontos.removeIndex(i);
				i--;
			}
		}
	}

	private void draw() {
		fundo.draw(batch);

		for (Cano c : canos) {
			c.draw(batch);
		}

		passaro.draw(batch);

		drawPontuacao();
		drawBotoes();
	}

	private void drawPontuacao() {
		String pontuacao = String.valueOf(pontos);
		float pontuacaoWidth = getTamX(fonte, pontuacao);
		fonte.draw(batch, pontuacao,
				(screenX - pontuacaoWidth)/2,
				0.98f * screenY);
	}

	private void drawBotoes() {
		if (estadoJogo == PARADO)	btnStart.draw(batch);
		else if (estadoJogo == AGUARDANDO_RESTART)	btnRestart.draw(batch);
	}

	private void input() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = screenY - Gdx.input.getY();

			switch (estadoJogo) {
				case PARADO:
					btnStart.setPressed(x, y);
					break;
				case RODANDO:
					passaro.impulso();
					som.play(SOM.VOA);
					break;
				case AGUARDANDO_RESTART:
					btnRestart.setPressed(x, y);
					break;
			}
		} else {
			if (btnStart.pressed) {
				estadoJogo = RODANDO;
				btnStart.pressed = false;
			}
			if (btnRestart.pressed) {
				restart();
				btnRestart.pressed = false;
			}
		}
	}

	private void restart() {
		estadoJogo = RODANDO;
		passaro = new Passaro(passaroIniX, screenY/2);
		canos.clear();
		objPontos.clear();
		pontos = 0;
		tempoCano = tempoCanos;
	}

	private float getTamX(BitmapFont fonte, String texto) {
		glyphLayout.reset();
		glyphLayout.setText(fonte, texto);
		return glyphLayout.width;
	}

	@Override
	public void dispose () {
		batch.dispose();
		fonte.dispose();
		som.dispose();
		disposeElements(new Elemento[]{fundo, passaro, btnStart, btnRestart});
		disposeElements(canos.toArray());
	}

	private void disposeElements(Elemento[] elementos) {
		for (Elemento el : elementos)
			el.dispose();
	}
}
