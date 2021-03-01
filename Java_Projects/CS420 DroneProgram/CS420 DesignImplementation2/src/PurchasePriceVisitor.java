

public class PurchasePriceVisitor implements Visitor {
    public double visit(ItemLeaf itemLeaf) {
        return itemLeaf.getPrice();
    }

    public double visit(ItemContainer itemContainer) {
        double price = itemContainer.getPrice();
        for(Item i : itemContainer.getChildren()){
            if(i.getChildren() != null){
                price += visit((ItemContainer) i);
            }else{
                price += i.getPrice();
            }
        }
        return price;
    }
}
