package net.jmb19905.niftycarts.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.predicates.*;
import net.minecraft.advancements.predicates.entity.*;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ReaperHarvestCriterion extends SimpleCriterionTrigger<ReaperHarvestCriterion.@NotNull TriggerInstance>  {

    public void trigger(ServerPlayer serverPlayer, BlockState state) {
        super.trigger(serverPlayer, t -> t.matches(state));
    }

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Optional<SimpleBlockPredicate> block) implements SimpleInstance {

        private static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        SimpleBlockPredicate.CODEC.optionalFieldOf("block").forGetter(TriggerInstance::block)
                ).apply(instance, TriggerInstance::new));

        public static Criterion<@NotNull TriggerInstance> reaperHarvest() {
            return NCCriteriaTriggers.REAPER_HARVEST.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty()));
        }

        public static Criterion<@NotNull TriggerInstance> reaperHarvest(SimpleBlockPredicate.Builder builder) {
            return NCCriteriaTriggers.REAPER_HARVEST.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(builder.build())));
        }

        public boolean matches(BlockState state) {
            return block.isEmpty() || block.get().test(state);
        }
    }

}