package com.azanini.thenotelist.managers;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class TypeFaceManager {

    private static HashMap<String, Typeface> typefaceHashMap;

    public static final String TRASANDINA_W03_LIGHT = "trasandina_w03_light";
    public static final String TRASANDINA_W03_MEDIUM = "trasandina_w03_medium";

    public static Typeface getTypeFaceByName(Context context, String typefaceName) {
        if (typefaceHashMap == null) {
            initMap(context);
        }
        if (typefaceHashMap.containsKey(typefaceName)) {
            return typefaceHashMap.get(typefaceName);
        } else {
            return null;
        }
    }

    private static void initMap(Context context) {
        typefaceHashMap = new HashMap<>();
        typefaceHashMap.put(TRASANDINA_W03_LIGHT, Typeface.createFromAsset(context.getAssets(), "trasandina_w03_light.ttf"));
        typefaceHashMap.put(TRASANDINA_W03_MEDIUM, Typeface.createFromAsset(context.getAssets(), "trasandina_w03_medium.ttf"));
    }

}
