package net.xiaoyu.xinyu_attributes.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

public class AbsorbedBlockEntity extends Entity {
    private static final EntityDataAccessor<Integer> BLOCK_STATE_ID = SynchedEntityData.defineId(AbsorbedBlockEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> ABSORPTION_PROGRESS = SynchedEntityData.defineId(AbsorbedBlockEntity.class, EntityDataSerializers.FLOAT);
    
    private Vec3 targetPosition;
    private Vec3 originalPosition;
    private boolean isBeingAbsorbed = true;
    private int absorptionTime = 40;
    private int currentTime = 0;
    
    public AbsorbedBlockEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }
    
    public AbsorbedBlockEntity(EntityType<?> entityType, Level level, BlockPos blockPos, BlockState blockState, Vec3 targetPos) {
        this(entityType, level);
        this.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.originalPosition = this.position();
        this.targetPosition = targetPos;
        this.setBlockState(blockState);
    }
    
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(BLOCK_STATE_ID, Block.getId(Blocks.STONE.defaultBlockState()));
        builder.define(ABSORPTION_PROGRESS, 0.0F);
    }
    
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.setBlockState(Block.stateById(compound.getInt("BlockState")));
        this.isBeingAbsorbed = compound.getBoolean("IsBeingAbsorbed");
        this.currentTime = compound.getInt("CurrentTime");
    }
        
    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("BlockState", Block.getId(this.getBlockState()));
        compound.putBoolean("IsBeingAbsorbed", this.isBeingAbsorbed);
        compound.putInt("CurrentTime", this.currentTime);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (isBeingAbsorbed && targetPosition != null) {
            currentTime++;
            float progress = Math.min(1, (float)currentTime / absorptionTime);
            this.entityData.set(ABSORPTION_PROGRESS, progress);

            float spiralRadius = 0.5F * (1 - progress);
            float spiralAngle = (float)(progress * Math.PI * 4);
            
            Vec3 spiralOffset = new Vec3(
                Math.cos(spiralAngle) * spiralRadius,
                Math.sin(spiralAngle * 2) * spiralRadius * 0.5F,
                Math.sin(spiralAngle) * spiralRadius
            );
            
            Vec3 newPos = originalPosition.add(targetPosition.subtract(originalPosition).scale(progress)).add(spiralOffset);
            this.setPos(newPos.x, newPos.y, newPos.z);
            
            this.setYRot((float)(spiralAngle * 180 / Math.PI));
            this.setXRot((float)(progress * 360));
            
            if (progress >= 1) {
                this.discard();
            }
        }
    }
    
    public void setBlockState(BlockState blockState) {
        this.entityData.set(BLOCK_STATE_ID, Block.getId(blockState));
    }
    
    public BlockState getBlockState() {
        return Block.stateById(this.entityData.get(BLOCK_STATE_ID));
    }
    
    public float getAbsorptionProgress() {
        return this.entityData.get(ABSORPTION_PROGRESS);
    }
    
    public void setAbsorptionTime(int time) {
        this.absorptionTime = time;
    }
    
    @Override
    public boolean isPickable() {
        return true;
    }
    
    @Override
    public boolean shouldBeSaved() {
        return false;
    }
    
    @Override
    public boolean fireImmune() {
        return true;
    }
}