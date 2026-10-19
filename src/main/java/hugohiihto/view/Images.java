package hugohiihto.view;

import java.awt.Image;
import javax.swing.ImageIcon;

public final class Images {
    private Images() {
    }

    public static Image load(String path) {
        return load(path, -1, -1);
    }

    public static Image load(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        int w = width > 0 ? width : icon.getIconWidth();
        int h = height > 0 ? height : icon.getIconHeight();
        icon.setImage(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        return icon.getImage();
    }
}
