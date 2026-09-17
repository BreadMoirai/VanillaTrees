package com.breadmoirai.vanillatrees.mixin;

import com.breadmoirai.vanillatrees.VanillaTrees;
import com.breadmoirai.vanillatrees.grower.VanillaTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AzaleaBlock.class)
public class AzaleaBlockMixin {

   @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/grower/TreeGrower;AZALEA:Lnet/minecraft/world/level/block/grower/TreeGrower;"), method = "performBonemeal")
   public TreeGrower replaceGenerator(ServerLevel level, RandomSource random, BlockPos pos) {
      if (VanillaTrees.GAME_RULES.isAlways(level)) {
         return VanillaTreeGrowers.INSTANCE.azalea();
      }
      if (VanillaTrees.GAME_RULES.isDispenserForced(level)) {
         for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.getBlock() instanceof DispenserBlock) {
               return VanillaTreeGrowers.INSTANCE.azalea();
            }
         }
      }
      return TreeGrower.AZALEA;
   }

}
