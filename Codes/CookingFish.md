### Cooking A fish event
**After cooking the fish, it should return the same lore and display name as the source,**
**you can also copy the display name if you want :>**
>Source code

	@EventHandler()
	public void onSmelt(FurnaceSmeltEvent event) {
	    ItemStack cook_source = event.getSource();    //Gets the source item, item being cooked
		
	    if(getCooked(cook_source.getType())!=null){   //Calls the getCooked Method, if it has a value, then proceed
	    
			  cook_source.setType(getCooked(cook_source.getType()));  //Sets the type of the source item
	      
			  String dispay = ChatColor.GRAY+ "Cooked "  + cook_source.getItemMeta().getDisplayName();  //you can do anything you want here
			  ItemMeta meta = cook_source.getItemMeta(); //meta = cook_source item meta, if it has an existing lore or data in it, it will be copied as well
	
			  meta.setDisplayName(dispay);;  //we set the display name of the cooked item
			  cook_source.setItemMeta(meta); //set the item meta to the source item
	
			  event.setResult(cook_source); //set the result as the source item
	    }
	}
    
    //this method returns null if material type is not a Cod or Salmon
    private Material getCooked(Material type){
	    Material material_type = null;
        
	    if(type.equals(Material.COD)) //if material is equals to COD
		    material_type = Material.COOKED_COD; // it returns the cooked result of COD which is COOKED_COD
	    if(type.equals(Material.SALMON)) //if material is equals to SALMON
		    material_type = Material.COOKED_SALMON; // it returns the cooked result of SALMON which is COOKED_SALMON
            
	    return material_type; 
    }
### Feel free to comment/suggest :>
