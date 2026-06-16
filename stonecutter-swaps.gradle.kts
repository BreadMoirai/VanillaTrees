// Per-version symbol swaps for Stonecutter. The shared src/ holds the newest version's
// identifiers; each entry maps them to the name used by `sc.current.parsed >= version`.
// Empty until a version rename requires it (see the `update`/`backport` skills).
extra["swaps"] = mapOf(
    // 1.21.11 renamed net.minecraft.resources.ResourceLocation -> Identifier.
    "1.21.11" to mapOf(
        "ResourceLocation" to "Identifier",
    ),
)
