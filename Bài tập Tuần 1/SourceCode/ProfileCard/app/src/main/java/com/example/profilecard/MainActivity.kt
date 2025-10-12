package com.example.profilecard

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.profilecard.databinding.ActivityMainBinding
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.os.Build
import android.content.pm.PackageManager
import com.bumptech.glide.Glide
import android.content.Intent
class MainActivity : AppCompatActivity() {

    private var isEditMode = false
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences("ProfileData", Context.MODE_PRIVATE)

        setupUI()
        loadProfileData()
        setupClickListeners()
        animateCardEntry()
    }
    private fun checkPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 trở lên
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                pickImageLauncher.launch("image/*")
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1001)
            }
        } else {
            // Android 6 đến 12
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                pickImageLauncher.launch("image/*")
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1001)
            }
        }
    }
    private fun setupUI() {
        // Set status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)

        // Initially hide edit form
        binding.editFormLayout.visibility = View.GONE
        binding.editFormLayout.alpha = 0f
    }
    private fun showInitials(name: String) {
        val initials = getInitials(name)
        binding.avatarText.text = initials
        binding.avatarImage.visibility = View.GONE
        binding.avatarTextLayout.visibility = View.VISIBLE
    }
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                // Giữ quyền đọc lâu dài (persistable URI)
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) { }

            // Load ảnh bằng Glide
            Glide.with(this)
                .load(uri)
                .circleCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.avatarImage)

            // Toggle hiển thị
            binding.avatarImage.visibility = View.VISIBLE
            binding.avatarTextLayout.visibility = View.GONE

            // Lưu URI vào SharedPreferences
            sharedPrefs.edit().apply {
                putString("avatarUri", uri.toString())
                apply()
            }
        } else {
            // Nếu user hủy chọn ảnh thì fallback về chữ JS
            val savedName = sharedPrefs.getString("name", "Johan Smith") ?: "Johan Smith"
            showInitials(savedName)
        }
    }
    private fun loadProfileData() {
        val savedName = sharedPrefs.getString("name", "Johan Smith") ?: "Johan Smith"
        val savedLocation = sharedPrefs.getString("location", "California, USA") ?: "California, USA"
        val avatarUri = sharedPrefs.getString("avatarUri", null)

        updateProfileDisplay(savedName, savedLocation)

        if (avatarUri != null) {
            try {
                Glide.with(this)
                    .load(Uri.parse(avatarUri))
                    .circleCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.avatarImage)

                binding.avatarImage.visibility = View.VISIBLE
                binding.avatarTextLayout.visibility = View.GONE
            } catch (e: Exception) {
                // Nếu load ảnh lỗi thì fallback về chữ JS
                showInitials(savedName)
            }
        } else {
            // Nếu chưa có ảnh thì fallback về chữ JS
            showInitials(savedName)
        }
    }


    private fun saveProfileData(name: String, location: String) {
        sharedPrefs.edit().apply {
            putString("name", name)
            putString("location", location)
            apply()
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            animateBackButton()
            finish()
        }

        binding.editButton.setOnClickListener {
            toggleEditMode()
        }

        binding.saveButton.setOnClickListener {
            saveProfile()
        }

        binding.cancelButton.setOnClickListener {
            cancelEdit()
        }

        // Add floating animation to profile card
        binding.profileCard.setOnClickListener {
            animateCardClick()
        }
        binding.avatarImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        binding.avatarText.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun toggleEditMode() {
        if (!isEditMode) {
            // Switch to edit mode
            binding.nameEditText.setText(binding.nameText.text)
            binding.locationEditText.setText(binding.locationText.text)

            animateToEditMode()
        }
    }

    private fun saveProfile() {
        val newName = binding.nameEditText.text.toString().trim()
        val newLocation = binding.locationEditText.text.toString().trim()

        if (newName.isNotEmpty() && newLocation.isNotEmpty()) {
            updateProfileDisplay(newName, newLocation)
            saveProfileData(newName, newLocation)
            animateToDisplayMode()

            Toast.makeText(this, "Đã lưu thông tin thành công!", Toast.LENGTH_SHORT).show()
            animateSuccessfulSave()
        } else {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelEdit() {
        animateToDisplayMode()
    }

    private fun updateProfileDisplay(name: String, location: String) {
        binding.nameText.text = name
        binding.locationText.text = location

        // Update avatar initials
        val initials = getInitials(name)
        binding.avatarText.text = initials
    }

    private fun getInitials(name: String): String {
        val words = name.split(" ")
        return words.take(2).map { it.firstOrNull()?.uppercaseChar() ?: "" }.joinToString("")
    }

    private fun animateToEditMode() {
        isEditMode = true

        val fadeOutDisplay = ObjectAnimator.ofFloat(binding.profileDisplayLayout, "alpha", 1f, 0f)
        val fadeOutButton = ObjectAnimator.ofFloat(binding.editProfileButton, "alpha", 1f, 0f)

        fadeOutDisplay.duration = 200
        fadeOutButton.duration = 200

        AnimatorSet().apply {
            playTogether(fadeOutDisplay, fadeOutButton)
            start()
        }

        // Show edit form after fade out
        fadeOutDisplay.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                binding.profileDisplayLayout.visibility = View.GONE
                binding.editProfileButton.visibility = View.GONE
                binding.editFormLayout.visibility = View.VISIBLE

                val fadeInForm = ObjectAnimator.ofFloat(binding.editFormLayout, "alpha", 0f, 1f)
                fadeInForm.duration = 300
                fadeInForm.start()
            }
        })
    }

    private fun animateToDisplayMode() {
        isEditMode = false

        val fadeOutForm = ObjectAnimator.ofFloat(binding.editFormLayout, "alpha", 1f, 0f)
        fadeOutForm.duration = 200
        fadeOutForm.start()

        fadeOutForm.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                binding.editFormLayout.visibility = View.GONE
                binding.profileDisplayLayout.visibility = View.VISIBLE


                val fadeInDisplay = ObjectAnimator.ofFloat(binding.profileDisplayLayout, "alpha", 0f, 1f)
                val fadeInButton = ObjectAnimator.ofFloat(binding.editProfileButton, "alpha", 0f, 1f)

                fadeInDisplay.duration = 300
                fadeInButton.duration = 300

                AnimatorSet().apply {
                    playTogether(fadeInDisplay, fadeInButton)
                    start()
                }
            }
        })
    }

    private fun animateCardEntry() {
        binding.profileCard.scaleX = 0.8f
        binding.profileCard.scaleY = 0.8f
        binding.profileCard.alpha = 0f

        val scaleXAnimator = ObjectAnimator.ofFloat(binding.profileCard, "scaleX", 0.8f, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(binding.profileCard, "scaleY", 0.8f, 1f)
        val alphaAnimator = ObjectAnimator.ofFloat(binding.profileCard, "alpha", 0f, 1f)

        AnimatorSet().apply {
            playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator)
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun animateCardClick() {
        val scaleX = ObjectAnimator.ofFloat(binding.profileCard, "scaleX", 1f, 1.05f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.profileCard, "scaleY", 1f, 1.05f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 150
            start()
        }
    }

    private fun animateBackButton() {
        val rotation = ObjectAnimator.ofFloat(binding.backButton, "rotation", 0f, -180f, 0f)
        rotation.duration = 300
        rotation.start()
    }

    private fun animateSuccessfulSave() {
        val scaleX = ObjectAnimator.ofFloat(binding.profileCard, "scaleX", 1f, 1.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.profileCard, "scaleY", 1f, 1.1f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

}