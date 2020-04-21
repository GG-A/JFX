/**
 * @author GG
 */
module gg.jfx {
    requires transitive java.desktop;
    requires transitive gg.os;
    requires transitive gg.functional;

    requires javafx.graphics;

    exports com.github.gg_a.awt;
    exports com.github.gg_a.jfx;
    exports com.github.gg_a.utils;

}