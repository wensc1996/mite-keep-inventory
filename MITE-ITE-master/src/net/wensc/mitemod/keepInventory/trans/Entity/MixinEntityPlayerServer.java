package net.wensc.mitemod.keepInventory.trans.Entity;

import net.minecraft.GameRules;
import net.minecraft.ServerPlayer;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerPlayer.class, priority = 2000)
public class MixinEntityPlayerServer {
    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/GameRules;getGameRuleBooleanValue(Ljava/lang/String;)Z"))
    public boolean injectKeepInventory(GameRules gameRules, String key) {
        boolean exist = ReflectHelper.dyCast(ServerPlayer.class, this).checkKeepInventory();
        return exist ? exist : gameRules.getGameRuleBooleanValue(key);
    }
}
