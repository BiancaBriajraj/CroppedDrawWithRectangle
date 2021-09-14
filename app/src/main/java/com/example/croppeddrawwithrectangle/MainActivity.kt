package com.example.croppeddrawwithrectangle

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = ContextCompat.getDrawable(this, R.drawable.laugh)?.toBitmap()
        bitmap?.apply {
            originalImageView.setImageBitmap(this)
            dimensionsOrginalImageTextView.text = " Width : $width , Height:$height "

            floatingActionButton.setOnClickListener {
                val newWidth = newWidthEditTextView.text.toString().toInt()
                val newHeight = newHeightEditText.text.toString().toInt()
                if (newWidthEditTextView.text.isEmpty() || newHeightEditText.text.isEmpty()) {
                    dimensionsCroppedImageTextView.text = "Dimensions error"
                } else {
                    if (newHeight > height || newWidth > width) {
                        dimensionsCroppedImageTextView.text = "Dimensions exceed original image"
                    } else {
                        originalImageView.setImageBitmap(drawRectangle(newWidth, newHeight))
                    }
                }
            }

            cropButton.setOnClickListener {
                val newWidth = newWidthEditTextView.text.toString().toInt()
                val newHeight = newHeightEditText.text.toString().toInt()
                if (newWidthEditTextView.text.isEmpty() || newHeightEditText.text.isEmpty()) {
                    dimensionsCroppedImageTextView.text = "Dimensions error"
                } else {
                    if (newHeight > height || newWidth > width) {
                        dimensionsCroppedImageTextView.text = "Dimensions exceed original image"
                    } else {
                        bitmap.apply {
                            cropRectangle(
                                newWidth,
                                newHeight
                            )?.let {
                                croppedImageView.setImageBitmap(it)
                                dimensionsCroppedImageTextView.text = "Width: $newWidth , height $newHeight"
                            }
                        }
                    }
                }
            }
        }
    }


    private fun Bitmap.cropRectangle(newWidth: Int = this.width, newHeight: Int = this.height): Bitmap? {
        return try {
            Bitmap.createBitmap(this, 0, 0, newWidth, newHeight)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun Bitmap.drawRectangle(width: Int = this.width, height: Int = this.height): Bitmap? {
        val bitmap = copy(config, true)
        val canvas = Canvas(bitmap)

        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), this)
        }
        return bitmap
    }
}

