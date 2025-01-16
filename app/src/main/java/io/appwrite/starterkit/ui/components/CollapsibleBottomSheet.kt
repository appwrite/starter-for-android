package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.appwrite.starterkit.data.models.Log
import io.appwrite.starterkit.data.models.ProjectInfo
import io.appwrite.starterkit.data.models.mockProjectInfo

/**
 * Column widths for the custom response table.
 */
val columns = listOf(
    "Date" to 150f, "Status" to 80f,
    "Method" to 100f, "Path" to 125f, "Response" to 125f
)

/**
 * A view that displays a collapsible bottom sheet showing logs. It includes a header with a
 * title and a count of logs, and the content of the bottom sheet can be expanded or collapsed
 * based on user interaction.
 *
 * @param title The title displayed at the top of the bottom sheet.
 * @param logs The list of logs to be displayed in the bottom sheet.
 * @param projectInfo Contains project details like endpoint, project ID, project name, and version.
 */
@Composable
fun CollapsibleBottomSheet(
    title: String = "Logs",
    logs: List<Log> = emptyList(),
    projectInfo: ProjectInfo,
) {
    val isExpanded = remember { mutableStateOf(false) }
    val rotateAnimation = animateFloatAsState(
        label = "",
        animationSpec = tween(durationMillis = 250),
        targetValue = if (isExpanded.value) 180f else 0f,
    )

    BoxWithConstraints {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .heightIn(0.dp, maxHeight * 0.575f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = Color(0xffEDEDF0),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = ripple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isExpanded.value = !isExpanded.value
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF56565C),
                                fontWeight = FontWeight.SemiBold,
                            )
                        )

                        Text(
                            text = logs.size.toString(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF56565C),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
                                .background(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.Black.copy(alpha = 0.1f),
                                )
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = when {
                                        logs.size > 99 -> 5.dp
                                        logs.size > 9 -> 3.dp
                                        else -> 2.dp
                                    },
                                )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Chevron Icon
                    Icon(
                        tint = Color(0xff97979B),
                        contentDescription = null,
                        imageVector = Icons.Default.KeyboardArrowDown,
                        modifier = Modifier.rotate(rotateAnimation.value),
                    )
                }

                LogsBottomSheet(
                    logs = logs,
                    projectInfo = projectInfo,
                    isExpanded = isExpanded.value,
                )
            }
        }
    }
}

/**
 * A view to display project information like endpoint, project ID, name, and version.
 *
 * @param projectInfo Contains project details like endpoint, project ID, project name, and version.
 */
@Composable
fun ProjectSection(
    projectInfo: ProjectInfo,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            fontSize = 14.sp,
            text = "Project",
            color = Color(0xFF97979B),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFAFAFB))
                .drawBehind {
                    drawLine(
                        color = Color(0xFFEDEDF0),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                    drawLine(
                        color = Color(0xFFEDEDF0),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, max = 200.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = listOf(
                        "Endpoint" to projectInfo.endpoint,
                        "Project ID" to projectInfo.projectId,
                        "Project name" to projectInfo.projectName,
                        "Version" to projectInfo.version
                    )
                ) { (title, value) ->
                    ProjectRow(title = title, value = value)
                }
            }
        }
    }
}

/**
 * A reusable component to display individual project details like endpoint, project ID, etc.
 *
 * @param title The title of the project row.
 * @param value The value corresponding to the project row.
 */
