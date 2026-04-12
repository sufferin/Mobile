package com.student.task.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.student.task.presentation.model.CardState
import com.student.task.presentation.model.HolidayUiModel

@Composable
fun HolidayCard(
    uiModel: HolidayUiModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel.cardState) {
        CardState.Default -> {
            DefaultHolidayCard(
                uiModel = uiModel,
                onClick = onClick,
                modifier = modifier
            )
        }

        CardState.Expanded -> {
            DefaultHolidayCard(
                uiModel = uiModel,
                onClick = onClick,
                modifier = modifier
            )
        }

        CardState.Favorite -> {
            DefaultHolidayCard(
                uiModel = uiModel,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun DefaultHolidayCard(
    uiModel: HolidayUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val holiday = uiModel.holiday

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = holiday.category.emoji,
                    style = MaterialTheme.typography.headlineMedium
                )
                CategoryBadge(category = holiday.category)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = holiday.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = holiday.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (holiday.isOfficial) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Выходной день",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun CategoryBadge(category: com.student.task.domain.model.HolidayCategory) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            text = category.displayName,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
