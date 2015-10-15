package com.gmail.jpk.stu.Troops;

import java.io.Serializable;

import com.gmail.jpk.stu.World.tile;

public class Troop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected NAMES Name;
	protected int LvlUnlock;
	protected int Level;
	protected boolean isBusy = false;
	protected int x;
	protected int y;
	protected tile Tiles;
	protected long timeCost = -1;
	protected int goldCost;
	
	public enum NAMES{
		Grunt("grunt");
		
		private String Name;
		
		NAMES(String Name)
		{
			this.Name= Name;
		}
		
		public String getName()
		{
			return Name;
		}
	}
	
	public String getName()
	{
		return Name.getName();
	}
	
	public int getLvlUnlock()
	{
		return LvlUnlock;
	}
	
	public int getLevel()
	{
		return Level;
	}
	
	public boolean getIsBusy()
	{
		return isBusy;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getY()
	{
		return y;
	}
	
	public tile getTiles()
	{
		return Tiles;
	}
	
	public long getTimeCost()
	{
		return timeCost;
	}
	
	public int getGoldCost()
	{
		return goldCost;
	}
}
