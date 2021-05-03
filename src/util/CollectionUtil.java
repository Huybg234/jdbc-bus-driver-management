package util;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return !ObjectUtil.isEmpty(collection) && collection.isEmpty();
    }

}
