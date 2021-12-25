package com.example.movieapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.MainActivity
import com.example.movieapplication.R
import com.example.movieapplication.adapter.MovieListAdapter
import com.example.movieapplication.data.models.GetAllMovies
import com.example.movieapplication.data.models.Results
import com.example.movieapplication.data.models.api.ApiHelperImpl
import com.example.movieapplication.data.models.api.RetrofitBuilder
import com.example.movieapplication.databinding.FragmentMovieListBinding
import com.example.movieapplication.utils.Status
import com.example.movieapplication.utils.ViewModelFactory
import com.example.movieapplication.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MovieListFragment : Fragment() ,MovieListAdapter.DataListener {

private lateinit var binding:FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel
    private lateinit var adapter: MovieListAdapter
    private var movieList= mutableListOf<Results>()
    var pageCount=1
    var isLoading=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObserver()
        setupUI()

binding.movieList.addOnScrollListener(object :RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(!isLoading){ val layoutManager = binding.movieList.layoutManager as GridLayoutManager
        if(layoutManager .findLastCompletelyVisibleItemPosition() ==movieList.size-1){
        isLoading=true
        pageCount++
        viewModel.page=pageCount
        viewModel.fetchMovies()
          }
        }
    }
})

    }

    private fun setupViewModel() {
           viewModel=ViewModelProviders.of(this,ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))).get(MovieListViewModel::class.java)
           viewModel.page=pageCount
           viewModel.fetchMovies()
    }

    private fun  setupObserver() {
       viewModel.getMovies().observe(viewLifecycleOwner,{
    when(it.status){
        Status.SUCCESS->{
            (requireActivity() as MainActivity).progressBar.visibility=View.GONE
            binding.movieList.visibility = View.VISIBLE
            it.data?.results?.let { movies -> renderList(movies) }
        }
        Status.LOADING -> {
           (requireActivity() as MainActivity).progressBar.visibility = View.VISIBLE
            binding.movieList.visibility = View.GONE
        }
        Status.ERROR -> {
            (requireActivity() as MainActivity).progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }
       })
    }

     fun renderList(movies: List<Results>) {
         isLoading=false
         movieList.clear()
         movieList.addAll(movies)
         adapter.setData(movies)

    }

    private fun setupUI() {

        binding.movieList.layoutManager =GridLayoutManager(requireContext(),3)
        adapter =MovieListAdapter(this)
        binding.movieList.adapter = adapter
    }

    override fun itemClicked(movieId: Int) {
        val bundle=Bundle()
        bundle.putInt(resources.getString(R.string.movieId), movieId)
       findNavController().navigate(R.id.action_movieListFragment_to_showMovieDetailsFragment,bundle)
    }
}