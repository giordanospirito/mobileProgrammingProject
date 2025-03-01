package com.example.mobileprogrammingproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileprogrammingproject.databinding.MainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: MainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //start play button
        binding.playButton.setOnClickListener {
            startActivity(Intent(applicationContext, PlayActivity::class.java))
        }
        //end play button

        //start rules button
        binding.rulesButton.setOnClickListener{
            val rulesFile = RuleDialog()
            rulesFile.show(supportFragmentManager, "rulesDialogTag")
        }
        //end rules button

        //start exit button
        binding.exitButton.setOnClickListener {
            finish()
        }
        //end exit button
    }
}

//TODO back button 1st press only dialog, then close app
//TODO back button default dialog (alert)
//TODO main "custom" dialog (cancel, submit, etc ...)
//TODO passing events to dialog's host
//TODO main xml graphic
//TODO hide sysUI
//TODO fullscreen
//TODO antialias edges of dialogs and buttons
//TODO create landscape variant
//TODO delete and modify gradle files
//TODO translating the app in "italian" and "english"
//TODO scroll view
//TODO check the version
//TODO daynight theme
//TODO check manifest for important lines (like app icon, app name, etc ...)
//TODO use a good theme and set a nice color pallet
//TODO create a splash screen
//TODO action bar color
//TODO animations (also in splash screen)
//TODO change app icon background color
//todo apertura scattosa (risolto dalle prestazioni del telefono)
//todo fare le cose di gradle che dice il prof, tipo cancellare alcuni file, e aggiungere i pacchetti necessari
//todo rimuovere i warning dai file gradle
//todo icona rotonda e quadrata
//todo pensare a rifinire i bottoni, cambiare i colori, pensare se usare un custom layout, fare una bozza delle schermate
//todo scoreboard
//todo sql lite
//todo aggiungere suoni di roll e soundtrack dell'app
//todo schermata regole ad immagine
//todo selezionare numero di giocatori, username
//todo schermata play/history/scoreboard

/* cosa è stato fatto fin'ora
*****UPDATE***** creato un tasto "RULES" che apre un dialog per mostrare sinteticamente le regole del gioco
* modificata l'icona dell'app
* aggiunto un folder icon con tutte le dimensioni all'interno
* creato un drawable splash_screen
* creato un tema dedicato alla splashscreen
* modificato il manifest aggiungendo una nuova activity e mettendola come launchactivity e settando il suo theme
* modificato il nuovo file activity.kt della splash screen usando un'intent
*
* workflow
* splash screen--> main menu--> ...
*
* */