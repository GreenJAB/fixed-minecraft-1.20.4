package net.greenjab.fixedminecraft.mixin.transport;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    /**
     * Extends the duration of the riptide effect based on the enchantment level, similar to rocket boosts.
     */
    @ModifyArg(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;useRiptide(I)V"), index = 0)
    private int modifyRiptideTicks(int riptideTicks, @Local(argsOnly = true) ItemStack stack) {
        int level = EnchantmentHelper.getLevel(Enchantments.RIPTIDE, stack);
        return riptideTicks * level;
    }
}
