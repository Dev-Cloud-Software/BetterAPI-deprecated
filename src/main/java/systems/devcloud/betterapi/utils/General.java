/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.utils;

import com.google.gson.JsonArray;

public class General {

    public static JsonArray distinct(JsonArray jsonArray) {
        JsonArray distinctArray = new JsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            if (!distinctArray.contains(jsonArray.get(i))) {
                distinctArray.add(jsonArray.get(i));
            }
        }
        return distinctArray;
    }
}
