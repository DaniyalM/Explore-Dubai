package com.app.dubaiculture.utils

import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat


class FileUtils {

//    @SuppressLint("SimpleDateFormat")
//    @Throws(IOException::class)
//    fun createImageFile(activity: Activity): Pair<File, String> {
//        // Create an image file name
//        val timeStamp: String =
//                SimpleDateFormat(Constants.CreatePost.DATETIME_PATTERN).format(Date())
//        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val tempFile = File.createTempFile(
//                Constants.CreatePost.FILENAME_PATTERN + "${timeStamp}_", /* prefix */
//                Constants.CreatePost.IMAGE_FILE_TYPE, /* suffix */
//                storageDir /* directory */
//        )
//        return Pair(tempFile, tempFile.absolutePath)
//    }

//    @SuppressLint("SimpleDateFormat")
//    @Throws(IOException::class)
//    fun getTempUri(content: Context): Uri {
//        // Create an image file name
//        val timeStamp: String =
//                SimpleDateFormat(Constants.CreatePost.DATETIME_PATTERN).format(Date())
//        val storageDir: File? = content.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return Uri.fromFile(
//                File.createTempFile(
//                        Constants.CreatePost.FILENAME_PATTERN + "${timeStamp}_", /* prefix */
//                        Constants.CreatePost.IMAGE_FILE_TYPE, /* suffix */
//                        storageDir /* directory */
//                )
//        )
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    @Throws(IOException::class)
//    fun createVideoFile(activity: Activity): File {
//        // Create an image file name
//        val timeStamp: String =
//                SimpleDateFormat(Constants.CreatePost.DATETIME_PATTERN).format(Date())
//        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
//        return File.createTempFile(
//                Constants.CreatePost.FILENAME_PATTERN + "${timeStamp}_", /* prefix */
//                Constants.CreatePost.VIDEO_FILE_TYPE, /* suffix */
//                storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            //TODO JAMIL: currentVideoPath should not be accessed here, it belongs to createPostFragment
//            currentVideoPath = absolutePath
//        }
//    }

    // to be in utils
    fun calculateFileSize(file: File): Double {
        val fileLength = if (!file.exists()) 0.0 else file.length().toDouble()
        val fileSizeKB = fileLength / 1024
        val fileSizeMB = fileSizeKB / 1024
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(fileSizeMB).toDouble()
    }

}