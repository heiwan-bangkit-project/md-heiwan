package com.c23ps291.heiwan.ui.seller.product.crudproduct

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.data.model.DeleteAnimal
import com.c23ps291.heiwan.data.model.Seller
import com.c23ps291.heiwan.databinding.ActivityAddUpdateProductBinding
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory
import com.c23ps291.heiwan.utils.reduceFileImage
import com.c23ps291.heiwan.utils.uriToFile
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID


class AddUpdateProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateProductBinding

    private lateinit var auth: FirebaseAuth

    private var isSeller = false
    private var photoFile: File? = null
    private var isEdit = false
    private lateinit var idAnimal: String
    private lateinit var idUser: String


    private val addUpdateProductViewModel: AddUpdateProductViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        idAnimal = intent.getStringExtra(EXTRA_ID_ANIMAL).orEmpty()
        idUser = auth.currentUser?.uid.orEmpty()

        isEdit = idAnimal.isNotEmpty()

        val btnUploadChangeText: String
        val btnAddUpdateText: String
        if (isEdit) {
            btnUploadChangeText = resources.getString(R.string.btn_text_change_image)
            btnAddUpdateText = resources.getString(R.string.text_btn_update)
            editMode()
        } else {
            btnUploadChangeText = resources.getString(R.string.btn_text_upload_image)
            btnAddUpdateText = resources.getString(R.string.text_btn_add)
            addMode()
        }


        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }

            btnUploadChangePhoto.text = btnUploadChangeText
            btnAddUpdate.text = btnAddUpdateText

            btnUploadChangePhoto.setOnClickListener {
                startGallery()
            }
        }


    }

    private fun editMode() {
        getDataAnimal(idAnimal)
        binding.apply {
            toolbar.title = getString(R.string.edit_product)
            toolbar.inflateMenu(R.menu.delete_menu)

            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_delete) {
                    showAlertDialog()
                }
                false
            }

            btnAddUpdate.setOnClickListener {
                addUpdate()

            }


        }


    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_delete))
            .setTitle(resources.getString(R.string.text_menu_delete))
            .setMessage(resources.getString(R.string.text_dialog_msg_delete))
            .setNegativeButton(resources.getString(R.string.text_no)) { dialog, which ->
                return@setNegativeButton
            }
            .setPositiveButton(resources.getString(R.string.text_yes)) { dialog, which ->
                setDeleteDataAnimal(idAnimal.toInt())

            }
            .show()
    }

    private fun updateAnimal(
        id: String,
        name: String,
        description: String,
        price: String,
        imageMultipart: MultipartBody.Part,
    ) {
        addUpdateProductViewModel.setUpdateAnimal(
            id.toRequestBody("text/plain".toMediaType()),
            name.toRequestBody("text/plain".toMediaType()),
            description.toRequestBody("text/plain".toMediaType()),
            price.toRequestBody("text/plain".toMediaType()),
            imageMultipart,
            idUser.toRequestBody("text/plain".toMediaType())
        ).observe(this@AddUpdateProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val isSuccess = it.data?.data?.affectedRows ?: 0
                    if (isSuccess > 0) {
                        val resultIntent = Intent()
                        resultIntent.putExtra(
                            RESULT_UPDATE_MESSAGE,
                            getString(R.string.product_success_update)
                        )
                        setResult(RESULT_UPDATE, resultIntent)
                        finish()
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Snackbar.make(
                        binding.root as ViewGroup,
                        getString(R.string.product_failed_update),
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }
    }

    private fun setDeleteDataAnimal(id: Int) {
        addUpdateProductViewModel.setDeleteAnimal(DeleteAnimal(id))
            .observe(this@AddUpdateProductActivity) {
                when (it) {
                    is Resource.Loading -> showLoadingState(true)
                    is Resource.Success -> {
                        showLoadingState(false)
                        val isSuccess = it.data?.data?.affectedRows ?: 0
                        if (isSuccess > 0) {
                            val resultIntent = Intent()
                            resultIntent.putExtra(
                                RESULT_DELETE_MESSAGE,
                                getString(R.string.product_success_delete)
                            )
                            setResult(RESULT_DELETE, resultIntent)
                            finish()
                        }
                    }

                    is Resource.Error -> {
                        showLoadingState(false)
                        Snackbar.make(
                            binding.root as ViewGroup,
                            getString(R.string.product_failed_delete),
                            Snackbar.LENGTH_LONG
                        ).show()

                    }
                }
            }
    }

    private fun getDataAnimal(id: String) {
        addUpdateProductViewModel.getAnimal(id).observe(this@AddUpdateProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val animal = it.data?.animal?.get(0)
                    if (animal != null) {
                        populateData(animal)
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@AddUpdateProductActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun populateData(data: Animal) {

        binding.apply {
            edtAnimalName.setText(data.name)
            edtAnimalDescription.setText(data.description)
            edtPrice.setText(data.price)
            ivAnimal.load(data.image) {
                crossfade(true)
                placeholder(R.drawable.ic_image_pet)
                error(R.drawable.ic_image_pet)
            }
            val loader = ImageLoader(this@AddUpdateProductActivity)
            val req = ImageRequest.Builder(this@AddUpdateProductActivity)
                .data(data.image)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    photoFile = convertImageViewToFile(bitmap)
                }
                .build()

            val disposable = loader.enqueue(req)


        }

    }

    private fun addMode() {
        checkSeller(idUser)
        binding.apply {
            toolbar.title = getString(R.string.add_product)
            btnAddUpdate.setOnClickListener {
                addUpdate(true)

            }
        }
    }

    private fun checkSeller(id: String) {
        addUpdateProductViewModel.getSeller(id).observe(this@AddUpdateProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val sizeSeller = it.data?.seller?.size ?: 0
                    if (sizeSeller > 0) {
                        val idSeller = it.data?.seller?.get(0)?.id
                        val va = it.data?.seller?.get(0)?.id
                        isSeller = idSeller != null
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Snackbar.make(
                        binding.root as ViewGroup,
                        getString(R.string.failed_to_load_user_data),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun addUpdate(isAdd: Boolean = false) {
        binding.apply {

            val name = edtAnimalName.text.toString()
            val description = edtAnimalDescription.text.toString()
            val price = edtPrice.text.toString()


            var allInputAreFilled = false

            if (name.isEmpty()) {
                checkInputIsEmpty(
                    inputLayoutAnimalName,
                    getString(R.string.text_label_name).lowercase()
                )
            } else {
                checkInputIsEmpty(inputLayoutAnimalName)
                if (description.isEmpty()) {
                    checkInputIsEmpty(
                        inputLayoutAnimalDescription,
                        getString(R.string.text_label_description).lowercase()
                    )
                } else {
                    checkInputIsEmpty(inputLayoutAnimalDescription)
                    if (price.isEmpty()) {
                        checkInputIsEmpty(
                            inputLayoutPrice,
                            getString(R.string.text_label_price).lowercase()
                        )
                    } else {
                        checkInputIsEmpty(inputLayoutPrice)
                        allInputAreFilled = true
                    }
                }
            }

            if (allInputAreFilled) {

                if (photoFile != null) {
                    val file = reduceFileImage(photoFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestImageFile
                    )

                    if (name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()) {
                        if (isAdd) {
                            if (isSeller)

                                addAnimal(name, description, price, imageMultipart)
                            else
                                addSeller(name, description, price, imageMultipart)
                        } else {
                            updateAnimal(idAnimal, name, description, price, imageMultipart)
                        }

                    }

                } else {
                    Snackbar.make(
                        binding.root as ViewGroup,
                        getString(R.string.empty_image),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }


        }

    }

    private fun addSeller(
        name: String,
        description: String,
        price: String,
        imageMultipart: MultipartBody.Part,
    ) {
        val currentUser = auth.currentUser
        val nameUser = currentUser?.displayName ?: "-"
        val email = currentUser?.email ?: "-"
        val phone = currentUser?.phoneNumber ?: "-"

        val seller = Seller(idUser, nameUser, email, phone)
        addUpdateProductViewModel.setAddSeller(seller)
            .observe(this@AddUpdateProductActivity) {
                when (it) {
                    is Resource.Loading -> showLoadingState(true)
                    is Resource.Success -> {
                        showLoadingState(false)
                        val isSuccess = it.data?.data?.affectedRows ?: 0
                        if (isSuccess > 0) {
                            addAnimal(name, description, price, imageMultipart)
                        }
                    }

                    is Resource.Error -> {
                        showLoadingState(false)
                        Snackbar.make(
                            binding.root as ViewGroup,
                            getString(R.string.product_failed_add),
                            Snackbar.LENGTH_LONG
                        ).show()

                    }
                }
            }
        /*
        addUpdateProductViewModel.setAddSeller(
            idUser.toRequestBody("text/plain".toMediaType()),
            nameUser.toRequestBody("text/plain".toMediaType()),
            email.toRequestBody("text/plain".toMediaType()),
            phone.toRequestBody("text/plain".toMediaType())).observe(this@AddUpdateProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val isSuccess = it.data?.data?.affectedRows ?: 0
                    if (isSuccess > 0) {
                        addAnimal(name, description, price, imageMultipart)
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Snackbar.make(
                        binding.root as ViewGroup,
                        getString(R.string.product_failed_add),
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }
        */
    }


    private fun addAnimal(
        name: String,
        description: String,
        price: String,
        imageMultipart: MultipartBody.Part,
    ) {
        addUpdateProductViewModel.setAddAnimal(
            name.toRequestBody("text/plain".toMediaType()),
            description.toRequestBody("text/plain".toMediaType()),
            price.toRequestBody("text/plain".toMediaType()),
            imageMultipart,
            idUser.toRequestBody("text/plain".toMediaType())
        ).observe(this@AddUpdateProductActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val isSuccess = it.data?.data?.affectedRows ?: 0
                    if (isSuccess > 0) {
                        val resultIntent = Intent()
                        resultIntent.putExtra(
                            RESULT_ADD_MESSAGE,
                            getString(R.string.product_success_add)
                        )
                        setResult(RESULT_ADD, resultIntent)
                        finish()
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Snackbar.make(
                        binding.root as ViewGroup,
                        getString(R.string.product_failed_add),
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }
    }

    private fun checkInputIsEmpty(
        inputView: TextInputLayout,
        message: String? = null,
    ) {
        if (message != null) {
            inputView.isErrorEnabled = true
            inputView.error = getString(R.string.empty_input, message)
        } else {
            inputView.error = null
            inputView.isErrorEnabled = false
        }

    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_a_pic))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            photoFile = myFile
            binding.ivAnimal.setImageURI(selectedImg)
        }
    }

    private fun convertImageViewToFile(bitmap: Bitmap): File {
        val wrapper = ContextWrapper(this)
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream)
        stream.flush()
        stream.close()
        return file
    }


    companion object {
        const val TAG = "AddUpdateProduct"
        private const val REQUEST_CODE_PERMISSIONS = 10


        const val EXTRA_ID_ANIMAL = "extra_id_animal"

        const val RESULT_ADD = 101
        const val RESULT_ADD_MESSAGE = "result_add_message"

        const val RESULT_UPDATE = 201
        const val RESULT_UPDATE_MESSAGE = "result_update_message"


        const val RESULT_DELETE = 301
        const val RESULT_DELETE_MESSAGE = "result_delete_message"
    }


}