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
package com.github.gg_a.jfx;

import com.github.gg_a.function.R0;
import com.github.gg_a.function.V0;
import com.github.gg_a.function.V1;
import javafx.application.Platform;

import java.util.concurrent.*;

/**
 * 刷新UI
 * @author GG
 * @version 0.0.1
 */
public class RefreshUI {

    private static ScheduledExecutorService stp = new ScheduledThreadPoolExecutor(20) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future) {
                try {
//                    Object result = ((Future<?>) r).get();
                    Future<?> future = (Future<?>) r;
                    if (future.isDone()) {
                        future.get();
                    }
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }

            if (t != null) {
                t.printStackTrace();
            }
        }
    };

    /**
     * 更新UI
     * @param action UI操作
     */
    public static void run(final Runnable action){
        Platform.runLater(action);
    }

    /**
     * 等待一段时间后再更新UI
     * @param delay 延迟时间（毫秒）
     * @param action UI操作
     */
    public static void runDelay(final long delay, final Runnable action) {
        stp.schedule(() -> {
            run(action);
        }, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行某些耗时（Time-consuming）操作并获取操作结果之后，再更新UI
     * @param action 耗时操作
     * @param uiAction UI操作
     * @param <T> 耗时操作以后返回结果类型
     */
    public static <T> void runAfterAction(final R0<T> action , final V1<T> uiAction) {
        stp.execute(() -> {
            final T actionResult = action.$();
            run(() -> uiAction.$(actionResult));
        });
    }

    /**
     * 执行某些耗时（Time-consuming）操作之后，再更新UI
     * @param action 耗时操作
     * @param uiAction UI操作
     */
    public static void runAfterAction(final V0 action , final V0 uiAction) {
        stp.execute(() -> {
            action.$();
            run(uiAction::$);
        });
    }

}
