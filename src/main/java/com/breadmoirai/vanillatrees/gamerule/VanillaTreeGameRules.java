package com.breadmoirai.vanillatrees.gamerule;

import com.breadmoirai.vanillatrees.gamerule.v21_11.VanillaTreeGameRulesImpl;
import net.minecraft.server.level.ServerLevel;

/**
 * Version-agnostic access to the mod's two boolean gamerules. The GameRules API was reworked in
 * 1.21.11, so registration and reads live in a versioned implementation.
 */
public interface VanillaTreeGameRules {
   /** Registers the gamerules. Call exactly once, during mod initialisation. */
   static VanillaTreeGameRules register() {
      return new VanillaTreeGameRulesImpl();
   }

   boolean isAlways(ServerLevel level);

   boolean isDispenserForced(ServerLevel level);

   void setAlways(ServerLevel level, boolean value);

   void setDispenserForced(ServerLevel level, boolean value);
}
