package com.breadmoirai.vanillatrees;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
//? if >=1.21.11 {
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
//? } else {

/*import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

*///? }

public class VanillaTrees implements ModInitializer {

   //? if >=1.21.11 {
   public static final GameRule<Boolean> DO_VANILLA_TREE_GROWTH_ALWAYS =
      GameRuleBuilder.forBoolean(false).category(GameRuleCategory.MISC).buildAndRegister(Identifier.fromNamespaceAndPath("vanilla-trees", "do_vanilla_tree_growth_always"));
   public static final GameRule<Boolean> DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH =
      GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MISC).buildAndRegister(Identifier.fromNamespaceAndPath("vanilla-trees", "do_dispensers_force_vanilla_tree_growth"));

   public static boolean isAlways(ServerLevel level) {
      return level.getGameRules().get(DO_VANILLA_TREE_GROWTH_ALWAYS);
   }

   public static boolean isDispenserForced(ServerLevel level) {
      return level.getGameRules().get(DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH);
   }

   public static void setAlways(ServerLevel level, boolean value) {
      level.getGameRules().set(DO_VANILLA_TREE_GROWTH_ALWAYS, value, level.getServer());
   }

   public static void setDispenserForced(ServerLevel level, boolean value) {
      level.getGameRules().set(DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH, value, level.getServer());
   }
   //? } else {
   
   /*public static final GameRules.Key<GameRules.BooleanValue> DO_VANILLA_TREE_GROWTH_ALWAYS =
      GameRuleRegistry.register("doVanillaTreeGrowthAlways", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
   public static final GameRules.Key<GameRules.BooleanValue> DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH =
      GameRuleRegistry.register("doDispensersForceVanillaTreeGrowth", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

   public static boolean isAlways(ServerLevel level) {
      return level.getGameRules().getBoolean(DO_VANILLA_TREE_GROWTH_ALWAYS);
   }

   public static boolean isDispenserForced(ServerLevel level) {
      return level.getGameRules().getBoolean(DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH);
   }

   public static void setAlways(ServerLevel level, boolean value) {
      level.getGameRules().getRule(DO_VANILLA_TREE_GROWTH_ALWAYS).set(value, level.getServer());
   }

   public static void setDispenserForced(ServerLevel level, boolean value) {
      level.getGameRules().getRule(DO_DISPENSERS_FORCE_VANILLA_TREE_GROWTH).set(value, level.getServer());
   }
   *///?}

   @Override
   public void onInitialize() {
   }
}
