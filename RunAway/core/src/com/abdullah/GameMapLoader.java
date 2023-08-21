package com.abdullah;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;


public class GameMapLoader implements Disposable {
	

	private GameMain  gameMain ;
	public OrthogonalTiledMapRenderer tileMapRederer;
	
	public TiledMap tiledMap;
	private TmxMapLoader tmxMapLoader;
	
	private String MapPath;
	public GameMapLoader() {
		
	}
	public GameMapLoader(GameMain gameMain ,String MapPath) {
		super();
		this.gameMain = gameMain ;
		this.MapPath=MapPath;
		this.MapLoad();
	}
	
	public void MapLoad() {
		this.tmxMapLoader=new TmxMapLoader();
		this.tiledMap =this.tmxMapLoader.load(String.valueOf(Gdx.files.internal(this.MapPath)));
		this.tileMapRederer=new OrthogonalTiledMapRenderer(this.tiledMap,1f/GameDefine.GAME_PPM,this.gameMain.spriteBatch);
		this.tileMapRederer.setView(this.gameMain .orthographicCamera);
	}
	public void ReMapLoad(String MapPath) {
		this.MapPath=MapPath;
		this.tmxMapLoader.load(this.MapPath);
		this.tileMapRederer.setMap(this.tiledMap);
		
	}
	public void render() {
		this.tileMapRederer.setView(this.gameMain.orthographicCamera);
		this.tileMapRederer.render();
		
	}
	public void info () {
		Iterable<String> keys3=(Iterable<String>) this.tiledMap.getProperties().getKeys();
		for (String string : keys3) {
			System.out.println(string +" : "+this.tiledMap.getProperties().get(string)); 
		}
	}
	
	public MapLayer getLayer(int index) {
		return this.tiledMap.getLayers().get(index);
	}
	public MapLayer getLayer(String name) {
		return this.tiledMap.getLayers().get(name);
	}
	public MapObjects getLayerObjects(int index) {
		return this.getLayer(index).getObjects();
	}
	public MapObjects getLayerObjects(String name) {
		return this.getLayer(name).getObjects();
	}
	
	@Override
	public void dispose() {
		this.tileMapRederer.dispose();
		this.tiledMap.dispose();
		
	}
	
	
	
	

	
}