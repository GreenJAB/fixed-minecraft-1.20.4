package net.greenjab.fixedminecraft.network;


import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.greenjab.fixedminecraft.registry.item.map_book.MapBookItem;
import net.greenjab.fixedminecraft.registry.item.map_book.MapBookState;
import net.greenjab.fixedminecraft.registry.item.map_book.MapBookStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record BookShelfSyncPayload(int bookID, int[] mapIDs) implements CustomPayload {
    public static final Id<BookShelfSyncPayload> PACKET_ID = new Id<>(Identifier.of("fixedminecraft", "book_shelf_sync"));

    public static final PacketCodec<RegistryByteBuf, BookShelfSyncPayload> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            BookShelfSyncPayload::bookID,
            IntArray.ARRAY_CODEC,
            BookShelfSyncPayload::mapIDs,
            BookShelfSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(PACKET_ID, PACKET_CODEC);
    }

    @Nullable
    public static BookShelfSyncPayload of(ServerPlayerEntity player, ItemStack itemStack) {
        Integer bookId = ((MapBookItem)itemStack.getItem()).getMapBookId(itemStack);
        if (bookId == null) return null;

        MapBookState mapBookState = MapBookStateManager.Companion.getINSTANCE().getMapBookState(player.server, bookId);
        if (mapBookState != null) {
            return new BookShelfSyncPayload(bookId, mapBookState.getMapIDs().stream().mapToInt(i -> i).toArray());
        }
        return null;
    }
}
