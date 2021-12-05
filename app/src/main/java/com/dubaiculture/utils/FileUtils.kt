package com.dubaiculture.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


class FileUtils {
     val DATETIME_PATTERN = "yyyyMMdd_HHmmss"
     val FILENAME_PATTERN = "DUBAI_"
     val IMAGE_FILE_TYPE = ".jpeg"

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(activity: Activity): Pair<File, String> {
        // Create an image file name
        val timeStamp: String =
                SimpleDateFormat(DATETIME_PATTERN).format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile(
                FILENAME_PATTERN + "${timeStamp}_", /* prefix */
                IMAGE_FILE_TYPE, /* suffix */
                storageDir /* directory */
        )
        return Pair(tempFile, tempFile.absolutePath)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun getTempUri(content: Context): Uri {
        // Create an image file name
        val timeStamp: String =
                SimpleDateFormat(DATETIME_PATTERN).format(Date())
        val storageDir: File? = content.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return Uri.fromFile(
                File.createTempFile(
                        FILENAME_PATTERN + "${timeStamp}_", /* prefix */
                        IMAGE_FILE_TYPE, /* suffix */
                        storageDir /* directory */
                )
        )
    }


    // to be in utils
    fun calculateFileSize(file: File): Double {
        val fileLength = if (!file.exists()) 0.0 else file.length().toDouble()
        val fileSizeKB = fileLength / 1024
        val fileSizeMB = fileSizeKB / 1024
        val symbols = DecimalFormatSymbols(Locale.US)

        val df = DecimalFormat("#.##",symbols)
        df.roundingMode = RoundingMode.FLOOR
        return df.format(fileSizeMB).toDouble()
    }
    fun checkForImagesFormats(extension: String) : Boolean{
        return extension.toLowerCase() == Constants.PHOTO.FORMAT_JPEG
                || extension.toLowerCase() == Constants.PHOTO.FORMAT_JPG
                || extension.toLowerCase() == Constants.PHOTO.FORMAT_GIF
                || extension.toLowerCase() == Constants.PHOTO.FORMAT_PNG
    }

}