

import items.*;

public interface Visitor {

    public double visit(ItemLeaf itemLeaf);
    public double visit(ItemContainer itemContainer);

}
