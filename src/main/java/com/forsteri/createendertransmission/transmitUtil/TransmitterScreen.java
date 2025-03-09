package com.forsteri.createendertransmission.transmitUtil;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.Label;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.gui.widget.SelectionScrollInput;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class TransmitterScreen extends AbstractSimiScreen {

    public KineticBlockEntity te;

    public ItemStack renderedItem;

    public TransmitterScreen(KineticBlockEntity te, ItemStack renderedItem) {
        super(CreateLang.translateDirect("gui.sequenced_gearshift.title"));
        this.te = te;
        this.renderedItem = renderedItem;
    }

    private final AllGuiTextures background = AllGuiTextures.WAND_OF_SYMMETRY;
    private IconButton confirmButton;

    private Label labelChannel;

    private ScrollInput areaChannel;

    private EditBox areaTestInput;

    @Override
    protected void renderWindow(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft;
        int y = guiTop;

        background.render(guiGraphics, x, y);
        guiGraphics.drawString(font, renderedItem.getHoverName(), x + 11, y + 4, 0x6B3802, false);

        GuiGameElement.of(renderedItem)
                .<GuiGameElement.GuiRenderBuilder>at(x + background.getWidth() + 6, y + background.getHeight() - 56, -200)
                .scale(5)
                .render(guiGraphics);
    }

    @Override
    protected void init() {
        setWindowSize(background.getWidth(), background.getHeight());
        setWindowOffset(-20, 0);
        super.init();

        int x = guiLeft;
        int y = guiTop;

        labelChannel = new Label(x + 49, y + 28, Component.empty()).colored(0xFFFFFFFF)
                .withShadow();

        areaChannel = new SelectionScrollInput(x + 45, y + 21, 109, 18).forOptions(
                        List.of(Component.translatable("transmitter.network.1"), Component.translatable("transmitter.network.2"), Component.translatable("transmitter.network.3"), Component.translatable("transmitter.network.4"), Component.translatable("transmitter.network.5"), Component.translatable("transmitter.network.6"), Component.translatable("transmitter.network.7"), Component.translatable("transmitter.network.8"), Component.translatable("transmitter.network.9"), Component.translatable("transmitter.network.10"))
                )
                .titled(CreateLang.translateDirect("gui.transmitter.channel_title").plainCopy())
                .writingTo(labelChannel)
                .setState(
                        ((ITransmitter) te).getChannel()
                );

        areaTestInput = new EditBox(font, x + 49, y + 50, 109, 18, Component.empty());
        areaTestInput.setBordered(false);
        areaTestInput.setMaxLength(16);
        areaTestInput.setValue(
                ((ITransmitter) te).getPassword()
        );


        confirmButton =
                new IconButton(x + background.getWidth() - 33, y + background.getHeight() - 24, AllIcons.I_CONFIRM);
        confirmButton.withCallback(this::onClose);

        addRenderableWidget(labelChannel);
        addRenderableWidget(areaChannel);
//        addRenderableWidget(labelTestInput);
        addRenderableWidget(areaTestInput);


        addRenderableWidget(confirmButton);
    }

    @Override
    public void removed() {
        super.removed();
        CatnipServices.NETWORK.sendToServer(new ConfigureTransmitterPacket(te.getBlockPos(), areaChannel.getState(), areaTestInput.getValue()));
    }
}
