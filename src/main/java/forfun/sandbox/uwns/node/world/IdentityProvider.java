package forfun.sandbox.uwns.node.world;

public interface IdentityProvider {

    public int getNext();

    public void release(int id);
}
