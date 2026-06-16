package com.breadmoirai.vanillatrees;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class VanillaTreeConfiguredFeatures {
   private static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
      return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath("vanilla-trees", id));
   }

   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK = of("oak");
   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_005 = of("oak_bees_005");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK = of("fancy_oak");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_005 = of("fancy_oak_bees_005");

   public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE = of("spruce");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_SPRUCE = of("mega_spruce");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_PINE = of("mega_pine");

   public static final ResourceKey<ConfiguredFeature<?, ?>> MANGROVE = of("mangrove");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_MANGROVE = of("tall_mangrove");

   public static final ResourceKey<ConfiguredFeature<?, ?>> AZALEA_TREE = of("azalea_tree");

   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH = of("birch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_005 = of("birch_bees_005");

   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_JUNGLE_TREE = of("mega_jungle_tree");
   public static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_TREE_NO_VINE = of("jungle_tree_no_vine");

   public static final ResourceKey<ConfiguredFeature<?, ?>> ACACIA = of("acacia");

   public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY = of("cherry");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BEES_005 = of("cherry_bees_005");

   public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK = of("dark_oak");

   public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_BONEMEAL = of("pale_oak_bonemeal");
}
