package com.breadmoirai.vanillatrees.mixin;

import com.breadmoirai.vanillatrees.VanillaTreeSaplingGenerators;
import com.breadmoirai.vanillatrees.VanillaTrees;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AzaleaBlock.class)
public class AzaleaBlockMixin {

   @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/block/SaplingGenerator;AZALEA:Lnet/minecraft/block/SaplingGenerator;"), method = "grow")
   public SaplingGenerator replaceGenerator(ServerWorld world, Random random, BlockPos pos) {
      if (world.getGameRules().getBoolean(VanillaTrees.DO_VANILLA_TREE_GROWTH_ALWAYS)) {
         return VanillaTreeSaplingGenerators.AZALEA;
      }
      if (world.getGameRules().getBoolean(VanillaTrees.DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH)) {
         for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.offset(direction);
            BlockState adjacentState = world.getBlockState(adjacentPos);
            if (adjacentState.getBlock() instanceof DispenserBlock) {
               return VanillaTreeSaplingGenerators.AZALEA;
            }
         }
      }
      return SaplingGenerator.AZALEA;
   }

}
