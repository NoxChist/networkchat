package server;

public enum ServerCommand {
    STOP;

    @Override
    public String toString() {
        return String.valueOf(name().hashCode()) + "Command#" + name();
    }
}
