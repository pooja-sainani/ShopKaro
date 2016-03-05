package cfh.com.shopkaro.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CartContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<CartItem> ITEMS = new ArrayList<CartItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, CartItem> ITEM_MAP = new HashMap<String, CartItem>();

    public static  int COUNT = 0;

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }
//
//    private static void addItem(DummyItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }
//
//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }
//
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class CartItem {
        public  String id;
        public  String productName;
        public  String productId;
        public  int Quantity;
        public  Double price;
        public  String buyerid;
        public Double Amount;


        public CartItem(String id, String  name,String productId,int Quantity,Double price,String buyerid,Double Amount) {
            this.id = id;
            this.productName = name;
            this.productId=productId;
            this.Quantity=Quantity;
            this.price=price;
            this.buyerid=buyerid;
            this.Amount=Amount;

        }

        @Override
        public String toString() {
            return productName;
        }
    }
}
