package net.wensc.mitemod.keepInventory;

import net.wensc.mitemod.keepInventory.trans.TransMarker;
import net.wensc.mitemod.keepInventory.util.Configs;
import net.xiaoyu233.fml.AbstractMod;


import net.xiaoyu233.fml.classloading.Mod;
import net.xiaoyu233.fml.config.InjectionConfig;
import org.spongepowered.asm.mixin.MixinEnvironment;

@Mod
public class MITEKeepInventory extends AbstractMod {

    public MITEKeepInventory() {
    }

    public void preInit() {
    }


    @Override
    public InjectionConfig getInjectionConfig() {
        return InjectionConfig.Builder.of("mite-keep-inventory", TransMarker.class.getPackage(), MixinEnvironment.Phase.INIT).setRequired().build();
    }


    public void postInit() {
        super.postInit();
        Configs.loadConfigs();
    }


    public String modId() {
        return "mite-keep-inventory";
    }

    public int modVerNum() {
        return 1;
    }

    public String modVerStr() {
        return "0.0.1";
    }
}
