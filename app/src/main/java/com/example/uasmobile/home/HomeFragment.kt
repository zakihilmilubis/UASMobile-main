package com.example.uasmobile.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import adapter.LogoAdapter
import api.ApiConfig
import api.ResponseItem
import com.example.uasmobile.databinding.HomeFragmentBinding
import repository.NoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var agentsAdapter: LogoAdapter
    private lateinit var binding: HomeFragmentBinding
    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.logo_recycler_view)
        val application = requireNotNull(this.activity).application
        noteRepository = NoteRepository(application)
        agentsAdapter = LogoAdapter(ArrayList(), noteRepository)
        recyclerView.adapter = agentsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getAgents()

        return view
    }

    private fun getAgents() {
        val client = ApiConfig.getApiService().getAgents("Sport")

        client.enqueue(object:Callback<List<ResponseItem>>{
            override fun onResponse(
                call: Call<List<ResponseItem>>,
                response: Response<List<ResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val adapter = responseBody?.let { LogoAdapter(it, noteRepository) }
                    binding.logoRecyclerView.adapter = adapter
                }
                else {
                    Log.e("Home Fragment" , "network call failure on response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                Log.e("Home Fragment","network call failure: ${t.message}")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}