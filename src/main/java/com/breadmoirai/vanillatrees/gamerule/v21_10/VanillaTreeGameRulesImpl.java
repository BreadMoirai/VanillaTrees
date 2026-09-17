//? if <1.21.11 {
/*package com.breadmoirai.vanillatrees.gamerule.v21_11;

import com.breadmoirai.vanillatrees.gamerule.VanillaTreeGameRules;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

public class VanillaTreeGameRulesImpl implements VanillaTreeGameRules {
   private final GameRules.Key<GameRules.BooleanValue> doVanillaTreeGrowthAlways =
      GameRuleRegistry.register("doVanillaTreeGrowthAlways", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
   private final GameRules.Key<GameRules.BooleanValue> doDispensersForceVanillaTreeGrowth =
      GameRuleRegistry.register("doDispensersForceVanillaTreeGrowth", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

   @Override
   public boolean isAlways(ServerLevel level) {
      return level.getGameRules().getBoolean(doVanillaTreeGrowthAlways);
   }

   @Override
   public boolean isDispenserForced(ServerLevel level) {
      return level.getGameRules().getBoolean(doDispensersForceVanillaTreeGrowth);
   }

   @Override
   public void setAlways(ServerLevel level, boolean value) {
      level.getGameRules().getRule(doVanillaTreeGrowthAlways).set(value, level.getServer());
   }

   @Override
   public void setDispenserForced(ServerLevel level, boolean value) {
      level.getGameRules().getRule(doDispensersForceVanillaTreeGrowth).set(value, level.getServer());
   }
}
*///?}
