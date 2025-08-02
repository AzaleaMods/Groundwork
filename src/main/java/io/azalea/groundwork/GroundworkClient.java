package io.azalea.groundwork;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static io.azalea.groundwork.GroundworkMod.*;

@Environment(EnvType.CLIENT)
public class GroundworkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client initialized");
    }

}
