package com.lowp.kungfuboy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * 游戏资源类
 * 
 * @author lowp
 *
 */
public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	public float frameDuration = 1.0f / 12.0f;

	public AssetGameObject gameObject;
	public AssetHero hero;
	public AssetMaps maps;
	public AssetsLionMonster lionMonster;
	public AssetsStoneMonster stoneMonster;
	public AssetsWoodMonster woodMonster;
	public AssetsGUI gameGUI;
	public AssetFonts assetFonts;
	public AssetMusic assetMusic;
	public AssetSound assetSound;

	// 单例
	private Assets() {
	}

	// 初始化资源管理器
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		// 将资源添加到加载队列
		assetManager.load(Constants.TEXTURE_ATLAS_HERO, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_MAPS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_LIONS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_STONE, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_WOOD, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_GUI, TextureAtlas.class);
		assetManager.load("audio/btn_select.wav", Sound.class);
		assetManager.load("audio/button.wav", Sound.class);
		assetManager.load("audio/hero_att.wav", Sound.class);
		assetManager.load("audio/hero_log.wav", Sound.class);
		assetManager.load("audio/hero_fist.wav", Sound.class);
		assetManager.load("audio/hero_dao.wav", Sound.class);
		assetManager.load("audio/hero_gun.wav", Sound.class);
		assetManager.load("audio/hero_jump.wav", Sound.class);
		assetManager.load("audio/hero_run.wav", Sound.class);
		assetManager.load("audio/hero_isHit.wav", Sound.class);
		assetManager.load("audio/hero_isHit2.wav", Sound.class);
		assetManager.load("audio/hero_jineng1.wav", Sound.class);
		assetManager.load("audio/hero_jineng2.wav", Sound.class);
		assetManager.load("audio/monster_att.wav", Sound.class);
		assetManager.load("audio/liondie.wav", Sound.class);
		assetManager.load("audio/stonedie.wav", Sound.class);
		assetManager.load("audio/wooddie.wav", Sound.class);
		assetManager.load("audio/gameScreen.mp3", Music.class);
		assetManager.load("audio/menuScreen.mp3", Music.class);
		assetManager.load("audio/selectScreen.mp3", Music.class);
		assetManager.load("audio/victory.mp3", Music.class);
		// 加载资源
		assetManager.finishLoading();

		// 打印资源列表
		Gdx.app.debug(TAG,
				"# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas hero_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_HERO);
		for (Texture texture : hero_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		TextureAtlas maps_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_MAPS);
		for (Texture texture : maps_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		TextureAtlas lion_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_LIONS);
		for (Texture texture : lion_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		TextureAtlas stone_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_STONE);
		for (Texture texture : stone_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		TextureAtlas wood_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_WOOD);
		for (Texture texture : wood_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		TextureAtlas gui_gamescreen_Atlas = assetManager
				.get(Constants.TEXTURE_ATLAS_GUI);
		for (Texture texture : gui_gamescreen_Atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		gameObject = new AssetGameObject(hero_Atlas);
		hero = new AssetHero(hero_Atlas);
		maps = new AssetMaps(maps_Atlas);
		lionMonster = new AssetsLionMonster(lion_Atlas);
		stoneMonster = new AssetsStoneMonster(stone_Atlas);
		woodMonster = new AssetsWoodMonster(wood_Atlas);
		gameGUI = new AssetsGUI(gui_gamescreen_Atlas);
		assetFonts = new AssetFonts();
		assetMusic = new AssetMusic(assetManager);
		assetSound = new AssetSound(assetManager);
	}

	// 音乐资源类
	public class AssetMusic {
		public final Music gameScreen;
		public final Music menuScreen;
		public final Music selectScreen;
		public final Music victory;

		public AssetMusic(AssetManager manager) {
			gameScreen = manager.get("audio/gameScreen.mp3", Music.class);
			menuScreen = manager.get("audio/menuScreen.mp3", Music.class);
			selectScreen = manager.get("audio/selectScreen.mp3", Music.class);
			victory = manager.get("audio/victory.mp3", Music.class);
		}
	}

	// 音效资源类
	public class AssetSound {
		public final Sound button;
		public final Sound btn_select;
		public final Sound hero_att;
		public final Sound hero_log;
		public final Sound hero_fist;
		public final Sound hero_dao;
		public final Sound hero_gun;
		public final Sound hero_jump;
		public final Sound hero_run;
		public final Sound hero_isHit;
		public final Sound hero_isHit2;
		public final Sound hero_jineng1;
		public final Sound hero_jineng2;
		public final Sound monster_att;
		public final Sound liondie;
		public final Sound stonedie;
		public final Sound wooddie;

		public AssetSound(AssetManager manager) {
			btn_select = manager.get("audio/btn_select.wav", Sound.class);
			button = manager.get("audio/button.wav", Sound.class);
			hero_att = manager.get("audio/hero_att.wav", Sound.class);
			hero_log = manager.get("audio/hero_log.wav", Sound.class);
			hero_fist = manager.get("audio/hero_fist.wav", Sound.class);
			hero_dao = manager.get("audio/hero_dao.wav", Sound.class);
			hero_gun = manager.get("audio/hero_gun.wav", Sound.class);
			hero_jump = manager.get("audio/hero_jump.wav", Sound.class);
			hero_run = manager.get("audio/hero_run.wav", Sound.class);
			hero_isHit = manager.get("audio/hero_isHit.wav", Sound.class);
			hero_isHit2 = manager.get("audio/hero_isHit2.wav", Sound.class);
			hero_jineng1 = manager.get("audio/hero_jineng1.wav", Sound.class);
			hero_jineng2 = manager.get("audio/hero_jineng2.wav", Sound.class);
			monster_att = manager.get("audio/monster_att.wav", Sound.class);
			liondie = manager.get("audio/liondie.wav", Sound.class);
			stonedie = manager.get("audio/stonedie.wav", Sound.class);
			wooddie = manager.get("audio/wooddie.wav", Sound.class);
		}

	}

	// 字体资源类
	public class AssetFonts {
		public final BitmapFont lianjiFont;

		public AssetFonts() {
			lianjiFont = new BitmapFont(
					Gdx.files.internal("font/lianji/lianji.fnt"), false);
			lianjiFont.getData().scale(0.01f);
			lianjiFont.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		}
	}

	// 游戏对象共有资源类
	public class AssetGameObject {
		public Animation animShadow;

		public AssetGameObject(TextureAtlas atlas) {

			Array<AtlasRegion> regions = null;
			regions = atlas.findRegions("shadow");
			animShadow = new Animation(frameDuration, regions, PlayMode.NORMAL);

		}
	}

	// 英雄资源类
	public class AssetHero {
		// 头上标记
		public final Animation animHeadMark;
		// 状态动画
		public final Animation animNormal;
		public final Animation animRun;
		public final Animation animJump_up;
		public final Animation animJump_down;
		public final Animation animAttack_hand;
		public final Animation animAttack_leg;
		public final Animation animHurt;
		// 技能动画
		public final Animation animSkill_combo;
		public final Animation animSkill_bakandao;
		public final Animation animSkill_gun;

		public AssetHero(TextureAtlas atlas) {

			Array<AtlasRegion> regions = null;
			regions = atlas.findRegions("headMark");
			animHeadMark = new Animation(frameDuration / 2f, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("idle");
			animNormal = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("fist");
			animAttack_hand = new Animation(frameDuration / 1.8f, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("leg");
			animAttack_leg = new Animation(frameDuration / 1.8f, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("run");
			animRun = new Animation(frameDuration/1.2f, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("jumpup");
			animJump_up = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("jumpdown");
			animJump_down = new Animation(frameDuration, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("hurt");
			animHurt = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("combo");
			animSkill_combo = new Animation(frameDuration, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("bakandao");
			animSkill_bakandao = new Animation(frameDuration / 1.3f, regions,
					PlayMode.NORMAL);

			regions = atlas.findRegions("gun");
			animSkill_gun = new Animation(frameDuration, regions,
					PlayMode.NORMAL);

		}
	}

	// 地图资源类
	public class AssetMaps {
		public final AtlasRegion level01;
		public final AtlasRegion level02_left;
		public final AtlasRegion level02_right;
		public final AtlasRegion level03_left;
		public final AtlasRegion level03_right;
		public final AtlasRegion level04_left;
		public final AtlasRegion level04_right;
		public final AtlasRegion level05_left;
		public final AtlasRegion level05_right;

		public AssetMaps(TextureAtlas atlas) {
			level01 = atlas.findRegion("level01");
			level02_left = atlas.findRegion("level02", 1);
			level02_right = atlas.findRegion("level02", 2);
			level03_left = atlas.findRegion("level03", 1);
			level03_right = atlas.findRegion("level03", 2);
			level04_left = atlas.findRegion("level04", 1);
			level04_right = atlas.findRegion("level04", 2);
			level05_left = atlas.findRegion("level05", 1);
			level05_right = atlas.findRegion("level05", 2);
		}

	}

	// 狮子怪物资源类
	public class AssetsLionMonster {
		public final Animation animNormal;
		public final Animation animAttack;
		public final Animation animDie;
		public final Animation animHurt;
		public final Animation animWalk;

		public AssetsLionMonster(TextureAtlas atlas) {
			Array<AtlasRegion> regions = null;

			regions = atlas.findRegions("normal");
			animNormal = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("lionA");
			animAttack = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("lionDie");
			animDie = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("lionHurt");
			animHurt = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("lionWalk");
			animWalk = new Animation(frameDuration, regions, PlayMode.NORMAL);

		}
	}

	// 木人桩怪物资源类
	public class AssetsWoodMonster {
		public final Animation animNormal;
		public final Animation animAttack;
		public final Animation animDie;
		public final Animation animHurt;
		public final Animation animWalk;

		public AssetsWoodMonster(TextureAtlas atlas) {
			Array<AtlasRegion> regions = null;

			regions = atlas.findRegions("monsterA");
			animAttack = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("monsterDie");
			animDie = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("monsterHurt");
			animHurt = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("monsterWalk");
			animWalk = new Animation(frameDuration, regions, PlayMode.NORMAL);

			animNormal = new Animation(frameDuration, regions.get(4));
		}
	}

	// 石头人怪物资源类
	public class AssetsStoneMonster {
		public final Animation animAttack;
		public final Animation animDie;
		public final Animation animHurt;
		public final Animation animWalk;

		public AssetsStoneMonster(TextureAtlas atlas) {
			Array<AtlasRegion> regions = null;

			regions = atlas.findRegions("stoneA");
			animAttack = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("stoneDie");
			animDie = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("stoneHurt");
			animHurt = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("stoneWalk");
			animWalk = new Animation(frameDuration, regions, PlayMode.NORMAL);
		}

	}

	// 游戏界面的GUI资源
	public class AssetsGUI {
		public final Skin skin;
		public final AtlasRegion game_flashBg;
		public final AtlasRegion game_gameOverBg;
		public final AtlasRegion game_rotationLight;
		public final AtlasRegion icon_starBig;
		public final AtlasRegion icon_starLittle;
		public final AtlasRegion game_victory;
		public final AtlasRegion game_tipsNext;
		public final AtlasRegion icon_tili;
		public final AtlasRegion icon_jinbi;
		public final AtlasRegion select_title;
		public final AtlasRegion select_bg;
		public final AtlasRegion level01;
		public final AtlasRegion level02;
		public final AtlasRegion level03;
		public final AtlasRegion level04;
		public final AtlasRegion level05;
		public final AtlasRegion startScreen_bg;
		public final AtlasRegion startScreen_logo;
		public final AtlasRegion pause_bg;
		public final AtlasRegion dialog_bg;

		public final Animation animLianJi;
		public final Animation gameOverFlash;

		public AssetsGUI(TextureAtlas atlas) {
			skin = new Skin(Gdx.files.internal(Constants.SKIN_GUI), atlas);
			Array<AtlasRegion> regions = null;
			game_flashBg = atlas.findRegion("flashbackground");
			game_gameOverBg = atlas.findRegion("gameover-bg");
			game_rotationLight = atlas.findRegion("light");
			icon_starBig = atlas.findRegion("StarBig");
			icon_starLittle = atlas.findRegion("StarLittle");
			game_victory = atlas.findRegion("victory");
			icon_tili = atlas.findRegion("tili");
			icon_jinbi = atlas.findRegion("jinbi");
			game_tipsNext = atlas.findRegion("tipsNext");
			select_title = atlas.findRegion("select-title");
			select_bg = atlas.findRegion("select-bg");
			level01 = atlas.findRegion("level01");
			level02 = atlas.findRegion("level02");
			level03 = atlas.findRegion("level03");
			level04 = atlas.findRegion("level04");
			level05 = atlas.findRegion("level05");
			startScreen_bg = atlas.findRegion("MainMenuBackground");
			startScreen_logo = atlas.findRegion("Title");
			pause_bg = game_gameOverBg;
			dialog_bg = atlas.findRegion("BGPicSet");
			
			regions = atlas.findRegions("lianji");
			animLianJi = new Animation(frameDuration, regions, PlayMode.NORMAL);

			regions = atlas.findRegions("gameoverflash");
			gameOverFlash = new Animation(frameDuration, regions,
					PlayMode.NORMAL);

		}

	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "无法载入资源: '" + asset.fileName + "'",
				(Exception) throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

}
