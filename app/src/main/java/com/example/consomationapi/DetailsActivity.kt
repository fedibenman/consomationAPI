package com.example.consomationapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
lateinit var Offre: offre
var code:Int = 0
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val scope = CoroutineScope(Dispatchers.Main)
        val intent = intent
        if (intent != null) {
            code = intent.getIntExtra("code" , 0)
        }


        scope.launch {
            try {

                val responseById = ApiClient.apiService.getOffreById(code)
                if (responseById.isSuccessful && responseById.body() != null) {
                    Log.i("Success", responseById.body().toString())
                    var code = responseById.body()!!.code
                    var intitule = responseById.body()!!.intitule
                    var societe = responseById.body()!!.societe
                    var specialite = responseById.body()!!.specialite
                    var nbspots=responseById.body()!!.nbposts
                    var pays=responseById.body()!!.pays


                    var code_affichage = findViewById<TextView>(R.id.code_affichage)
                    var intitule_affichage = findViewById<TextView>(R.id.intitule_affichage)
                    var societe_affichage = findViewById<TextView>(R.id.societe_affichage)
                    var specialite_affichage = findViewById<TextView>(R.id.specialite_affichage)
                    var nbspots_affichage = findViewById<TextView>(R.id.nbspots_affichage)
                    var pays_affichage = findViewById<TextView>(R.id.pays_affichage)

                    code_affichage.text = code.toString()
                    intitule_affichage.text = intitule.toString()
                    societe_affichage.text = societe.toString()
                    specialite_affichage.text = specialite.toString()
                    nbspots_affichage.text = nbspots.toString()
                    pays_affichage.text = pays.toString()


                } else {
                    Log.e("Error", responseById.message())
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }
}