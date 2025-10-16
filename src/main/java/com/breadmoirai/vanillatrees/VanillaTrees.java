package com.breadmoirai.vanillatrees;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class VanillaTrees implements ModInitializer {

   public static final GameRules.Key<GameRules.BooleanRule> DO_VANILLA_TREE_GROWTH_ALWAYS =
      GameRuleRegistry.register("doVanillaTreeGrowthAlways", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
   public static final GameRules.Key<GameRules.BooleanRule> DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH =
      GameRuleRegistry.register("doDispensersForceVanillaTreeGrowth", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

   @Override
   public void onInitialize() {
   }
}
