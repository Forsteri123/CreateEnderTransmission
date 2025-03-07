package com.forsteri.createendertransmission.blocks.chunkLoader;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

public class LoaderInstance extends SingleAxisRotatingVisual<LoaderBlockEntity> {
    public LoaderInstance(VisualizationContext context, LoaderBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.DOWN, Models.partial(AllPartialModels.SHAFT_HALF));
    }
}
