package com.breadmoirai.vanillatrees;

import com.breadmoirai.vanillatrees.gamerule.VanillaTreeGameRules;
import net.fabricmc.api.ModInitializer;

public class VanillaTrees implements ModInitializer {

   public static final VanillaTreeGameRules GAME_RULES = VanillaTreeGameRules.register();

   @Override
   public void onInitialize() {
   }
}
