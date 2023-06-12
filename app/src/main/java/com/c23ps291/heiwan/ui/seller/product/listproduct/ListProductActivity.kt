package com.c23ps291.heiwan.ui.seller.product.listproduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ActivityListProductBinding
import com.c23ps291.heiwan.ui.common.OnItemClickCallback
import com.c23ps291.heiwan.ui.common.adapter.AnimalAdapter
import com.c23ps291.heiwan.ui.seller.product.crudproduct.AddUpdateProductActivity
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductBinding

    private lateinit var auth: FirebaseAuth

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                AddUpdateProductActivity.RESULT_ADD -> {
                    getDataProducts(auth.currentUser?.uid.orEmpty())
                    val message =
                        result.data?.getStringExtra(AddUpdateProductActivity.RESULT_ADD_MESSAGE)
                    showSnackBarMessage(message)
                }

                AddUpdateProductActivity.RESULT_UPDATE -> {
                    getDataProducts(auth.currentUser?.uid.orEmpty())
                    val message =
                        result.data?.getStringExtra(AddUpdateProductActivity.RESULT_UPDATE_MESSAGE)
                    showSnackBarMessage(message)
                }

                AddUpdateProductActivity.RESULT_DELETE -> {
                    getDataProducts(auth.currentUser?.uid.orEmpty())
                    val message =
                        result.data?.getStringExtra(AddUpdateProductActivity.RESULT_DELETE_MESSAGE)
                    showSnackBarMessage(message)

                }
            }
        }
    }

    private val viewModel: ListProductViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        getDataProducts(auth.currentUser?.uid.orEmpty())

        binding.apply {
            toolbar.apply {
                title = getString(R.string.text_btn_my_store)
                setNavigationOnClickListener { finish() }
            }
            fabAdd.setOnClickListener {
                goToAddMode()
            }
        }

    }

    private fun showSnackBarMessage(message: String?) {
        Snackbar.make(
            binding.root as ViewGroup,
            message.orEmpty(),
            Snackbar.LENGTH_LONG
        ).setAnchorView(binding.fabAdd).show()
    }

    private fun getDataProducts(id: String) {
        viewModel.getProducts(id).observe(this@ListProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    it.data?.data?.let { product -> populateData(product) }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@ListProductActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun populateData(data: List<Animal>) {
        binding.apply {
            viewNoData.root.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
            viewNoData.tvNoData.text = getString(R.string.no_product)
        }

        val animalAdapter = AnimalAdapter(data, object : OnItemClickCallback {
            override fun onItemClicked(animal: Animal) {
                goToEditMode(animal.id)
            }

        })
        binding.rvAnimal.apply {
            layoutManager = GridLayoutManager(this@ListProductActivity, 2)
            setHasFixedSize(true)
            adapter = animalAdapter
        }
    }

    /*
    private fun getDataDetailSeller(id: String) {
        viewModel.getDetailSeller(id).observe(this@ListProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val sizeSeller = it.data?.seller?.size ?: 0
                    if (sizeSeller > 0) {
                        val idSeller = it.data?.seller?.get(0)?.id
                        if (idSeller != null) {
                            getDataProducts(idSeller)
                        } else {
                            Log.e("TAG", "null $idSeller")
                        }
                        Log.e("TAG", "")
                    }


                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@ListProductActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
*/

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun goToAddMode() {
        goTo()
    }

    private fun goToEditMode(value: String) {
        goTo(true, value)
    }

    private fun goTo(goEdit: Boolean = false, value: String = "") {
        val intent = Intent(this@ListProductActivity, AddUpdateProductActivity::class.java)
        if (goEdit) {
            intent.putExtra(AddUpdateProductActivity.EXTRA_ID_ANIMAL, value)
        }
        resultLauncher.launch(intent)
    }


}