package testing;

import model.component.visual.ImagePath;

/**
 * Created by rhondusmithwick on 4/13/16.
 *
 * @author Rhondu Smithwick
 */
public class ImagePathWriteTest {

    public static void main(String[] args) {
        ImagePath path = new ImagePath();
        ImagePath path2 = path.clone(ImagePath.class);
        System.out.println(path2.getImageView());
    }
}
