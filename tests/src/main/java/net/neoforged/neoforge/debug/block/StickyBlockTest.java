/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

@Mod(StickyBlockTest.MODID)
public class StickyBlockTest {
    static final String MODID = "custom_slime_block_test";
    static final String BLOCK_ID = "test_block";
    static final String BLOCK_ID_2 = "test_block_2";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS.getRegistryName(), MODID);

    public static final RegistryObject<Block> BLUE_BLOCK = BLOCKS.register(BLOCK_ID, () -> new Block(Block.Properties.of().mapColor(MapColor.STONE)) {
        @Override
        public boolean isStickyBlock(BlockState state) {
            return true;
        }
    });

    public static final RegistryObject<Block> RED_BLOCK = BLOCKS.register(BLOCK_ID_2, () -> new Block(Block.Properties.of().mapColor(MapColor.STONE)) {
        @Override
        public boolean isStickyBlock(BlockState state) {
            return true;
        }

        @Override
        public boolean isSlimeBlock(BlockState state) {
            return true;
        }

        @Override
        public boolean canStickTo(BlockState state, BlockState other) {
            if (state.getBlock() == RED_BLOCK.get() && other.getBlock() == Blocks.SLIME_BLOCK) return false;
            return state.isStickyBlock() || other.isStickyBlock();
        }
    });

    public static final RegistryObject<Item> BLUE_BLOCK_ITEM = ITEMS.register(BLOCK_ID, () -> new BlockItem(BLUE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_BLOCK_ITEM = ITEMS.register(BLOCK_ID_2, () -> new BlockItem(RED_BLOCK.get(), new Item.Properties()));

    public StickyBlockTest() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
