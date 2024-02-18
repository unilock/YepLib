package cc.unilock.yeplib.api.event;

import cc.unilock.yeplib.YepLib;
import cc.unilock.yeplib.api.AdvancementType;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.List;

public class YepAdvancementEvent {
    protected final MinecraftChannelIdentifier type;
    protected final List<String> parameters;
    protected final ServerConnection source;
    private final Player player;
    private final String username;
    private final String displayname;
    private final AdvancementType advType;
    private final String title;
    private final String description;

    public YepAdvancementEvent(MinecraftChannelIdentifier type, List<String> params, ServerConnection source) {
        this.type = Preconditions.checkNotNull(type);
        this.parameters = Preconditions.checkNotNull(params);
        this.source = source;
        this.player = YepLib.getProxy().getPlayer(params.get(0)).orElse(null);
        this.username = params.get(0);
        this.displayname = params.get(1);
        this.advType = AdvancementType.valueOf(params.get(2));
        this.title = params.get(3);
        this.description = params.get(4);
    }

    public AdvancementType getAdvType() {
        return this.advType;
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

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
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
                .add("advType", advType)
                .add("title", title)
                .add("description", description)
                .toString();
    }
}
