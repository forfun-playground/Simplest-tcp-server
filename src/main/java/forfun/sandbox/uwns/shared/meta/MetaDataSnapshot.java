package forfun.sandbox.uwns.shared.meta;

import java.util.LinkedList;
import java.util.List;

public class MetaDataSnapshot extends MetaData {

    public double radius;
    public List<Actor> targets = new LinkedList<>();
    public List<Agent> players = new LinkedList<>();
    public List<Agent> nonplayers = new LinkedList<>();

    public MetaDataSnapshot() {
    }

    public MetaDataSnapshot(
            double radius,
            List<Actor> targets,
            List<Agent> players,
            List<Agent> nonplayers
    ) {
        this.radius = radius;
        this.targets = targets;
        this.players = players;
        this.nonplayers = nonplayers;
    }

    public class Actor {

        public int uid;
        public boolean active;
        public Vector position = new Vector();

        public Actor(
                int uid,
                boolean active,
                Vector position
        ) {
            this.uid = uid;
            this.active = active;
            this.position = position;
        }
    }

    public class Agent extends Actor {

        public int tid;
        public Vector velocity = new Vector();

        public Agent(
                int uid,
                int tid,
                boolean active,
                Vector position,
                Vector velocity
        ) {
            super(uid, active, position);
            this.tid = tid;
            this.velocity = velocity;
        }
    }
}
