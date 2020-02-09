package com.hfr.tileentity.machine;

import com.hfr.handler.FluidHandler;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityMachineTurbine extends TileEntityMachineBase implements IEnergyProvider, IFluidHandler {

	public EnergyStorage storage = new EnergyStorage(100000000, 100000, 100000);
	
	public FluidTank water;
	public FluidTank steam;

	public TileEntityMachineTurbine() {
		super(1);
		
		steam = new FluidTank(FluidHandler.STEAM, 0, 128000);
		water = new FluidTank(FluidRegistry.WATER, 0, 128000);
	}

	@Override
	public String getName() {
		return "container.machineTurbine";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.updateGauge(water.getFluidAmount(), 0, 25);
			this.updateGauge(steam.getFluidAmount(), 1, 25);
			this.updateGauge(storage.getEnergyStored(), 2, 25);
		}
	}
	
	public void processGauge(int val, int id) {
		switch(id) {
		case 0: water.getFluid().amount = val; break;
		case 1: steam.getFluid().amount = val; break;
		case 2: storage.setEnergyStored(val); break;
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.DOWN && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		
		if(canFill(from, resource.getFluid())) {

			int fill = Math.min(steam.getCapacity() - steam.getFluidAmount(), resource.amount);
			
			if(fill <= 0)
				return 0;
			
			FluidStack sauce = resource.copy();
			sauce.amount = fill;
			
			return steam.fill(sauce, doFill);
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		
		if(canDrain(from, resource.getFluid()) && water.getFluidAmount() > 0) {
			return water.drain(Math.min(water.getFluidAmount(), resource.amount), doDrain);
		}
		
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		
		if(water.getFluidAmount() > 0) {
			return water.drain(Math.min(water.getFluidAmount(), maxDrain), doDrain);
		}
		
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		
		if(fluid == FluidHandler.STEAM && steam.getFluidAmount() < steam.getCapacity()) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		
		if(fluid == FluidRegistry.WATER && water.getFluidAmount() > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { new FluidTankInfo(steam), new FluidTankInfo(water) };
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		water.readFromNBT(nbt);
		steam.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		water.writeToNBT(nbt);
		steam.writeToNBT(nbt);
	}
	
	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	public int getWaterScaled(int i) {
		return (water.getFluidAmount() * i) / water.getCapacity();
	}
	
	public int getSteamScaled(int i) {
		return (steam.getFluidAmount() * i) / steam.getCapacity();
	}

}