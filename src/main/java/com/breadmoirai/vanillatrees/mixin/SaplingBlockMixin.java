package com.breadmoirai.vanillatrees.mixin;

import com.breadmoirai.vanillatrees.VanillaTreeSaplingGenerators;
import com.breadmoirai.vanillatrees.VanillaTrees;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {

   @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/block/SaplingBlock;generator:Lnet/minecraft/block/SaplingGenerator;"), method = "generate")
   public SaplingGenerator replaceGenerator(SaplingBlock sapling, ServerWorld world, BlockPos pos) {
      if (world.getGameRules().getBoolean(VanillaTrees.DO_VANILLA_TREE_GROWTH_ALWAYS)) {
         return VanillaTreeSaplingGenerators.generatorFor(sapling);
      }
      if (world.getGameRules().getBoolean(VanillaTrees.DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH)) {
         for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.offset(direction);
            BlockState adjacentState = world.getBlockState(adjacentPos);
            if (adjacentState.getBlock() instanceof DispenserBlock) {
               return VanillaTreeSaplingGenerators.generatorFor(sapling);
            }
         }
      }
      return sapling.generator;
   }

}
