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
public class ProductContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static int COUNT = 0;

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
//        return new DummyItem(String.valueOf(position), "Item " + position,200, makeDetails(position));
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
    public static class DummyItem {
        public final String id;
        public final String name;
        public final Double cost;
        public final String tags;
        public final String place;
        public final boolean isService;

        public DummyItem(String id, String name,Double cost ,String details,String place, boolean isService) {
            this.id = id;
            this.name = name;
            this.cost = cost;
            this.tags = details;
            this.place = place;
            this.isService = isService;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
