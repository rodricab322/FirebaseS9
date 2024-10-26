package com.example.firebases9.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebases9.R
import com.example.firebases9.adapter.ProductAdapter
import com.example.firebases9.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore


class ProductFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)
        val db = FirebaseFirestore.getInstance()
        val rvProduct: RecyclerView = view.findViewById(R.id.rvProduct)
        var lstProducts : List<ProductModel>

        db.collection("products")
            .addSnapshotListener{ value, error ->
                if(error!=null){
                    Log.e("Firestore Error", error.message.toString())
                    return@addSnapshotListener
                }

                lstProducts = value!!.documents.map { document ->
                    ProductModel(
                        document["name"].toString(),
                        document["price"].toString(),
                        document["stock"].toString(),
                        document["imageUrl"].toString()
                    )

                }
                rvProduct.adapter = ProductAdapter(lstProducts)
                rvProduct.layoutManager = LinearLayoutManager(requireContext())

            }
        return view
    }

}