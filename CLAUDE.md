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
  - `VanillaTrees.java` — `ModInitializer`; registers the two boolean gamerules and exposes
    version-agnostic `isAlways(ServerLevel)` / `isDispenserForced(ServerLevel)` helpers that
    hide the 1.21.11 GameRules API rework from the mixins.
  - `VanillaTreeConfiguredFeatures.java` — `ResourceKey<ConfiguredFeature<?,?>>` constants for
    the bundled `data/vanilla-trees/worldgen/configured_feature/*.json`.
  - `VanillaTreeSaplingGenerators.java` — `TreeGrower` instances mirroring vanilla, plus a
    `Block -> TreeGrower` map.
  - `mixin/SaplingBlockMixin.java` — `@Redirect` on `SaplingBlock.advanceTree` field access of
    `treeGrower`; reads the default grower through `SaplingBlockAccessor`.
  - `mixin/SaplingBlockAccessor.java` — `@Accessor("treeGrower")` interface exposing the
    `SaplingBlock.treeGrower` field (replaces the former access widener).
  - `mixin/AzaleaBlockMixin.java` — `@Redirect` on `AzaleaBlock.performBonemeal` static field
    access of `TreeGrower.AZALEA` (`TreeGrower.AZALEA` is public — no accessor needed).
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
- **26.x line** (26.1, 26.1.1, 26.1.2, 26.2 — `26.2` is the `vcsVersion`; shared `src/` holds its
  code): un-obfuscated / JDK-25 toolchain. Registered in `settings.gradle.kts` via
  `versions("26.2", "26.1.2", "26.1.1", "26.1").buildscript("build.unobf.gradle.kts")` (plain
  `fabric-loom`, no Mojang mappings, `restoreUnnamedVars` on switch, `jar` not `remapJar`).
  The 26.1.x patches are API-identical to 26.1, so there is no Java source divergence between them.

### API divergences handled

- **1.21.11 GameRules rework** — `net.minecraft.world.GameRules.Key<BooleanValue>` /
  Fabric `GameRuleRegistry.register(String, Category, GameRuleFactory.createBooleanRule(...))`
  became `net.minecraft.world.level.gamerules.GameRule<Boolean>` /
  Fabric `GameRuleBuilder.forBoolean(b).category(GameRuleCategory.MISC).buildAndRegister(Identifier)`,
  and reads went from `getGameRules().getBoolean(key)` to `getGameRules().get(key)`. Handled by an
  inline `//? if >=1.21.11 { ... } else { ... }` block in `VanillaTrees.java` (mixins call the
  version-agnostic helpers, so they need no conditions). 26.x takes the `>=1.21.11` branch.
  Note: the gamerule IDs are now namespaced — `vanilla-trees:do_vanilla_tree_growth_always` /
  `vanilla-trees:do_dispensers_force_vanilla_tree_growth` (snake_case) on 1.21.11+.
- **1.21.11 `ResourceLocation` → `Identifier`** (`net.minecraft.resources`, persists into 26.x) —
  handled by a swap in `stonecutter-swaps.gradle.kts`: shared `src/` holds the new name
  `Identifier`; the bidirectional swap reverses it to `ResourceLocation` when `current < 1.21.11`.
- No `vXX_Y` versioned packages yet — flat layout. The mixin targets
  (`SaplingBlock.advanceTree`/`treeGrower`, `AzaleaBlock.performBonemeal`, `TreeGrower.AZALEA`)
  are identical across all supported versions.
- **`tree` feature config `below_trunk_provider`** — the 26.x `TreeConfiguration` codec made
  `below_trunk_provider` a **required** field (it replaces the old 1.21.x `dirt_provider` +
  `force_dirt`). Every bundled `worldgen/configured_feature/*.json` (18 in `src/main`, plus the
  test override in `src/test`) now carries a `below_trunk_provider` (a `rule_based_state_provider`
  placing the tree's dirt block where the `minecraft:cannot_replace_below_tree_trunk` tag does not
  forbid it), mirroring vanilla 26.x. The old `dirt_provider`/`force_dirt` keys are kept too:
  MC codecs ignore unknown map keys, so a single shared JSON parses on **all** versions — 1.21.x
  reads `dirt_provider` (ignores `below_trunk_provider`), 26.x reads `below_trunk_provider`
  (ignores the other two). This was also required for the 26.1.x gametests, which the old JSONs
  silently failed.

## Build (WSL2 / Windows filesystem)

Run via the Windows wrapper (`./gradlew` fails on WSL2). For task names **with spaces**, pass them
unwrapped — nesting quotes yields `Task '"Set' not found`:

```bash
cmd.exe /c gradlew.bat :26.1.2:compileJava
cmd.exe /c gradlew.bat "Set active project to 26.1.2"
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
cmd.exe /c gradlew.bat "Set active project to 26.1.2"
cmd.exe /c gradlew.bat :26.1.2:runGameTest   # one version
cmd.exe /c gradlew.bat runGameTest           # all versions (also run in CI before publish)
```

- **Tier 1** (`acaciaGrowsWithAlwaysGamerule`, `azaleaGrowsNextToDispenser`): the mixins fire and a
  vanilla tree grows via the gamerule / dispenser paths. Guards mixin-application across versions.
- **Tier 2** (`defaultGrowthUsesDatapackFeature`, `dispenserBypassesDatapackOverride`): a test datapack
  (`src/test/resources/data/minecraft/worldgen/configured_feature/acacia.json`) overrides the vanilla
  `minecraft:acacia` feature with a distinctive **gold** tree. Untriggered growth produces gold (the
  override); dispenser-triggered growth produces a real acacia (the mod uses its own `vanilla-trees:acacia`,
  bypassing the override). This proves the mod's actual purpose.
- Tests force growth deterministically by setting `STAGE=1` and calling `SaplingBlock.advanceTree` /
  `AzaleaBlock.performBonemeal` directly in a retry loop, then scanning the 8×8×8 region for the
  expected log/gold block. Gamerules are set per-test via `VanillaTrees.setAlways` / `setDispenserForced`
  (version-conditional, since the GameRules API differs on 1.21.11+).

## Adding versions

Use the **`update`** skill for a newer MC version (becomes the new `vcsVersion`) and the
**`backport`** skill for an older one. Both read this file for the placeholders above.
