package cc.unilock.yeplib.api.event;

import cc.unilock.yeplib.YepLib;
import com.google.common.base.MoreObjects;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.List;

public class YepDeathEvent extends YepMessageEvent {
    private final Player player;
    private final String username;
    private final String displayname;
    private final String message;

    public YepDeathEvent(MinecraftChannelIdentifier type, List<String> params, ServerConnection source) {
        super(type, params, source);
        this.player = YepLib.getProxy().getPlayer(params.get(0)).orElse(null);
        this.username = params.get(0);
        this.displayname = params.get(1);
        this.message = params.get(2);
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getUsername() {
        return this.username;
    }

    public String getDisplayName() {
        return this.displayname;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("parameters", parameters)
                .add("source", source)
                .add("player", player)
                .add("username", username)
                .add("displayname", displayname)
                .add("message", message)
                .toString();
    }
}
