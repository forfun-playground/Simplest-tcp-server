package forfun.sandbox.uwns.node.world;

import java.util.LinkedList;
import java.util.Queue;

public class IdentityProviderImpl implements IdentityProvider {

    private int counter;
    private final Queue<Integer> fifo = new LinkedList<>();
    
    @Override
    public synchronized int getNext() {
        if(!fifo.isEmpty()){
            return fifo.remove();
        }
        return ++counter;
    }

    @Override
    public synchronized void release(int id) {
        fifo.add(id);
    }

}
