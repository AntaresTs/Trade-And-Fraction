package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileIncendiary extends EntityMissileBaseAdvanced {

	public EntityMissileIncendiary(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileIncendiary(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 10.0F, true, true, true);
    	worldObj.newExplosion(this, posX, posY, posZ, 10F, true, true);
	}

	@Override
	public int getMissileType() {
		return 0;
	}

}
