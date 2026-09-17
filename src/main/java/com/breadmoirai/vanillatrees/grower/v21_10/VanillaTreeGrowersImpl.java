//? if <26.3 {
/*package com.breadmoirai.vanillatrees.grower.v26_3;

import com.breadmoirai.vanillatrees.grower.VanillaTreeGrowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

// Mirrors TreeGrower's static initialiser. Grower names are namespaced because the TreeGrower
// constructor registers each name into TreeGrower's codec map, and the vanilla names would clobber
// vanilla's own growers there.
public class VanillaTreeGrowersImpl implements VanillaTreeGrowers {
   private static final ResourceKey<ConfiguredFeature<?, ?>> OAK_TREE = feature("oak");
   private static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_005 = feature("oak_bees_005");
   private static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK = feature("fancy_oak");
   private static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_005 = feature("fancy_oak_bees_005");
   private static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_TREE = feature("spruce");
   private static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_SPRUCE = feature("mega_spruce");
   private static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_PINE = feature("mega_pine");
   private static final ResourceKey<ConfiguredFeature<?, ?>> MANGROVE_TREE = feature("mangrove");
   private static final ResourceKey<ConfiguredFeature<?, ?>> TALL_MANGROVE = feature("tall_mangrove");
   private static final ResourceKey<ConfiguredFeature<?, ?>> AZALEA_TREE = feature("azalea_tree");
   private static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TREE = feature("birch");
   private static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_005 = feature("birch_bees_005");
   private static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_TREE_NO_VINE = feature("jungle_tree_no_vine");
   private static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_JUNGLE_TREE = feature("mega_jungle_tree");
   private static final ResourceKey<ConfiguredFeature<?, ?>> ACACIA_TREE = feature("acacia");
   private static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_TREE = feature("cherry");
   private static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BEES_005 = feature("cherry_bees_005");
   private static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK_TREE = feature("dark_oak");
   private static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_BONEMEAL = feature("pale_oak_bonemeal");

   private static final TreeGrower OAK = new TreeGrower("vanilla-trees:oak", 0.1F, Optional.empty(), Optional.empty(), Optional.of(OAK_TREE), Optional.of(FANCY_OAK), Optional.of(OAK_BEES_005), Optional.of(FANCY_OAK_BEES_005));
   private static final TreeGrower SPRUCE = new TreeGrower("vanilla-trees:spruce", 0.5F, Optional.of(MEGA_SPRUCE), Optional.of(MEGA_PINE), Optional.of(SPRUCE_TREE), Optional.empty(), Optional.empty(), Optional.empty());
   private static final TreeGrower MANGROVE = new TreeGrower("vanilla-trees:mangrove", 0.85F, Optional.empty(), Optional.empty(), Optional.of(MANGROVE_TREE), Optional.of(TALL_MANGROVE), Optional.empty(), Optional.empty());
   private static final TreeGrower AZALEA = new TreeGrower("vanilla-trees:azalea", Optional.empty(), Optional.of(AZALEA_TREE), Optional.empty());
   private static final TreeGrower BIRCH = new TreeGrower("vanilla-trees:birch", Optional.empty(), Optional.of(BIRCH_TREE), Optional.of(BIRCH_BEES_005));
   private static final TreeGrower JUNGLE = new TreeGrower("vanilla-trees:jungle", Optional.of(MEGA_JUNGLE_TREE), Optional.of(JUNGLE_TREE_NO_VINE), Optional.empty());
   private static final TreeGrower ACACIA = new TreeGrower("vanilla-trees:acacia", Optional.empty(), Optional.of(ACACIA_TREE), Optional.empty());
   private static final TreeGrower CHERRY = new TreeGrower("vanilla-trees:cherry", Optional.empty(), Optional.of(CHERRY_TREE), Optional.of(CHERRY_BEES_005));
   private static final TreeGrower DARK_OAK = new TreeGrower("vanilla-trees:dark_oak", Optional.of(DARK_OAK_TREE), Optional.empty(), Optional.empty());
   private static final TreeGrower PALE_OAK = new TreeGrower("vanilla-trees:pale_oak", Optional.of(PALE_OAK_BONEMEAL), Optional.empty(), Optional.empty());

   private static final Map<Block, TreeGrower> BY_BLOCK = Map.ofEntries(
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

   private static ResourceKey<ConfiguredFeature<?, ?>> feature(String id) {
      return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath("vanilla-trees", id));
   }

   @Override
   public TreeGrower azalea() {
      return AZALEA;
   }

   @Override
   public TreeGrower forBlock(Block block) {
      return BY_BLOCK.get(block);
   }
}
*///?}
