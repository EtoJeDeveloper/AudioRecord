package android.bignerdranch.audiorecord.fileSystems

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun getFilesFromPath(path: String, showHiddenFiles: Boolean = false, onlyFolders: Boolean = false): List<File> {
    val file = File(path)
    return file.listFiles()
        .filter { showHiddenFiles || !it.name.startsWith(".") }
        .filter { !onlyFolders || it.isDirectory }
        .toList()
}

fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
    return files.map {
        FileModel(it.path, FileType.getFileType(it), it.name, convertFileSizeToMB(it.length()), it.extension, it.listFiles()?.size
            ?: 0)
    }
}

fun convertFileSizeToMB(sizeInBytes: Long): Double {
    return (sizeInBytes.toDouble()) / (1024 * 1024)
}

fun Context.launchFileIntent(fileModel: FileModel) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = FileProvider.getUriForFile(this, packageName, File(fileModel.path))
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    startActivity(Intent.createChooser(intent, "Select Application"))
}

fun deleteFile(path: String) {
    val file = File(path)
    if (file.isDirectory) {
        file.deleteRecursively()
    } else {
        file.delete()
    }
}