package io.azalea.groundwork.resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.azalea.groundwork.GroundworkMod;
import io.azalea.groundwork.multiblock.Multiblock;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MultiblockDataResourceListener implements SimpleResourceReloadListener<Multiblock.Data> {

    private final RegistryWrapper.WrapperLookup lookup;

    public MultiblockDataResourceListener(RegistryWrapper.WrapperLookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public CompletableFuture<Multiblock.Data> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {

            for (Identifier id : manager.findAllResources(
                    "multiblocks",
                    path -> path.getPath().endsWith(".json")).keySet()
            ) {
                try (InputStream stream = manager.getResource(id).get().getInputStream()) {

                    JsonObject json = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    DataResult<Multiblock.Data> result = Multiblock.Data.CODEC.parse(
                            RegistryOps.of(JsonOps.INSTANCE, lookup),
                            json
                    );

                    Multiblock.Data data = result.resultOrPartial(GroundworkMod.LOGGER::error).get();

                    return data;

                } catch (Exception error) {
                    GroundworkMod.LOGGER.error("Failed to load multiblock {}: {}", id, error);
                }
            }

            return null;

        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Multiblock.Data data, ResourceManager manager, Profiler profiler, Executor executor) {
        return null;
    }

    @Override
    public Identifier getFabricId() {
        return GroundworkMod.id("multiblocks");
    }

}
