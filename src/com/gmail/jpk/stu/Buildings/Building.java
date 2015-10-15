package com.gmail.jpk.stu.Buildings;

import java.io.Serializable;

import com.gmail.jpk.stu.World.tile;

public class Building implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected NAMES Name;
	protected int MaxLevel;
	protected int Level;
	protected boolean isBusy = false;
	protected int x;
	protected int y;
	protected int xSpace;
	protected int ySpace;
	protected tile Tiles;
	protected long timeCost = -1;
	protected int goldCost = 0;
	
	public enum NAMES{
		House("house"), Chest("chest"), Barrack("barrack");
		
		private String Name;
		
		NAMES(String Name)
		{
			this.Name = Name;
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
	
	public int getMaxLevel()
	{
		return MaxLevel;
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
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getXSpace()
	{
		return xSpace;
	}
	
	public int getYSpace()
	{
		return ySpace;
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
	
	//Override this
	public void BeginUpgrade()
	{
	}
	
	//Override this
	public void EndUpgrade()
	{
	}
}
