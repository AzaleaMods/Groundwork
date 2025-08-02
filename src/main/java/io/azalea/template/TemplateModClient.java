package io.azalea.template;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static io.azalea.template.TemplateMod.*;

@Environment(EnvType.CLIENT)
public class TemplateModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client initialized");
    }

}
