package com.pg.scoalview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var actualString = ""
    var array = HashMap<String, Boolean>()
    var boldString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boldString = "Hello <b>Purva</b> how <b>Fine</b> Purva"

        array["Purva"] = true


        /*var split = "Hello <b>Purva</b> How <b>Are<b/> you".split(" ")
        split
        var array = ArrayList<String>()
        for (data in split) {
            if (!data.contains("Pur")) {
                array.add(data)
            }
        }
        array*/


        textView.addTextChangedListener(object : TextWatcher {
            private var prevLength = 0
            private var isBackPressed = false
            override fun afterTextChanged(charSequence: Editable?) {

                isBackPressed = prevLength > charSequence.toString().length

                if (isBackPressed) {
                    Toast.makeText(getApplicationContext(), "Back press keyboard $actualString", Toast.LENGTH_LONG).show();

                    val spaceArray = textView.text.toString().split(" ")
                    for (dataFromMentionedArray in array.keys) {
                        var isFound = false
                        for (data in spaceArray) {
                            if (dataFromMentionedArray == data) {
                                isFound = true
                                break
                            }
                        }
                        if (!isFound) {
                            array[dataFromMentionedArray] = false
                        }
                    }

                    var map = array.filterValues { it == false }
                    if (map.isNotEmpty()) {
                        var split = boldString.split(" ")
                        var array = ArrayList<String>()
                        for (notMatched in map.keys) {
                            for (data in split) {
                                if (!data.contains(notMatched)) {
                                    array.add(data)
                                }
                            }
                        }

                        Log.d("Merge ", "Merge ${array.toString()}")
                        var a = array.joinToString(" ")


                    }


                }

                prevLength = charSequence.toString().length
            }

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                prevLength = charSequence.toString().length
                actualString = textView.text.toString()

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

    }
}
