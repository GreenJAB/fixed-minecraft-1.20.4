package net.greenjab.fixedminecraft.mixin.map_book;

import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MapBannerMarker.class)
public class MapBannerMarkerMixin {

    @Inject(method = "fromWorldBlock", at = @At("HEAD"),cancellable = true)
    private static void fakeBanner(BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<MapBannerMarker> cir){
        if (blockPos.getY()<=-1000 && blockPos.getY() > -2000) {
            DyeColor[] dye = {DyeColor.PURPLE, DyeColor.PINK, DyeColor.LIGHT_BLUE, DyeColor.RED, DyeColor.CYAN, DyeColor.LIGHT_GRAY};
            cir.setReturnValue(new MapBannerMarker(blockPos, dye[Math.abs(blockPos.getY())-1000], Optional.empty()));
        }
    }
}
