/*
 * Copyright 2019 GG-A, <2018158885@qq.com, https://github.com/GG-A/JFX>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.gg_a.utils;

import javafx.scene.image.Image;

import java.io.File;

/**
 * @author GG
 * @version 0.0.1
 */
public class ImageUtils {
    /**
     * 从项目路径中获取Image对象
     * @param resPath 资源路径
     * @param clazz 字节码对象
     * @return Image
     */
    public static Image getImage(String resPath, Class<?> clazz) {
        return new Image(ResourcesUtils.getUrlStr(resPath, clazz));
    }

    /**
     * 从项目路径中获取Image对象
     * @param resPath 资源路径
     * @return Image
     */
    public static Image getImage(String resPath) {
        return new Image(ResourcesUtils.getUrlStr(resPath));
    }

    /**
     * 从File对象中获取Image对象
     * @param file file对象
     * @return Image
     */
    public static Image getImageFromFile(File file) {
        return new Image(ResourcesUtils.getUrlStrFromFile(file));
    }

    /**
     * 从绝对路径中获取Image对象
     * @param absPath 绝对路径
     * @return Image
     */
    public static Image getImageFromAbsPath(String absPath) {
        return new Image(ResourcesUtils.getUrlStrFromPath(absPath));
    }
}
