package com.student.task.ui.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.student.task.databinding.ItemHolidayCardBinding
import com.student.task.databinding.ItemLoadingMoreBinding
import com.student.task.presentation.model.CardState
import com.student.task.presentation.model.HolidayUiModel

class HolidayAdapter(
    private val onCardClick: (Int) -> Unit,
    private val onFavoriteClick: (Int) -> Unit
) : ListAdapter<HolidayAdapter.ListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private var isLoadingMore = false

    sealed class ListItem {
        data class HolidayItem(val uiModel: HolidayUiModel) : ListItem()
        data object LoadingItem : ListItem()
    }

    fun submitHolidays(holidays: List<HolidayUiModel>, loadingMore: Boolean) {
        isLoadingMore = loadingMore
        val items = mutableListOf<ListItem>()
        items.addAll(holidays.map { ListItem.HolidayItem(it) })
        if (loadingMore) {
            items.add(ListItem.LoadingItem)
        }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.HolidayItem -> VIEW_TYPE_HOLIDAY
            is ListItem.LoadingItem -> VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HOLIDAY -> {
                val binding = ItemHolidayCardBinding.inflate(inflater, parent, false)
                HolidayViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val binding = ItemLoadingMoreBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.HolidayItem -> (holder as HolidayViewHolder).bind(item.uiModel)
            is ListItem.LoadingItem -> { }
        }
    }

    inner class HolidayViewHolder(
        private val binding: ItemHolidayCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: HolidayUiModel) {
            val holiday = uiModel.holiday
            val isExpanded = uiModel.cardState == CardState.Expanded
            val isFavorite = uiModel.cardState == CardState.Favorite

            binding.emojiText.text = holiday.category.emoji
            binding.categoryBadge.text = holiday.category.displayName
            binding.holidayName.text = holiday.name
            binding.holidayDate.text = holiday.date
            binding.holidayDescription.text = holiday.description
            binding.officialBadge.visibility = if (holiday.isOfficial) View.VISIBLE else View.GONE

            binding.cardRoot.setOnClickListener {
                android.transition.TransitionManager.beginDelayedTransition(binding.cardRoot as ViewGroup)
                onCardClick(holiday.id)
            }

            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(holiday.id)
            }

            // Expanded State
            binding.holidayDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.expandIndicator.animate()
                .rotation(if (isExpanded) 180f else 0f)
                .setDuration(300)
                .start()

            // Favorite State
            val favoriteIcon = if (isFavorite) {
                android.R.drawable.btn_star_big_on
            } else {
                android.R.drawable.btn_star_big_off
            }
            binding.favoriteButton.setImageResource(favoriteIcon)

            val backgroundColor = if (isFavorite) {
                android.graphics.Color.parseColor("#FFF9C4") // Light Yellow
            } else if (isExpanded) {
                android.graphics.Color.parseColor("#F5F5F5") // Light Gray
            } else {
                android.graphics.Color.WHITE
            }
            binding.cardRoot.setCardBackgroundColor(backgroundColor)
            
            val elevation = if (isFavorite) 8f else 4f
            binding.cardRoot.cardElevation = elevation * binding.root.resources.displayMetrics.density
        }

    }

    class LoadingViewHolder(binding: ItemLoadingMoreBinding) : RecyclerView.ViewHolder(binding.root)

    private class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.HolidayItem && newItem is ListItem.HolidayItem ->
                    oldItem.uiModel.holiday.id == newItem.uiModel.holiday.id
                oldItem is ListItem.LoadingItem && newItem is ListItem.LoadingItem -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_HOLIDAY = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}
