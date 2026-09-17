package com.breadmoirai.vanillatrees.testmod.bonemeal;

import com.breadmoirai.vanillatrees.testmod.bonemeal.v26_3.AzaleaBonemealImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.state.BlockState;

/** {@code AzaleaBlock.performBonemeal} gained a {@code BonemealSource} parameter in 26.3. */
public interface AzaleaBonemeal {
   AzaleaBonemeal INSTANCE = new AzaleaBonemealImpl();

   void performBonemeal(AzaleaBlock azalea, ServerLevel level, RandomSource random, BlockPos pos, BlockState state);
}
