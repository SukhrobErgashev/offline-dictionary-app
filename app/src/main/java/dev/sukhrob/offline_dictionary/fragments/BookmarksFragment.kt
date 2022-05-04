package dev.sukhrob.offline_dictionary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.sukhrob.offline_dictionary.R
import dev.sukhrob.offline_dictionary.adapter.CursorAdapter
import dev.sukhrob.offline_dictionary.databinding.FragmentBookmarksBinding
import dev.sukhrob.offline_dictionary.viewmodel.ListViewModel


class BookmarksFragment: Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val adapter: CursorAdapter by lazy { CursorAdapter() }
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()

        viewModel.getBookmarks()

        viewModel.bookmarks.observe(viewLifecycleOwner) {
            adapter.submitCursor(it)
        }

        adapter.listener = {
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetMainStyle)
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
            view.findViewById<TextView>(R.id.bsh_word).text = it.word
            view.findViewById<TextView>(R.id.bsh_word_type).text = " " + it.wordType
            view.findViewById<TextView>(R.id.bsh_definition).text = it.definition

            val shareBody = "Word: ${it.word}\nWord type: ${it.wordType}\nWord definition: ${it.definition}"
            view.findViewById<ViewGroup>(R.id.bsh_share).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share Subject")
                intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(
                    Intent.createChooser(intent, "Share Via")
                )
            }
            //dialog.setCancelable(false)
            //dialog.dismiss()
            dialog.setContentView(view)
            dialog.show()
        }

        adapter.bookmarkListener = {
            viewModel.update(it)
            viewModel.getBookmarks()
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewBookmarks.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewBookmarks.adapter = this@BookmarksFragment.adapter
            recyclerViewBookmarks.addItemDecoration(
                DividerItemDecoration(
                    recyclerViewBookmarks.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}