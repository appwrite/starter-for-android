package io.appwrite.starterkit.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppwriteIcon: ImageVector
    get() {
        if (_Appwrite != null) {
            return _Appwrite!!
        }
        _Appwrite = ImageVector.Builder(
            name = "Logo",
            defaultWidth = 112.dp,
            defaultHeight = 98.dp,
            viewportWidth = 112f,
            viewportHeight = 98f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFD366E)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(111.1f, 73.4729f)
                verticalLineTo(97.9638f)
                horizontalLineTo(48.8706f)
                curveTo(30.74060f, 97.96380f, 14.91050f, 88.1140f, 6.44110f, 73.47290f)
                curveTo(5.20990f, 71.34440f, 4.13230f, 69.11130f, 3.22830f, 66.79350f)
                curveTo(1.45390f, 62.25160f, 0.33840f, 57.37790f, 00f, 52.29260f)
                verticalLineTo(45.6712f)
                curveTo(0.07350f, 44.53790f, 0.18920f, 43.41350f, 0.34060f, 42.30250f)
                curveTo(0.65010f, 40.02270f, 1.11770f, 37.79180f, 1.73220f, 35.62320f)
                curveTo(7.54540f, 15.06410f, 26.4480f, 00f, 48.87060f, 00f)
                curveTo(71.29320f, 00f, 90.19350f, 15.06410f, 96.00680f, 35.62320f)
                horizontalLineTo(69.3985f)
                curveTo(65.03020f, 28.92160f, 57.46920f, 24.4910f, 48.87060f, 24.4910f)
                curveTo(40.2720f, 24.4910f, 32.7110f, 28.92160f, 28.34270f, 35.62320f)
                curveTo(27.01130f, 37.66040f, 25.97820f, 39.90690f, 25.30140f, 42.30250f)
                curveTo(24.70020f, 44.42660f, 24.37960f, 46.66640f, 24.37960f, 48.98190f)
                curveTo(24.37960f, 56.00190f, 27.33190f, 62.32950f, 32.06530f, 66.79350f)
                curveTo(36.45150f, 70.93690f, 42.36490f, 73.47290f, 48.87060f, 73.47290f)
                horizontalLineTo(111.1f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFD366E)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(111.1f, 42.3027f)
                verticalLineTo(66.7937f)
                horizontalLineTo(65.6759f)
                curveTo(70.40940f, 62.32970f, 73.36160f, 56.00210f, 73.36160f, 48.98210f)
                curveTo(73.36160f, 46.66660f, 73.0410f, 44.42680f, 72.43990f, 42.30270f)
                horizontalLineTo(111.1f)
                close()
            }
        }.build()
        return _Appwrite!!
    }

private var _Appwrite: ImageVector? = null
