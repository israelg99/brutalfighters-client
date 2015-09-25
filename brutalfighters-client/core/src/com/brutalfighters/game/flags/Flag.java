package com.brutalfighters.game.flags;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.utility.CollisionDetection;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;

public class Flag {
	private static int WIDTH = 150, HEIGHT = 200;
	private static float DELAY = 0.099f;
	
	private static TextureRegion[][] flagSprite;
	
	private FlagData flag;
	
	public static void Load() {
		flagSprite = TextureHandle.TextureSplit("flags/flags.png", 70, 75, true, false); //$NON-NLS-1$
	}
	
	public Flag(FlagData flag) {
		setFlag(flag);
	}
	
	public void setFlag(FlagData flag) {
		this.flag = flag;
	}
	public FlagData getFlag() {
		return flag;
	}
	
	private TexturePacker getDraw(int team) {
		return new TexturePacker(AnimationHandler.getAnimation(getFlag().getFlip(), flagSprite[team], DELAY, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(getFlag().getPos().getX(), WIDTH), RenderUtility.CenterY(getFlag().getPos().getY(), HEIGHT));
	}
	public void drawFlag(int team) {
		TexturePacker sprite = getDraw(team);
		Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
	
	public void updateFlag() {
//		if(getFlag().isTaken) {
//			// Check and update flag : players
//			for(int i = 0; i < Assets.players.getPlayers().length; i++) {
//				if(updateFlag(flag, Assets.players.getPlayerData(i), t)) {
//					return;
//				}
//			}
//			
//			// Check and update flag : client
//			updateFlag(flag, Assets.player.getPlayer(), t);
//			
//		} else {
//			getFlag().posx += ServerInfo.syncServer(getFlag().velx);
//			
//			if(!Assets.map.intersects(getFlag().posx, getFlag().posy-ServerInfo.FLAG_HEIGHT/2+getFlag().vely, getBounds(flag))) {
//				getFlag().posy += ServerInfo.syncServer(getFlag().vely);
//			}
//		}
		
		if(!getFlag().isTaken()) {
			getFlag().getPos().addX(ServerInfo.syncServer(getFlag().getVel().getX()));
			
			if(!Assets.map.intersects(getFlag().getPos().getX(), getFlag().getPos().getY()-FlagData.getSize().getY()/2+getFlag().getVel().getY(), getBounds())) {
				getFlag().getPos().addY(ServerInfo.syncServer(getFlag().getVel().getY()));
			}
		}
	}
	
	private Rectangle getBounds() {
		return CollisionDetection.getBounds("both", getFlag().getPos().getX(), getFlag().getPos().getX(), FlagData.getSize().getX(), FlagData.getSize().getY()); //$NON-NLS-1$
	}
}
