package com.breadmoirai.vanillatrees;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class VanillaTreeSaplingGenerators {
   public static final TreeGrower OAK = new TreeGrower("oak", 0.1F, Optional.empty(), Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.OAK), Optional.of(VanillaTreeConfiguredFeatures.FANCY_OAK), Optional.of(VanillaTreeConfiguredFeatures.OAK_BEES_005), Optional.of(VanillaTreeConfiguredFeatures.FANCY_OAK_BEES_005));
   public static final TreeGrower SPRUCE = new TreeGrower("spruce", 0.5F, Optional.of(VanillaTreeConfiguredFeatures.MEGA_SPRUCE), Optional.of(VanillaTreeConfiguredFeatures.MEGA_PINE), Optional.of(VanillaTreeConfiguredFeatures.SPRUCE), Optional.empty(), Optional.empty(), Optional.empty());
   public static final TreeGrower MANGROVE = new TreeGrower("mangrove", 0.85F, Optional.empty(), Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.MANGROVE), Optional.of(VanillaTreeConfiguredFeatures.TALL_MANGROVE), Optional.empty(), Optional.empty());
   public static final TreeGrower AZALEA = new TreeGrower("azalea", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.AZALEA_TREE), Optional.empty());
   public static final TreeGrower BIRCH = new TreeGrower("birch", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.BIRCH), Optional.of(VanillaTreeConfiguredFeatures.BIRCH_BEES_005));
   public static final TreeGrower JUNGLE = new TreeGrower("jungle", Optional.of(VanillaTreeConfiguredFeatures.MEGA_JUNGLE_TREE), Optional.of(VanillaTreeConfiguredFeatures.JUNGLE_TREE_NO_VINE), Optional.empty());
   public static final TreeGrower ACACIA = new TreeGrower("acacia", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.ACACIA), Optional.empty());
   public static final TreeGrower CHERRY = new TreeGrower("cherry", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.CHERRY), Optional.of(VanillaTreeConfiguredFeatures.CHERRY_BEES_005));
   public static final TreeGrower DARK_OAK = new TreeGrower("dark_oak", Optional.of(VanillaTreeConfiguredFeatures.DARK_OAK), Optional.empty(), Optional.empty());
   public static final TreeGrower PALE_OAK = new TreeGrower("pale_oak", Optional.of(VanillaTreeConfiguredFeatures.PALE_OAK_BONEMEAL), Optional.empty(), Optional.empty());

   public static TreeGrower generatorFor(SaplingBlock block) {
      return MAP.get(block);
   }

   private static final Map<Block, TreeGrower> MAP = Map.ofEntries(
      entry(Blocks.OAK_SAPLING, OAK),
      entry(Blocks.SPRUCE_SAPLING, SPRUCE),
      entry(Blocks.MANGROVE_PROPAGULE, MANGROVE),
      entry(Blocks.AZALEA, AZALEA),
      entry(Blocks.FLOWERING_AZALEA, AZALEA),
      entry(Blocks.BIRCH_SAPLING, BIRCH),
      entry(Blocks.JUNGLE_SAPLING, JUNGLE),
      entry(Blocks.ACACIA_SAPLING, ACACIA),
      entry(Blocks.CHERRY_SAPLING, CHERRY),
      entry(Blocks.DARK_OAK_SAPLING, DARK_OAK),
      entry(Blocks.PALE_OAK_SAPLING, PALE_OAK)
   );
}
