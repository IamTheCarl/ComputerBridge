package com.thecarlcore.computer_bridge;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(ComputerBridge.MODID)
@Mod.EventBusSubscriber(modid = ComputerBridge.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ComputerBridge
{
    public static final String MODID = "computer_bridge";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Block> COMPUTER_BRIDGE_BLOCK = BLOCKS.register("computer_bridge", () -> new ComputerBridgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Item> COMPUTER_BRIDGE_ITEM = ITEMS.register("computer_bridge", () -> new BlockItem(COMPUTER_BRIDGE_BLOCK.get(), new Item.Properties()));
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ComputerBridge.MODID);
    public static final RegistryObject<BlockEntityType<ComputerBridgeEntity>> COMPUTER_BRIDGE_ENTITY = TILE_ENTITY_TYPES.register("computer_bridge",
            () -> BlockEntityType.Builder.of(ComputerBridgeEntity::new, COMPUTER_BRIDGE_BLOCK.get()).build(null));

    public static final RegistryObject<CreativeModeTab> COMPUTER_BRIDGE_TAB = CREATIVE_MODE_TABS.register("computer_bridge", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> COMPUTER_BRIDGE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(COMPUTER_BRIDGE_ITEM.get());
            }).build());

    public ComputerBridge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        BLOCKS.register(modEventBus);
        TILE_ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ComputerCraftAPI.registerGenericSource(new ComputerBridgePeripheral());
    }
}
