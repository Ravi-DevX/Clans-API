package com.shyamstudio.clans.api;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.UserProfile;
import com.shyamstudio.clans.api.extension.ClanMenuAction;
import com.shyamstudio.clans.api.extension.ClanSubcommand;
import com.shyamstudio.clans.api.extension.ExtensionRegistration;
import com.shyamstudio.clans.api.service.ClansExtensionService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClansApiBackwardCompatibilityTest {
    @Test
    void implementationWithOnlyLegacyAbstractMethodsStillLinks() throws Exception {
        ClansAPI legacy = new LegacyImplementation();
        assertEquals(List.of(), legacy.getClans());

        for (String name : List.of("getChatService", "getPendingRequestService",
                "getClanService", "getExtensionService")) {
            Method method = ClansAPI.class.getMethod(name);
            assertTrue(method.isDefault(), name + " must remain binary-safe");
        }
    }

    @Test
    void legacyExtensionImplementationInheritsSafeMenuDefault() throws Exception {
        ClansExtensionService legacy = new LegacyExtensionImplementation();
        Player player = (Player) java.lang.reflect.Proxy.newProxyInstance(
                Player.class.getClassLoader(), new Class<?>[]{Player.class},
                (proxy, method, arguments) -> method.getReturnType() == boolean.class ? false : null);

        assertTrue(ClansExtensionService.class.getMethod("openClanMenu", Player.class).isDefault());
        assertFalse(legacy.openClanMenu(player));
    }

    private static final class LegacyImplementation implements ClansAPI {
        @Override
        public ClanProfile getClan(UUID clanId) {
            return null;
        }

        @Override
        public ClanProfile getClanByPlayer(UUID playerUuid) {
            return null;
        }

        @Override
        public ClanProfile getClanByTag(String tag) {
            return null;
        }

        @Override
        public ClanProfile getClanByName(String name) {
            return null;
        }

        @Override
        public UserProfile getUser(UUID playerUuid) {
            return null;
        }

        @Override
        public Collection<ClanProfile> getClans() {
            return List.of();
        }

        @Override
        public Map<UUID, UserProfile> getOnlineUsers() {
            return Map.of();
        }
    }

    private static final class LegacyExtensionImplementation implements ClansExtensionService {
        @Override
        public ExtensionRegistration registerSubcommand(Plugin owner, ClanSubcommand subcommand) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ExtensionRegistration registerMenuAction(Plugin owner, String actionId,
                                                         ClanMenuAction action) {
            throw new UnsupportedOperationException();
        }
    }
}
