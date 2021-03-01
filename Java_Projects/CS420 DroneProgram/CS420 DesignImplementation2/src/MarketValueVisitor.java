

public class MarketValueVisitor implements Visitor{
    //TODO this method
    public double visit(ItemLeaf itemLeaf) {
        return itemLeaf.getMarketValue();
    }

    //TODO this method
    public double visit(ItemContainer itemContainer) {
        double marketValue = 0;
        for (Item i : itemContainer.getChildren()){
            if (i.getChildren() == null){
                ItemLeaf il = (ItemLeaf) i;
                marketValue += il.getMarketValue();
            }else{
                marketValue += visit((ItemContainer)i);
            }
        }

        return marketValue;
    }
}
