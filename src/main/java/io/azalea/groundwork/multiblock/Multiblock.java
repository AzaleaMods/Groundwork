package io.azalea.groundwork.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.Map;

public class Multiblock {

    public record Data(
            Identifier structure,
            boolean canRotate,
            Map<Identifier, RegistryEntryList<Block>> tags
    ) {

        public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("structure").forGetter(Data::structure),
                Codec.BOOL.fieldOf("can_rotate").orElse(true).forGetter(Data::canRotate),
                Codecs.strictUnboundedMap(
                        Identifier.CODEC,
                        RegistryCodecs.entryList(RegistryKeys.BLOCK)
                ).fieldOf("tags").forGetter(Data::tags)
        ).apply(instance, Data::new));

    }

}
