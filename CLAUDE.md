# VanillaTrees — project notes

Fabric mod that forces saplings (and azalea) to grow full vanilla trees when next to a
dispenser or via gamerule, instead of the configured datapack growth. Multi-version via
Stonecutter. Server + client (`"environment": "*"`), `main` entrypoint, no config UI
(no YACL/ModMenu).

## Naming / layout (placeholders for the `backport` and `update` skills)

| Placeholder | Value |
|-------------|-------|
| `<modid>` | `vanilla-trees` |
| `<mod-id>` (mixins.json prefix / jar artifact / access-widener prefix) | `vanilla-trees` |
| `<modpkg>` | `com.breadmoirai.vanillatrees` |

- **Shared main code:** `src/main/java/com/breadmoirai/vanillatrees/`
  - `VanillaTrees.java` — `ModInitializer`; holds `GAME_RULES`, created (and so registered) when
    the entrypoint class loads.
  - `gamerule/VanillaTreeGameRules.java` — version-agnostic interface (`isAlways`,
    `isDispenserForced`, `setAlways`, `setDispenserForced`); `register()` builds the versioned impl.
    - `gamerule/v21_10/VanillaTreeGameRulesImpl.java` — `<1.21.11` (`GameRules.Key` + `GameRuleRegistry`).
    - `gamerule/v21_11/VanillaTreeGameRulesImpl.java` — `>=1.21.11` (`GameRule` + `GameRuleBuilder`).
  - `grower/VanillaTreeGrowers.java` — version-agnostic interface (`azalea()`, `forBlock(Block)`);
    `INSTANCE` is the versioned impl.
    - `grower/v21_10/VanillaTreeGrowersImpl.java` — `<26.3`: `ResourceKey<ConfiguredFeature<?, ?>>`
      keys + `Optional`-based `TreeGrower` constructors.
    - `grower/v26_3/VanillaTreeGrowersImpl.java` — `>=26.3`: `ResourceKey<Feature>` keys +
      `WeightedList`-based constructors, plus poplar.
    Each impl mirrors its version's `TreeGrower` static initialiser exactly (every feature key it
    references, weights/chances included) but points at the bundled `vanilla-trees:*` features.
  - `mixin/SaplingBlockMixin.java` — `@Redirect` on `SaplingBlock.advanceTree` field access of
    `treeGrower`; reads the default grower through `SaplingBlockAccessor`.
  - `mixin/SaplingBlockAccessor.java` — `@Accessor("treeGrower")` interface exposing the
    `SaplingBlock.treeGrower` field (replaces the former access widener).
  - `mixin/AzaleaBlockMixin.java` — `@Redirect` on `AzaleaBlock.performBonemeal` static field
    access of `TreeGrower.AZALEA` (`TreeGrower.AZALEA` is public — no accessor needed).
- **Shared test code:** `src/test/java/com/breadmoirai/vanillatrees/testmod/`
  - `VanillaTreesGameTests.java` — the game tests (see below).
  - `bonemeal/AzaleaBonemeal.java` + `bonemeal/v21_10/` (`<26.3`) / `bonemeal/v26_3/` (`>=26.3`) —
    wraps `AzaleaBlock.performBonemeal`, which gained a `BonemealSource` parameter in 26.3.
- **Versioned packages** follow the usual convention: named after the minimum MC version the code
  runs on, each impl file wrapped in one outer `//? if` condition, the shared interface importing the
  newest package, and chained package swaps in `stonecutter-swaps.gradle.kts` (`26.3`:
  `grower.v21_10 -> grower.v26_3`, `testmod.bonemeal.v21_10 -> testmod.bonemeal.v26_3`; `1.21.11`:
  `gamerule.v21_10 -> gamerule.v21_11`). No inline version conditions remain in non-versioned files.
- **Worldgen resources are per version, not shared.** Every version bundles its own vanilla tree
  features in `versions/<v>/src/main/resources/data/vanilla-trees/worldgen/<registry>/`, copied
  verbatim from that version's vanilla data (misode/mcmeta `<v>-data` tag — the features are
  code-defined, so they are not in the MC jar). `<registry>` is `configured_feature` below 26.3 and
  `feature` from 26.3. The set is exactly the features that version's `TreeGrower` references
  (19, plus 3 poplars on 26.3). The test datapack override likewise lives per version at
  `versions/<v>/src/test/resources/data/minecraft/worldgen/<registry>/acacia.json`: that version's
  vanilla acacia with `trunk_provider`/`foliage_provider` swapped for gold blocks. When adding a
  version, re-fetch both from its mcmeta tag rather than copying a neighbour — the JSON format drifts
  between versions (e.g. 26.1 added `below_trunk_provider`, 26.2 omits default values).