@Composable
fun ProjectRow(
    title: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            color = Color(0xFF97979B),
        )

        Text(
            text = value,
            maxLines = 1,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = Color(0xFF56565C),
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * A composable that displays a placeholder section when there are no logs available.
 */
@Composable
fun EmptyLogsSection() {
    Column {
        Text(
            fontSize = 14.sp,
            text = "Logs",
            color = Color(0xFF97979B),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFAFAFB))
                .drawBehind {
                    drawLine(
                        color = Color(0xFFEDEDF0),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                    drawLine(
                        color = Color(0xFFEDEDF0),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )

        Text(
            fontSize = 14.sp,
            color = Color(0xFF56565C),
            text = "There are no logs to show",
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

/**
 * A view displaying logs in a table format, including a project section and a list of logs.
 * If there are no logs, a placeholder message is shown.
 *
 * @param logs The list of logs to be displayed in the bottom sheet.
 * @param isExpanded Boolean to indicate if the bottom sheet is expanded or collapsed.
 * @param projectInfo Contains project details like endpoint, project ID, project name, and version.
 */
@Composable
fun LogsBottomSheet(
    logs: List<Log>,
    isExpanded: Boolean,
    projectInfo: ProjectInfo,
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = fadeIn() + expandVertically(
            animationSpec = tween(durationMillis = 500)
        ),
        exit = fadeOut() + shrinkVertically(
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .animateContentSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProjectSection(projectInfo = projectInfo)

            if (logs.isEmpty()) {
                EmptyLogsSection()
            } else {
                Column(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    LogsTableHeader()
                    LazyColumn(
                        modifier = Modifier
                            .animateContentSize()
                            .heightIn(max = 300.dp)
                    ) {
                        items(logs) { log ->
                            LogsTableRow(log = log)
                        }
                    }
                }
            }
        }
    }
}

/**
 * A view to display the header of the logs table, with column names as headers.
 */
@Composable
fun LogsTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFB))
            .drawBehind {
                drawLine(
                    color = Color(0xFFEDEDF0),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
                drawLine(
                    color = Color(0xFFEDEDF0),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        columns.forEach { (name, width) ->
            Text(
                text = name,
                fontSize = 14.sp,
                color = Color(0xFF97979B),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(width.dp)
                    .background(Color(0xFFF9F9FA))
            )
        }
    }

}

/**
 * A component that displays a log row in the table, with dynamic content based on the column name.
 *
 * @param log The log entry containing details like date, status, method, path, and response.
 *
 */
@Composable
fun LogsTableRow(log: Log) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .drawBehind {
                drawLine(
                    color = Color(0xFFEDEDF0),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.5.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        columns.forEach { (name, width) ->
            when (name) {
                "Date" -> Text(
                    text = log.date,
                    fontSize = 14.sp,
                    color = Color(0xFF56565C),
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.width(width.dp)
                )

                "Status" -> StatusTag(
                    status = log.status,
                    modifier = Modifier.width(width.dp)
                )

                "Method" -> Text(
                    text = log.method,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF56565C),
                    modifier = Modifier.width(width.dp)
                )

                "Path" -> Text(
                    text = log.path,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF56565C),
                    modifier = Modifier.width(width.dp)
                )

                "Response" ->
                    Box(modifier = Modifier.width(width.dp)) {
                        Text(
                            maxLines = 1,
                            fontSize = 12.sp,
                            text = log.response,
                            color = Color(0xFF56565C),
                            fontFamily = FontFamily.Monospace,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .background(
                                    Color.Gray.copy(alpha = 0.25f),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }

                else -> Spacer(modifier = Modifier.width(width.dp))
            }
        }
    }
    HorizontalDivider(color = Color(0xFFEDEDF0), thickness = 1.dp)
}

/**
 * A view to display a status tag with conditional styling based on the status value.
 *
 * @param status The status value to display.
 * @param modifier Modifier to style or adjust the layout of the status tag.
 */
@Composable
fun StatusTag(
    status: String,
    modifier: Modifier = Modifier,
) {
    val isSuccessful = status.toIntOrNull() in 200..399
    val textColor = if (isSuccessful) Color(0xFF0A714F) else Color(0xFFB31212)
    val backgroundColor = if (isSuccessful) Color(0x4010B981) else Color(0x40FF453A)

    Box(modifier = modifier) {
        Text(
            text = status,
            style = TextStyle(
                fontSize = 12.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(6.dp),
                    color = backgroundColor.copy(alpha = 0.1f),
                )
                .padding(
                    vertical = 2.dp,
                    horizontal = 5.dp,
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun CollapsibleBottomSheetPreview() {
    val isEmptyState = remember { mutableStateOf(false) }
    val logItems = List(10) {
        listOf(
            Log(
                date = "Dec 10, 02:51",
                status = "200",
                method = "GET",
                path = "/v1/ping",
                response = "Success"
            ),
        )
    }.flatten()

    Scaffold(
        bottomBar = {
            CollapsibleBottomSheet(
                logs = if (isEmptyState.value) listOf() else logItems,
                projectInfo = mockProjectInfo
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                border = BorderStroke(1.dp, Color(0xFFFD366E)),
                colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Transparent),
                onClick = {
                    isEmptyState.value = !isEmptyState.value
                }) {
                Text(
                    fontSize = 16.sp,
                    color = Color.Black,
                    text = "Logs state: ${if (isEmptyState.value) "Empty" else "Full"}",
                )
            }
        }
    }
}