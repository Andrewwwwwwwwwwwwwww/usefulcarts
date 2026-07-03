# CurseForge release checklist — UsefulCarts

Everything in this folder is what you need to publish. You do the actual uploading in the CurseForge
author dashboard (https://authors.curseforge.com).

## Files in this folder
| File | Use |
|---|---|
| `usefulcarts-26.2.0+mc26.2.jar` | Main release file (tag Minecraft **26.2**) |
| `usefulcarts-26.1.2+mc26.1.2.jar` | Additional file (tag Minecraft **26.1.2**) |
| `icon.png` | Project logo (128×128 — see note below) |
| `images/` | Header, demo gifs, recipe/screenshot images for the description |
| `DESCRIPTION.md` | Paste into the description editor; insert images at the `[[IMAGE: …]]` markers |
| `LICENSE` | MIT license (already bundled inside the jars too) |

## 1. Create the project
- **Name:** **UsefulCarts** (this is distinct from the original, so no name clash).
- **Summary:** e.g. *"Horse-drawn and hand-pulled carts for travel, hauling, and farming — a Fabric port
  of NiftyCarts to Minecraft 26.2 / 26.1.2."*
- **Category:** likely **Adventure & RPG** and/or **Miscellaneous** — pick 1–3 that fit.
- **Mod loader:** Fabric.
- **License:** **MIT**.

## 2. Upload the files
- `usefulcarts-26.2.0+mc26.2.jar` → Release type **Release** → Minecraft **26.2**, loader **Fabric**.
- `usefulcarts-26.1.2+mc26.1.2.jar` → Minecraft **26.1.2**, loader **Fabric**.
- **Environment:** Client **and** Server.

## 3. Relations (dependencies) — add on each file
- **Required:** `Fabric API`
- **Required:** `Forge Config API Port`
- **Optional:** `Sodium`

## 4. Logo & description
- Set `icon.png` as the project logo. ⚠️ It's **128×128**; CurseForge recommends **≥400×400** square, so
  it may look soft — upscale or remake it if you want it crisp (accepted as-is otherwise).
- Paste `DESCRIPTION.md`; at each `[[IMAGE: images/xyz]]` marker use the editor's image button to upload
  that file from `images/`. **Do not hot-link** the Modrinth CDN URLs.

## 5. Links (project settings)
- **Source:** https://github.com/jmb05/nifty-carts (the original — or your own fork if you publish one).
- **Issues:** wherever you'll take port bug reports.

## 6. Changelog (per file)
> Port of NiftyCarts to Minecraft 26.2 / 26.1.2 (Fabric). Same content, recipes, and behavior as the
> original, rebranded as UsefulCarts. Requires Fabric API + Forge Config API Port.

## Naming note (already handled)
The mod is fully rebranded to **UsefulCarts** — display name, jar filename, creative-tab title, keybind
category, and every in-game item/entity/recipe ID (`usefulcarts:…`). The one thing left unchanged is the
internal **Java package** `net.jmb19905.niftycarts`, which is invisible in-game (only shows if someone
decompiles the jar) and is genuinely the original author's code namespace — renaming it adds risk for no
player-facing benefit. Say the word if you want that changed too.

## ⚠️ Attribution & etiquette (important)
Publishing this port is allowed under NiftyCarts' **MIT license**, but:
1. **Keep the credit** — the description credits jmb05 / MennoMax and links the original. Don't remove it.
2. **Keep the LICENSE** with the original copyright (bundled in the jar and this folder). ✅
3. **It's clearly marked as an unofficial port** (name + description). ✅
4. **Check the original author's wishes** — MIT permits reuploads, but some authors ask that ports not be
   posted to CurseForge. Skim jmb05's pages; if there's such a note, consider messaging them first or
   publishing only via GitHub / your modpack.
5. **The gifs/icon are the original author's artwork.** Reusing them for a credited port is generally
   accepted; if you want to be fully in the clear on visuals, record your own gifs and make your own logo.

## Same steps for your OWN mods
vanillaskills / holograms / spawnmanager / thp / villagershop / serverstatusdiscord: identical process
minus the port caveats — upload each repo's `curseforge-upload/` jar, loader Fabric + the MC version, add
Fabric API as a required relation, set logo + description. Those are your own work; no third-party
attribution needed.
