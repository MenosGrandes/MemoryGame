package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var karty: List<Card>
    private var poprzednioOkdryta: Int? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ids = intArrayOf(
            R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8
        );
        var images = mutableListOf(
            R.drawable.ic_axe_svgrepo_com,
            R.drawable.ic_corn_svgrepo_com,
            R.drawable.ic_cow_svgrepo_com,
            R.drawable.ic_watering_can_svgrepo_com
        )
        images.addAll(images);
        images.shuffle();

        karty = images.mapIndexed { index, i -> Card(false, false, i) };
        buttons = ids.map { findViewById<ImageButton>(it) }
        //buttons = ids.indices.map { index -> ImageButton(findViewById(index) };

        buttons.forEachIndexed { index, imageButton ->

            imageButton.setOnClickListener {
                updateCards(index);
                updateView();

            }
        }

    }


    private fun updateView() {

        karty.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.czyWidoczna) {
                button.setImageResource(card.id);
            } else {
                button.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
            }

        }
    }

    private fun updateCards(cardIndex: Int) {
        var currentCard = karty[cardIndex];

        if (currentCard.czyWidoczna) {
            Toast.makeText(this, "Juz ja widac!", Toast.LENGTH_SHORT).show();
            return;
        }
        //1 karta nie odsloniona, czyli jest 0 odslonietych kart! pozaycja wejsciowa do gry
        if (poprzednioOkdryta == null) {

            karty.map {
                if (!it.czyOdgadnieta) {
                    it.czyWidoczna = false;
                }
            }

            poprzednioOkdryta = cardIndex;
        } else {
            sprawdzCzySparowane(poprzednioOkdryta!!, cardIndex)
            poprzednioOkdryta = null
        }

        karty[cardIndex].czyWidoczna = !karty[cardIndex].czyWidoczna

    }

    private fun sprawdzCzySparowane(poprzednioOkdryta: Int, terazOkdryta: Int) {
        if (karty[poprzednioOkdryta].czyTakieSame(karty[terazOkdryta])) {
            Toast.makeText(this, "Takie same!!", Toast.LENGTH_SHORT).show();
            karty[poprzednioOkdryta].czyOdgadnieta = true;
            karty[terazOkdryta].czyOdgadnieta = true;
        }
    }
}