package com.gmail.jpk.stu.World;

import com.gmail.jpk.stu.Buildings.Barrack;
import com.gmail.jpk.stu.Buildings.Building;
import com.gmail.jpk.stu.Options.Options;
import com.gmail.jpk.stu.Players.Player;
import com.gmail.jpk.stu.Troops.Troop;

public class CompWorld {
	
	private tile[] map;
	private int xsize;
	private int ysize;
	private Player p;
	
	public CompWorld()
	{
		createWorld();
	}
	
	private void setCell(int x, int y, tile type)
	{
		map[x + xsize * y] = type;
	}
	
	private tile getCell(int x, int y)
	{
		return map[x + xsize * y];
	}
	
	public void createWorld()
	{
		int X = 80;
		int Y = 50;
		map = new tile[X * Y];
		xsize = X;
		ysize = Y;
		p = new Player();
		
		for(int y = 0; y < ysize; y++)
		{
			for(int x = 0; x < xsize; x++)
			{
				if(y == 0) setCell(x, y, tile.A_WALL);
				else if(y == ysize-1) setCell(x, y, tile.A_WALL);
				else if(x == 0) setCell(x, y, tile.A_WALL);
				else if(x == xsize-1) setCell(x, y, tile.A_WALL);
				
				else setCell(x, y, tile.UNUSED);
			}
		}
		
		Build(xsize/2, ysize/2, p.Init(), false);
	}
	
	private boolean CheckLocation(int x, int xLength, int y, int yLength)
	{
		for(int ytemp = y; ytemp < (yLength + y); ytemp++)
		{
			if(ytemp == 0 || ytemp > ysize) return false;
			for(int xtemp = x; xtemp < (xLength + x); xtemp++)
			{
				if(xtemp == 0 || xtemp > xsize) return false;
				if(getCell(xtemp, ytemp) != tile.UNUSED) return false;
			}
		}
		return true;
	}
	
	public void Build(int x, int y, Building b, boolean isLoad)
	{
		if(CheckLocation(x, b.getXSpace(), y, b.getYSpace()))
		{
			for(int ytemp = y; ytemp < (b.getYSpace() + y); ytemp++)
			{
				if(ytemp == y) b.setY(ytemp);
				for(int xtemp = x; xtemp < (b.getXSpace() + x); xtemp++)
				{
					if(xtemp == x) b.setX(xtemp);
					setCell(xtemp, ytemp,b.getTiles());
				}
			}
			
			for(int i = 0; i < p.getAllBuildings().length; i++)
			{
				if(isLoad) break;
				if(p.getAllBuildings()[i] == null)
				{
					p.getAllBuildings()[i] = b;
					break;
				}
			}
			showMap();
		}
		else
		{
			System.out.println("There is something in the way!");
		}
		
		/*for(int ytemp = y; ytemp > (y - b.getYSpace()); ytemp--)
		{
			if(ytemp == 0 || ytemp > ysize) return false;
			for(int xtemp = (x-(b.getXSpace() / 2)); xtemp < (x+(b.getXSpace()+1) /2); xtemp++)
			{
				if(xtemp == 0 || xtemp > xsize) return false;
				if(getCell(xtemp, ytemp) != tile.UNUSED) return false;
			}
		}
		
		for(int ytemp = y; ytemp > (y - b.getYSpace()); ytemp--)
		{
			for(int xtemp = (x-(b.getXSpace() / 2)); xtemp < (x+(b.getXSpace()+1) /2); xtemp++)
			{
				setCell(xtemp, ytemp, b.getTiles());
			}
		}
		*/
	}
	
	public Building GetBuilding(String buildingname)
	{
		for(Building b : p.getAllBuildings())
		{
			if(b == null) break;
			if(b.getName().equals(buildingname.toLowerCase()))
			{
				return b;
			}
		}
		return null;
	}
	
	public Building GetBuilding(String buildingname, int count)
	{
		int index = 0;
		for(int i = 0; i < p.getAllBuildings().length; i++)
		{
			if(p.getAllBuildings()[i] == null) break;
			if(p.getAllBuildings()[i].getName().equals(buildingname.toLowerCase()) && (index == count))
				return p.getAllBuildings()[i];
			if(p.getAllBuildings()[i].getName().equals(buildingname.toLowerCase()) && (index < count))
				index++;
		}
		return null;
	}
	
	public void AddTroop(Troop t)
	{
		if(GetBuilding("barrack") == null) return;
		Barrack b = (Barrack)GetBuilding("barrack");
		b.AddTroop(t);
		if(!Options.OverrideTrue)
			p.setGold(p.getGold() - t.getGoldCost());
	}
	
	public void SetGold(Building b)
	{
		if(b.getGoldCost() > p.getGold()) return;
		p.setGold(p.getGold() - b.getGoldCost());
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public void showMap()
	{
		for(int y = 0; y < ysize; y++)
		{
			for(int x = 0; x < xsize; x++)
			{
				switch(getCell(x, y))
				{
					case HOUSE:
					{
						System.out.print("#");
						break;
					}
					case CHEST:
					{
						System.out.print("$");
						break;
					}
					case BARRACK:
					{
						System.out.print("*");
						break;
					}
					case A_WALL:
					{
						System.out.print("X");
						break;
					}
					case UNUSED:
					{
						System.out.print(" ");
						break;
					}
					case GRUNT:
					{
						break;
					}
				}
			}
		}
	}
}
