package net.greenjab.fixedminecraft.mixin.food;

import net.greenjab.fixedminecraft.registry.GameruleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{
    @ModifyVariable(method = "addExhaustion", at = @At(value = "HEAD"), argsOnly = true)
    private float exhaustionGamerule(float value) {
        PlayerEntity PE = (PlayerEntity)(Object)this;
        if (PE.getWorld() instanceof ServerWorld serverWorld) {
            return value* serverWorld.getGameRules().getInt(GameruleRegistry.INSTANCE.getStamina_Drain_Speed())/100f;
        }
        return value;
    }
}
