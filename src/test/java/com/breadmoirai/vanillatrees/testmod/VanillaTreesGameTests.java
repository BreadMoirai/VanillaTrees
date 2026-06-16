package com.breadmoirai.vanillatrees.testmod;

import com.breadmoirai.vanillatrees.VanillaTrees;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Server game tests for VanillaTrees. The mod redirects sapling/azalea growth to its own
 * vanilla tree features (namespace {@code vanilla-trees}) when a dispenser is adjacent or the
 * gamerule is set. Tests run headless via {@code runGameTest} on every supported MC version.
 *
 * <p>Tier 1 verifies the mixins fire and growth still works. Tier 2 ships a datapack
 * (src/test/resources) that overrides the vanilla {@code minecraft:acacia} feature with a
 * distinctive gold tree, proving the mod bypasses the datapack override.
 */
public class VanillaTreesGameTests {

   private static final BlockPos DIRT = new BlockPos(3, 1, 3);
   private static final BlockPos SAPLING = new BlockPos(3, 2, 3);
   private static final BlockPos DISPENSER = new BlockPos(3, 2, 2); // face-neighbour of SAPLING

   // --- Tier 1: the mixins fire and growth happens -------------------------------------------

   @GameTest(skyAccess = true)
   public void acaciaGrowsWithAlwaysGamerule(GameTestHelper helper) {
      ServerLevel level = helper.getLevel();
      VanillaTrees.setAlways(level, true);            // force the "always" path
      VanillaTrees.setDispenserForced(level, false);  // ensure growth is due to the gamerule, not a dispenser

      helper.setBlock(DIRT, Blocks.DIRT);
      helper.setBlock(SAPLING, Blocks.ACACIA_SAPLING.defaultBlockState().setValue(BlockStateProperties.STAGE, 1));
      growSapling(helper, SAPLING);

      if (!hasBlock(helper, Blocks.ACACIA_LOG)) {
         helper.fail("acacia tree did not grow with the always-gamerule");
      }
      helper.succeed();
   }

   @GameTest(skyAccess = true)
   public void azaleaGrowsNextToDispenser(GameTestHelper helper) {
      ServerLevel level = helper.getLevel();
      VanillaTrees.setAlways(level, false);
      VanillaTrees.setDispenserForced(level, true);

      helper.setBlock(DIRT, Blocks.DIRT);
      helper.setBlock(DISPENSER, Blocks.DISPENSER);
      helper.setBlock(SAPLING, Blocks.AZALEA);
      growAzalea(helper, SAPLING);

      if (!hasBlock(helper, Blocks.OAK_LOG)) {
         helper.fail("azalea tree did not grow next to a dispenser");
      }
      helper.succeed();
   }

   // --- Tier 2: the mod bypasses a datapack override of the vanilla feature -------------------

   @GameTest(skyAccess = true)
   public void defaultGrowthUsesDatapackFeature(GameTestHelper helper) {
      ServerLevel level = helper.getLevel();
      VanillaTrees.setAlways(level, false);
      VanillaTrees.setDispenserForced(level, true); // no dispenser placed, so this path is not triggered

      helper.setBlock(DIRT, Blocks.DIRT);
      helper.setBlock(SAPLING, Blocks.ACACIA_SAPLING.defaultBlockState().setValue(BlockStateProperties.STAGE, 1));
      growSapling(helper, SAPLING);

      // The test datapack overrides minecraft:acacia with a gold tree; untriggered growth uses it.
      if (!hasBlock(helper, Blocks.GOLD_BLOCK)) {
         helper.fail("expected the datapack-overridden (gold) tree from default growth");
      }
      if (hasBlock(helper, Blocks.ACACIA_LOG)) {
         helper.fail("default growth unexpectedly produced a vanilla acacia");
      }
      helper.succeed();
   }

   @GameTest(skyAccess = true)
   public void dispenserBypassesDatapackOverride(GameTestHelper helper) {
      ServerLevel level = helper.getLevel();
      VanillaTrees.setAlways(level, false);
      VanillaTrees.setDispenserForced(level, true);

      helper.setBlock(DIRT, Blocks.DIRT);
      helper.setBlock(DISPENSER, Blocks.DISPENSER);
      helper.setBlock(SAPLING, Blocks.ACACIA_SAPLING.defaultBlockState().setValue(BlockStateProperties.STAGE, 1));
      growSapling(helper, SAPLING);

      // With a dispenser adjacent the mod uses vanilla-trees:acacia, which the datapack did NOT override.
      if (!hasBlock(helper, Blocks.ACACIA_LOG)) {
         helper.fail("expected a real acacia (mod bypass) but none grew");
      }
      if (hasBlock(helper, Blocks.GOLD_BLOCK)) {
         helper.fail("the datapack override leaked through despite the dispenser");
      }
      helper.succeed();
   }

   // --- helpers ------------------------------------------------------------------------------

   private static void growSapling(GameTestHelper helper, BlockPos rel) {
      ServerLevel level = helper.getLevel();
      BlockPos abs = helper.absolutePos(rel);
      RandomSource random = level.getRandom();
      for (int attempt = 0; attempt < 16; attempt++) {
         BlockState state = level.getBlockState(abs);
         if (!(state.getBlock() instanceof SaplingBlock sapling)) {
            return; // sapling consumed -> a tree grew
         }
         state = state.setValue(BlockStateProperties.STAGE, 1);
         level.setBlock(abs, state, 4);
         sapling.advanceTree(level, abs, state, random);
      }
   }

   private static void growAzalea(GameTestHelper helper, BlockPos rel) {
      ServerLevel level = helper.getLevel();
      BlockPos abs = helper.absolutePos(rel);
      RandomSource random = level.getRandom();
      for (int attempt = 0; attempt < 16; attempt++) {
         BlockState state = level.getBlockState(abs);
         if (!(state.getBlock() instanceof AzaleaBlock azalea)) {
            return; // azalea consumed -> a tree grew
         }
         azalea.performBonemeal(level, random, abs, state);
      }
   }

   private static boolean hasBlock(GameTestHelper helper, Block target) {
      for (int x = 0; x < 8; x++) {
         for (int y = 0; y < 8; y++) {
            for (int z = 0; z < 8; z++) {
               if (helper.getBlockState(new BlockPos(x, y, z)).is(target)) {
                  return true;
               }
            }
         }
      }
      return false;
   }
}
