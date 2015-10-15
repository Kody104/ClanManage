package com.gmail.jpk.stu.ClanManage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.gmail.jpk.stu.Buildings.Barrack;
import com.gmail.jpk.stu.Buildings.Building;
import com.gmail.jpk.stu.Buildings.Chest;
import com.gmail.jpk.stu.Buildings.House;
import com.gmail.jpk.stu.Options.Options;
import com.gmail.jpk.stu.Players.Player;
import com.gmail.jpk.stu.Troops.Grunt;
import com.gmail.jpk.stu.World.World;

public class ClanManage {
	
	World world;
	Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		ClanManage cm = new ClanManage();
		cm.StartNewGame();
	}
	
	private void StartNewGame()
	{
		world = new World();
		mainloop();
	}
	
	private void mainloop()
	{	
		do
		{
			GetCommand();
		}
		while(world.iterate());
	}
	
	private void GetCommand()
	{	
		String input = scan.nextLine();
		if(input.equalsIgnoreCase("override"))
			Options.OverrideTrue = !Options.OverrideTrue;
		String cmd = null;
		String args = null;
		if(input.contains(" "))
		{
			cmd = input.substring(0, input.indexOf(' '));
			args = input.substring(input.indexOf(' ')+1);
		}
		else
		{
			cmd = input;
			args = "NULL";
		}
		
		switch(cmd.toLowerCase())
		{
			case "move":
			{
				if(!moveCmd(args.toLowerCase()))
					System.out.println(args.toUpperCase() + " doesn't exist!");
				break;
			}
			case "upgrade":
			{
				if(!upgradeCmd(args.toLowerCase()))
					System.out.println(args.toUpperCase() + " doesn't exist!");
				break;
			}
			case "time":
			{
				if(!timeCmd(args.toLowerCase()))
					System.out.println(args.toUpperCase() + " doesn't exist!");
				break;
			}
			case "build":
			{
				if(!buildCmd(args.toLowerCase()))
					System.out.println(args.toUpperCase() + " already exists!");
				break;
			}
			case "collect":
			{
				if(!collectCmd())
					System.out.println("You don't have any gold producers!");
				break;
			}
			case "show":
			{
				showMap(args.toLowerCase());
				break;
			}
			case "check":
			{
				if(!check(args))
					System.out.println(args.toUpperCase() + " doesn't exist!");
				break;
			}
			case "train":
			{
				if(!trainCmd(args))
					System.out.println(args.toUpperCase() + " can't be trained!");
				break;
			}
			case "save":
			{
				if(!saveCmd())
					System.out.println("There was a save error.");
				break;
			}
			case "load":
			{
				if(!loadCmd())
					System.out.println("There was a load error.");
				break;
			}
		}
	}
	
	private boolean moveCmd(String args)
	{
		if(!args.contains(" "))
		{
			if(world.GetBuilding(args) != null)
			{
				int loc1;
				int loc2;
				System.out.print("Where?(Max 79) X: ");
				loc1 = ParseString(scan.nextLine());
				System.out.print("(Max 48) Y: ");
				loc2 = ParseString(scan.nextLine());
				if((loc1 > 0 && loc2 > 0) && (loc1 < 80 && loc2 < 49))
					world.MoveBuilding(loc1, loc2, world.GetBuilding(args));
				else
				{
					System.out.println("Max range is 1 - 79 on X Value, and 1 - 48 on Y Value.");
					return true;
				}
				return true;
			}
		}
		else
		{
			int extra = ParseString(args.substring(args.indexOf(" ")+1));
			String object = args.substring(0, args.indexOf(" "));
			if(extra == -1)
			{ 
				System.out.println("Error parsing number of objects!");
				return true;
			}
			if(world.GetBuilding(object, extra-1) != null)
			{
				int loc1;
				int loc2;
				System.out.print("Where?(Max 79) X: ");
				loc1 = ParseString(scan.nextLine());
				System.out.print("(Max 48) Y: ");
				loc2 = ParseString(scan.nextLine());
				if((loc1 > 0 && loc2 > 0) && (loc1 < 80 && loc2 < 49))
					world.MoveBuilding(loc1, loc2, world.GetBuilding(object, extra-1));
				else
				{
					System.out.println("Max range is 1 - 79 on X Value, and 1 - 48 on Y Value.");
					return true;
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean upgradeCmd(String args)
	{
		if(!args.contains(" "))
		{
			if(world.GetBuilding(args) != null) 
			{
				if(world.GetBuilding(args).getIsBusy())
				{
					System.out.println(args.toUpperCase() + " is busy!");
					return true;
				}
				if(world.getPlayer().getGold() >= world.GetBuilding(args).getGoldCost())
				{
					world.SetGold(world.GetBuilding(args));
					world.GetBuilding(args).BeginUpgrade();
					return true;
				}
				else
				{
					System.out.println("You need " + world.GetBuilding(args).getGoldCost() + " gold to upgrade this building!");
					return true;
				}
			}
		}
		else
		{
			int extra = ParseString(args.substring(args.indexOf(" ")+1));
			String object = args.substring(0, args.indexOf(" "));
			if(extra == -1)
			{ 
				System.out.println("Error parsing number of objects!");
				return true;
			}
			if(world.GetBuilding(object, extra-1) != null)
			{
				if(world.GetBuilding(object, extra-1).getIsBusy())
				{
					System.out.println(object.toUpperCase() + " is busy!");
					return true;
				}
				if(world.getPlayer().getGold() >= world.GetBuilding(object, extra-1).getGoldCost())
				{
					world.SetGold(world.GetBuilding(object, extra-1));
					world.GetBuilding(object, extra-1).BeginUpgrade();
					return true;
				}
				else
				{
					System.out.println("You need " + world.GetBuilding(object, extra-1).getGoldCost() + " gold to upgrade this building!");
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean timeCmd(String args)
	{
		if(!args.contains(" "))
		{
			if(world.GetBuilding(args) != null)
			{
				int timeLeft = Math.round(((world.GetBuilding(args).getTimeCost() - System.currentTimeMillis()) / 1000));
				if(timeLeft < 0) timeLeft = 0;
				System.out.println(args.toUpperCase() + " has " + timeLeft + " seconds left!");
				return true;
			}
		}
		else
		{
			int extra = ParseString(args.substring(args.indexOf(" ")+1));
			String object = args.substring(0, args.indexOf(" "));
			if(extra == -1)
			{ 
				System.out.println("Error parsing number of objects!");
				return true;
			}
			if(world.GetBuilding(object, extra-1) != null)
			{
				int timeLeft = Math.round(((world.GetBuilding(object, extra-1).getTimeCost() - System.currentTimeMillis()) / 1000));
				if(timeLeft < 0) timeLeft = 0;
				System.out.println(object.toUpperCase() + " has " + timeLeft + " seconds left!");
				return true;
			}
		}
		return false;
	}
	
	
	private boolean buildCmd(String args)
	{
		switch(args)
		{
			case "chest":
			{
				int extra = (world.GetBuilding("house").getLevel() < 5) ? 0 : (world.GetBuilding("house").getLevel() / 5);
				if(world.GetBuilding(args, extra) == null)
				{
					Chest c = new Chest();
					int loc1;
					int loc2;
					System.out.print("Where?(Max 79) X: ");
					loc1 = ParseString(scan.nextLine());
					System.out.print("(Max 48) Y: ");
					loc2 = ParseString(scan.nextLine());
					if((loc1 > 0 && loc2 > 0) && (loc1 < 80 && loc2 < 49))
					{
						world.Build(loc1, loc2, c, false);
						return true;
					}
					else
					{
						System.out.println("Max range is 1 - 79 on X Value, and 1 - 48 on Y Value.");
						return true;
					}
				}
				break;
			}
			case "barrack":
			{
				int extra = (world.GetBuilding("house").getLevel() < 5) ? 0 : (world.GetBuilding("house").getLevel() / 5);
				if(world.GetBuilding(args, extra) == null)
				{
					Barrack b = new Barrack();
					int loc1;
					int loc2;
					System.out.print("Where?(Max 79) X: ");
					loc1 = ParseString(scan.nextLine());
					System.out.print("(Max 48) Y: ");
					loc2 = ParseString(scan.nextLine());
					if((loc1 > 0 && loc2 > 0) && (loc1 < 80 && loc2 < 49))
					{
						world.Build(loc1, loc2, b, false);
						return true;
					}
					else
					{
						System.out.println("Max range is 1 - 79 on X Value, and 1 - 48 on Y Value.");
						return true;
					}
				}
				break;
			}
		}
		return false;
	}
	
	private boolean collectCmd()
	{
		return world.CollectGold();
	}
	
	private boolean showMap(String args)
	{
		if(!args.equals("map"))
			return false;
		world.showMap();
		return true;
	}
	
	private boolean check(String args)
	{
		if(!args.contains(" "))
		{
			if(world.GetBuilding(args) != null)
			{
				if(world.GetBuilding(args) instanceof Chest)
				{
					Chest c = (Chest)world.GetBuilding(args);
					System.out.println(String.format("%s Lvl: %d   Gold Out Per Second: %.2f",c.getName().toUpperCase(), c.getLevel(), c.getCashOutput()));
					return true;
				}
				else if(world.GetBuilding(args) instanceof House)
				{
					System.out.println(String.format("%s Lvl: %d   Gold: %d", args.toUpperCase(), world.GetBuilding(args).getLevel(), world.getPlayer().getGold()));
					return true;
				}
				else if(world.GetBuilding(args) instanceof Barrack)
				{
					Barrack b = (Barrack)world.GetBuilding(args);
					System.out.println(String.format("%s Lvl: %d   Capacity: %d/%d", b.getName().toUpperCase(), b.getLevel(), b.getCurrentCapacity(), b.getMaxCapacity()));
					return true;
				}
				else
				{
					System.out.println(String.format("%s Lvl: %d", args.toUpperCase(), world.GetBuilding(args).getLevel()));
					return true;
				}
			}
		}
		else
		{
			int extra = ParseString(args.substring(args.indexOf(" ")+1));
			String object = args.substring(0, args.indexOf(" "));
			if(extra == -1)
			{ 
				System.out.println("Error parsing number of objects!");
				return true;
			}
			if(world.GetBuilding(object, extra-1) != null)
			{
				if(world.GetBuilding(object, extra-1) instanceof Chest)
				{
					Chest c = (Chest)world.GetBuilding(object, extra-1);
					System.out.println(String.format("%s Lvl: %d   Gold Out Per Second: %.2f",c.getName().toUpperCase(), c.getLevel(), c.getCashOutput()));
					return true;
				}
				else if(world.GetBuilding(object, extra-1) instanceof House)
				{
					System.out.println(String.format("%s Lvl: %d   Gold: %d", object.toUpperCase(), world.GetBuilding(object, extra-1).getLevel(), world.getPlayer().getGold()));
					return true;
				}
				else
				{
					System.out.println(String.format("%s Lvl: %d", object.toUpperCase(), world.GetBuilding(object, extra-1).getLevel()));
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean trainCmd(String args)
	{
		if(world.GetBuilding("barrack") == null)
		{
			System.out.println("You don't have a barrack!");
			return true;
		}
		if(!args.contains(" "))
		{
			for(Building b : world.getPlayer().getAllBuildings())
			{
				if(b instanceof Barrack)
				{
					Barrack bb = (Barrack) b;
					if(bb.canTrainTroop(args))
					{
						switch(args)
						{
							default:
							{
								return false;
							}
							case "grunt":
							{
								Grunt g =  new Grunt();
								if(!Options.OverrideTrue)
								{
									if(g.getGoldCost() <= world.getPlayer().getGold())
									{
										world.AddTroop(g);
										return true;
									}
									else
									{
										System.out.println("You need " + g.getGoldCost() + " gold to train this unit!");
										return true;
									}
								}
								else
								{
									world.AddTroop(g);
									return true;
								}
							}
						}
					}
				}
			}
		}
		else
		{
			int amount = ParseString(args.substring(args.indexOf(" ")+1));
			String troop = args.substring(0, args.indexOf(" "));
			if(amount == -1)
			{ 
				System.out.println("Error parsing number of objects!");
				return true;
			}
			for(Building b : world.getPlayer().getAllBuildings())
			{
				if(b instanceof Barrack)
				{
					Barrack bb = (Barrack) b;
					if(bb.canTrainTroop(troop))
					{
						switch(troop)
						{
							default:
							{
								return false;
							}
							case "grunt":
							{
								if((bb.getCurrentCapacity() + amount) <= bb.getMaxCapacity())
								{
									Grunt g = new Grunt();
									if(!Options.OverrideTrue)
									{
										if((g.getGoldCost() * amount) <= world.getPlayer().getGold())
										{
											for(int i = 0; i < amount; i++)
											{
												Grunt t = new Grunt();
												world.AddTroop(t);
											}
											return true;
										}
										else
										{
											System.out.println("You need " + (g.getGoldCost() * amount) + " gold to train " + amount + " units!");
											return true;
										}
									}
									else
									{
										for(int i = 0; i < amount; i++)
										{
											Grunt t = new Grunt();
											world.AddTroop(t);
										}
										return true;
									}
								}
								else
								{
									System.out.println("Barrack doesn't have enough capacity for that many troops!");
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean saveCmd()
	{
		try {
			FileOutputStream fout = new FileOutputStream("save.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(world.getPlayer());
			oos.close();
			fout.close();
			System.out.println("Save Successful!");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean loadCmd()
	{
		try{
			Player p;
			FileInputStream fin = new FileInputStream("save.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			p = (Player) ois.readObject();
			world.Load(p);
			ois.close();
			fin.close();
			System.out.println("Load Successful!");
			return true;
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private int ParseString(String parsee)
	{
		try{
			return Integer.parseInt(parsee);
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}
}
