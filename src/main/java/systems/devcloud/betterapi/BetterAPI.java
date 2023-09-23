/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi;

import static spark.Spark.*;

import lombok.extern.java.Log;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import systems.devcloud.betterapi.controller.PlayerController;

@Log
public final class BetterAPI extends JavaPlugin {

    private final Configuration pluginConfig = getConfig();
    private final PlayerController playerController = new PlayerController(this);
    private final int apiPort = pluginConfig.getInt("api.port");
    public static final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        port(apiPort);

        log.info("BetterAPI is now running on port 7799");
        playerController.implementPlayerRoutes();
        log.info("Player routes have been imported");
    }

    @Override
    public void onDisable() {
        stop(); // Stops the API
        log.info("BetterAPI has been disabled");
    }

    @Override
    public void onLoad() {
        log.info("BetterAPI has been loaded");
    }

    private void configureSpark() {
        internalServerError((req, res) -> {
            log.info("An Internal Server Error Occurred on the BetterAPI!");
            res.type("application/json");
            return "{\"message\":\"SSSsssorry there was an internal Problem!\"}";
        });
        notFound((req, res) -> {
            log.info("A 404 Error Occurred on the BetterAPI!");
            res.type("application/json");
            return "{\"message\":\"SSSsssorry there was an internal Problem!\"}";
        });
    }
}
