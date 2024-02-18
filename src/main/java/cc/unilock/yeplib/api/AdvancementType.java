package cc.unilock.yeplib.api;

public enum AdvancementType {
    DEFAULT("default"),
    CHALLENGE("challenge"),
    GOAL("goal"),
    TASK("task");

    private final String id;

    AdvancementType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