- **Mappings:** official Mojang mappings (converted from the original Yarn).
- **No access wideners.** Access to the non-public `SaplingBlock.treeGrower` field is provided by
  the `SaplingBlockAccessor` mixin (above). `@Accessor("treeGrower")` remaps automatically across
  both the 1.21.x (named) and 26.x (official/un-obfuscated) toolchains — no namespace handling
  needed, which is why there are no `.accesswidener` files, no `accessWidener` key in
  `fabric.mod.json`, and no `loom.accessWidenerPath` in either build script.
- **Mixins config:** `versions/<v>/src/main/resources/vanilla-trees.mixins.json` (real
  per-version files, all identical; `mixins` array lists `AzaleaBlockMixin`,
  `SaplingBlockAccessor`, `SaplingBlockMixin`, alphabetically sorted).

## Versions

- **1.21.x line** (1.21.10, 1.21.11): normal `fabric-loom-remap` + Mojang-mappings path, Java 21,
  `build.gradle.kts`, `transformUnnamedVars` on switch.
- **26.x line** (26.1, 26.1.1, 26.1.2, 26.2, 26.3 — `26.3` is the `vcsVersion`; shared `src/` holds its
  code): un-obfuscated / JDK-25 toolchain. Registered in `settings.gradle.kts` via
  `versions("26.3", "26.2", "26.1.2", "26.1.1", "26.1").buildscript("build.unobf.gradle.kts")` (plain
  `fabric-loom`, no Mojang mappings, `restoreUnnamedVars` on switch, `jar` not `remapJar`).
  The 26.1.x patches are API-identical to 26.1 and 26.2, so there is no Java source divergence
  between them; 26.3 diverges heavily (see below).

- **1.21.11 GameRules rework** — `net.minecraft.world.GameRules.Key<BooleanValue>` /
  Fabric `GameRuleRegistry.register(String, Category, GameRuleFactory.createBooleanRule(...))`
  became `net.minecraft.world.level.gamerules.GameRule<Boolean>` /
  Fabric `GameRuleBuilder.forBoolean(b).category(GameRuleCategory.MISC).buildAndRegister(Identifier)`,
  and reads went from `getGameRules().getBoolean(key)` to `getGameRules().get(key)`. Handled by the
  `gamerule/v21_10` / `gamerule/v21_11` impls. 26.x uses `v21_11`.
  Note: the gamerule IDs are now namespaced — `vanilla-trees:do_vanilla_tree_growth_always` /
  `vanilla-trees:do_dispensers_force_vanilla_tree_growth` (snake_case) on 1.21.11+.
- **1.21.11 `ResourceLocation` → `Identifier`** (`net.minecraft.resources`, persists into 26.x) —
  handled by a swap in `stonecutter-swaps.gradle.kts`: shared `src/` holds the new name
  `Identifier`; the bidirectional swap reverses it to `ResourceLocation` when `current < 1.21.11`.
- **26.1 `TreeConfiguration.below_trunk_provider`** — required from 26.1, replacing `dirt_provider` +
  `force_dirt`. Absorbed by the per-version resources (each version ships its own vanilla JSON).
- **26.3 worldgen rework: `ConfiguredFeature` -> `Feature`.** `ConfiguredFeature<FC, F>` is gone; the
  datapack registry is now `Registries.FEATURE` at `data/<ns>/worldgen/feature/` (was
  `Registries.CONFIGURED_FEATURE` at `worldgen/configured_feature/`), and the JSON is flattened (no
  `config` wrapper), uses the `{"id": ..., "properties": {...}}` block-state shorthand for state
  providers, and takes `below_trunk_provider` as a registry reference
  (`"minecraft:soil_beneath_tree"`). Handled by `grower/v26_3` plus the per-version resources.
- **26.3 `TreeGrower` constructor rework** — the primary/secondary + `secondaryChance` `Optional`
  constructors became
  `TreeGrower(String, WeightedList<ResourceKey<Feature>> trees, WeightedList<...> megaTrees, WeightedList<...> flowerTrees, ResourceKey<Feature> shortestTreeType)`.
  Handled by `grower/v21_10` vs `grower/v26_3`.
- **26.3 `performBonemeal` gained a `BonemealSource`** — only the game test calls it directly
  (`testmod/bonemeal`). `AzaleaBlockMixin`'s `@Redirect` handler keeps its
  `(ServerLevel, RandomSource, BlockPos)` prefix signature: Mixin allows truncating the enclosing
  method's trailing parameters, so no mixin change was needed. `SaplingBlock.advanceTree` is
  unchanged, so the mixins are not versioned.
