# Clans API

The official developer API for the **Clans** Minecraft plugin (Paper / Folia, 1.21+).

This repository contains **only the public API** - the interfaces, events, and enums you
compile against. The implementation ships inside the Clans plugin itself and is provided
at runtime, so you never bundle this dependency into your jar.

[![](https://jitpack.io/v/Ravi-DevX/Clans-API.svg)](https://jitpack.io/#Ravi-DevX/Clans-API)

Current API contract version: **1.2.0** (`ApiVersion.CURRENT`).

---

## Adding the dependency

The API is distributed through [JitPack](https://jitpack.io). Add the repository and the
dependency to your build. Always use `compileOnly` (Gradle) / `provided` (Maven) - the
classes are supplied by the Clans plugin at runtime, so bundling them causes classloader
conflicts.

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Ravi-DevX:Clans-API:1.2.0")
}
```

### Gradle (Groovy DSL)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.Ravi-DevX:Clans-API:1.2.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Ravi-DevX</groupId>
    <artifactId>Clans-API</artifactId>
    <version>1.2.0</version>
    <scope>provided</scope>
</dependency>
```

> Replace `1.2.0` with any released tag, or use a commit hash / `main-SNAPSHOT` for the
> latest build.

---

## Depending on the Clans plugin

So that Clans loads **before** your plugin and the API is ready, declare it in your
`plugin.yml`:

```yaml
name: MyPlugin
version: 1.0.0
main: com.example.MyPlugin
api-version: "1.21"

depend: [Clans]          # hard dependency - your plugin will not load without Clans
# or
softdepend: [Clans]      # optional - guard your calls with ClansProvider.isAvailable()
```

Use `depend` when your plugin cannot function without Clans. Use `softdepend` when Clans
is optional; in that case always guard access with `ClansProvider.isAvailable()` before
calling `ClansAPI.get()`.

---

## Obtaining the API

`ClansAPI.get()` returns the instance registered by the running Clans plugin. With a hard
dependency the provider is available when your addon enables, but licensed database
startup may still be running. Check `api.isReady()` before gameplay calls or listen for
`ClansReadyEvent` and finish initialization there.

```java
import com.shyamstudio.clans.api.ClansAPI;
import com.shyamstudio.clans.api.ClansProvider;
import com.shyamstudio.clans.api.model.ClanProfile;

if (ClansProvider.isAvailable()) {
    ClansAPI api = ClansAPI.get();
    if (api.isReady()) {
        ClanProfile clan = api.getClanByPlayer(player.getUniqueId());
        if (clan != null) {
            getLogger().info(player.getName() + " is in clan " + clan.getTag());
        }
    }
}
```

With `depend: [Clans]` provider load order is guaranteed, so the `isAvailable()` guard is
optional. Readiness is separate and must still be observed. With `softdepend`, both the
provider guard and readiness handling are required.

---

## Reading clan data

```java
ClanProfile clan = api.getClanByTag("WOLVES");
if (clan != null) {
    String name        = clan.getDisplayName();
    int members        = clan.getAllMembers().size();
    double balance     = clan.getBalance();
    boolean open       = clan.isOpen();
    boolean friendlyFF = clan.isFriendlyFire();

    // Members and their roles
    clan.getAllMembers().forEach(member ->
        System.out.println(member.getUsername() + " - " + member.getRole().getName()));

    // Allies
    clan.getAllyClans().forEach(ally ->
        System.out.println("Allied with " + ally.getTag()));
}
```

Collections returned by the read model (member lists, ally lists, permission sets, the
online-user map) should be treated as read-only. An implementation may hand back a live
view, so copy the collection when you need a stable snapshot.

## Reading player stats

```java
import com.shyamstudio.clans.api.model.UserProfile;
import com.shyamstudio.clans.api.option.ProfileMetric;

UserProfile user = api.getUser(player.getUniqueId());
if (user != null) {
    int kills  = user.getStatistic(ProfileMetric.KILLS);
    int deaths = user.getStatistic(ProfileMetric.DEATHS);
}
```

---

## Mutating clans

The model interfaces keep direct setters (for example `ClanProfile#setOpen` or
`UserProfile#setStatistic`) for API 1.0.0 binary compatibility, but they are
`@Deprecated(since = "1.1.0")` because they bypass one or more validation, permission,
event, limit, feature, or persistence rules.

For any change, prefer the validated services reached from `ClansAPI`:

```java
import com.shyamstudio.clans.api.service.ClanService;
import com.shyamstudio.clans.api.result.OperationResult;

ClanService clans = api.getClanService();
OperationResult result = clans.setOpen(actor, true);
if (!result.isSuccess()) {
    actor.sendMessage("Could not open the clan: " + result.getStatus());
}
```

Every service call returns an `OperationResult` carrying an `OperationStatus` and an
optional detail map (for example `limit` or `remainingSeconds`). See the
[Services](https://github.com/Ravi-DevX/Clans-API/wiki/Services) and
[Models-and-Threading](https://github.com/Ravi-DevX/Clans-API/wiki/Models-and-Threading) wiki pages.

---

## Addon extensions (1.2.0)

API 1.2.0 adds an owner-scoped extension registry for supported `/clan` subcommands and
Clans menu actions. Register through `ClansAPI#getExtensionService()`; do not intercept
`PlayerCommandPreprocessEvent` or modify Bukkit's command map.

```java
import com.shyamstudio.clans.api.ClansAPI;
import com.shyamstudio.clans.api.extension.ClanCommandContext;
import com.shyamstudio.clans.api.extension.ClanSubcommand;
import com.shyamstudio.clans.api.extension.ExtensionRegistration;
import com.shyamstudio.clans.api.result.OperationResult;
import com.shyamstudio.clans.api.service.ClansExtensionService;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class MyAddon extends JavaPlugin {
    private final List<ExtensionRegistration> registrations = new ArrayList<>();

    @Override
    public void onEnable() {
        ClansAPI api = ClansAPI.get();
        ClansExtensionService extensions = api.getExtensionService();

        registrations.add(extensions.registerSubcommand(this, new ClanSubcommand() {
            @Override public String name() { return "panel"; }
            @Override public Set<String> aliases() { return Set.of("info"); }
            @Override public String permission() { return "myaddon.panel"; }

            @Override
            public OperationResult execute(ClanCommandContext context) {
                context.player().sendMessage("Opening the addon panel for /" + context.label());
                return OperationResult.success();
            }

            @Override
            public List<String> suggest(ClanCommandContext context) {
                // Cache-only: never query SQL, Redis, HTTP, or the filesystem here.
                return List.of("status", "list", "help");
            }
        }));

        registrations.add(extensions.registerMenuAction(this, "OPEN_ADDON_PANEL", context -> {
            context.player().sendMessage("Opening the addon panel for clan " + context.clanId());
            return OperationResult.success();
        }));
    }

    @Override
    public void onDisable() {
        registrations.forEach(ExtensionRegistration::unregister);
        registrations.clear();
    }
}
```

Registrations belong to the addon `Plugin` passed at registration time. Clans removes
them automatically when that plugin disables; keeping the handles and unregistering them
yourself is still useful for partial startup rollback or a feature-level shutdown.
`ExtensionRegistration#unregister()` is idempotent.

The extension registry is available during Clans bootstrap, so registration itself does
not require `isReady()`. Any addon initialization that queries clans or calls gameplay
services must still wait for `ClansReadyEvent`; Clans does not dispatch `/clan` or menu
actions before its runtime is ready.

`ClanSubcommand#permission()` is a Bukkit permission checked by Clans before dispatch.
Clan-role authorization is separate: addons should resolve the current `MemberProfile`
and check their own feature-specific role or permission rule again at the operation
boundary. Suggestions must be non-blocking and derived from memory/cache only.

An action becomes available only when a Clans menu references that action ID. Registering
an arbitrary action does not inject a new button; the menu configuration must already
reference and dispatch the ID.

Addon menus can return a player through the supported reverse-navigation API instead of
dispatching `/clan` or reaching into Clans internals:

```java
boolean accepted = extensions.openClanMenu(player);
```

The method follows the normal `/clan` destination: clan members open their clan details
and players without a clan open the clan list. Clans schedules the inventory operation on
the player's owning thread, so `true` means accepted for scheduling. It returns `false`
when Clans is not ready, the player is offline or lacks `clans.use`, or an older runtime
does not implement reverse navigation. The interface method has a binary-compatible
default implementation, allowing addons compiled against this 1.2 contract to detect
unsupported runtimes safely.

See [Extensions](https://github.com/Ravi-DevX/Clans-API/wiki/Extensions) for lifecycle,
validation, context fields, privilege checks, and complete examples.

---

## Chat-moderation integration

Clans routes clan and ally chat through its own channels, so a moderation plugin should
integrate with the Clans events rather than the raw platform chat event.

`ClanChatMessageEvent` and `AllyChatMessageEvent` are both `Cancellable`. The plain-text
message is mutable through `setMessage(String)` (blank values are rejected), and the
recipient set returned by `getRecipients()` is mutable, so you can filter or redirect
delivery. `ClanChatModeChangeEvent` reports when a player switches between the PUBLIC,
CLAN, and ALLY channels.

```java
import com.shyamstudio.clans.api.event.ClanChatMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatModerationListener implements Listener {

    @EventHandler
    public void onClanChat(ClanChatMessageEvent event) {
        // The event may be async; check before touching thread-confined Bukkit state.
        String cleaned = filter(event.getMessage());
        if (cleaned.isBlank()) {
            event.setCancelled(true);
            return;
        }
        event.setMessage(cleaned);
    }
}
```

**Ordering caveat.** When a player has a private channel or prompt active, Clans cancels
the underlying platform chat event and delivers the message through its own channel. A
moderation plugin that only inspects the raw chat event will therefore miss clan and ally
traffic. Read the Clans channel with `ClansAPI.getChatChannel(uuid)` (or
`ChatService.getChannel(uuid)`), or key your logic off `ClanChatMessageEvent`,
`AllyChatMessageEvent`, and `ClanChatModeChangeEvent`, instead of relying on the platform
chat event.

See the [Chat API](https://github.com/Ravi-DevX/Clans-API/wiki/Chat-API) wiki page for the full flow.

---

## Listening to events

Most clan actions fire a Bukkit event. Many are `Cancellable`, but not all: for example
`ClanScoreChangeEvent` and `ClanFeatureStateChangeEvent` are notification-only and do not
implement `Cancellable`. Always check the specific event before assuming you can cancel
it. See the [Events](https://github.com/Ravi-DevX/Clans-API/wiki/Events) wiki page for the complete list and which
fields are mutable.

```java
import com.shyamstudio.clans.api.event.ClanCreateEvent;
import com.shyamstudio.clans.api.event.PlayerJoinClanEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClanListener implements Listener {

    @EventHandler
    public void onClanCreate(ClanCreateEvent event) {
        if (event.getTag().equalsIgnoreCase("staff")) {
            event.setCancelled(true); // block reserved tags
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinClanEvent event) {
        getLogger().info(event.getUser().getName()
                + " joined clan " + event.getClan().getTag());
    }
}
```

Events may fire asynchronously on Folia and Paper. Call `isAsynchronous()` before
touching thread-confined Bukkit state, and call services from the main / owning thread.

---

## API surface

### Entry points
| Class | Purpose |
|-------|---------|
| `ClansAPI` | Main accessor - `ClansAPI.get()`; also exposes the public services |
| `ClansProvider` | Holds the runtime instance - `isAvailable()` |
| `ApiVersion` | Exposes `CURRENT` (the API contract version, `1.2.0`) |

### Services (`com.shyamstudio.clans.api.service`)
| Interface | Purpose |
|-----------|---------|
| `ClanService` | Validated clan mutations (membership, alliances, settings, bank, ...) |
| `ChatService` | Read and control clan / ally chat channels and delivery |
| `PendingRequestService` | Read-only snapshots of pending invites and requests |
| `ClansExtensionService` | Owner-scoped `/clan` subcommands and menu actions for addons |

### Extensions (`com.shyamstudio.clans.api.extension`)
| Type | Purpose |
|------|---------|
| `ClanSubcommand` | Addon-owned `/clan <name>` execution and non-blocking suggestions |
| `ClanCommandContext` | Immutable player, label, subcommand, and argument input |
| `ClanMenuAction` | Handler for a registered action referenced by a Clans menu |
| `ClanMenuActionContext` | Immutable player, menu id, optional clan id, and attributes |
| `ExtensionRegistration` | Idempotent registration handle with `unregister()` / `close()` |

### Models (`com.shyamstudio.clans.api.model`)
| Type | Represents |
|------|------------|
| `ClanProfile` | A clan: tag, name, members, allies, home, spawn, bank, score, rank, settings |
| `UserProfile` | A player: clan membership, statistics, perk limits |
| `MemberProfile` | A clan member: uuid, username, role, permissions |
| `LeaderProfile` | The clan owner (extends `MemberProfile`) |
| `RoleProfile` | A clan role: name, priority, permissions, symbol, color |
| `ClanInvite` | Immutable snapshot of an invitation to a player |
| `ClanJoinRequest` | Immutable snapshot of a player's request to join a clan |
| `ClanAllianceRequest` | Immutable snapshot of a pending alliance request |

### Options (`com.shyamstudio.clans.api.option`)
| Enum | Values |
|------|--------|
| `ClanPrivilege` | Per-role permissions, including `USE_CLAN_SHIELD` |
| `ProfileMetric` | Tracked stats (KILLS, DEATHS, DIAMONDS_MINED, SCRAP_SMELTED, BLOCKS_PLACED, BLOCKS_BROKEN, MOBS_KILLED) |
| `ChatChannel` | PUBLIC, CLAN, ALLY |
| `ChatMessageSource` | TOGGLED_MODE, COMMAND, SHORTCUT, API |
| `ChatModeChangeCause` | COMMAND, MENU, API, FEATURE_DISABLED, CLAN_LEFT, PLUGIN_RELOAD |
| `ClanFeature` | HOME, SPAWN, CHEST, BANK, STATISTICS, SCORING, LEADERBOARD, CLAN_CHAT, ALLY_CHAT, CHAT_SHORTCUTS, ANTI_ALT, CLAN_SHIELD |
| `ClanShieldDeliveryMode` | APPLY_TO_HELD_SHIELD, GIVE_NEW_SHIELD |
| `PendingAction` | SEND, CANCEL, ACCEPT, DENY, EXPIRE |
| `StatisticChangeCause` | GAMEPLAY, ADMIN, API |

### Results (`com.shyamstudio.clans.api.result`)
| Type | Purpose |
|------|---------|
| `OperationResult` | Outcome of a service call: status, `isSuccess()`, and a detail map |
| `OperationStatus` | Stable outcome codes (SUCCESS, CANCELLED, NOT_ALLOWED, LIMIT_REACHED, ...) |

### Events (`com.shyamstudio.clans.api.event`)
See [Events](https://github.com/Ravi-DevX/Clans-API/wiki/Events) for the grouped list and cancellable / notification-only
markers. `ClanShieldApplyEvent` allows integrations to cancel delivery or replace the prepared shield before payment and inventory changes.

---

## Documentation

Full documentation lives in the [wiki](https://github.com/Ravi-DevX/Clans-API/wiki):

- [Home](https://github.com/Ravi-DevX/Clans-API/wiki/Home)
- [Getting Started](https://github.com/Ravi-DevX/Clans-API/wiki/Getting-Started)
- [Chat API](https://github.com/Ravi-DevX/Clans-API/wiki/Chat-API)
- [Events](https://github.com/Ravi-DevX/Clans-API/wiki/Events)
- [Services](https://github.com/Ravi-DevX/Clans-API/wiki/Services)
- [Extensions](https://github.com/Ravi-DevX/Clans-API/wiki/Extensions)
- [Models and Threading](https://github.com/Ravi-DevX/Clans-API/wiki/Models-and-Threading)
- [Migration 1.0.0 to 1.1.0](https://github.com/Ravi-DevX/Clans-API/wiki/Migration-1.0.0-to-1.1.0)
- [Migration 1.1.0 to 1.2.0](https://github.com/Ravi-DevX/Clans-API/wiki/Migration-1.1.0-to-1.2.0)

---

## Notes

- **Roles use inverted priority** - a *lower* priority number means a *higher* rank
  (Leader = -1, Member = larger numbers). Use `RoleProfile#isHigherThan` rather than
  comparing numbers by hand.
- `ClanProfile#getClanId` is a stable, immutable clan identifier. It does **not**
  necessarily equal the owner's UUID and is preserved across renames and ownership
  transfers. A disbanded clan no longer exists; a later clan must be treated as a
  new identity.
- The API is read-oriented. Prefer the services from `ClansAPI` over the deprecated model
  setters so validation, permission, event, and persistence rules are applied.

## Versioning

The API follows semantic versioning. Added methods -> minor bump; changed / removed
methods -> major bump. Pin a specific tag in production. The service getters added in
1.1.0 and the extension getter added in 1.2.0 are `default` methods,
preserving binary compatibility with older implementations. A 1.2.0 consumer must still
feature-detect newer services when it intentionally supports an older Clans runtime.

## License

Released under the MIT License - see [LICENSE](LICENSE).
