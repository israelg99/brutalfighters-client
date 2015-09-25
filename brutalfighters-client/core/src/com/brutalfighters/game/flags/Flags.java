package com.brutalfighters.game.flags;


public class Flags {
	
	private static final int BLUE_TEAM = 0;
	private static final int RED_TEAM = 1;
	
	private Flag[] flags;
	
	public Flags(FlagData[] flags) {
		reset(flags);
	}

	private Flag[] getFlags() {
		return flags;
	}
	private void setFlags(Flag[] flags) {
		this.flags = flags;
	}
	
	public static int getBlueTeam() {
		return BLUE_TEAM;
	}
	public static int getRedTeam() {
		return RED_TEAM;
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
	
	public void reset(FlagData[] flags) {
		this.flags = new Flag[flags.length];
		for(int i = 0; i < getFlags().length; i++) {
			getFlags()[i] = new Flag(flags[i]);
		}
	}
	
	public void setNew(FlagData[] flags) {
		for(int i = 0; i < getFlags().length; i++) {
			setNew(i, flags[i]);
		}
	}
	public void setNew(int i, FlagData flag) {
		this.getFlags()[i].setFlag(flag);
	}
	
	public void assignFlags(FlagData[] newFlags) {
		for(int i = 0; i < newFlags.length; i++) {
			
			// Data Denial - Extrapolation Technique.
			FlagData newFlag = newFlags[i];
			FlagData original = getFlag(i).getFlag();
			
			float posx = newFlag.getVel().getX() != 0 ? original.getVel().getX() : newFlag.getVel().getX();
			float posy = newFlag.getVel().getY() != 0 ? original.getVel().getY() : newFlag.getVel().getY();
			
			String flip = original.getFlip();
			
			setNew(i, newFlags[i]);
			
			if(newFlag.isTaken() && flip.equals(newFlag.getFlip())) {
				getFlags()[i].getFlag().getPos().set(posx, posy);
			}
		}
	}
	
	public void updateFlags() {
		for(int i = 0; i < getFlags().length; i++) {
			getFlag(i).updateFlag();
		}
	}
	
	public void renderFlags() {
		for(int i = 0; i < getFlags().length; i++) {
			getFlag(i).drawFlag(i);
		}
	}
}
