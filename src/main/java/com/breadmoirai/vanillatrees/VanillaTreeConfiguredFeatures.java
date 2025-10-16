package com.breadmoirai.vanillatrees;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class VanillaTreeConfiguredFeatures {
   private static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
      return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of("vanilla-trees", id));
   }

   public static final RegistryKey<ConfiguredFeature<?, ?>> OAK = of("oak");
   public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_BEES_005 = of("oak_bees_005");
   public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_OAK = of("fancy_oak");
   public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_005 = of("fancy_oak_bees_005");

   public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE = of("spruce");
   public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_SPRUCE = of("mega_spruce");
   public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_PINE = of("mega_pine");

   public static final RegistryKey<ConfiguredFeature<?, ?>> MANGROVE = of("mangrove");
   public static final RegistryKey<ConfiguredFeature<?, ?>> TALL_MANGROVE = of("tall_mangrove");

   public static final RegistryKey<ConfiguredFeature<?, ?>> AZALEA_TREE = of("azalea_tree");

   public static final RegistryKey<ConfiguredFeature<?, ?>> BIRCH = of("birch");
   public static final RegistryKey<ConfiguredFeature<?, ?>> BIRCH_BEES_005 = of("birch_bees_005");

   public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_JUNGLE_TREE = of("mega_jungle_tree");
   public static final RegistryKey<ConfiguredFeature<?, ?>> JUNGLE_TREE_NO_VINE = of("jungle_tree_no_vine");

   public static final RegistryKey<ConfiguredFeature<?, ?>> ACACIA = of("acacia");

   public static final RegistryKey<ConfiguredFeature<?, ?>> CHERRY = of("cherry");
   public static final RegistryKey<ConfiguredFeature<?, ?>> CHERRY_BEES_005 = of("cherry_bees_005");

   public static final RegistryKey<ConfiguredFeature<?, ?>> DARK_OAK = of("dark_oak");

   public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_OAK_BONEMEAL = of("pale_oak_bonemeal");
}
