package com.decoBlocks;


import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BlockCobblePlank extends Block {

	private String name = "cobblePlank";
	public String MODID = "decoblocks";
	
	protected BlockCobblePlank() {
		//fun code that does cool things, I hope it is self explanatory
		super(Material.rock);
		//setUnlocalizedName(DecoBlocks.MODID+"_"+name);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer(){
		return EnumWorldBlockLayer.SOLID;
	}
	
	//the point of this I am yet to figure out, but it makes it so
	//the name is unchangable, but readable (refer to name String)
	public String getName(){
		return name;
	}
	
	public static final PropertyEnum TYPEWOOD = PropertyEnum.create("type", EnumTypeWood.class);
	
	@Override
	public int damageDropped(IBlockState ibs){
		EnumTypeWood enumWood = (EnumTypeWood)ibs.getValue(TYPEWOOD);
		return enumWood.getMetaData();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list){
		EnumTypeWood[] allWoods = EnumTypeWood.values();
		for (EnumTypeWood type : allWoods){
			list.add(new ItemStack(itemIn, 1, type.getMetaData()));
		}
	}
	
	//reading our metadata from the state and vice versa
	@Override
	public IBlockState getStateFromMeta(int meta){
		int typeBits = (meta & 0x06) >> 2;
		EnumTypeWood typeWoods = EnumTypeWood.byMetaData(typeBits);
		return this.getDefaultState().withProperty(TYPEWOOD, typeWoods);
	}
	
	@Override
	public int getMetaFromState(IBlockState ibs){
		EnumTypeWood typeWood = (EnumTypeWood)ibs.getValue(TYPEWOOD);
		int typeBits = typeWood.getMetaData() << 2;
		return typeBits;
	}
	
	@Override
	protected BlockState createBlockState(){
		return new BlockState(this, new IProperty[] {TYPEWOOD});
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing faceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		EnumTypeWood typeWood = EnumTypeWood.byMetaData(meta);
		return this.getDefaultState().withProperty(TYPEWOOD, typeWood);
	}
	
	//Our lovely enumeration for our different types of planks, corresponds to vanilla values
	public static enum EnumTypeWood implements IStringSerializable{
		
		OAK(0,"oak"),
		SPRUCE(1,"spruce"),
		BIRCH(2,"birch"),
		JUNGLE(3,"jungle"),
		ACACIA(4,"acacia"),
		DARK_OAK(5,"dark_oak");

		@Override
		public String getName() {
			return this.name();
		}
		
		@Override
		public String toString(){
			return this.name;
		}
		
		public int getMetaData(){
			return this.meta;
		}
		
		public static EnumTypeWood byMetaData(int meta){
			if(meta < 0 || meta >= META_LOOKUP.length){
				meta = 0;
			}
			return META_LOOKUP[meta];
		}
		
		public final int meta;
		private final String name;
		private static final EnumTypeWood[] META_LOOKUP = new EnumTypeWood[values().length];
		
		private EnumTypeWood(int i_meta, String i_name){
			this.meta = i_meta;
			this.name = i_name;
		}
		
		static{
			for(EnumTypeWood typeW : values()){
				META_LOOKUP[typeW.getMetaData()] = typeW;
			}
			
		}
		
	}

}
