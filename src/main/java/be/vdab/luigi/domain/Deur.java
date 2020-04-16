package be.vdab.luigi.domain;
import java.io.Serializable;
/**
 * @Author Andre Komdeur
 */
public class Deur implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int index;
    private final boolean metFriet;
    private boolean open;

    public Deur(int index, boolean metFriet) {
        this.index = index;
        this.metFriet = metFriet;
        this.open = false;
    }

    public void open() {
        open = true;
    }

    public int getIndex() {
        return index;
    }

    public boolean isMetFriet() {
        return metFriet;
    }

    public boolean isOpen() {
        return open;
    }
}
