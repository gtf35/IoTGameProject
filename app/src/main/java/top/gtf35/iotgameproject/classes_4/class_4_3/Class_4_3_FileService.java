package top.gtf35.iotgameproject.classes_4.class_4_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class Class_4_3_FileService {

    private Context mContext;
    public static final String FOLDER_NAME = "/SnapShotImage";
    public final String TAG = this.getClass().getSimpleName();

    public Class_4_3_FileService(Context context) {
        mContext = context;
    }

    public boolean saveBitmapToSDCard(String filename, Bitmap bmp){
        try {
            if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                String fileDir = Environment.getExternalStorageDirectory() + FOLDER_NAME;
                if (!createDir(fileDir)) {
                    Log.w(TAG, "创建文件夹失败");
                    return false;
                }
                File file = new File(fileDir, filename);
                FileOutputStream outputStream = new FileOutputStream(file.getPath());
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                outputStream.close();
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return false;
        }
    }

    private boolean createDir(String filePath){
        File fileDir = new File(filePath);
        boolean bRet = true;
        if (!fileDir.exists()){
            String[] aDirs = filePath.split("/");
            StringBuffer strDir = new StringBuffer();
            for (int i = 0; i < aDirs.length; i++) {
                fileDir = new File(strDir.append("/").append(aDirs[i]).toString());
                if (!fileDir.exists()){
                    if (!fileDir.mkdir()){
                        bRet = false;
                        break;
                    }
                }
            }
        }
        return bRet;
    }

    private boolean myCreateDir(String fileDir){
        if (fileDir.endsWith(File.separator)) return false;
        try {
            File file = new File(fileDir);
            if (file.exists()) return true;
            if (file.getParentFile().exists()) return true;
            if (file.getParentFile().mkdirs()) return true;
            return false;
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return false;
        }
    }
}
