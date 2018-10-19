package varo.com.moviefinder.util

import varo.com.data.remote.config.HostConfig
import varo.com.moviefinder.config.Config

class ImageSizingUtil {

    companion object {

        fun getPathForThumbnail(
            path: String) = HostConfig.BASE_IMAGE_URL + Config.THUMBNAIL_SIZE + path

        fun getPathForBanner(
            path: String) = HostConfig.BASE_IMAGE_URL + Config.BANNER_SIZE + path
    }
}