package com.example.jejutalae

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jejutalae.ui.theme.Typography


@Composable
fun TopSearchBar(modifier: Modifier = Modifier) {
    var text1 by remember { mutableStateOf("제주국제공항") }
    var text2 by remember { mutableStateOf("오설록 티 뮤지엄") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RectangleShape,
                color = Color(0x44000000),
                blur = 4.dp,
                offsetX = (-1).dp,
                offsetY = 4.dp,
                spread = 0.dp
            )
            .background(color = Color(0xFFFFFFFF), shape = RectangleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            val temp = text1
            text1 = text2
            text2 = temp
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.group_4_2x),
                contentDescription = null
            )
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = text1,
                onValueChange = { text1 = it },
                shape = RoundedCornerShape(10.dp), // 네 모서리 둥글게 설정
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F8F8),
                    unfocusedContainerColor = Color(0xFFF8F8F8),
                    disabledContainerColor = Color(0xFFF8F8F8),
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                ),
                textStyle = Typography.labelSmall.copy(color = Color.Black),
                trailingIcon = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.delevesvg),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                text1 = ""
                            })
                })
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = text2,
                onValueChange = { text2 = it },
                shape = RoundedCornerShape(10.dp), // 네 모서리 둥글게 설정
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F8F8),
                    unfocusedContainerColor = Color(0xFFF8F8F8),
                    disabledContainerColor = Color(0xFFF8F8F8),
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                ),
                textStyle = Typography.labelSmall.copy(color = Color.Black),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "My Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}