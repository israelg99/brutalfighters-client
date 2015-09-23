package com.brutalfighters.game.flags;


public class Flags {
	
	public static final int BLUE_TEAM = 0;
	public static final int RED_TEAM = 1;
	
	private Flag[] flags;
	
	public Flags(Flag[] flags) {
		this.flags = flags.clone();
	}
	
	public Flag getFlag(int team) {
		return flags[team];
	}
	public Flag getEnemyFlag(int team) {
		if(team == BLUE_TEAM) {
			return flags[RED_TEAM];
		}
		
		return flags[BLUE_TEAM];
	}
	
	public void setNew(Flag[] flags) {
		this.flags = flags.clone();
	}
	public void setNew(int i, Flag flag) {
		this.flags[i] = flag;
	}
	
	public void updateFlags(Flag[] flags) {
		for(int i = 0; i < flags.length; i++) {
			
			Flag newFlag = flags[i];
			Flag original = this.flags[i];
			
			float posx = newFlag.velx != 0 ? original.posx : newFlag.posx;
			float posy = newFlag.vely != 0 ? original.posy : newFlag.posy;
			
			String flip = original.flip;
			
			this.flags[i] = flags[i];
			
			if(newFlag.isTaken && flip.equals(newFlag.flip)) {
				this.flags[i].posx = posx;
				this.flags[i].posy = posy;
			}
		}
	}
	
	public void updateFlags() {
		FlagHandler.updateFlags(flags);
	}
	
	public void renderFlags() {
		for(int i = 0; i < flags.length; i++) {
			FlagHandler.drawFlag(i, flags[i]);
		}
	}
}
