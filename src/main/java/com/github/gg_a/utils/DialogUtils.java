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

import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 资源加载工具类
 *
 * @author GG
 * @version 0.0.3
 * @since 0.0.3
 */
public class DialogUtils {
    public static Stage getStage(Dialog<?> dialog){
        return (Stage) dialog.getDialogPane().getScene().getWindow();
    }

    public static void setIcon(Dialog<?> dialog, String imagePath) {
        getStage(dialog).getIcons().add(ImageUtils.getImage(imagePath));
    }

    public static void setIcon(Dialog<?> dialog, Image image) {
        getStage(dialog).getIcons().add(image);
    }
}
