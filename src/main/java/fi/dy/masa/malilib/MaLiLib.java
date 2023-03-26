package fi.dy.masa.malilib;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fi.dy.masa.malilib.event.InitializationHandler;

@Mod("malilib")
public class MaLiLib {
    public static final Logger logger = LogManager.getLogger(MaLiLibReference.MOD_ID);

    public MaLiLib() {
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MOD_BUS.addListener(this::onClientSetup);
        MOD_BUS.addListener(this::onInterModProcess);
    }

    private void onClientSetup(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ForgeInputEventHandler());
        MinecraftForge.EVENT_BUS.register(new ForgeTickEventHandler());

        InitializationHandler.getInstance().registerInitializationHandler(new MaLiLibInitHandler());

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(((client, screen) -> {
            MaLiLibConfigGui gui = new MaLiLibConfigGui();
            gui.setParent(screen);
            return gui;
        })));
    }

    private void onInterModProcess(final InterModProcessEvent event) {

        ((InitializationHandler) InitializationHandler.getInstance()).onGameInitDone();
    }
}
