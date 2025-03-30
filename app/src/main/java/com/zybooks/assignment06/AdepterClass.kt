import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zybooks.assignment06.Expense
import com.zybooks.assignment06.ExpenseDetailActivity
import com.zybooks.assignment06.R

class AdepterClass(private val expenses: MutableList<Expense>,
                   private  val onDeleteClick: (Int) -> Unit,
                   private  val onShowDetailClick: (Int) -> Unit,
):
    RecyclerView.Adapter<AdepterClass.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.expenseNameText)
        val amountText: TextView = itemView.findViewById(R.id.amountTextView)
        val textDate: TextView = itemView.findViewById(R.id.dateView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val showDetailsButton: Button = itemView.findViewById(R.id.showDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val expense = expenses[position]
        holder.nameText.text = expense.name
        holder.amountText.text = "$${expense.amount}"
        holder.textDate.text = expense.date

        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
        holder.showDetailsButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, ExpenseDetailActivity::class.java).apply {
                putExtra("expense_name", expense.name)
                putExtra("expense_amount", expense.amount)
                putExtra("expense_date", expense.date)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = expenses.size

}