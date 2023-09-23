/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static systems.devcloud.betterapi.BetterAPI.localizer;
import static systems.devcloud.betterapi.BetterAPI.mm;
import static systems.devcloud.betterapi.utils.General.distinct;
import static systems.devcloud.betterapi.utils.PlayerHelper.playerToJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Request;
import spark.Response;
import systems.devcloud.betterapi.dto.PlayerDTO;

@Log
public class PlayerController {

    private final JavaPlugin plugin;

    public PlayerController(JavaPlugin plugin) {
        this.plugin = plugin;
        log.info(String.format(localizer.get("api.player.loaded"), localizer.get("prefix")));
    }

    public void implementPlayerRoutes() {
        get("/players/list", this::listPlayers);
        get("/player/:uuid", this::getPlayer);
        post("player/health/:uuid", this::setPlayerHealth);
        post("player/food/:uuid", this::setPlayerFoodLevel);
        post("player/saturation/:uuid", this::setPlayerSaturation);
    }

    private JsonArray listPlayers(Request request, Response response) {
        Vector<OfflinePlayer> offlinePlayers = new Vector<>(List.of(plugin.getServer().getOfflinePlayers()));
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        for (OfflinePlayer player : offlinePlayers) {
            jsonArray.add(new Gson().toJsonTree(playerToJSON(player)));
        }
        jsonObject.add("players", jsonArray);
        response.type("application/json");
        return distinct(jsonArray);
    }

    private JsonObject getPlayer(Request request, Response response) {
        String uuid = request.params(":uuid");
        JsonObject jsonObject = new JsonObject();
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));

        if (player != null) {
            PlayerDTO playerDTO = PlayerDTO
                .builder()
                .playerName(player.getName())
                .playerUUID(player.getUniqueId())
                .playerIP(Objects.requireNonNull(player.getAddress()).getAddress())
                .playerLocation(player.getLocation().toString())
                .playerWorldName(player.getWorld().getName())
                .playerHealth(player.getHealth())
                .playerSaturation(player.getSaturation())
                .playerFoodLevel(player.getFoodLevel())
                .playerLevel(player.getLevel())
                .playerExpereince(player.getTotalExperience())
                .playerGamemode(player.getGameMode())
                .build();
            jsonObject.addProperty("status", "success");
            JsonObject playerObject = new Gson().toJsonTree(playerDTO).getAsJsonObject();
            jsonObject.add("player", playerObject);
        } else {
            jsonObject.addProperty("status", "error");
            jsonObject.addProperty("error", "Player not found / online");
        }
        response.type("application/json");
        return jsonObject;
    }

    private JsonObject setPlayerHealth(Request request, Response response) {
        JsonObject jsonObject = new JsonObject();
        String uuid = request.params(":uuid");
        int value = request.queryParams("value") != null
            ? Integer.parseInt(request.queryParams("value"))
            : 20;
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player != null) {
            player.setHealth(value);
            jsonObject.addProperty("status", "success");
            jsonObject.addProperty("message", "Set health of Player (" + player.getName() + ") to " + value);
            player.sendMessage(
                mm.deserialize(
                    String.format(localizer.get("api.player.health.set"), localizer.get("prefix"), value)
                )
            );
        } else {
            jsonObject.addProperty("status", "error");
            jsonObject.addProperty("message", "Player not found");
        }
        return jsonObject;
    }

    private JsonObject setPlayerFoodLevel(Request request, Response response) {
        JsonObject jsonObject = new JsonObject();
        String uuid = request.params(":uuid");
        int value = request.queryParams("value") != null
            ? Integer.parseInt(request.queryParams("value"))
            : 20;
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player != null) {
            player.setFoodLevel(value);
            jsonObject.addProperty("status", "success");
            jsonObject.addProperty(
                "message",
                "Set food level of Player (" + player.getName() + ") to " + value
            );
            player.sendMessage(
                mm.deserialize(
                    String.format(localizer.get("api.player.food.set"), localizer.get("prefix"), value)
                )
            );
        } else {
            jsonObject.addProperty("message", "Player not found / online");
        }

        return jsonObject;
    }

    private JsonObject setPlayerSaturation(Request request, Response response) {
        JsonObject jsonObject = new JsonObject();
        String uuid = request.params(":uuid");
        int value = request.queryParams("value") != null
            ? Integer.parseInt(request.queryParams("value"))
            : 20;
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player != null) {
            player.setSaturation(value);
            jsonObject.addProperty("status", "success");
            jsonObject.addProperty(
                "message",
                "Set Saturation of Player (" + player.getName() + ") to " + value
            );
            player.sendMessage(
                mm.deserialize(
                    String.format(localizer.get("api.player.saturation.set"), localizer.get("prefix"), value)
                )
            );
        } else {
            jsonObject.addProperty("message", "Player not found / online");
        }

        return jsonObject;
    }
}
