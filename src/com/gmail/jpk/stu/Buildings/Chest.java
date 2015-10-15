package com.gmail.jpk.stu.Buildings;

import com.gmail.jpk.stu.Options.Options;
import com.gmail.jpk.stu.World.tile;

public class Chest extends Building{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long lastCheck;
	private double CashOutput;
	
	public Chest()
	{
		Name = NAMES.Chest;
		MaxLevel = 15;
		Level = 1;
		xSpace = 2;
		ySpace = 2;
		lastCheck = System.currentTimeMillis();
		CashOutput = 0.25d;
		goldCost = 30;
		
		Tiles = tile.CHEST;
	}
	
	public double getCashOutput()
	{
		return CashOutput;
	}
	
	@Override
	public void BeginUpgrade()
	{
		if(Level >= MaxLevel)
		{
			System.out.println("This chest has been upgraded to max level!");
			return;
		}
		if(!Options.OverrideTrue)
		{
			isBusy = true;
			timeCost = System.currentTimeMillis() + (long)(10000 * (Math.pow(1.45d, Level)));
			int timeLeft = Math.round(((timeCost - System.currentTimeMillis()) / 1000));
			goldCost = (int)(30 * Math.pow(1.65d, Level));
			System.out.println("Upgrading to level " + (Level+1) + "! ETA: " + timeLeft + " seconds");
		}
		else
		{
			isBusy = true;
			timeCost = 1;
			goldCost = (int)(30 * Math.pow(1.65d, Level));
			System.out.println("Upgrading to level " + (Level+1) + "! ETA: 0 seconds");
		}
	}
	
	@Override
	public void EndUpgrade()
	{
		isBusy = false;
		timeCost = -1;
		Level++;
		CashOutput = (0.25d * Math.pow(1.45, Level));
		lastCheck = System.currentTimeMillis();
		System.out.println("\n\nChest upgraded to level " + Level + "!");
	}
	
	public int Collect()
	{
		if(!isBusy)
		{
			long difference = System.currentTimeMillis() - lastCheck;
			int inc = (int)(difference / 1000);
			int collect = (int)(CashOutput * inc);
			lastCheck = System.currentTimeMillis();
			return collect;
		}
		return 0;
	}
}
