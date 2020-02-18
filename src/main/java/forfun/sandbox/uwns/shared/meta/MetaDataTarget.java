package forfun.sandbox.uwns.shared.meta;

public class MetaDataTarget extends MetaData {

    public Vector position = new Vector();

    public MetaDataTarget(
            float x,
            float y
    ) {
        position.x = x;
        position.y = y;
    }
}
