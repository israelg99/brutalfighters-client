package com.brutalfighters.game.flags;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.utility.CollisionDetection;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;

public class FlagHandler {
	
	private static int WIDTH, HEIGHT;
	private static float DELAY;
	
	private static TextureRegion[][] flagSprite;
	
	public static void Load() {
		WIDTH = 150;
		HEIGHT = 200;
		DELAY = 0.099f;
		
		flagSprite = TextureHandle.TextureSplit("flags/flags.png", 70, 75, true, false); //$NON-NLS-1$
	}
	
	public static TexturePacker getDraw(int index, Flag flag) {
		return new TexturePacker(AnimationHandler.getAnimation(flag.flip, flagSprite[index], DELAY, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(flag.posx, WIDTH), RenderUtility.CenterY(flag.posy, HEIGHT));
	}
	
	public static void drawFlag(int index, Flag flag) {
		TexturePacker sprite = getDraw(index, flag);
		Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
	
	public static void updateFlags(Flag[] flags) {
		for(int i = 0; i < flags.length; i++) {
			updateFlag(i, flags[i]);
		}
	}
	public static void updateFlag(int t, Flag flag) {
		if(flag.isTaken) {
			// Check and update flag : players
			for(int i = 0; i < Assets.players.getPlayers().length; i++) {
				if(updateFlag(flag, Assets.players.getPlayerData(i), t)) {
					return;
				}
			}
			
			// Check and update flag : client
			updateFlag(flag, Assets.player.getPlayer(), t);
			
		} else {
			flag.posx += ServerInfo.syncServer(flag.velx);
			
			if(!Assets.map.intersects(flag.posx, flag.posy-ServerInfo.FLAG_HEIGHT/2+flag.vely, getBounds(flag))) {
				flag.posy += ServerInfo.syncServer(flag.vely);
			}
		}
	}
	
	public static boolean updateFlag(Flag flag, PlayerData p, int t) {
		if(p.team != t && p.isFlagged) {
			int pad = p.width/3; // IT DEPENDS ON THE PLAYER, THEREFORE STATIC FIELD IS NOT VIABLE!
			
			if(p.flip.equals("left")) { //$NON-NLS-1$
				flag.flip = "right"; //$NON-NLS-1$
			} else {
				pad = -pad;
				flag.flip = "left"; //$NON-NLS-1$
			}
			
			flag.posx = p.posx + pad;
			flag.posy = p.posy + p.height/3 + 15;
			
			return true;
		}
		return false;
	}
	
	public static Rectangle getBounds(Flag flag) {
		return CollisionDetection.getBounds("both", flag.posx, flag.posy, ServerInfo.FLAG_WIDTH, ServerInfo.FLAG_HEIGHT); //$NON-NLS-1$
	}
}
