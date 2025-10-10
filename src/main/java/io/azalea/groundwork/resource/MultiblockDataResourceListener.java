package io.azalea.groundwork.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.azalea.groundwork.GroundworkMod;
import io.azalea.groundwork.multiblock.Multiblock;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MultiblockDataResourceListener implements SimpleResourceReloadListener<List<Multiblock.Data>> {

    private final RegistryWrapper.WrapperLookup lookup;

    public MultiblockDataResourceListener(RegistryWrapper.WrapperLookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public CompletableFuture<List<Multiblock.Data>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        ResourceFinder finder = ResourceFinder.json("multiblocks");
        return CompletableFuture.supplyAsync(() -> finder.findResources(manager)).thenCompose(resources -> {

            List<CompletableFuture<Multiblock.Data>> data = new ArrayList<>();
            for (Identifier id : resources.keySet()) {

                data.add(CompletableFuture.supplyAsync(() -> {
                    Resource resource = resources.get(id);
                    try (InputStream stream = resource.getInputStream()) {

                        JsonObject json = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                        Multiblock.Data datum = Multiblock.Data.CODEC.parse(
                                RegistryOps.of(JsonOps.INSTANCE, lookup),
                                json
                        ).resultOrPartial(GroundworkMod.LOGGER::error).get();

                        return datum;

                    } catch (Exception error) {
                        GroundworkMod.LOGGER.error("Failed to load multiblock {}: {}", id, error);
                    }

                    return null;

                }, executor));

            }

            return Util.combineSafe(data);

        });
    }

    @Override
    public CompletableFuture<Void> apply(List<Multiblock.Data> data, ResourceManager manager, Profiler profiler, Executor executor) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getSessionService().joinServer();
        return CompletableFuture.runAsync(() -> data.forEach(datum -> {
            StructureTemplate structure =
        }));
    }

    @Override
    public Identifier getFabricId() {
        return GroundworkMod.id("multiblocks");
    }

}
