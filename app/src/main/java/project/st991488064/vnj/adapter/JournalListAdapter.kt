package project.st991488064.vnj.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.activityType
import kotlinx.android.synthetic.main.list_item.view.date
import kotlinx.android.synthetic.main.list_item.view.distance
import kotlinx.android.synthetic.main.list_item.view.duration
import kotlinx.android.synthetic.main.list_item.view.image_view
import kotlinx.android.synthetic.main.list_item.view.time
import kotlinx.android.synthetic.main.list_journal.view.*
import project.st991488064.vnj.R
import project.st991488064.vnj.database.models.ActivitiesEntity
import project.st991488064.vnj.fragments.JournalFragmentDirections
import project.st991488064.vnj.fragments.TitleFragmentDirections
import java.util.*

class JournalListAdapter : RecyclerView.Adapter<JournalListAdapter.MyViewHolder>() {

    var activityList = emptyList<ActivitiesEntity>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_journal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = activityList[position]
        val distanceString = currentItem.distance + " km"
        val setString = currentItem.distance + " sets"
        holder.itemView.activityType.text = currentItem.activityName
        holder.itemView.date.text = currentItem.activityDate
        holder.itemView.time.text = currentItem.startTime
        holder.itemView.duration.text = currentItem.endTime
        holder.itemView.duration.text = currentItem.duration

        when {
            currentItem.activityName.toLowerCase(Locale.ROOT) == "cycling" -> {
                holder.itemView.distance.text = distanceString
                holder.itemView.image_view.setImageResource(R.drawable.ic_baseline_directions_bike_24)
            }
            currentItem.activityName.toLowerCase(Locale.ROOT) == "running" -> {
                holder.itemView.distance.text = distanceString
                holder.itemView.image_view.setImageResource(R.drawable.ic_baseline_directions_run_24)
            }
            currentItem.activityName.toLowerCase(Locale.ROOT) == "weight lifting" -> {
                holder.itemView.distance.text = setString
                holder.itemView.image_view.setImageResource(R.drawable.ic_baseline_fitness_center_24)
            }
            else -> {
                holder.itemView.image_view.setImageResource(R.drawable.ic_baseline_error_outline_24)
            }
        }

        //for journal fragment
        holder.itemView.journalRowLayout.setOnClickListener {
            val action =
                JournalFragmentDirections.actionJournalFragmentToDetailActivityFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(cycle: List<ActivitiesEntity>) {
        this.activityList = cycle
        notifyDataSetChanged()
    }


}