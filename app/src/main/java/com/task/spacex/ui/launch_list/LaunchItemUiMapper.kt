package com.task.spacex.ui.launch_list

import com.task.spacex.R
import com.task.spacex.repository.domain.LaunchDomain
import com.task.spacex.ui.launch_list.RocketListViewModel.LaunchItemUiModel
import com.task.spacex.util.StringsWrapper
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class LaunchItemUiMapper @Inject constructor(
    private val strings: StringsWrapper
) {

    fun map(launch: LaunchDomain): LaunchItemUiModel =
        LaunchItemUiModel(
            missionName = launch.missionName,
            dateAtTime = formatDateAtTimeLabel(launch.offsetDateTime),
            daysToSince = resolveToSinceLabel(launch),
            daysCount = resolveDaysCount(launch),
            statusIcon = resolveIcon(launch),
            missionIconUrl = launch.patchURL,
            domain = launch
        )

    private fun resolveIcon(launch: LaunchDomain): Int {
        return when(launch.success) {
            true -> R.drawable.ic_baseline_success
            false -> R.drawable.ic_baseline_fail
            null -> R.drawable.ic_timer
        }
    }

    private fun resolveToSinceLabel(launch: LaunchDomain): String {
        val stringId = if (launch.upcoming) R.string.to_launch else R.string.since_launch
        return strings.resolve(stringId)
    }

    private fun resolveDaysCount(launch: LaunchDomain): String {
        return if(launch.upcoming) {
            ChronoUnit.DAYS.between(OffsetDateTime.now(), launch.offsetDateTime).toString()
        } else {
            ChronoUnit.DAYS.between(launch.offsetDateTime, OffsetDateTime.now()).toString()
        }
    }



    private fun formatDateAtTimeLabel(dateTime: OffsetDateTime): String {
        val dateFormat = strings.resolve(R.string.date_format)
        val timeFormat = strings.resolve(R.string.time_format)
        val date = DateTimeFormatter.ofPattern(dateFormat).format(dateTime).toString()
        val time = DateTimeFormatter.ofPattern(timeFormat).format(dateTime).toString()
        return strings.resolve(R.string.date_at_time, date, time)
    }

}
