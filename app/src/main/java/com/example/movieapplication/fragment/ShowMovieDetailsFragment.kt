package com.example.movieapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapplication.MainActivity
import com.example.movieapplication.R
import com.example.movieapplication.adapter.MovieListAdapter
import com.example.movieapplication.adapter.MovieTypeAdapter
import com.example.movieapplication.data.models.Genres
import com.example.movieapplication.data.models.GetMovieDetailsById
import com.example.movieapplication.data.models.Results
import com.example.movieapplication.data.models.api.ApiHelperImpl
import com.example.movieapplication.data.models.api.RetrofitBuilder
import com.example.movieapplication.databinding.FragmentShowMovieDetailsBinding
import com.example.movieapplication.utils.Status
import com.example.movieapplication.utils.ViewModelFactory
import com.example.movieapplication.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_item_layout.view.*


class ShowMovieDetailsFragment : Fragment() {

private lateinit var binding:FragmentShowMovieDetailsBinding
    private lateinit var viewModel: MovieListViewModel
    private lateinit var adapter: MovieTypeAdapter
    var id: Int? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_show_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null)
            id= requireArguments().getInt(resources.getString(R.string.movieId))

        setupViewModel()
        setupObserver()
        setupUI()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setupViewModel() {
        viewModel= ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))).get(MovieListViewModel::class.java)
        viewModel.id=id
        viewModel.getMovieDetails()
    }
    private fun  setupObserver() {
        viewModel.getMovieById().observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS->{
                    (requireActivity() as MainActivity).progressBar.visibility=View.GONE
                    binding.generesRecycler.visibility = View.VISIBLE
                    setData(it.data)
                    it.data?.genres?.let { genres -> renderList(genres) }
                }
                Status.LOADING -> {
                    (requireActivity() as MainActivity).progressBar.visibility = View.VISIBLE
                    binding.generesRecycler.visibility = View.GONE
                }
                Status.ERROR -> {
                    (requireActivity() as MainActivity).progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setData(data: GetMovieDetailsById?) {
        binding.movieTitle.text=data?.title
        binding.releaseDate.text=data?.release_date
        binding.votes.text= data?.vote_count.toString()+"(votes)"
        binding.details.text= data?.overview
        binding.language.text=data?.original_language
        Glide.with(requireContext())
            .load(data?.belongs_to_collection?.poster_path)
            .error(R.drawable.movie_time)
            .fitCenter()
            .into(binding.imagePoster)

    }

    private fun renderList(genres: List<Genres>) {
        adapter.setData(genres)

    }
    private fun setupUI() {
        binding.generesRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        adapter =MovieTypeAdapter()
        binding.generesRecycler.adapter = adapter
    }
}