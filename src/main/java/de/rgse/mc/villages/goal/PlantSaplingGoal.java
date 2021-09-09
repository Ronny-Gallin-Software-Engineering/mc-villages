package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.skill.Skill;
import de.rgse.mc.villages.skill.VillagesSkills;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class PlantSaplingGoal extends Goal {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SKILL_THRESHOLD = 500;
    private static final int MIN_COOLDOWN = 200;

    private final SettlerEntity settler;
    private int indexToPlace = -1;
    private int counter;
    private int cooldown = MIN_COOLDOWN;
    private BlockPos blockPos;

    public PlantSaplingGoal(SettlerEntity settler) {
        this.settler = settler;
    }

    @Override
    public boolean canStart() {
        if (++cooldown < MIN_COOLDOWN) {
            return false;
        }

        for (int i = 0; i < settler.getInventory().size(); i++) {
            ItemStack stack = settler.getInventory().getStack(i);
            if (ItemTags.SAPLINGS.contains(stack.getItem())) {
                indexToPlace = i;
                break;
            }
        }

        blockPos = settler.getBlockPos();
        BlockState blockState = settler.world.getBlockState(blockPos.down());
        BlockState targetBlockState = settler.world.getBlockState(blockPos);

        return indexToPlace > -1 && (Feature.isSoil(blockState) || blockState.isOf(Blocks.GRASS_BLOCK)) &&
                targetBlockState.isOf(Blocks.AIR) &&
                Feature.isExposedToAir(settler.world::getBlockState, blockPos)
                && Feature.isExposedToAir(settler.world::getBlockState, blockPos.up())
                && Feature.isExposedToAir(settler.world::getBlockState, blockPos.up(2))
                && Feature.isExposedToAir(settler.world::getBlockState, blockPos.up(3));
    }

    @Override
    public void start() {
        LOGGER.info("{} start", settler.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(settler, IdentifierUtil.goal(this));
        cooldown = 0;
        counter = 0;
        super.start();
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", settler.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(settler, IdentifierUtil.goal(this));
        super.stop();
    }

    @Override
    public boolean shouldContinue() {
        return indexToPlace >= 0;
    }

    @Override
    public void tick() {
        super.tick();
        Optional<Skill> skill = settler.getSettlerData().getSkills().getSkill(VillagesSkills.LUMBERJACK);
        float level = skill.map(Skill::getLevel).orElse(1f);

        if (++counter / level >= SKILL_THRESHOLD) {
            ItemStack stack = settler.getInventory().getStack(indexToPlace);
            Identifier id = Registry.ITEM.getId(stack.getItem());
            Block block = Registry.BLOCK.get(id);
            //settler.getMovementDirection()
            settler.world.setBlockState(blockPos, block.getDefaultState());
            stack.decrement(1);
            settler.getInventory().setStack(indexToPlace, stack);
            indexToPlace = -1;
        }
    }
}
