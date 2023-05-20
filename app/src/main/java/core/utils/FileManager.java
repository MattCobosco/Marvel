package core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;

public class FileManager {
    private Context context;
    TimeProvider timeProvider = new TimeProvider();

    public FileManager(Context context) {
        this.context = context;
    }

    public void saveImageToGallery(Bitmap bitmap) {
        String displayName = "Character Image_" + timeProvider.currentTimeMillis();
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, displayName, "Character Image From Marvel Library");
    }
}
