//? if >=26.3 {
package com.breadmoirai.vanillatrees.grower.v26_3;

import com.breadmoirai.vanillatrees.grower.VanillaTreeGrowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.Map;

import static java.util.Map.entry;

// Mirrors TreeGrower's static initialiser (weights included). Grower names are namespaced because the
// TreeGrower constructor registers each name into TreeGrower's codec map, and the vanilla names would
// clobber vanilla's own growers there.
public class VanillaTreeGrowersImpl implements VanillaTreeGrowers {
   private static final ResourceKey<Feature> OAK_TREE = feature("oak");
   private static final ResourceKey<Feature> OAK_BEES_005 = feature("oak_bees_005");
   private static final ResourceKey<Feature> FANCY_OAK = feature("fancy_oak");
   private static final ResourceKey<Feature> FANCY_OAK_BEES_005 = feature("fancy_oak_bees_005");
   private static final ResourceKey<Feature> SPRUCE_TREE = feature("spruce");
   private static final ResourceKey<Feature> MEGA_SPRUCE = feature("mega_spruce");
   private static final ResourceKey<Feature> MEGA_PINE = feature("mega_pine");
   private static final ResourceKey<Feature> MANGROVE_TREE = feature("mangrove");
   private static final ResourceKey<Feature> TALL_MANGROVE = feature("tall_mangrove");
   private static final ResourceKey<Feature> AZALEA_TREE = feature("azalea_tree");
   private static final ResourceKey<Feature> BIRCH_TREE = feature("birch");
   private static final ResourceKey<Feature> BIRCH_BEES_005 = feature("birch_bees_005");
   private static final ResourceKey<Feature> JUNGLE_TREE_NO_VINE = feature("jungle_tree_no_vine");
   private static final ResourceKey<Feature> MEGA_JUNGLE_TREE = feature("mega_jungle_tree");
   private static final ResourceKey<Feature> ACACIA_TREE = feature("acacia");
   private static final ResourceKey<Feature> CHERRY_TREE = feature("cherry");
   private static final ResourceKey<Feature> CHERRY_BEES_005 = feature("cherry_bees_005");
   private static final ResourceKey<Feature> DARK_OAK_TREE = feature("dark_oak");
   private static final ResourceKey<Feature> PALE_OAK_BONEMEAL = feature("pale_oak_bonemeal");
   private static final ResourceKey<Feature> RED_POPLAR = feature("red_poplar");
   private static final ResourceKey<Feature> ORANGE_POPLAR = feature("orange_poplar");
   private static final ResourceKey<Feature> YELLOW_POPLAR = feature("yellow_poplar");

   private static final TreeGrower OAK = new TreeGrower("vanilla-trees:oak", WeightedList.of(new Weighted<>(OAK_TREE, 9), new Weighted<>(FANCY_OAK, 1)), WeightedList.of(), WeightedList.of(new Weighted<>(OAK_BEES_005, 9), new Weighted<>(FANCY_OAK_BEES_005, 1)), OAK_TREE);
   private static final TreeGrower SPRUCE = new TreeGrower("vanilla-trees:spruce", WeightedList.of(SPRUCE_TREE), WeightedList.of(new Weighted<>(MEGA_SPRUCE, 1), new Weighted<>(MEGA_PINE, 1)), WeightedList.of(), SPRUCE_TREE);
   private static final TreeGrower MANGROVE = new TreeGrower("vanilla-trees:mangrove", WeightedList.of(new Weighted<>(MANGROVE_TREE, 15), new Weighted<>(TALL_MANGROVE, 85)), WeightedList.of(), WeightedList.of(), MANGROVE_TREE);
   private static final TreeGrower AZALEA = new TreeGrower("vanilla-trees:azalea", WeightedList.of(AZALEA_TREE), WeightedList.of(), WeightedList.of(), AZALEA_TREE);
   private static final TreeGrower BIRCH = new TreeGrower("vanilla-trees:birch", WeightedList.of(BIRCH_TREE), WeightedList.of(), WeightedList.of(BIRCH_BEES_005), BIRCH_TREE);
   private static final TreeGrower JUNGLE = new TreeGrower("vanilla-trees:jungle", WeightedList.of(JUNGLE_TREE_NO_VINE), WeightedList.of(MEGA_JUNGLE_TREE), WeightedList.of(), JUNGLE_TREE_NO_VINE);
   private static final TreeGrower ACACIA = new TreeGrower("vanilla-trees:acacia", WeightedList.of(ACACIA_TREE), WeightedList.of(), WeightedList.of(), ACACIA_TREE);
   private static final TreeGrower CHERRY = new TreeGrower("vanilla-trees:cherry", WeightedList.of(CHERRY_TREE), WeightedList.of(), WeightedList.of(CHERRY_BEES_005), CHERRY_TREE);
   private static final TreeGrower DARK_OAK = new TreeGrower("vanilla-trees:dark_oak", WeightedList.of(), WeightedList.of(DARK_OAK_TREE), WeightedList.of(), null);
   private static final TreeGrower PALE_OAK = new TreeGrower("vanilla-trees:pale_oak", WeightedList.of(), WeightedList.of(PALE_OAK_BONEMEAL), WeightedList.of(), null);
   private static final TreeGrower POPLAR = new TreeGrower("vanilla-trees:poplar", WeightedList.of(new Weighted<>(RED_POPLAR, 1), new Weighted<>(ORANGE_POPLAR, 1), new Weighted<>(YELLOW_POPLAR, 1)), WeightedList.of(), WeightedList.of(), RED_POPLAR);

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
      entry(Blocks.PALE_OAK_SAPLING, PALE_OAK),
      entry(Blocks.POPLAR_SAPLING, POPLAR)
   );

   private static ResourceKey<Feature> feature(String id) {
      return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath("vanilla-trees", id));
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
//?}
