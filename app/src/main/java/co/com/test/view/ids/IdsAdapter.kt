package co.com.test.view.ids

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.com.test.R
import co.com.test.databinding.AdapterIdsItemBinding
import co.com.test.extention.click

import co.com.test.util.BindingHolder

/**
 * @desc this is adapter class will handle list of ids and display id in view
 * @author : Mahesh Vayak
 * @required
 **/
class IdsAdapter(
    private var idsList: MutableList<String>,
    private val onItemClick: (String) -> Unit = { _ -> }
) : RecyclerView.Adapter<BindingHolder<AdapterIdsItemBinding>>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<AdapterIdsItemBinding> {
        return BindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_ids_item,
                parent,
                false
            )
        )
    }

    /**
     * @desc ids list size count.
     * @return int- array size
     */
    override fun getItemCount(): Int {
        return idsList.size
    }

    /**
     *@desc Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: BindingHolder<AdapterIdsItemBinding>, position: Int) {
        holder.binding.textViewId.text = idsList[holder.adapterPosition]
        holder.binding.textViewId.click {
            onItemClick(idsList[holder.adapterPosition])
        }

    }


}
