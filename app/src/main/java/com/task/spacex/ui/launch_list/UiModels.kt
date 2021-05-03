package com.task.spacex.ui.launch_list

import androidx.annotation.DrawableRes


sealed class PaginetedCell

data class LaunchCell(
    val id: String,
    val missionName: String,
    val dateAtTime: String,
    val daysToSince: String,
    val daysCount: String,
    @DrawableRes val statusIcon: Int,
    val missionIconUrl: String?,
) : PaginetedCell()

object Separator : PaginetedCell()

sealed class CellUiModel

object LoadingCell : CellUiModel()
data class TextCell(val text: String) : CellUiModel()
data class SeparatorCell(val text: String) : CellUiModel()
