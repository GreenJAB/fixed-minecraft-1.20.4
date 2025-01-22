package net.greenjab.fixedminecraft.mixin.transport;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.function.Predicate;

@Mixin(AreaEffectCloudEntity.class)
public class AreaEffectCloudEntityMixin {
    @Shadow
    private PotionContentsComponent potionContentsComponent;

    @Inject(method = "serverTick", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;entrySet()Ljava/util/Set;"
    ))
    private void waterAreaEffect(CallbackInfo ci, @Local(argsOnly = true) ServerWorld serverWorld) {
        AreaEffectCloudEntity AECE = (AreaEffectCloudEntity) (Object)this;
        PotionContentsComponent potionContentsComponent = this.potionContentsComponent;
        if (potionContentsComponent.matches(Potions.WATER)) {
            this.applyWater(serverWorld);
        }

    }


    @Unique
    private void applyWater(ServerWorld serverWorld) {
        AreaEffectCloudEntity AECE = (AreaEffectCloudEntity) (Object)this;
        Box box = AECE.getBoundingBox();
        Predicate<LivingEntity> AFFECTED_BY_WATER = entity -> entity.hurtByWater() || entity.isOnFire();
        for (LivingEntity livingEntity : AECE.getWorld().getEntitiesByClass(LivingEntity.class, box, AFFECTED_BY_WATER)) {
            double d = AECE.squaredDistanceTo(livingEntity);
            if (d < AECE.getWidth()*AECE.getWidth()) {
                if (livingEntity.hurtByWater()) {
                    livingEntity.damage(serverWorld, AECE.getDamageSources().indirectMagic(AECE, AECE.getOwner()), 1.0F);
                }

                if (livingEntity.isOnFire() && livingEntity.isAlive()) {
                    livingEntity.extinguishWithSound();
                }
            }
        }

        for (AxolotlEntity axolotlEntity : AECE.getWorld().getNonSpectatingEntities(AxolotlEntity.class, box)) {
            axolotlEntity.hydrateFromPotion();
        }
    }
}
