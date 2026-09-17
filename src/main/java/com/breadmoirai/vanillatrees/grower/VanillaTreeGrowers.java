package com.breadmoirai.vanillatrees.grower;

import com.breadmoirai.vanillatrees.grower.v26_3.VanillaTreeGrowersImpl;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;

/**
 * {@link TreeGrower}s mirroring vanilla, but pointing at this mod's bundled copies of the vanilla
 * tree features ({@code vanilla-trees:*}) so datapack overrides of the {@code minecraft:} features
 * are bypassed. The feature registry and the {@code TreeGrower} constructors changed in 26.3, so the
 * tables live in a versioned implementation.
 */
public interface VanillaTreeGrowers {
   VanillaTreeGrowers INSTANCE = new VanillaTreeGrowersImpl();

   TreeGrower azalea();

   /** Returns {@code null} when the block has no vanilla-tree mapping (e.g. a modded sapling). */
   TreeGrower forBlock(Block block);
}
