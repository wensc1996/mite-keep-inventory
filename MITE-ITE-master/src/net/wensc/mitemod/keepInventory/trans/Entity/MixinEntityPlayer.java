package net.wensc.mitemod.keepInventory.trans.Entity;

import net.minecraft.EntityPlayer;
import net.minecraft.GameRules;
import net.wensc.mitemod.keepInventory.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Calendar;

@Mixin(value = EntityPlayer.class, priority = 2000)
public abstract class MixinEntityPlayer {
    @Shadow public abstract String getEntityName();

    @Shadow public int experience;

    @Shadow public float cameraYaw;

    @Shadow protected abstract void fall(float par1);

    public boolean checkKeepInventory() {
        String [] names = Configs.wenscConfig.keepInventoryPlayers.ConfigValue.split(",");
        if(names.length > 0) {
            if(names[0].equals("*")) {
                return true;
            } else {
                if(Arrays.stream(names).anyMatch(e -> e.equals(this.getEntityName()))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Redirect(method = {"onDeath", "getExperienceValue", "clonePlayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/GameRules;getGameRuleBooleanValue(Ljava/lang/String;)Z"))
    public boolean injectKeepInventory(GameRules gameRules, String key) {
        boolean exist = this.checkKeepInventory();
        return exist ? exist : gameRules.getGameRuleBooleanValue(key);
    }

    @Inject(method = "clonePlayer", at = @At("RETURN"))
    public void injectHowMuchExperice(EntityPlayer par1EntityPlayer, boolean par2, CallbackInfo callbackInfo) {
        if(this.checkKeepInventory()) {
            this.experience = (int)((double)this.experience * Configs.wenscConfig.afterDeathDropExpericeRate.ConfigValue);
        }
    }
}
