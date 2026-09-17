package com.breadmoirai.vanillatrees.mixin;

import com.breadmoirai.vanillatrees.VanillaTrees;
import com.breadmoirai.vanillatrees.grower.VanillaTreeGrowers;
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
      // null for a sapling this mod has no vanilla-tree mapping for (a modded sapling, or a vanilla
      // one added by a newer MC version) - fall through to the block's own grower rather than NPE.
      TreeGrower vanilla = VanillaTreeGrowers.INSTANCE.forBlock(sapling);
      if (vanilla != null) {
         if (VanillaTrees.GAME_RULES.isAlways(level)) {
            return vanilla;
         }
         if (VanillaTrees.GAME_RULES.isDispenserForced(level)) {
            for (Direction direction : Direction.values()) {
               BlockPos adjacentPos = pos.relative(direction);
               BlockState adjacentState = level.getBlockState(adjacentPos);
               if (adjacentState.getBlock() instanceof DispenserBlock) {
                  return vanilla;
               }
            }
         }
      }
      return ((SaplingBlockAccessor) (Object) sapling).vanillatrees$getTreeGrower();
   }

}
