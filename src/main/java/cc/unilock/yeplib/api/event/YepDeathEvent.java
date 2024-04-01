package cc.unilock.yeplib.api.event;

import cc.unilock.yeplib.YepLib;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.List;

public class YepDeathEvent {
    protected final MinecraftChannelIdentifier type;
    protected final List<String> parameters;
    protected final ServerConnection source;
    private final Player player;
    private final String username;
    private final String displayname;
    private final String message;

    public YepDeathEvent(MinecraftChannelIdentifier type, List<String> params, ServerConnection source) {
        this.type = Preconditions.checkNotNull(type);
        this.parameters = Preconditions.checkNotNull(params);
        this.source = source;
        this.player = YepLib.getProxy().getPlayer(params.get(0)).orElse(null);
        this.username = params.get(0);
        this.displayname = params.get(1);
        this.message = params.get(2);
    }

    public MinecraftChannelIdentifier getType() {
        return this.type;
    }

    public List<String> getParameters() {
        return this.parameters;
    }

    public ServerConnection getSource() {
        return this.source;
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
