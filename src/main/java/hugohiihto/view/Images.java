package hugohiihto.view;

import hugohiihto.Resources;
import java.awt.Image;
import javax.swing.ImageIcon;

public final class Images {
    private Images() {
    }

    public static Image load(String path) {
        return new ImageIcon(Resources.file(path).getPath()).getImage();
    }

    public static Image load(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(Resources.file(path).getPath());
        int w = width > 0 ? width : icon.getIconWidth();
        int h = height > 0 ? height : icon.getIconHeight();
        icon.setImage(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        return icon.getImage();
    }
}
