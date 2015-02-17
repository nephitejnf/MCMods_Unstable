package com.decoBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCobbleBlockPlank extends ItemBlock{

	public ItemCobbleBlockPlank(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int meta){
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		BlockCobblePlank.EnumTypeWood typeW = BlockCobblePlank.EnumTypeWood.byMetaData(stack.getMetadata());
//		return "decoBlock"+"."+typeW.toString();
		return super.getUnlocalizedName()+"."+typeW.toString();
	}

}
