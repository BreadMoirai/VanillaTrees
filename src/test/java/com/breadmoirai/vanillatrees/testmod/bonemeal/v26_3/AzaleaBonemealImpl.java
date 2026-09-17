//? if >=26.3 {
package com.breadmoirai.vanillatrees.testmod.bonemeal.v26_3;

import com.breadmoirai.vanillatrees.testmod.bonemeal.AzaleaBonemeal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.state.BlockState;

public class AzaleaBonemealImpl implements AzaleaBonemeal {
   @Override
   public void performBonemeal(AzaleaBlock azalea, ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      azalea.performBonemeal(level, random, pos, state, BonemealSource.INTERACTION);
   }
}
//?}
