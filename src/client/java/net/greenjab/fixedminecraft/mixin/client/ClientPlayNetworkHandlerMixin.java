package net.greenjab.fixedminecraft.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.fixedminecraft.MapPacketAccessor;
import net.greenjab.fixedminecraft.mixin.map_book.MapStateAccessor;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.map.MapState;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/map/MapState;of(BZLnet/minecraft/registry/RegistryKey;)Lnet/minecraft/item/map/MapState;"), method = "onMapUpdate")
    private MapState setMapPosition(MapState original, @Local(ordinal = 0) MapUpdateS2CPacket packet) {
        MapPacketAccessor i = (MapPacketAccessor)packet;
        return MapStateAccessor.createMapState(i.fixedminecraft$readX(), i.fixedminecraft$readZ(), original.scale, false, false, original.locked, original.dimension);
    }
}
