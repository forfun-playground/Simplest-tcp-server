package forfun.sandbox.uwns.shared.pack.json;

import com.google.gson.annotations.SerializedName;

public class Packet {

    public enum MessageType {
        @SerializedName("0")
        EnterWorldResponse,
        @SerializedName("1")
        SnapshotResponse,
        @SerializedName("2")
        TargetPositionRequest
    }

    public MessageType type;
    public String message;
}
