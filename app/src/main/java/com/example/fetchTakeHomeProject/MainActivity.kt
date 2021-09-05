package com.example.fetchTakeHomeProject

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest

private const val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var urlData : MutableList<DataObject>
    private val requestQueue = VolleySingleton.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
         progressBar = findViewById(R.id.indeterminateBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        urlData = mutableListOf()
       loadUrlData()


    }

    /*
function makes url request
On response, function verifies and sorts input, then loads the data into the recycler adapter
on error, function displays toast to user
*/
    private fun loadUrlData()
    {
        //Make request for Json Array from url specified
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for(i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    // for each json object, verify name field is not null and empty
                    if (jsonObj.getString("name")!="null" && jsonObj.getString("name")!="") {
                        // create dataObject using jsonObject variables, then insert into url data
                        val toInsert = DataObject(
                            jsonObj.getInt("id"),
                            jsonObj.getInt("listId"),
                            jsonObj.getString("name")
                        )
                        urlData.add(toInsert)
                    }
                }
                // loading finished
                progressBar.visibility = View.GONE
                //sort dataObjects from urlData by descending listId, then name.
                val sortedList = urlData.sortedWith(compareBy<DataObject> {it.listId}.thenBy { it.name })

                recyclerView.adapter = DataRecyclerAdapter(sortedList)

            },
            {error ->
                Toast.makeText(this,"Error loading data", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.addToRequestQueue(request)

    }
}