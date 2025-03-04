package net.greenjab.fixedminecraft.mixin.minecart;

import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FurnaceMinecartEntity.class)
public class FurnaceMinecartEntityMixin {

    /*@Redirect(method = "interact", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/Vec3d;getHorizontal()Lnet/minecraft/util/math/Vec3d;"
    ))
    private Vec3d set(Vec3d instance) {
        FurnaceMinecartEntity FME = (FurnaceMinecartEntity)(Object)this;
        return new Vec3d(1, 0, 0).rotateY(FME.getYaw());
    }*/

    @ModifyConstant(method = "getMaxSpeed", constant = @Constant(doubleValue = 0.5))
    private double notReducedSpeed(double constant) {
        return 1;
    }
}
