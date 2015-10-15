package com.gmail.jpk.stu.Troops;

import com.gmail.jpk.stu.World.tile;

public class Grunt extends Troop{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Grunt()
	{
		Name = NAMES.Grunt;
		LvlUnlock = 1;
		this.Level = 1;
		goldCost = 5 * Level;
		
		Tiles = tile.GRUNT;
	}
	
	public void setLevel(int Level)
	{
		this.Level = Level;
		goldCost = 5 * Level;
	}

}
