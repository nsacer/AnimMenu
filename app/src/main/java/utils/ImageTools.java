package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * Tools for handler picture
 * 
 * @author Ryan.Tang
 * 
 */
public final class ImageTools {
	

	
	/**
	 * Save image to the SD card 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static boolean savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){

        boolean result = photoBitmap == null || TextUtils.isEmpty(path) || TextUtils.isEmpty(photoName);

		if (checkSDCardAvailable()) {

            // 目录不存在就创建
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }

            File photoFile = new File(dirPath + photoName);

            try {

                if(photoFile.exists()) {

                    FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
                    if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        result = Boolean.TRUE;
                    }

                } else {

                    if(photoFile.createNewFile()) {

                        FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
                        if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            result = Boolean.TRUE;
                        }
                    }
                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			}
		}

        return result;
	}
	
	public static boolean savePhotoToSDCard(Bitmap photoBitmap,String path){

        if(photoBitmap == null || TextUtils.isEmpty(path))
            return Boolean.FALSE;

		if (checkSDCardAvailable()) {

			File photoFile = new File(path);
			boolean isMade = false;
			if(!photoFile.exists()){

				isMade = photoFile.mkdirs();
			}

			if(isMade){

				try {

                    FileOutputStream out = new FileOutputStream(photoFile);
                    photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

				} catch (Exception e) {
//					photoFile.delete();
					e.printStackTrace();
				}
			}
		}

		return Boolean.FALSE;
	}
	
	/**
	 * Check the SD card 
	 * @return
	 */
	public static boolean checkSDCardAvailable(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 根据路径加载bitmap
	 * 
	 * @param path
	 *            路径
	 * @param w
	 *            款
	 * @param h
	 *            长
	 * @return
	 */
	public static final Bitmap convertToBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			BitmapFactory.decodeFile(path);
			// 设置为ture只获取图片大小
			opts.inJustDecodeBounds = true;
//			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			// 返回为空
			BitmapFactory.decodeFile(path, opts);
			opts.inJustDecodeBounds = false;
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				// 缩放
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int) scale;
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
			Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
			if (bMapRotate != null) {
				return bMapRotate;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
