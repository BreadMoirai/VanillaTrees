package com.breadmoirai.vanillatrees;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class VanillaTreeSaplingGenerators {
   public static final SaplingGenerator OAK = new SaplingGenerator("oak", 0.1F,Optional.empty(), Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.OAK), Optional.of(VanillaTreeConfiguredFeatures.FANCY_OAK), Optional.of(VanillaTreeConfiguredFeatures.OAK_BEES_005), Optional.of(VanillaTreeConfiguredFeatures.FANCY_OAK_BEES_005));
   public static final SaplingGenerator SPRUCE = new SaplingGenerator("spruce", 0.5F, Optional.of(VanillaTreeConfiguredFeatures.MEGA_SPRUCE), Optional.of(VanillaTreeConfiguredFeatures.MEGA_PINE), Optional.of(VanillaTreeConfiguredFeatures.SPRUCE), Optional.empty(), Optional.empty(), Optional.empty());
   public static final SaplingGenerator MANGROVE = new SaplingGenerator("mangrove", 0.85F, Optional.empty(), Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.MANGROVE), Optional.of(VanillaTreeConfiguredFeatures.TALL_MANGROVE), Optional.empty(), Optional.empty());
   public static final SaplingGenerator AZALEA = new SaplingGenerator("azalea", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.AZALEA_TREE), Optional.empty());
   public static final SaplingGenerator BIRCH = new SaplingGenerator("birch", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.BIRCH), Optional.of(VanillaTreeConfiguredFeatures.BIRCH_BEES_005));
   public static final SaplingGenerator JUNGLE = new SaplingGenerator("jungle", Optional.of(VanillaTreeConfiguredFeatures.MEGA_JUNGLE_TREE), Optional.of(VanillaTreeConfiguredFeatures.JUNGLE_TREE_NO_VINE), Optional.empty());
   public static final SaplingGenerator ACACIA = new SaplingGenerator("acacia", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.ACACIA), Optional.empty());
   public static final SaplingGenerator CHERRY = new SaplingGenerator("cherry", Optional.empty(), Optional.of(VanillaTreeConfiguredFeatures.CHERRY), Optional.of(VanillaTreeConfiguredFeatures.CHERRY_BEES_005));
   public static final SaplingGenerator DARK_OAK = new SaplingGenerator("dark_oak", Optional.of(VanillaTreeConfiguredFeatures.DARK_OAK), Optional.empty(), Optional.empty());
   public static final SaplingGenerator PALE_OAK = new SaplingGenerator("pale_oak", Optional.of(VanillaTreeConfiguredFeatures.PALE_OAK_BONEMEAL), Optional.empty(), Optional.empty());

   public static SaplingGenerator generatorFor(SaplingBlock block) {
      return MAP.get(block);
   }

   private static final Map<Block, SaplingGenerator> MAP = Map.ofEntries(
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
