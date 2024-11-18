package net.greenjab.fixedminecraft.mixin.util;

import net.greenjab.fixedminecraft.FixedMinecraft;
import net.greenjab.fixedminecraft.enchanting.Networking;
import net.greenjab.fixedminecraft.registry.item.map_book.MapBookState;
import net.greenjab.fixedminecraft.registry.item.map_book.MapBookStateManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void loadWorld(CallbackInfo ci) {
        synchronized (Networking.SERVER_LOCK) {
            FixedMinecraft.INSTANCE.setSERVER((MinecraftServer) (Object) this);
            Networking.SERVER_LOCK.notifyAll();
        }
        MinecraftServer SW = (MinecraftServer)(Object) this;
        for (int id : MapBookStateManager.INSTANCE.getCurrentBooks()) {
            MapBookState state = MapBookStateManager.INSTANCE.getMapBookState(SW, id);
            assert state != null;
            state.sendData(SW, id);
        }
        MapBookStateManager.INSTANCE.setCurrentBooks(new ArrayList<>());
    }
}
