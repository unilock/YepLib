# YepLib

YepLib is a Velocity plugin that makes it easier to receive and parse plugin messages from backend servers.

## Usage

In your `build.gradle`:

```groovy
repositories {
    maven { url = "https://jitpack.io" }
}

dependecies {
    implementation "com.github.unilock:yeplib:${project.yeplib_version}"
}
```

In a class registered to Velocity's event manager (e.g. your main plugin class):

```java
// The first part of any Yep message is the identifier.
// This is intended to differentiate Yep messages from one another,
// such that different plugins can listen to the same event
// for different purposes
private static final MinecraftChannelIdentifier MY_MESSAGE_TYPE = MinecraftChannelIdentifier.create("plugin_name", "my_message_type"); 

@Subscribe
public void onYepMessage(YepMessageEvent event) {
    // Verify that the message is of the type you're listening for.
    // There is, of course, nothing stopping another plugin from
    // listening for the same identifier. This may change in the future?
    if (!event.getType().equals(MY_MESSAGE_TYPE)) return;

    // Get the parameters of the message
    List<String> parameters = event.getParameters();

    // Get the source (backend server) of the message
    ServerConnection source = event.getSource();

    // A message can have an undefined amount of parameters,
    // so make sure you're receiving as many as you're expecting
    if (parameters.size() != 2) return;

    // Let's say the first parameter is a player's username,
    // and the second parameter is their chat message
    // (ignore the fact that Velocity already parses chat packets)
    String username = parameters.get(0);
    String message = parameters.get(1);

    // Make sure the specified player is connected to the proxy...
    Optional<Player> optional = this.proxy.getPlayer(username);
    if (optional.isEmpty()) {
        this.logger.error("Player cannot be empty!");
        return;
    }
    Player player = optional.get();

    // Now you can do whatever you want, like send the chat message
    // back to the player in all uppercase I guess?
    player.sendMessage(Component.text(message.toUpperCase(Locale.ROOT)));
}
```

In the above example, the plugin message would be in the format of `plugin_name:my_message_type␞username␟message`.

Note the usage of `␞` and `␟`:  
`␞` is used to separate the type from the parameters. There should only be one in any plugin message sent over the Yep channel.  
`␟` is used to separate the different parameters. You can use as many as you want, for as many parameters as you want.

**All plugin messages should be sent over the channel `yep:generic`.**

Fabric / Forge:
```java
private static final Identifier YEP_GENERIC = new Identifier("yep", "generic");
private static final Identifier MY_MESSAGE_TYPE = new Identifier("plugin_name", "my_message_type");

public static void sendMessage(ServerPlayerEntity player, String message) {
    String username = player.getName().getString();

    // Format the message
    String msg = String.format("%s␞%s␟%s", MY_MESSAGE_TYPE.toString(), username, message);
    // Turn it into a packet payload
    PacketByteBuf payload = new PacketByteBuf(Unpooled.wrappedBuffer(msg.getBytes(StandardCharsets.UTF_8)));

    // Send the plugin message.
    // Fabric:
    ServerPlayNetworking.send(player, YEP_GENERIC, payload);
    // Forge:
    player.networkHandler.sendPacket(new CustomPayloadS2CPacket(YEP_GENERIC, payload));
}
```

Paper:
```
TBD
```