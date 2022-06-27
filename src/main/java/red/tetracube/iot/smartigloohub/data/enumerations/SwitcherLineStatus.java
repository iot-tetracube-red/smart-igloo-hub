package red.tetracube.iot.smartigloohub.data.enumerations;

public enum SwitcherLineStatus {

    ONLINE("Online"),
    OFFLINE("OffLine");

    private final String status;

    SwitcherLineStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
