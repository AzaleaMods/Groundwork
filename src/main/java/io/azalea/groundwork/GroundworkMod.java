package io.azalea.groundwork;

import io.azalea.groundwork.resource.MultiblockDataResourceListener;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroundworkMod implements ModInitializer {

	public static final String NAME = "Groundwork";
	public static final String ID = "groundwork";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                id("multiblocks"),
                MultiblockDataResourceListener::new
        );

		LOGGER.info("Initialized");

	}

	public static Identifier id(String path) {
		return Identifier.of(ID, path);
	}

}