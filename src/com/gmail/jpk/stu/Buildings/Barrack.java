package com.gmail.jpk.stu.Buildings;

import java.util.ArrayList;
import java.util.List;

import com.gmail.jpk.stu.Options.Options;
import com.gmail.jpk.stu.Troops.Grunt;
import com.gmail.jpk.stu.Troops.Troop;
import com.gmail.jpk.stu.World.tile;

public class Barrack extends Building{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Troop> troops = new ArrayList<Troop>();
	
	private int gLvl;
	private int MaxCapacity;
	
	public Barrack()
	{
		Name = NAMES.Barrack;
		MaxLevel = 10;
		Level = 1;
		xSpace = 3;
		ySpace = 3;
		goldCost = 20;
		gLvl = 1;
		MaxCapacity = 15;
		
		Tiles = tile.BARRACK;
	}
	
	public void AddTroop(Troop t)
	{
		if(troops.size() >= MaxCapacity) return;
		if(t instanceof Grunt)
		{
			Grunt g = (Grunt) t;
			g.setLevel(gLvl);
			troops.add(g);
		}
	}
	
	public boolean canTrainTroop(String args)
	{
		if(troops.size() >= MaxCapacity) return false;
		Troop t;
		switch(args)
		{
			default:
			{
				return false;
			}
			case "grunt":
			{
				t = new Grunt();
			}
		}
		if(t.getLvlUnlock() > this.Level) return false;
		return true;
	}
	
	public int getCurrentCapacity()
	{
		return troops.size();
	}
	
	public int getMaxCapacity()
	{
		return MaxCapacity;
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
			timeCost = System.currentTimeMillis() + (long)(10000 * (Math.pow(1.41d, Level)));
			int timeLeft = Math.round(((timeCost - System.currentTimeMillis()) / 1000));
			goldCost = (int)(20 * Math.pow(1.65d, Level));
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
		MaxCapacity += 5;
		System.out.println("\n\nBarrack upgraded to level " + Level + "!");
	}
}
