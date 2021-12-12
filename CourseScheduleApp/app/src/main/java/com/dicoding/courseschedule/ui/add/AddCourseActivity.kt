package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
	private lateinit var viewModel: AddCourseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_course)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = getString(R.string.add_course)

		val factory = ListViewModelFactory.createFactory(this)
		viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_add, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_insert -> {
				val edtCourse = findViewById<TextView>(R.id.add_ed_course)
				val edtLecturer = findViewById<TextView>(R.id.add_ed_lecturer)
				val edtNote = findViewById<TextView>(R.id.add_ed_note)
				val spinner = findViewById<Spinner>(R.id.spinner2)

				val course = edtCourse.text.toString().trim()
				val lecturer = edtLecturer.text.toString().trim()
				val note = edtNote.text.toString().trim()
				val day = spinner.selectedItemPosition
				val startTime = findViewById<TextView>(R.id.add_tv_start_time).text.toString()
				val endTime = findViewById<TextView>(R.id.add_tv_end_time).text.toString()

				if (course.isNotEmpty() && lecturer.isNotEmpty() && note.isNotEmpty()) {
					viewModel.insertCourse(
						courseName = course,
						day = day,
						startTime = startTime,
						endTime = endTime,
						lecturer = lecturer,
						note = note
					)
					finish()
				} else {
					Toast.makeText(this, getString(R.string.empty_list_message), Toast.LENGTH_SHORT)
						.show()
				}

				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	fun showTimePickerStartTime(view: View) {
		val dialogFragment = TimePickerFragment()
		dialogFragment.show(supportFragmentManager, "startPicker")
	}

	fun showTimePickerEndTime(view: View) {
		val dialogFragment = TimePickerFragment()
		dialogFragment.show(supportFragmentManager, "endPicker")
	}

	override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.HOUR_OF_DAY, hour)
		calendar.set(Calendar.MINUTE, minute)
		val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

		if (tag == "startPicker") {
			findViewById<TextView>(R.id.add_tv_start_time).text = dateFormat.format(calendar.time)
		} else {
			findViewById<TextView>(R.id.add_tv_end_time).text = dateFormat.format(calendar.time)
		}
	}
}