package io.appwrite.starterkit.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AndroidIcon: ImageVector
    get() {
        if (_Android != null) {
            return _Android!!
        }
        _Android = ImageVector.Builder(
            name = "Android",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(40f, 720f)
                quadToRelative(9f, -107f, 65.5f, -197f)
                reflectiveQuadTo(256f, 380f)
                lineToRelative(-74f, -128f)
                quadToRelative(-6f, -9f, -3f, -19f)
                reflectiveQuadToRelative(13f, -15f)
                quadToRelative(8f, -5f, 18f, -2f)
                reflectiveQuadToRelative(16f, 12f)
                lineToRelative(74f, 128f)
                quadToRelative(86f, -36f, 180f, -36f)
                reflectiveQuadToRelative(180f, 36f)
                lineToRelative(74f, -128f)
                quadToRelative(6f, -9f, 16f, -12f)
                reflectiveQuadToRelative(18f, 2f)
                quadToRelative(10f, 5f, 13f, 15f)
                reflectiveQuadToRelative(-3f, 19f)
                lineToRelative(-74f, 128f)
                quadToRelative(94f, 53f, 150.5f, 143f)
                reflectiveQuadTo(920f, 720f)
                close()
                moveToRelative(240f, -110f)
                quadToRelative(21f, 0f, 35.5f, -14.5f)
                reflectiveQuadTo(330f, 560f)
                reflectiveQuadToRelative(-14.5f, -35.5f)
                reflectiveQuadTo(280f, 510f)
                reflectiveQuadToRelative(-35.5f, 14.5f)
                reflectiveQuadTo(230f, 560f)
                reflectiveQuadToRelative(14.5f, 35.5f)
                reflectiveQuadTo(280f, 610f)
                moveToRelative(400f, 0f)
                quadToRelative(21f, 0f, 35.5f, -14.5f)
                reflectiveQuadTo(730f, 560f)
                reflectiveQuadToRelative(-14.5f, -35.5f)
                reflectiveQuadTo(680f, 510f)
                reflectiveQuadToRelative(-35.5f, 14.5f)
                reflectiveQuadTo(630f, 560f)
                reflectiveQuadToRelative(14.5f, 35.5f)
                reflectiveQuadTo(680f, 610f)
            }
        }.build()
        return _Android!!
    }

private var _Android: ImageVector? = null
