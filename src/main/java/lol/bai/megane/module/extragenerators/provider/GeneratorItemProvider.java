package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.utils.SimpleSidedInventory;
import lol.bai.megane.api.provider.ItemProvider;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("rawtypes")
public class GeneratorItemProvider<T extends AbstractGeneratorBlockEntity> extends ItemProvider<T> {

    private final Function<T, SimpleSidedInventory> inventoryGetter;
    private SimpleSidedInventory inventory;

    public GeneratorItemProvider(Function<T, SimpleSidedInventory> inventoryGetter) {
        this.inventoryGetter = inventoryGetter;
    }

    @Override
    public void setContext(World world, BlockPos pos, PlayerEntity player, T t) {
        super.setContext(world, pos, player, t);

        this.inventory = inventoryGetter.apply(t);
    }

    @Override
    public int getSlotCount() {
        return inventory.size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

}
