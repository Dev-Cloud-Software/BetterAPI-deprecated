/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.utils;

import com.google.gson.JsonObject;
import lombok.extern.java.Log;
import org.bukkit.OfflinePlayer;

@Log
public class PlayerHelper {

    public static JsonObject playerToJSON(OfflinePlayer player) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("playerName", player.getName());
        jsonObject.addProperty("playerUUID", player.getUniqueId().toString());
        jsonObject.addProperty("isOnline", player.isOnline());
        return jsonObject;
    }
}
