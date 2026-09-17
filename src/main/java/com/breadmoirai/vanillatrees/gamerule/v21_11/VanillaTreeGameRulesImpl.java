//? if >=1.21.11 {
package com.breadmoirai.vanillatrees.gamerule.v21_11;

import com.breadmoirai.vanillatrees.gamerule.VanillaTreeGameRules;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class VanillaTreeGameRulesImpl implements VanillaTreeGameRules {
   private final GameRule<Boolean> doVanillaTreeGrowthAlways =
      GameRuleBuilder.forBoolean(false).category(GameRuleCategory.MISC).buildAndRegister(Identifier.fromNamespaceAndPath("vanilla-trees", "do_vanilla_tree_growth_always"));
   private final GameRule<Boolean> doDispensersForceVanillaTreeGrowth =
      GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MISC).buildAndRegister(Identifier.fromNamespaceAndPath("vanilla-trees", "do_dispensers_force_vanilla_tree_growth"));

   @Override
   public boolean isAlways(ServerLevel level) {
      return level.getGameRules().get(doVanillaTreeGrowthAlways);
   }

   @Override
   public boolean isDispenserForced(ServerLevel level) {
      return level.getGameRules().get(doDispensersForceVanillaTreeGrowth);
   }

   @Override
   public void setAlways(ServerLevel level, boolean value) {
      level.getGameRules().set(doVanillaTreeGrowthAlways, value, level.getServer());
   }

   @Override
   public void setDispenserForced(ServerLevel level, boolean value) {
      level.getGameRules().set(doDispensersForceVanillaTreeGrowth, value, level.getServer());
   }
}
//?}
