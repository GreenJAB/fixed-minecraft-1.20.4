package net.greenjab.fixedminecraft.mixin.food;


import net.greenjab.fixedminecraft.data.ModTags;
import net.greenjab.fixedminecraft.registry.ItemRegistry;
import net.greenjab.fixedminecraft.registry.item.BrickItem;
import net.greenjab.fixedminecraft.registry.item.GlisteringMelonSliceItem;
import net.greenjab.fixedminecraft.registry.item.PhantomMembraneItem;
import net.greenjab.fixedminecraft.registry.item.TotemItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SaddleItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

import static net.minecraft.item.Items.register;

@Mixin(Items.class)
public class ItemsMixin {


    @Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice( from = @At(value = "FIELD",
                                                                                                                                                                                                  target = "Lnet/minecraft/item/Items;TADPOLE_BUCKET:Lnet/minecraft/item/Item;")))
    private static Item throwableBrick(String id) {
        return register("brick", BrickItem::new, new Item.Settings().maxCount(16));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                  target = "Lnet/minecraft/item/Items;ENCHANTED_BOOK:Lnet/minecraft/item/Item;")))
    private static Item throwableNetherBrick(String id) {
        return register("nether_brick", BrickItem::new, new Item.Settings().maxCount(16));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;SHIELD:Lnet/minecraft/item/Item;")))
    private static Item useableTotem(String id, Item.Settings settings) {
        return register("totem_of_undying", TotemItem::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(DataComponentTypes.DEATH_PROTECTION, DeathProtectionComponent.TOTEM_OF_UNDYING));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;WARPED_FUNGUS_ON_A_STICK:Lnet/minecraft/item/Item;")))
    private static Item edibleMembrane(String id) {
        return register("phantom_membrane", PhantomMembraneItem::new, new Item.Settings().maxCount(64).food(FoodComponents.CHORUS_FRUIT));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                                                                                                                                                                                   target = "Lnet/minecraft/item/Items;ENDER_EYE:Lnet/minecraft/item/Item;")))
    private static Item edibleGoldMelon(String id) {
        return register("glistering_melon_slice", GlisteringMelonSliceItem::new, new Item.Settings().food(FoodComponents.GLOW_BERRIES));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                                                                                                                                                                                   target = "Lnet/minecraft/item/Items;SWEET_BERRIES:Lnet/minecraft/item/Item;")))
    private static Item glowingGlowBerries(String id, Function<Item.Settings, Item> factory,
                                           Item.Settings settings) {
        return register("glow_berries", createBlockItemWithUniqueName(Blocks.CAVE_VINES), new Item.Settings().food(FoodComponents.GLOW_BERRIES, ItemRegistry.GLOW_BERRIES_EFFECT));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;COOKED_RABBIT:Lnet/minecraft/item/Item;")))
    private static Item stackedRabbitstew(String id, Item.Settings settings) {
        return register("rabbit_stew", new Item.Settings().maxCount(16).food(FoodComponents.RABBIT_STEW).useRemainder(Items.BOWL));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;STICK:Lnet/minecraft/item/Item;")))
    private static Item stackedMushroomstew(String id, Item.Settings settings) {
        return register("mushroom_stew", new Item.Settings().maxCount(16).food(FoodComponents.MUSHROOM_STEW).useRemainder(Items.BOWL));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;CROSSBOW:Lnet/minecraft/item/Item;")))
    private static Item stackedSuspiciousSoup(String id, Item.Settings settings) {
        return register("suspicious_stew", new Item.Settings().maxCount(16).food(FoodComponents.SUSPICIOUS_STEW).component(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffectsComponent.DEFAULT).useRemainder(Items.BOWL));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;GLASS_BOTTLE:Lnet/minecraft/item/Item;")))
    private static Item stackedPotions(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("potion",PotionItem::new,new Item.Settings().maxCount(16).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK).useRemainder(Items.GLASS_BOTTLE));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;ACTIVATOR_RAIL:Lnet/minecraft/item/Item;")))
    private static Item stackedSaddles(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("saddle", SaddleItem::new, new Item.Settings().maxCount(16));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;DISC_FRAGMENT_5:Lnet/minecraft/item/Item;")))
    private static Item repairableTrident(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("trident", TridentItem::new, new Item.Settings().rarity(Rarity.RARE).maxDamage(250).attributeModifiers(TridentItem.createAttributeModifiers()).component(DataComponentTypes.TOOL, TridentItem.createToolComponent()).enchantable(1).repairable(Items.PRISMARINE_SHARD));
    }

    //As string is initilized after bow, need to pass itemtag os just string rather than string itself
    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                    target = "Lnet/minecraft/item/Items;APPLE:Lnet/minecraft/item/Item;")))
    private static Item repairableBow(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("bow", BowItem::new, new Item.Settings().maxDamage(384).enchantable(1).repairable(ModTags.INSTANCE.getSTRINGTAG()));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                   target = "Lnet/minecraft/item/Items;HEART_OF_THE_SEA:Lnet/minecraft/item/Item;")))
    private static Item repairableCrossBow(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("crossbow",
                CrossbowItem::new,
                new Item.Settings().maxCount(1).maxDamage(465).component(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT).enchantable(1).repairable(Items.STRING));
    }

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice(from = @At( value = "FIELD",
                                 target = "Lnet/minecraft/item/Items;BLACK_BUNDLE:Lnet/minecraft/item/Item;")))
    private static Item repairableFishingRod(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register("fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(64).enchantable(1).repairable(Items.STRING));
    }

    @Unique
    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return /* method_63813 */ settings -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }
}
