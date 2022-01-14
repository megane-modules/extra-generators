package lol.bai.megane.module.extragenerators.provider;

import java.util.function.Function;

import io.github.lucaargolo.extragenerators.common.blockentity.AbstractGeneratorBlockEntity;
import lol.bai.megane.api.provider.FluidProvider;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

@SuppressWarnings({"rawtypes", "UnstableApiUsage"})
public class GeneratorFluidProvider<T extends AbstractGeneratorBlockEntity> extends FluidProvider<T> {

    private final Function<T, SingleVariantStorage<FluidVariant>> storageGetter;
    private SingleVariantStorage<FluidVariant> storage;

    public GeneratorFluidProvider(Function<T, SingleVariantStorage<FluidVariant>> storageGetter) {
        this.storageGetter = storageGetter;
    }

    @Override
    public void setContext(World world, BlockPos pos, PlayerEntity player, T t) {
        super.setContext(world, pos, player, t);

        this.storage = storageGetter.apply(t);
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return storage.getResource().getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(storage.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(storage.getCapacity());
    }

}
