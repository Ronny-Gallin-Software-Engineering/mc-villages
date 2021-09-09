package de.rgse.mc.villages.util;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemStackHelperTest {

    @BeforeAll
    public static void beforeClass() {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }

    @Test
    void mergeToOneStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 1);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 2);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(1, merged.size());
        ItemStack result = merged.get(0);
        Assertions.assertEquals(3, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }

    @Test
    void mergeOneEmptyStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 1);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 0);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(1, merged.size());
        ItemStack result = merged.get(0);
        Assertions.assertEquals(1, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }


    @Test
    void mergeOtherEmptyStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 0);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 1);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(1, merged.size());
        ItemStack result = merged.get(0);
        Assertions.assertEquals(1, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }

    @Test
    void mergeEmptyStacksTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 0);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 0);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(1, merged.size());
        ItemStack result = merged.get(0);
        Assertions.assertEquals(0, result.getCount());
        Assertions.assertEquals(Items.AIR, result.getItem());
    }

    @Test
    void mergeUnfittingStacksTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 5);
        ItemStack stackB = new ItemStack(Blocks.SAND, 5);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(2, merged.size());

        ItemStack result = merged.get(0);
        Assertions.assertEquals(5, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());

        result = merged.get(1);
        Assertions.assertEquals(5, result.getCount());
        Assertions.assertEquals(Items.SAND, result.getItem());
    }

    @Test
    void mergeFullStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 64);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 5);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(2, merged.size());

        ItemStack result = merged.get(0);
        Assertions.assertEquals(64, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());

        result = merged.get(1);
        Assertions.assertEquals(5, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }

    @Test
    void mergeOtherFullStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 5);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 64);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(2, merged.size());

        ItemStack result = merged.get(0);
        Assertions.assertEquals(64, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());

        result = merged.get(1);
        Assertions.assertEquals(5, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }


    @Test
    void mergeFillingStackTest() {
        ItemStack stackA = new ItemStack(Blocks.COBBLESTONE, 60);
        ItemStack stackB = new ItemStack(Blocks.COBBLESTONE, 5);

        List<ItemStack> merged = ItemStackHelper.merge(stackA, stackB);
        Assertions.assertNotNull(merged);
        Assertions.assertEquals(2, merged.size());

        ItemStack result = merged.get(0);
        Assertions.assertEquals(64, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());

        result = merged.get(1);
        Assertions.assertEquals(1, result.getCount());
        Assertions.assertEquals(Items.COBBLESTONE, result.getItem());
    }
}
