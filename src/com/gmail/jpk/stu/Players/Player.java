package com.gmail.jpk.stu.Players;

import java.io.Serializable;

import com.gmail.jpk.stu.Buildings.Building;
import com.gmail.jpk.stu.Buildings.House;

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gold;
	private Building[] all_buildings;
	
	public Player()
	{
		
	}
	
	public Building Init()
	{
		all_buildings = new Building[15];
		gold = 0;
		House h = new House();
		return h;
	}
	
	public void setGold(int gold)
	{
		this.gold = gold;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public Building[] getAllBuildings()
	{
		return all_buildings;
	}
}
