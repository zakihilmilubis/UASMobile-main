package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import database.Note
import database.NoteDao
import com.example.uasmobile.databinding.ItemRowFavLogoBinding
import repository.NoteRepository

class FavLogoAdapter(private val noteRepository: NoteRepository) : RecyclerView.Adapter<FavLogoAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()

    inner class NoteViewHolder(private val binding: ItemRowFavLogoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemName.text = note.name
                tvItemDescription.text = note.ticker

                Glide.with(itemView.context)
                    .load(note.img)
                    .into(imgItemPhoto)
            }
        }

        init {
            binding.deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val noteToDelete = listNotes[position]

                    noteToDelete.name?.let { name ->
                        noteToDelete.ticker?.let { ticker ->
                            noteToDelete.img?.let { img ->
                                noteRepository.deleteExistingNote(name, ticker, img)

                                listNotes.removeAt(position)
                                notifyItemRemoved(position)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemRowFavLogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        listNotes.clear()
        listNotes.addAll(notes)
        notifyDataSetChanged()
    }
}