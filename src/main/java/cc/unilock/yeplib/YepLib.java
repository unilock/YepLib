package cc.unilock.yeplib;

import cc.unilock.yeplib.api.event.YepAdvancementEvent;
import cc.unilock.yeplib.api.event.YepDeathEvent;
import cc.unilock.yeplib.api.event.YepMessageEvent;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Plugin(
        id = "yeplib",
        name = "YepLib",
        description = "Velocity plugin that makes it easier to receive and parse plugin messages from backend servers",
        version = Tags.VERSION,
        authors = {"unilock"}
)
public class YepLib {
    private static final String RECORD_SEPARATOR = "␞";
    private static final String UNIT_SEPARATOR = "␟";

    private static final MinecraftChannelIdentifier YEP_GENERIC = MinecraftChannelIdentifier.create("yep", "generic");
    private static final MinecraftChannelIdentifier YEP_ADVANCEMENT = MinecraftChannelIdentifier.create("yep", "advancement");
    private static final MinecraftChannelIdentifier YEP_DEATH = MinecraftChannelIdentifier.create("yep", "death");


    private final ProxyServer proxy;
    private final Logger logger;

    private static YepLib instance;

    @Inject
    public YepLib(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;

        instance = this;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        this.proxy.getChannelRegistrar().register(YEP_GENERIC);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(YEP_GENERIC)) return;
        if (!(event.getSource() instanceof ServerConnection source)) return;

        var data = new String(event.getData(), StandardCharsets.UTF_8);

        // type␞param1␟param2␟param3...
        var parts = data.split(RECORD_SEPARATOR);

        if (parts.length != 2) {
            logger.warn("Invalid Yep message: " + data);
            return;
        }

        var type = MinecraftChannelIdentifier.from(parts[0]);
        var parameters = Arrays.asList(parts[1].split(UNIT_SEPARATOR));

        this.proxy.getEventManager().fire(new YepMessageEvent(type, parameters, source));

        if (YEP_ADVANCEMENT.equals(type)) {
            this.proxy.getEventManager().fire(new YepAdvancementEvent(type, parameters, source));
        }
        if (YEP_DEATH.equals(type)) {
            this.proxy.getEventManager().fire(new YepDeathEvent(type, parameters, source));
        }
    }

    public static ProxyServer getProxy() {
        return instance.proxy;
    }
}
