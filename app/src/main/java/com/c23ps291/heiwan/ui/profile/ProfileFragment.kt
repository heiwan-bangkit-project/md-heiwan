package com.c23ps291.heiwan.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), OnClickListener {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoToEdit.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_go_to_edit -> {
                startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
            }

            R.id.btn_logout -> {}
        }
    }


}