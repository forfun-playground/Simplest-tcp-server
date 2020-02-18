package forfun.sandbox.uwns.shared.meta;

public class MetaData {

    public class Vector {

        public double x;
        public double y;

        public Vector() {
            this(0D, 0D);
        }

        public Vector(
                double x,
                double y
        ) {
            this.x = x;
            this.y = y;
        }
    }
}
