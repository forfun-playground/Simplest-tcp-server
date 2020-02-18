package forfun.sandbox.uwns.bothub.bot;

import forfun.sandbox.uwns.shared.math.Mathext;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.service.ExecuteMaster;
import forfun.sandbox.uwns.shared.service.SimpleTaskBase;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import org.dyn4j.geometry.Vector2;

@Slf4j
public class BotImpl extends SimpleTaskBase implements Bot {

    private final Connector connector;
    private final ExecuteMaster executeMaster;
    private ScheduledFuture<Bot> scheduledFutureTask;
    private volatile double arenaRadius;
    private volatile int uid;
    private volatile int targetUid;
    private volatile Vector2 targetPosition;
    private volatile Vector2 position;

    public BotImpl(
            Connector connector,
            ExecuteMaster executeMaster
    ) {
        this.connector = connector;
        this.executeMaster = executeMaster;
    }

    @Override
    public void onConnect() {
    }

    @Override
    public void acceptMessage(MetaData metadata) {
        if (metadata instanceof MetaDataEnterWorld) {
            uid = ((MetaDataEnterWorld) metadata).uid;
            scheduledFutureTask = executeMaster.executeScheduledTask(this, 500, 1000L / 10);
        } else if (metadata instanceof MetaDataSnapshot) {
            arenaRadius = ((MetaDataSnapshot) metadata).radius;
            extractSelfAgentData(((MetaDataSnapshot) metadata).players);
            extractSelfTargetData(((MetaDataSnapshot) metadata).targets);
        } else {
            log.debug("Unknown metadata came in...");
        }
    }

    private void extractSelfAgentData(List<MetaDataSnapshot.Agent> agents) {
        int self = uid;
        for (MetaDataSnapshot.Agent agent : agents) {
            if (agent.uid == self) {
                targetUid = agent.tid;
                position = new Vector2(agent.position.x, agent.position.y);
            }
        }
    }

    private void extractSelfTargetData(List<MetaDataSnapshot.Actor> actors) {
        for (MetaDataSnapshot.Actor actor : actors) {
            if (actor.uid == targetUid) {
                targetPosition = new Vector2(actor.position.x, actor.position.y);
            }
        }
    }

    @Override
    public void onDisconnect() {
        scheduledFutureTask.cancel(false);
    }

    @Override
    public void runImpl() {

        if (botIsNotReady()) {
            return;
        }
        if (goalIsNotAchieved()) {
            return;
        }
        Vector2 newTargetPosition = defineNewTargetPoint();
        MetaDataTarget metadata = builMetaDataTarget(newTargetPosition);
        sendNewTargetMessageToNode(metadata);
    }

    private boolean botIsNotReady() {
        return position == null || targetPosition == null;
    }

    private boolean goalIsNotAchieved() {
        return position.distance(targetPosition) >= 1;
    }

    private Vector2 defineNewTargetPoint() {
        return Mathext.doubleRandomCircle(arenaRadius);
    }

    private MetaDataTarget builMetaDataTarget(Vector2 newTargetPosition) {
        return new MetaDataTarget(
                (float) newTargetPosition.x,
                (float) newTargetPosition.y
        );
    }

    private void sendNewTargetMessageToNode(MetaDataTarget metadata) {
        connector.sendMessage(metadata);
    }

}
