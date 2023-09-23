/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi;

import static spark.Spark.*;

import com.google.gson.JsonObject;
import lombok.extern.java.Log;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import systems.devcloud.betterapi.controller.PlayerController;
import systems.devcloud.betterapi.utils.Localizer;

@Log
public class BetterAPI extends JavaPlugin {

    public static Localizer localizer;
    private static PlayerController playerController;
    private int apiPort;
    public static MiniMessage mm;

    @Override
    public void onLoad() {
        Configuration pluginConfig = getConfig();
        localizer = new Localizer(pluginConfig.getString("general.locale"));
        playerController = new PlayerController(this);
        this.apiPort = pluginConfig.getInt("api.port");
        mm = MiniMessage.miniMessage();
        log.info(String.format(localizer.get("plugin.loaded"), localizer.get("prefix")));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        port(apiPort);
        configureSpark();
        log.info(String.format(localizer.get("api.running"), apiPort));

        playerController.implementPlayerRoutes();
        log.info(String.format(localizer.get("plugin.enabled"), localizer.get("prefix")));
    }

    @Override
    public void onDisable() {
        stop(); // Stops the API
        log.info(String.format(localizer.get("plugin.disabled"), localizer.get("prefix")));
    }

    private void configureSpark() {
        internalServerError((req, res) -> {
            log.info("An Internal Server Error Occurred on the BetterAPI!");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status", "error");
            jsonObject.addProperty("message", "SSSsssorry there was an internal Problem!");

            res.type("application/json");
            return jsonObject;
        });
        notFound((req, res) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status", "not found");
            jsonObject.addProperty("message", "SSSsssorry, but the page you were looking for doesn't exist.");

            res.type("application/json");
            return jsonObject;
        });
    }
}
