package com.brutalfighters.game.map;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameMap {
	
	private static final String BLOCKED = "blocked"; //$NON-NLS-1$
	private static final String TELEPORT = "teleport"; //$NON-NLS-1$
	
	private String mapName;
	
	private TiledMap map;
	
	private TiledMapTileLayer colLayer;
	
	private float cellWidth; // Float because LibGDX's Tiled has float width and height.
	private float cellHeight; // Float because LibGDX's Tiled has float width and height.
	
	protected int leftBoundary;
	protected int rightBoundary;
	protected int topBoundary;
	protected int botBoundary;
	
	private final float TILE_SCALE;
	
	private List<Teleport> teleports;
	
	public GameMap(String name, float scale) {
		mapName = name;
		
		TILE_SCALE = scale;
		
		LoadMap("maps/" + name + "/" + name + ".tmx"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		teleports = new ArrayList<Teleport>();
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				if(hasProperty(TELEPORT(), x, y)) {
					// LibGDX library for Tiled has the function: `getCell(x,y)` it flips the Y, which means 0 will be the bottom,
					// unlike in Tiled or the map file, which is 0 there is the top of the map.
					// That's why we don't need to flip the position of the teleport, but we need to flip the destination of the teleport.
					String[] target = ((String) getCell(x,y).getTile().getProperties().get("teleport")).split(","); //$NON-NLS-1$ //$NON-NLS-2$
					teleports.add(new Teleport(toPixelX(x),toPixelY(y), toPixelX(Integer.parseInt(target[0])), (int) getHeightSize() - toPixelY(Integer.parseInt(target[1]))));
				}
			}
		}
	}
	
	public void LoadMap(String path) {
		System.out.println("Searching and locating the map to load it!"); //$NON-NLS-1$
		
		map = new TmxMapLoader().load(path);
		
		colLayer = (TiledMapTileLayer) map.getLayers().get(0);
		
		cellWidth = colLayer.getTileWidth();
		cellHeight = colLayer.getTileHeight();
		
		this.setLeftBoundary(5);
		this.setRightBoundary((int)getWidthSize()-5);
		
		this.setBotBoundary(5);
		this.setTopBoundary((int)getHeightSize()-5);
	}
	
	public TiledMap getMap() {
		return map;
	}
	public String getName() {
		return mapName;
	}
	public TiledMapTileLayer getColLayer() {
		return colLayer;
	}
	public Cell getCell(int x, int y) {
		return colLayer.getCell(x, y);
	}
	public Cell getCell(float x, float y) {
		return colLayer.getCell(toCellX(x), toCellY(y));
	}
	public boolean isBlocked(int x, int y) {
		return hasProperty(BLOCKED(), x, y);
	}
	public boolean isBlocked(float x, float y) {
		return hasProperty(BLOCKED(), x, y);
	}
	public float getTileWidth() {
		return cellWidth * getScaled();
	}
	public float getTileHeight() {
		return cellHeight * getScaled();
	}
	public float getWidthSize() {
		return getWidth() * getTileWidth();
	}
	public float getHeightSize() {
		return getHeight() * getTileHeight();
	}
	public float getWidth() {
		return colLayer.getWidth();
	}
	public float getHeight() {
		return colLayer.getHeight();
	}
	public int toCellX(float x) {
		return (int) (x / getTileWidth());
	}
	public int toCellY(float y) {
		return (int) (y / getTileHeight());
	}
	public int toPixelX(float x) {
		return (int) ((x) * getTileWidth() + getTileWidth()/2);
	}
	public int toPixelY(float y) {
		return (int) ((y) * getTileHeight() + getTileHeight()/2);
	}
	
	// Boundaries
	public int getRightBoundary() {
		return rightBoundary;
	}
	public void setRightBoundary(int rightBoundary) {
		this.rightBoundary = rightBoundary;
	}
	public int getLeftBoundary() {
		return leftBoundary;
	}
	public void setLeftBoundary(int leftBoundary) {
		this.leftBoundary = leftBoundary;
	}
	
	public int getTopBoundary() {
		return topBoundary;
	}
	public void setTopBoundary(int topBoundary) {
		this.topBoundary = topBoundary;
	}
	public int getBotBoundary() {
		return botBoundary;
	}
	public void setBotBoundary(int botBoundary) {
		this.botBoundary = botBoundary;
	}
	
	public boolean hasProperty(String key, int x, int y) {
		return hasProperty(key, getCell(x,y));
	}
	public boolean hasProperty(String key, float x, float y) {
		return hasProperty(key, getCell(x,y));
	}
	
	public static boolean hasProperty(String key, Cell cell) {
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(key);
	}
	
	public float getScaled() {
		return TILE_SCALE;
	}
	
	public List<Teleport> getTeleports() {
		return teleports;
	}
	public static String BLOCKED() {
		return BLOCKED;
	}
	public static String TELEPORT() {
		return TELEPORT;
	}
	
	public int alignTiledX(int posx) {
		return (posx/(int)getTileWidth()) * (int)getTileWidth();
	}
	public int alignTiledY(int posy) {
		return (posy/(int)getTileHeight()) * (int)getTileHeight();
	}
	
	// Collision Detection
	public Rectangle getBounds(float x, float y) {
		Rectangle bounds = new Rectangle(alignTiledX((int)x), alignTiledY((int)y), (int)getTileWidth(), (int)getTileHeight());
		return bounds;
	}
	public boolean intersect(float x, float y, Rectangle bounds) {
		return getBounds(x,y).intersects(bounds);
	}
	
	/**
	 * @param bounds The rectangle is used for AABB but it's disabled currently as it's not needed. 
	 */
	public boolean intersects(float x, float y, Rectangle bounds) {
		if(isBlocked(x,y)) {
			//return intersect(x,y,bounds); AABB Implemented
			return true;
		}
		return false;
	}
	public boolean intersectsSurroundX(float x, float y, Rectangle bounds) {
		return intersects(x-bounds.width/2+1,y, bounds) || intersects(x,y, bounds) || intersects(x+bounds.width/2-1,y, bounds);
	}
	public boolean intersectsSurroundY(float x, float y, Rectangle bounds) {
		return intersects(x,y-bounds.height/2+1, bounds) || intersects(x,y, bounds) || intersects(x,y+bounds.height/2-1, bounds);
	}
	
	
	public void dispose() {
		System.out.println("Disposing The Map"); //$NON-NLS-1$
		map.dispose();
	}
}