- **Poplar saplings are new in 26.3** and are covered by `grower/v26_3` and the 26.3 resources.
- **`forBlock` may return `null`** for a sapling with no vanilla-tree mapping (a modded sapling,
  or a vanilla one from a newer MC version). `SaplingBlockMixin` falls through to the block's own
  grower in that case instead of returning `null` into `advanceTree`.
- **Grower names are namespaced** (`vanilla-trees:oak`, ...). The `TreeGrower` constructor puts each
  name into `TreeGrower`'s codec lookup map, so reusing vanilla's names would overwrite vanilla's own
  growers there once this mod's growers class-loaded.
- **Previously missing features (fixed)** — `fancy_oak` and `tall_mangrove` were referenced by the
  OAK/MANGROVE growers but never shipped, so those rolls (10% of oaks, 85% of mangroves) silently
  failed to grow. The unused `fancy_oak_bees` was dropped.

## Build (WSL2 / Windows filesystem)

Run via the Windows wrapper (`./gradlew` fails on WSL2). For task names **with spaces**, pass them
unwrapped — nesting quotes yields `Task '"Set' not found`:

```bash
cmd.exe /c gradlew.bat :26.3:compileJava
cmd.exe /c gradlew.bat "Set active project to 26.3"
cmd.exe /c gradlew.bat buildAndCollect   # builds every version into build/libs/<mod.version>/
```

- `:<version>:build` works and does **not** run the MC server (the gametests are not JUnit;
  `build`/`test` only run `src/test`'s JUnit, of which there are none).
- `genSources` for a version is the reliable way to verify mixin `@Redirect` targets
  (compilation does **not** validate `@At` target strings).

## Game tests (server-side, headless)

Server game tests live in `src/test/java/com/breadmoirai/vanillatrees/testmod/VanillaTreesGameTests.java`
(Fabric `@net.fabricmc.fabric.api.gametest.v1.GameTest` + vanilla `GameTestHelper`), registered via
the `fabric-gametest` entrypoint in `src/test/resources/fabric.mod.json`. They are **server-side and
headless** — they run on every version (no client/display needed), unlike the client gametests OCC/OCA
use. The gametest API comes transitively from `fabric-api` (not bundled in the fat jar). Both build
scripts wire a `gameTest` server run + `src/test` source set; `runGameTest` is enabled via the
`-Dfabric-api.gametest` property and writes `build/junit.xml`.

```bash
cmd.exe /c gradlew.bat "Set active project to 26.3"
cmd.exe /c gradlew.bat :26.3:runGameTest     # one version
cmd.exe /c gradlew.bat runGameTest --max-workers=4   # all versions (also run in CI before publish)
```

- These are headless servers, so they are far cheaper than client gametests — but keep
  `--max-workers=4` as the default cap anyway, matching OneClickCrafting/OneClickAnvil, whose real
  Minecraft clients hang permanently past about four in parallel.
- **26.x Windows JVM stack crash (fixed):** `build.unobf.gradle.kts` passes
  `-XX:+UnlockDiagnosticVMOptions -XX:+AlwaysPreTouchStacks` to every loom run. Without it, 10–50% of
  26.x client launches die in the first resource reload (`NTSTATUS 0xC0000005`, no `hs_err`) from a
  HotSpot-on-Windows stack-growth bug — not mod code. Do not remove the flag. Full write-up: `update`
  skill → `references/api-divergences.md` → "26.x client startup crash on Windows".

- **Tier 1** (`acaciaGrowsWithAlwaysGamerule`, `azaleaGrowsNextToDispenser`): the mixins fire and a
  vanilla tree grows via the gamerule / dispenser paths. Guards mixin-application across versions.
- **Tier 2** (`defaultGrowthUsesDatapackFeature`, `dispenserBypassesDatapackOverride`): a test datapack
  (`versions/<v>/src/test/resources/data/minecraft/worldgen/<registry>/acacia.json`) overrides the vanilla
  `minecraft:acacia` feature with a **gold** acacia (gold trunk and foliage). Untriggered growth produces gold (the
  override); dispenser-triggered growth produces a real acacia (the mod uses its own `vanilla-trees:acacia`,
  bypassing the override). This proves the mod's actual purpose.
- Tests force growth deterministically by setting `STAGE=1` and calling `SaplingBlock.advanceTree` /
  `AzaleaBlock.performBonemeal` (via `AzaleaBonemeal`) directly in a retry loop, then scanning the 8×8×8
  region for the expected log/gold block. Gamerules are set per-test via
  `VanillaTrees.GAME_RULES.setAlways` / `setDispenserForced`.

## Adding versions

Use the **`update`** skill for a newer MC version (becomes the new `vcsVersion`) and the
**`backport`** skill for an older one. Both read this file for the placeholders above.
