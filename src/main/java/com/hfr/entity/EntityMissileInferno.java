package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileInferno extends EntityMissileBaseAdvanced {

	public EntityMissileInferno(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileInferno(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 35.0F, true, true, true);
		//ExplosionChaos.burn(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10);
		//ExplosionChaos.flameDeath(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25);
    	worldObj.newExplosion(this, posX, posY, posZ, 35F, true, true);
	}

	@Override
	public int getMissileType() {
		return 2;
	}
}
