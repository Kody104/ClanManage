package com.gmail.jpk.stu.Buildings;

import com.gmail.jpk.stu.Options.Options;
import com.gmail.jpk.stu.World.tile;

public class House extends Building{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public House()
	{
		Name = NAMES.House;
		MaxLevel = 15;
		Level = 1;
		xSpace = 2;
		ySpace = 2;
		goldCost = 100;
		
		Tiles = tile.HOUSE;
	}
	
	@Override
	public void BeginUpgrade()
	{
		if(Level >= MaxLevel)
		{
			System.out.println("This house has been upgraded to max level!");
			return;
		}
		if(!Options.OverrideTrue)
		{
			isBusy = true;
			timeCost = System.currentTimeMillis() + (long)(30000 * (Math.pow(1.4078d, Level)));
			goldCost = (int)(100 * Math.pow(1.65d, Level));
			int timeLeft = Math.round(((timeCost - System.currentTimeMillis()) / 1000));
			System.out.println("Upgrading to level " + (Level+1) + "! ETA: " + timeLeft + " seconds");
		}
		else
		{
			isBusy = true;
			timeCost = 1;
			goldCost = (int)(100 * Math.pow(1.65d, Level));
			System.out.println("Upgrading to level " + (Level+1) + "! ETA: 0 seconds");
		}
	}
	
	@Override
	public void EndUpgrade()
	{
		isBusy = false;
		timeCost = -1;
		Level++;
		System.out.println("\n\nHouse upgraded to level " + Level + "!");
	}
}
