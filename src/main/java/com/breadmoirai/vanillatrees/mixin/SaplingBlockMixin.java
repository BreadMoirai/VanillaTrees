package com.breadmoirai.vanillatrees.mixin;

import com.breadmoirai.vanillatrees.VanillaTreeSaplingGenerators;
import com.breadmoirai.vanillatrees.VanillaTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {

   @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/SaplingBlock;treeGrower:Lnet/minecraft/world/level/block/grower/TreeGrower;"), method = "advanceTree")
   public TreeGrower replaceGenerator(SaplingBlock sapling, ServerLevel level, BlockPos pos) {
      if (VanillaTrees.isAlways(level)) {
         return VanillaTreeSaplingGenerators.generatorFor(sapling);
      }
      if (VanillaTrees.isDispenserForced(level)) {
         for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.getBlock() instanceof DispenserBlock) {
               return VanillaTreeSaplingGenerators.generatorFor(sapling);
            }
         }
      }
      return ((SaplingBlockAccessor) (Object) sapling).vanillatrees$getTreeGrower();
   }

}
