package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import lol.bai.megane.api.provider.base.SlotArrayProgressProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public abstract class AbstractGeneratorProgressProvider<T extends AbstractGeneratorBlockEntity> extends SlotArrayProgressProvider<T> {

    private static final int[] OUTPUT_SLOTS = new int[0];

    private SimpleSidedInventory inventory;
    private final int[] inputSlots;
    private final Function<T, SimpleSidedInventory> inventoryGetter;

    protected AbstractGeneratorProgressProvider(Function<T, SimpleSidedInventory> inventoryGetter, int... inputSlots) {
        this.inputSlots = inputSlots;
        this.inventoryGetter = inventoryGetter;
    }

    abstract protected int getCurrentBurnTime();

    abstract protected int getBurnTime();

    @Override
    public void setContext(World world, BlockPos pos, PlayerEntity player, T t) {
        super.setContext(world, pos, player, t);

        this.inventory = inventoryGetter.apply(t);
    }

    @Override
    protected int[] getInputSlots() {
        return inputSlots;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected @NotNull ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

    @Override
    public int getPercentage() {
        return remainingPercentage(getCurrentBurnTime(), getBurnTime());
    }

}
