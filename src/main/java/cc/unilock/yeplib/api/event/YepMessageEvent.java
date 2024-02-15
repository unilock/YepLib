package cc.unilock.yeplib.api.event;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.List;

public final class YepMessageEvent {
    private final MinecraftChannelIdentifier type;
    private final List<String> parameters;
    private final ServerConnection source;

    public YepMessageEvent(MinecraftChannelIdentifier type, List<String> params, ServerConnection source) {
        this.type = Preconditions.checkNotNull(type);
        this.parameters = Preconditions.checkNotNull(params);
        this.source = source;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("parameters", parameters)
                .add("source", source)
                .toString();
    }
}
