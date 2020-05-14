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

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author GG
 * @version 0.0.1
 * @since 0.0.1
 */
public class AwtImgUtils {
    /**
     * 获取 AWT Image 对象
     * @param resPath 资源路径
     * @param clazz 字节码对象
     * @return Image
     */
    public static Image getImage(String resPath, Class<?> clazz){
        try {
            return ImageIO.read(ResourcesUtils.getStream(resPath, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 AWT Image 对象
     * @param resPath 资源路径
     * @return Image
     */
    public static Image getImage(String resPath){
        try {
            return ImageIO.read(ResourcesUtils.getStreamByCL(resPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 AWT Image 对象
     * @param stream 输入流
     * @return Image
     */
    public static Image getImage(InputStream stream){
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 AWT Image 对象
     * @param url URL
     * @return Image
     */
    public static Image getImage(URL url){
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
