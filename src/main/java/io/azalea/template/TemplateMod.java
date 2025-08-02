package io.azalea.template;

import io.azalea.template.registry.TemplateItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer {

	public static final String NAME = "Template";
	public static final String ID = "template";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {

		TemplateItems.register();

		LOGGER.info("Initialized");

	}

	public static Identifier id(String path) {
		return Identifier.of(path);
	}

}