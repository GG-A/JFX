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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * 资源加载工具类
 *
 * @author GG
 * @version 0.0.1
 * @since 0.0.1
 */
public class ResourcesUtils {
    /**
     * 通过字节码对象 获取项目资源的URL
     * @param resName 资源路径
     * @param clazz 字节码对象
     * @return URL
     */
    public static URL getUrl(String resName, Class<?> clazz) {
        return clazz.getResource(resName);
    }

    /**
     * 通过字节码对象 获取项目资源的输入流 InputStream
     * @param resName 资源路径
     * @param clazz 字节码对象
     * @return InputStream
     */
    public static InputStream getStream(String resName, Class<?> clazz) {
        return clazz.getResourceAsStream(resName);
    }

    /**
     * 通过ClassLoader 获取项目资源的URL
     * @param resName 资源路径
     * @return URL
     */
    public static URL getUrlByCL(String resName) {
        if (resName.startsWith("/") || resName.startsWith("\\"))  resName = resName.substring(1);
        ClassLoader loader = firstNonNull(Thread.currentThread().getContextClassLoader(), ResourcesUtils.class.getClassLoader());
        return loader.getResource(resName);
    }

    /**
     * 通过ClassLoader 获取项目资源的输入流 InputStream
     * @param resName 资源路径
     * @return InputStream
     */
    public static InputStream getStreamByCL(String resName) {
        if (resName.startsWith("/") || resName.startsWith("\\"))  resName = resName.substring(1);
        ClassLoader loader = firstNonNull(Thread.currentThread().getContextClassLoader(), ResourcesUtils.class.getClassLoader());
        return loader.getResourceAsStream(resName);
    }

    /**
     * 通过module 获取项目资源的输入流 InputStream
     * @param resName 资源路径
     * @param clazz 字节码对象
     * @return InputStream
     */
    public static InputStream getResStream(String resName, Class<?> clazz) {
        try {
            return clazz.getModule().getResourceAsStream(resName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取项目资源URL的字符串形式（以 file:/ 协议开头）
     *
     * @param resName 资源路径
     * @param clazz 字节码对象
     * @return String
     */
    public static String getUrlStr(String resName, Class<?> clazz) {
        return getUrl(resName, clazz).toString();
    }

    /**
     * 获取项目资源URL的字符串形式（以 file:/ 协议开头）
     *
     * @param resName 资源路径
     * @return String
     */
    public static String getUrlStr(String resName) {
        return getUrlByCL(resName).toString();
    }

    /**
     * 从文件中获取URL
     *
     * @param file 文件对象
     * @return URL
     */
    public static URL getUrlFromFile(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件绝对路径中获取URL
     *
     * @param absolutePath 绝对路径
     * @return URL
     */
    public static URL getUrlFromPath(String absolutePath) {
        try {
            return new File(absolutePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件中获取URL的字符串形式（以 file:/ 协议开头）
     *
     * @param file 文件对象
     * @return String
     */
    public static String getUrlStrFromFile(File file) {
        return Objects.requireNonNull(getUrlFromFile(file)).toString();
    }

    /**
     * 从绝对路径中获取URL的字符串形式（以 file:/ 协议开头）
     *
     * @param absolutePath 绝对路径
     * @return String
     */
    public static String getUrlStrFromPath(String absolutePath) {
        return Objects.requireNonNull(getUrlFromPath(absolutePath)).toString();
    }

    /**
     * 获取网络资源的URL
     *
     * @param resSite 网络资源网址
     * @return URL
     */
    public static URL getUrlFromNet(String resSite) {
        try {
            return new URL(resSite);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T firstNonNull(T first, T second) {
        return first != null ? first : second;
    }
}
