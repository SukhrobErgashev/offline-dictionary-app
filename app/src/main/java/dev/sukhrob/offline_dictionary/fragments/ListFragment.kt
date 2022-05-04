package dev.sukhrob.offline_dictionary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.sukhrob.offline_dictionary.R
import dev.sukhrob.offline_dictionary.adapter.CursorAdapter
import dev.sukhrob.offline_dictionary.databinding.FragmentListBinding
import dev.sukhrob.offline_dictionary.viewmodel.ListViewModel


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: CursorAdapter by lazy { CursorAdapter() }
    private val viewModel: ListViewModel by viewModels()

    private var mQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // Set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup RecyclerView
        setupRecyclerView()

        viewModel.loadWords()

        viewModel.cursorWordList.observe(viewLifecycleOwner) {
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
                startActivity(Intent.createChooser(intent, "Share Via")
                )
            }
            //dialog.setCancelable(false)
            //dialog.dismiss()

            dialog.setContentView(view)
            dialog.show()

            //"text", "${it.word}\n\n${it.wordType}$\n\n{it.definition}"
        }

        adapter.bookmarkListener = {
            viewModel.update(it)
            if (mQuery == "") {
                viewModel.loadWords()
            } else viewModel.search(mQuery)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_frag_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_bookmark) {
            findNavController().navigate(R.id.action_listFragment_to_bookmarksFragment)
        } else if (item.itemId == R.id.menu_about) {
            findNavController().navigate(R.id.action_listFragment_to_aboutFragment)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = this@ListFragment.adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            mQuery = query
            viewModel.search(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            mQuery = newText
            viewModel.search(newText)
        }
        return true
    }

}