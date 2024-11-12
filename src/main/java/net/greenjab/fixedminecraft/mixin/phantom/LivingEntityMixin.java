package net.greenjab.fixedminecraft.mixin.phantom;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.fixedminecraft.StatusEffects.StatusRegistry;
import net.greenjab.fixedminecraft.registry.ItemRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@SuppressWarnings("unchecked")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract void remove(Entity.RemovalReason reason);

    @Inject(method = "wakeUp", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setPose(Lnet/minecraft/entity/EntityPose;)V", shift = At.Shift.AFTER))
    private void turnInsomniaIntoHealthBoost(CallbackInfo ci) {
        if (!this.hasStatusEffect(StatusRegistry.INSTANCE.getINSOMNIA())) return;
        int i = this.getStatusEffect(StatusRegistry.INSTANCE.getINSOMNIA()).getAmplifier();
        this.removeStatusEffect(StatusRegistry.INSTANCE.getINSOMNIA());
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, (i+1)*5*60*20, i, true, true));
        if ((LivingEntity)(Object)this instanceof ServerPlayerEntity SPE && i == 4) {
            Criteria.CONSUME_ITEM.trigger(SPE, Items.RED_BED.getDefaultStack());
        }
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;emitGameEvent(Lnet/minecraft/world/event/GameEvent;)V"))
    private void increaseInsomnia(DamageSource damageSource, CallbackInfo ci, @Local Entity entity) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (LE instanceof PhantomEntity) {
            if (entity != null) {
                if (entity.isPlayer()) {
                    if (((ServerPlayerEntity) entity).hasStatusEffect(StatusRegistry.INSTANCE.getINSOMNIA())) {
                        int i = ((ServerPlayerEntity) entity).getStatusEffect(StatusRegistry.INSTANCE.getINSOMNIA()).getAmplifier();
                        if (i < 4) {
                            if (Math.random() < 1 / (5 * Math.pow(i + 1, 2))) {
                                ((ServerPlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusRegistry.INSTANCE.getINSOMNIA(), -1, ++i, true, false));
                                ((ServerPlayerEntity) entity).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.ELDER_GUARDIAN_EFFECT, GameStateChangeS2CPacket.DEMO_OPEN_SCREEN));
                            }
                        }
                    }
                }
            }
        }
    }
}
