package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.indev.gui.*;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.api.gui.font.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;

import java.awt.Color;

/**
 * Basic implementation of the renderer for the GUI. <br/>
 * Also implements the InputUtility interface, making it work for both parameters of a ContainerManager. <br/>
 * Created by Brandon Williams on 2/13/2017.
 */
public class BasicRenderer extends RenderManager implements InputUtility {

    public static final Color BACKGROUND = new Color(20, 20, 20, 255);
    public static final Color SECONDARY_BACKGROUND = new Color(35, 35, 35, 255);
    public static final Color ENABLED = new Color(41, 126, 37, 255);
    public static final Color GREY = new Color(45, 45, 45, 255);
    public static final Color SCROLLBAR = GREY.brighter();

    public final FontRenderer fontRenderer = new BasicFontRenderer();

    public BasicRenderer() {
        super();
        this.setRenderer(Button.class, new ButtonRenderer(fontRenderer));
        this.setRenderer(Checkbox.class, new CheckboxRenderer(fontRenderer));
        this.setRenderer(Container.class, new ContainerRenderer());
        this.setRenderer(ScrollableContainer.class, false, new ScrollableContainerRenderer(this));
        this.setRenderer(Slider.class, new SliderRenderer());
        this.setRenderer(Label.class, new LabelRenderer(fontRenderer));

    }

    @Override
    public int getMouseX() {
        return GLManager.getMouseX();
    }

    @Override
    public int getMouseY() {
        return GLManager.getMouseY();
    }

    @Override
    public int getWidth() {
        return GLManager.getScreenWidth();
    }

    @Override
    public int getHeight() {
        return GLManager.getScreenHeight();
    }

    @Override
    public boolean isPointInside(int x, int y, int[] rect) {
        return x > rect[0] && y > rect[1] && x < rect[0] + rect[2] && y < rect[1] + rect[3];
    }
}