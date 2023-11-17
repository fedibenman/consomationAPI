package com.example.consomationapi


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() , MyAdapter.OnItemClickListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    var selectedPosition = -1
    var values: List<offre> = emptyList()

    lateinit var  intituleEditText: EditText ;
    lateinit var  specialiteEditText: EditText ;
    lateinit var  societeEditText: EditText ;
    lateinit var  nbpostsEditText: EditText ;
    lateinit var  paysEditText: EditText ;

    lateinit var seeUserbtn: Button
    lateinit var  deleteuserbtn: Button
    lateinit var addbtn: Button
    lateinit var updatebtn:Button

    lateinit var  codeUpdateEditText: EditText ;
    lateinit var  intituleUpdateEditText: EditText ;
    lateinit var  specialiteUpdateEditText: EditText ;
    lateinit var  societeUpdateEditText: EditText ;
    lateinit var  nbpostsUpdateEditText: EditText ;
    lateinit var  paysUpdateEditText: EditText ;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAll()




        seeUserbtn = findViewById(R.id.seeUserbtn)
        seeUserbtn.setOnClickListener {
        seeUser() ;  }
        deleteuserbtn = findViewById(R.id.deleteUserbtn) ;
        deleteuserbtn.setOnClickListener {
            deleteUser() }
        addbtn =findViewById(R.id.addUserbtn)
        addbtn.setOnClickListener {

                AddUserDialogue()
        }
        updatebtn = findViewById(R.id.Updatebtn)
        updatebtn.setOnClickListener {
            UpdateUserDialogue()
        }


    }


    override fun onItemClick(position: Int) {

        selectedPosition = position ;
        myAdapter.notifyItemChanged(position)
    }


 fun addUser()
    {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
        try{
            var intitule = intituleEditText.text.toString() ;
            var specialite = specialiteEditText.text.toString()
            var societe = societeEditText.text.toString()
            var nbpostes  = nbpostsEditText.text.toString().toInt()
            var pays = paysEditText.text.toString()
            val post_response = ApiClient.apiService.AddOffre(offre( code
                ,intitule
                , specialite
                , societe
                , nbpostes
                , pays))
            if (post_response.isSuccessful && post_response.body() != null) {
                Log.i("first_Post",post_response.body().toString())
            }else{

                Log.e("Error",post_response.message())
            }
        } catch (e: Exception) {
            Log.e("Error",e.message.toString())
        }
            getAll()
            myAdapter.notifyDataSetChanged()
        }

    }
    fun seeUser(){
        val intent = Intent(this, DetailsActivity::class.java)
intent.putExtra("code",values[selectedPosition].code)
        startActivity(intent)

    }

 fun AddUserDialogue() {
        val builder = AlertDialog.Builder(this)



        val view: View = layoutInflater.inflate(R.layout.dialog_form, null)
        builder.setView(view)

        // Initialize the EditText fields after inflating the dialog layout

     intituleEditText = view.findViewById(R.id.IntituleEditText)
     specialiteEditText = view.findViewById(R.id.specialiteEditText)
     societeEditText = view.findViewById(R.id.societeEditText)
     nbpostsEditText = view.findViewById(R.id.nbpostsEditText)
     paysEditText = view.findViewById(R.id.paysEditText)


        builder.setPositiveButton("Cancel") { dialogin: DialogInterface, i: Int -> }
        builder.setNegativeButton("Add") { dialogin: DialogInterface, i: Int -> addUser()


        }

        val dialog = builder.create()
        dialog.show()
    }

    fun deleteUser() {
       val scope = CoroutineScope(Dispatchers.Main)
       scope.launch {
           try {
               Log.i("success",values[selectedPosition].code.toString())
              val deleteRes = ApiClient.apiService.deleteOffre(values[selectedPosition].code!!)
              if(deleteRes.isSuccessful){
                  Log.i("success",deleteRes.toString())
              }
               else{
                   Log.e("wefweq",deleteRes.toString())
               }
           } catch (e: Exception) {
               Log.e("Error", e.message.toString())
           }
           getAll()
           myAdapter.notifyDataSetChanged()
       }
    }


    fun getAll() {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            try {
                val responseById = ApiClient.apiService.getOffres()
                if (responseById.isSuccessful && responseById.body() != null) {
                    Log.i("Success", responseById.body().toString())
                    values = responseById.body() ?: emptyList()
                    manager = LinearLayoutManager(this@MainActivity)

                    myAdapter = MyAdapter(values,this@MainActivity)
                    recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
                        layoutManager = manager
                        adapter = myAdapter
                    }
                    myAdapter.notifyDataSetChanged()
                } else {
                    Log.e("Error", responseById.message())
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }






 fun updateById(){
     val scope = CoroutineScope(Dispatchers.Main)
     scope.launch {
         try {
             var intitule = intituleUpdateEditText.text.toString();
             var specialite = specialiteUpdateEditText.text.toString()
             var societe = societeUpdateEditText.text.toString()
             var nbpostes = nbpostsUpdateEditText.text.toString().toInt()
             var pays = paysUpdateEditText.text.toString()
             val putResponse = ApiClient.apiService.UpdateOffre(
                 values[selectedPosition].code!!, offre(
                     null, intitule, specialite, societe, nbpostes, pays
                 )
             )
             if (putResponse.isSuccessful && putResponse.body() != null) {
                 Log.i("Success", putResponse.body().toString())
             } else {
                 Log.e("Error", putResponse.message())
             }
         } catch (e: Exception) {
             Log.e("Error", e.message.toString())
         }
     }
        getAll()


    }

  fun UpdateUserDialogue() {

        val builder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.dialog_update_form, null)

        builder.setView(view)

        // Initialize the EditText fields after inflating the dialog layout
        intituleUpdateEditText = view.findViewById(R.id.IntituleUpdateEditText)
        specialiteUpdateEditText = view.findViewById(R.id.specialiteUpdateEditText)
        societeUpdateEditText = view.findViewById(R.id.societeUpdateEditText)
        nbpostsUpdateEditText = view.findViewById(R.id.nbpostsUpdateEditText)
        paysUpdateEditText = view.findViewById(R.id.paysUpdateEditText)



        builder.setPositiveButton("Cancel") { dialogin: DialogInterface, i: Int -> }
        builder.setNegativeButton("Update") { dialogin: DialogInterface, i: Int -> updateById()
        }
      val dialog = builder.create()
      dialog.show()
    }
}