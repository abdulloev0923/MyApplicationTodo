package com.example.myapplicationtodo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationtodo.R
import com.example.myapplicationtodo.data.Todo
import com.example.myapplicationtodo.data.TodoDatabase
import com.example.myapplicationtodo.databinding.ActivityAddBinding
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var spinner: Spinner
    lateinit var priority:Array<String>
    private var priority_text:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        priority = arrayOf("High priority", "Medium priority", "Low priority")
        spinner = binding.spinner
        val my_adapters_spiiner = ArrayAdapter(this, android.R.layout.simple_spinner_item, priority)

        my_adapters_spiiner.setDropDownViewResource(

            android.R.layout.simple_dropdown_item_1line
        )

        spinner!!.setAdapter(my_adapters_spiiner)

        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority_text = (p0?.getItemAtPosition(p2) as CharSequence?).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

//        priority = arrayOf("High priority", "Medium priority", "Low priority")
//        val adapter = ArrayAdapter(this@AddActivity, R.layout.todo_list,  priority)
//        spinner = binding.spinner
//        adapter.setDropDownViewResource(R.layout.todo_list)
//        spinner!!.setAdapter (adapter)

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                priority_text = (p0?.getItemAtPosition(p2) as CharSequence?).toString()
//                binding.text.text = priority_text
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }


    }



    private fun addUser() {


        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        lifecycleScope.launch {
            val todo = Todo(
                title = title, description = description, priority = priority_text.toString()
            )
//            Toast.makeText(this@AddActivity, "$todo", Toast.LENGTH_SHORT).show()
            TodoDatabase(this@AddActivity).getTodoDao().addTodo(todo)
        }

        val intent = Intent(this@AddActivity, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Successful added", Toast.LENGTH_SHORT).show()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_action ->{
                addUser()
            }
            R.id.back ->{
                val intent = Intent(this@AddActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
