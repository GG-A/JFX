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
package com.github.gg_a.awt;

import com.github.gg_a.function.V1;
import com.github.gg_a.os.OS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 自定义的托盘图标类 <br>
 * Custom tray icon class.
 * @see TrayIcon
 *
 * @author GG
 * @version 0.0.1
 */
public class AwtTray {

    private final static Toolkit toolkit;
    private final static SystemTray tray;
    private TrayIcon trayIcon;
    private Image iconImg;
    private Image blinkImg;
    private boolean isTrayBlink = false;
    private long blinkInterval = 400l;

    private MouseListener trayIconListener;
    private V1<MouseEvent> clickedListener;
    private V1<MouseEvent> pressedListener;
    private V1<MouseEvent> releasedListener;
    private V1<MouseEvent> enteredListener;
    private V1<MouseEvent> exitedListener;


    static {
        toolkit = Toolkit.getDefaultToolkit();
        tray = SystemTray.isSupported() ? SystemTray.getSystemTray() : null;
    }

    /**
     * 获取 AwtTray 实例 <br>
     * 使用之前最好检测一下系统是否支持系统托盘功能{@link SystemTray#isSupported} <br><br>
     * Gets the {@code AwtTray} instance that represents the desktop's {@link TrayIcon}.
     * On some platforms the system tray may not be supported.
     * You may use the {@link SystemTray#isSupported} method to check
     * if the system tray is supported.
     *
     * @param iconImg 托盘图标显示图片（tray icon image）
     */
    public AwtTray(Image iconImg) {
        this.iconImg = iconImg;
        this.trayIcon = new TrayIcon(iconImg);
    }

    /**
     * 获取 AwtTray 实例 <br>
     * 使用之前最好检测一下系统是否支持系统托盘功能{@link SystemTray#isSupported} <br><br>
     * Gets the {@code AwtTray} instance that represents the desktop's {@link TrayIcon}.
     * On some platforms the system tray may not be supported.
     * You may use the {@link SystemTray#isSupported} method to check
     * if the system tray is supported.
     *
     * @param iconImg 托盘图标显示图片（tray icon image）
     * @param blinkImg 托盘图标闪烁图片（tray icon blink image）
     */
    public AwtTray(Image iconImg, Image blinkImg) {
        this.iconImg = iconImg;
        this.blinkImg = blinkImg;
        this.trayIcon = new TrayIcon(iconImg);
    }

    public Image getIconImg() {
        return iconImg;
    }

    public AwtTray setIconImg(Image iconImg) {
        this.iconImg = iconImg;
        if (trayIcon == null) {
            trayIcon = new TrayIcon(iconImg);
        }else {
            trayIcon.setImage(this.iconImg);
        }
        return this;
    }

    public Image getBlinkImg() {
        return blinkImg;
    }

    public AwtTray setBlinkImg(Image blinkImg) {
        this.blinkImg = blinkImg;
        return this;
    }

    public long getBlinkInterval() {
        return blinkInterval;
    }

    public AwtTray setBlinkInterval(long blinkInterval) {
        this.blinkInterval = blinkInterval;
        return this;
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public static Toolkit getToolkit() {
        return toolkit;
    }

    public static SystemTray getTray() {
        return tray;
    }

    /**
     * 添加应用托盘图标 <br>
     * add the application tray icon to the system tray.
     */
    public void addToTray() {
        SwingUtilities.invokeLater(() -> {
            if (tray != null) {
                try {
                    tray.add(AwtTray.this.trayIcon);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 移除应用托盘图标 <br>
     * remove the application tray icon from the system tray.
     */
    public void removeFromTray() {
        if (tray != null)  tray.remove(AwtTray.this.trayIcon);
    }

    /**
     * 显示系统通知，只对windows有效 <br>
     * displays a notification message, Windows only.
     *
     * @param caption the caption displayed above the text, usually in
     *      * bold; may be {@code null}
     * @param text the text displayed for the particular message; may be
     *      * {@code null}
     * @param messageType an enum indicating the message type
     */
    public void displayMessage(String caption, String text, TrayIcon.MessageType messageType) {
        if (OS.IS_WINDOWS) {
            SwingUtilities.invokeLater(() ->
                    trayIcon.displayMessage(caption, text, messageType)
            );
        }
    }

    public void addMouseListener(AwtEventType eventType, V1<MouseEvent> mouselistener) {
        switch (eventType) {
            case MOUSE_CLICKED:
                clickedListener = mouselistener;
                break;
            case MOUSE_PRESSED:
                pressedListener = mouselistener;
                break;
            case MOUSE_RELEASED:
                releasedListener = mouselistener;
                break;
            case MOUSE_ENTERED:
                enteredListener = mouselistener;
                break;
            case MOUSE_EXITED:
                exitedListener = mouselistener;
                break;
            default:
                clickedListener = mouselistener;
        }

        trayIconMouseListener();

    }

    public void removeMouseListener(AwtEventType eventType) {
        switch (eventType) {
            case MOUSE_CLICKED:
                clickedListener = null;
                break;
            case MOUSE_PRESSED:
                pressedListener = null;
                break;
            case MOUSE_RELEASED:
                releasedListener = null;
                break;
            case MOUSE_ENTERED:
                enteredListener = null;
                break;
            case MOUSE_EXITED:
                exitedListener = null;
                break;
            default:
                clickedListener = null;
        }
        trayIconMouseListener();
    }

    private void trayIconMouseListener() {
        trayIcon.removeMouseListener(trayIconListener);     // 先移除之前的 MouseListener ，否则会重复添加

        trayIconListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickedListener != null) clickedListener.$(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (pressedListener != null) pressedListener.$(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (releasedListener != null) releasedListener.$(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (enteredListener != null) enteredListener.$(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickedListener != null) clickedListener.$(e);
            }
        };

        trayIcon.addMouseListener(trayIconListener);
    }

    public void setPopupMenu(PopupMenu popup) {
        trayIcon.setPopupMenu(popup);
    }

    /**
     * 托盘图标闪烁效果 <br>
     * tray blink.
     */
    public void trayBlink() {
        isTrayBlink = true;
        new Thread(() -> {
            while (isTrayBlink) {
                try {
                    Thread.sleep(blinkInterval);
                    trayIcon.setImage(blinkImg);
                    Thread.sleep(blinkInterval);
                    trayIcon.setImage(iconImg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 停止托盘图标闪烁
     */
    public void stopBlink() {
        isTrayBlink = false;
    }

}
