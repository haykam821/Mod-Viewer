# Mod Viewer

[![GitHub release](https://img.shields.io/github/release/haykam821/Mod-Viewer.svg?style=popout&label=github)](https://github.com/haykam821/Mod-Viewer/releases/latest)
[![CurseForge](https://img.shields.io/static/v1?style=popout&label=curseforge&message=project&color=6441A4)](https://www.curseforge.com/minecraft/mc-mods/mod-viewer)
[![Discord](https://img.shields.io/static/v1?style=popout&label=chat&message=discord&color=7289DA)](https://discord.gg/YtnXecuAwF)

Adds a server-side user interface for viewing loaded mods to Minecraft.

Mod Viewer requires the [Fabric modloader](https://fabricmc.net/use/) and [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api).

## Installation

1. Install [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) if it is not installed.
2. Download Mod Viewer from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/mod-viewer/files) or [GitHub](https://github.com/haykam821/Mod-Viewer/releases).
3. Place the downloaded file in your `mods` folder.

## Usage

When this mod is installed, permitted players will be able to use the `/modviewer` command to display the mod viewer user interface. This interface will show a paginated list of mods to select from. Information about the selected mod will be shown, including the mod's name, description, authors, and dependencies.

## Permissions

By default, the `/modviewer` command is available to level two operators. If a permission mod that supports permission keys is installed, then the `modviewer.command` permission key controls availability instead.

In addition, permission keys of the format `modviewer.view_mod.<id>`, where `<id>` is the ID of the mod, control the visibility of mods on an individual basis.
