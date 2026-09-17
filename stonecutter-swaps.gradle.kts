// Per-version symbol swaps for Stonecutter. The shared src/ holds the newest version's
// identifiers; each entry maps them to the name used by `sc.current.parsed >= version`.
// Versioned package names are chained: each entry renames the previous lower bound's package.
extra["swaps"] = mapOf(
    // 26.3 reworked worldgen (ConfiguredFeature -> Feature, new TreeGrower constructors) and added a
    // BonemealSource parameter to performBonemeal.
    "26.3" to mapOf(
        "vanillatrees.grower.v21_10" to "vanillatrees.grower.v26_3",
        "vanillatrees.testmod.bonemeal.v21_10" to "vanillatrees.testmod.bonemeal.v26_3",
    ),
    // 1.21.11 renamed net.minecraft.resources.ResourceLocation -> Identifier and reworked GameRules.
    "1.21.11" to mapOf(
        "ResourceLocation" to "Identifier",
        "vanillatrees.gamerule.v21_10" to "vanillatrees.gamerule.v21_11",
    ),
)
