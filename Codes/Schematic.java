package me.JayMar921.CustomEnchantment.extras;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

//Schematic by JayMar921
public class Schematic
{
    
    private Material[] blocks;
    private BlockData[] data;
    private int width;
    private int height;
    private int length;
    
    //constructors
    public Schematic(Material[] blocks, BlockData[] data, int width, int height, int length) {
    	this.data = data;
    	this.blocks = blocks;
    	this.width = width;
    	this.height = height;
    	this.length = length;
    }
    
    public Schematic(int width, int height, int length) {
    	this(new Material[width*length*height], new BlockData[(width*height*length)], width, height, length);
    }
    
    //getters
    public Material[] getBlocks() {
    	return blocks;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public int getHeight() {
    	return height;
    }
    
    public int getLength() {
    	return length;
    }
    
    //save the schematic from given location into the memory
    public void saveSchematic(Location loc) {
    	blocks = new Material[width*height*length];
    	int traverse = 0;
    	for(int x = loc.getBlockX(); x < loc.getBlockX()+getWidth(); x++) {
    		for(int y = loc.getBlockY(); y < loc.getBlockY()+getHeight(); y++) {
    			for(int z = loc.getBlockZ(); z < loc.getBlockZ()+getLength(); z++) {
    				data[traverse] = loc.getWorld().getBlockAt(x,y,z).getBlockData();
    				blocks[traverse++] =  loc.getWorld().getBlockAt(x,y,z).getType();
    			}
    		}
    	}
    	
    	System.out.println(traverse);
    }
    
    //paste the schematic stored from memory to the world at given location
    public void pasteSchematic(Location loc) {
    	
    	int traverse = 0;
    	for(int x = loc.getBlockX(); x < loc.getBlockX()+getWidth(); x++) {
    		for(int y = loc.getBlockY(); y < loc.getBlockY()+getHeight(); y++) {
    			for(int z = loc.getBlockZ(); z < loc.getBlockZ()+getLength(); z++) {
    				loc.getWorld().getBlockAt(x,y,z).setType(blocks[traverse]);;
    				loc.getWorld().getBlockAt(x,y,z).setBlockData(data[traverse++]);
    			}
    		}
    	}
    }
    
    //paste the schematic stored from memory to the world at given location
    //it denies air when pasting, it can be used to overlap a terrain
    public void pasteSchematic_neglectAIR(Location loc) {
    	
    	int traverse = 0;
    	for(int x = loc.getBlockX(); x < loc.getBlockX()+width; x++) {
    		for(int y = loc.getBlockY(); y < loc.getBlockY()+height; y++) {
    			for(int z = loc.getBlockZ(); z < loc.getBlockZ()+length; z++) {
    				if(blocks[traverse].equals(Material.AIR)) {
    					traverse++;
    					continue;
    				}
    				loc.getWorld().getBlockAt(x,y,z).setType(blocks[traverse]);;
    				loc.getWorld().getBlockAt(x,y,z).setBlockData(data[traverse++]);
    			}
    		}
    	}
    }
    
    //save the schematic from the memory to the plugins folder
    public void saveToFile(String filename) {
    	String extension = "custom_schematic";
    	
    	try {
    		File file = new File("plugins/CustomEnchantments/"+filename+"."+extension);
    	
			FileWriter writer = new FileWriter(file);
			
			StringBuffer content = new StringBuffer();
			
			//store width, length and height
			content.append(width + "-" + height + "-" + length +"@");
			
			//store material
			for(Material material : blocks)
				content.append(material.toString()).append(" ");
			
			content.append("@");
			
			//store data
			for(BlockData data : data)
				content.append(data.getAsString()).append(" ");
			
			writer.write(content.toString());;
			
			writer.close();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    //load the schematic file from the plugin's folder to the memory
    public void loadFromFile(String filename) {
    	String extension = "custom_schematic";
    	try {
    		File file = new File("plugins/CustomEnchantments/"+filename+"."+extension);
    		
    		Scanner scan = new Scanner(file);
    		StringBuffer content = new StringBuffer();
    		while(scan.hasNextLine()) {
    			content.append(scan.nextLine());
    		}
    		String[] contentRead = content.toString().split("[-@ ]+");
    		
    		width = Integer.parseInt(contentRead[0]);
    		height = Integer.parseInt(contentRead[1]);
    		length = Integer.parseInt(contentRead[2]);
    		
    		int max = width*height*length;
    		
    		blocks = new Material[max];
    		data = new BlockData[max];
    		
    		List<Material> materials = new LinkedList<Material>();
    		EnumSet.allOf(Material.class)
    		  .forEach(mats -> materials.add(mats));
    		

    		
    		
    		int traverse = 0;
    		
    		for(int i = 3; i <= max+3; i++) {
    			//System.out.print(contentRead[i] + " " + i);
    			for(Material material: materials)
    				if(material.toString().equals(contentRead[i])) 
    					blocks[traverse++] = material;
    		}
    		traverse= 0;
    		for(int i = max+3; i < max*2+3; i++) {
    			data[traverse++] = Bukkit.createBlockData(contentRead[i]);
    		}
    		scan.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    //load the schematic file as string,
    //saving a schematic will have this format
    //width-height-length@MATERIAL_STRING@BLOCKDATA_STRING
    public void loadAsString(String DATA) {
    	try {
    		StringBuffer content = new StringBuffer();
    		content.append(DATA);
    		
    		String[] contentRead = content.toString().split("[-@ ]+");
    		
    		width = Integer.parseInt(contentRead[0]);
    		height = Integer.parseInt(contentRead[1]);
    		length = Integer.parseInt(contentRead[2]);
    		
    		int max = width*height*length;
    		
    		blocks = new Material[max];
    		data = new BlockData[max];
    		
    		List<Material> materials = new LinkedList<Material>();
    		EnumSet.allOf(Material.class)
    		  .forEach(mats -> materials.add(mats));
    		

    		
    		
    		int traverse = 0;
    		
    		for(int i = 3; i <= max+3; i++) {
    			//System.out.print(contentRead[i] + " " + i);
    			for(Material material: materials)
    				if(material.toString().equals(contentRead[i])) 
    					blocks[traverse++] = material;
    		}
    		traverse= 0;
    		for(int i = max+3; i < max*2+3; i++) {
    			data[traverse++] = Bukkit.createBlockData(contentRead[i]);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }

    
    
}
