package net.greenjab.fixedminecraft.registry

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.GameRules
import net.minecraft.world.GameRules.BooleanRule
import net.minecraft.world.GameRules.IntRule


object GameruleRegistry {
    var Ice_Melt_In_Nether: GameRules.Key<BooleanRule> =
        GameRuleRegistry.register("iceMeltInNether", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true))
    var Insomnia_Sleep_Requirement: GameRules.Key<BooleanRule> =
        GameRuleRegistry.register("insomniaSleepRequirement", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false))
    var Require_Totem_Use: GameRules.Key<BooleanRule> =
        GameRuleRegistry.register("requireTotemUse", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false))
    var Stamina_Drain_Speed: GameRules.Key<IntRule> =
        GameRuleRegistry.register("staminaDrainSpeed", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(100))

    fun register() {
    }

}
