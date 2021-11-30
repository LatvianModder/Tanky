package dev.latvian.mods.tanky.client;

import dev.latvian.mods.tanky.TankyCommon;
import dev.latvian.mods.tanky.block.TankyBlocks;
import dev.latvian.mods.tanky.screen.TankScreen;
import dev.latvian.mods.tanky.screen.TankyMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TankyClient extends TankyCommon {
	@Override
	public void init() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	private void setup(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(TankyBlocks.IRON_TANK_GLASS.get(), RenderType.cutout());

		MenuScreens.register(TankyMenus.TANK.get(), TankScreen::new);
	}
}
