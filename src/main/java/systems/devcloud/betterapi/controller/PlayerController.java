/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.controller;

import static spark.Spark.*;
import static systems.devcloud.betterapi.utils.General.distinct;
import static systems.devcloud.betterapi.utils.PlayerHelper.playerToJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Pair;
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
        log.info("PlayerController has been initialized");
    }

    public void implementPlayerRoutes() {
        get("/players/list", this::listPlayers);
        get("/players/list/online", this::listOnlinePlayers);
        get("/players/list/offline", this::listOfflinePlayers);
        get("/player/:uuid", this::getPlayer);
        //todo: add more routes
    }

    //TODO: Use UUID for Player Identification
    private JsonObject getPlayer(Request request, Response response) {
        String uuid = request.params(":uuid");
        JsonObject jsonObject = new JsonObject();
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));

        log.info(uuid);
        if (player != null) {
            PlayerDTO playerDTO = PlayerDTO
                .builder()
                .playerName(player.getName())
                .playerUUID(player.getUniqueId())
                .playerIP(Objects.requireNonNull(player.getAddress()).getAddress())
                .playerLocation(player.getLocation().toString())
                .playerWorldName(player.getWorld().getName())
                .playerHealth(player.getHealth())
                .playerSaturation(player.getFoodLevel())
                .playerLevel(player.getLevel())
                .playerGamemode(player.getGameMode())
                .build();
            jsonObject = new Gson().toJsonTree(playerDTO).getAsJsonObject();
        } else {
            jsonObject.addProperty("error", "Player not found");
        }
        response.type("application/json");
        return jsonObject;
    }

    private JsonArray listPlayers(Request request, Response response) {
        Vector<Player> onlinePlayers = new Vector<>(plugin.getServer().getOnlinePlayers());
        Vector<OfflinePlayer> offlinePlayers = new Vector<>(List.of(plugin.getServer().getOfflinePlayers()));
        JsonArray jsonArray = new JsonArray();

        for (Player player : onlinePlayers) {
            jsonArray.add(new Gson().toJsonTree(playerToJSON(player)));
        }
        for (OfflinePlayer player : offlinePlayers) {
            jsonArray.add(new Gson().toJsonTree(playerToJSON(player)));
        }
        response.type("application/json");
        return distinct(jsonArray);
    }

    private JsonArray listOnlinePlayers(Request request, Response response) {
        Vector<Player> players = new Vector<>(plugin.getServer().getOnlinePlayers());
        JsonArray jsonArray = new JsonArray();
        for (Player player : players) {
            jsonArray.add(new Gson().toJsonTree(playerToJSON(player)));
        }
        response.type("application/json");
        return jsonArray;
    }

    //todo: implement the right Logic for the Offline Player List
    private JsonArray listOfflinePlayers(Request request, Response response) {
        Vector<OfflinePlayer> players = new Vector<>(List.of(plugin.getServer().getOfflinePlayers()));
        JsonArray jsonArray = new JsonArray();
        for (OfflinePlayer player : players) {
            jsonArray.add(new Gson().toJsonTree(playerToJSON(player)));
        }
        response.type("application/json");
        return jsonArray;
    }
}
