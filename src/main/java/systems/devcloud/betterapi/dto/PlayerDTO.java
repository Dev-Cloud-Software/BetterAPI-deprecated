/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.dto;

import java.net.InetAddress;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.bukkit.GameMode;

@Data
@Builder
public class PlayerDTO {

    private String playerName;
    private UUID playerUUID;
    private InetAddress playerIP;
    private String playerLocation;
    private String playerWorldName;
    private double playerHealth;
    private float playerSaturation;
    private int playerLevel;
    private int playerExpereince;
    private GameMode playerGamemode;
}
