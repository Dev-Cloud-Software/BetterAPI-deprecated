/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.utils;

import com.google.gson.JsonObject;
import lombok.extern.java.Log;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Log
public class PlayerHelper {

    public static JsonObject playerToJSON(Player player) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("playerName", player.getName());
        jsonObject.addProperty("playerUUID", player.getUniqueId().toString());
        return jsonObject;
    }

    public static JsonObject playerToJSON(OfflinePlayer player) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("playerName", player.getName());
        jsonObject.addProperty("playerUUID", player.getUniqueId().toString());
        return jsonObject;
    }
}
